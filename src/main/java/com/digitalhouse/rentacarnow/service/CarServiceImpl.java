package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Category;
import com.digitalhouse.rentacarnow.entity.Feature;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.CategoryRepository;
import com.digitalhouse.rentacarnow.repository.FeatureRepository;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final FeatureRepository featureRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public CarServiceImpl(CarRepository carRepository, UserRepository userRepository,
                          FeatureRepository featureRepository, CategoryRepository categoryRepository, FileStorageService fileStorageService) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.featureRepository = featureRepository;
        this.categoryRepository = categoryRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Page<Car> findAll(Boolean available, String category, String q, String feature, Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return carRepository.search(q, category, available, feature, pageable);
        }
        if (feature != null && !feature.isBlank()) {
            return carRepository.findByFeatureName(feature, pageable);
        }
        if (available == null && category == null) {
            return carRepository.findAll(pageable);
        }
        if (available == null) {
            return carRepository.findByCategory_Name(category, pageable);
        }
        if (category == null) {
            return carRepository.findByAvailable(available, pageable);
        }
        return carRepository.findByCategory_NameAndAvailable(category, available, pageable);
    }

    @Override
    public Page<Car> findByOwner(Long ownerId, Pageable pageable) {
        return carRepository.findByOwner_Id(ownerId, pageable);
    }

    @Override
    public List<Car> findRandom(Integer limit, Boolean available, String category, String q, String feature) {
        int size = limit == null ? 10 : Math.min(limit, 50);
        if (size <= 0) {
            return List.of();
        }
        if (q != null && !q.isBlank()) {
            return carRepository.findRandomWithSearch(size, available, category, q, feature);
        }
        if (feature != null && !feature.isBlank()) {
            return carRepository.findRandomByFeature(size, available, category, feature);
        }
        return carRepository.findRandom(size, available, category);
    }

    @Override
    public Car findByPlate(String plate) {
        return carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
    }

    @Override
    public Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay,
                         Double pricePerHour, Boolean available, Long categoryId,
                         Set<Long> featureIds, Long ownerId, User requester) {
        if (carRepository.findByPlate(plate).isPresent()) {
            throw new ConflictException("Ya existe un auto con la patente: " + plate);
        }
        Category category = resolveCategory(categoryId);
        User owner = resolveOwner(ownerId, requester);
        Car car = new Car();
        car.setPlate(plate);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        car.setCategory(category);
        car.setOwner(owner);
        car.setFeatures(resolveFeatures(featureIds));
        return carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id, User requester) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        assertCanManage(car, requester);
        for (String path : car.getImagePaths()) {
            fileStorageService.deleteFile(path);
        }
        carRepository.deleteById(id);
    }

    @Override
    public Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay,
                         Double pricePerHour, Boolean available, Long categoryId, Set<Long> featureIds,
                         User requester) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        assertCanManage(car, requester);
        Category category = resolveCategory(categoryId);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        car.setCategory(category);
        car.setFeatures(resolveFeatures(featureIds));
        return carRepository.save(car);
    }

    @Override
    public Car uploadImage(String plate, MultipartFile file, User requester) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        assertCanManage(car, requester);
        String filePath = fileStorageService.saveFile(file);
        car.getImagePaths().add(filePath);
        return carRepository.save(car);
    }

    @Override
    public void deleteImage(String plate, String imagePath, User requester) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        assertCanManage(car, requester);
        car.getImagePaths().remove(imagePath);
        fileStorageService.deleteFile(imagePath);
        carRepository.save(car);
    }

    private User resolveOwner(Long ownerId, User requester) {
        if ("OWNER".equals(requester.getRole())) {
            return requester;
        }
        if ("EMPLOYEE".equals(requester.getRole())) {
            if (requester.getOwner() == null) {
                throw new AccessDeniedException("Tu cuenta de EMPLOYEE no tiene OWNER asignado.");
            }
            return requester.getOwner();
        }
        if ("ADMIN".equals(requester.getRole())) {
            if (ownerId == null) {
                throw new IllegalArgumentException("Un ADMIN debe indicar el ownerId del auto.");
            }
            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));
            if (!"OWNER".equals(owner.getRole())) {
                throw new AccessDeniedException("El ownerId debe corresponder a un usuario OWNER.");
            }
            return owner;
        }
        throw new AccessDeniedException("No tenés permiso para crear autos.");
    }

    private void assertCanManage(Car car, User requester) {
        if ("ADMIN".equals(requester.getRole())) {
            return;
        }
        Long ownerId = car.getOwner() != null ? car.getOwner().getId() : null;
        if ("OWNER".equals(requester.getRole()) && ownerId != null && ownerId.equals(requester.getId())) {
            return;
        }
        if ("EMPLOYEE".equals(requester.getRole())
                && requester.getOwner() != null
                && ownerId != null
                && ownerId.equals(requester.getOwner().getId())) {
            return;
        }
        throw new AccessDeniedException("No tenés permiso para gestionar este auto.");
    }

    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ConflictException("La categoría con id " + categoryId + " no existe. Creala primero en /admin/categories."));
    }

    private Set<Feature> resolveFeatures(Set<Long> featureIds) {
        if (featureIds == null || featureIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(featureRepository.findAllById(featureIds));
    }
}
