package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final FileStorageService fileStorageService;

    public CarServiceImpl(CarRepository carRepository, FileStorageService fileStorageService){
        this.carRepository = carRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findByPlate(String plate) {
        return carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
    }

    @Override
    public Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        if (carRepository.findByPlate(plate).isPresent()) {
            throw new ConflictException("Ya existe un auto con la patente: " + plate);
        }
        Car car = new Car();
        car.setPlate(plate);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        return carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        for (String path : car.getImagePaths()) {
            fileStorageService.deleteFile(path);
        }
        carRepository.deleteById(id);
    }

    @Override
    public Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        return carRepository.save(car);
    }

    @Override
    public Car uploadImage(String plate, MultipartFile file) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        String filePath = fileStorageService.saveFile(file);
        car.getImagePaths().add(filePath);
        return carRepository.save(car);
    }

    @Override
    public void deleteImage(String plate, String imagePath) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        car.getImagePaths().remove(imagePath);
        fileStorageService.deleteFile(imagePath);
        carRepository.save(car);
    }
}
