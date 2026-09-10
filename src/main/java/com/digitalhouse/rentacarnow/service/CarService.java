package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface CarService {

    Page<Car> findAll(Boolean available, String category, String q, String feature, Pageable pageable);

    Page<Car> findByOwner(Long ownerId, Pageable pageable);

    List<Car> findRandom(Integer limit, Boolean available, String category, String q, String feature);

    Car findByPlate(String plate);

    Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available, Long categoryId, Set<Long> featureIds, Long ownerId, User requester);

    void deleteCarById(Long id, User requester);

    Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available, Long categoryId, Set<Long> featureIds, User requester);

    Car uploadImage(String plate, MultipartFile file, User requester);

    void deleteImage(String plate, String imagePath, User requester);
}