package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Category;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.CategoryRepository;
import com.digitalhouse.rentacarnow.repository.FavoriteRepository;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;
import com.digitalhouse.rentacarnow.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReservationRepository reservationRepository;
    private final FileStorageService fileStorageService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CarRepository carRepository,
                               FavoriteRepository favoriteRepository, ReservationRepository reservationRepository,
                               FileStorageService fileStorageService) {
        this.categoryRepository = categoryRepository;
        this.carRepository = carRepository;
        this.favoriteRepository = favoriteRepository;
        this.reservationRepository = reservationRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(String name, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + name);
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, String name, String description) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (!category.getName().equals(name) && categoryRepository.existsByName(name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + name);
        }
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        List<Car> cars = carRepository.findByCategory_Id(id);
        for (Car car : cars) {
            favoriteRepository.deleteByCar_Id(car.getId());
            reservationRepository.deleteByCar_Id(car.getId());
            for (String path : car.getImagePaths()) {
                fileStorageService.deleteFile(path);
            }
        }
        carRepository.deleteAll(cars);
        categoryRepository.delete(category);
    }
}
