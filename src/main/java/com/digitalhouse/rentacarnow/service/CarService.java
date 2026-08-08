package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface CarService {

    Page<Car> findAll(Boolean available, Pageable pageable);

    Car findByPlate(String plate);

    Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available);

    void deleteCarById(Long id);

    Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available);

    Car uploadImage(String plate, MultipartFile file);

    void deleteImage(String plate, String imagePath);
}
