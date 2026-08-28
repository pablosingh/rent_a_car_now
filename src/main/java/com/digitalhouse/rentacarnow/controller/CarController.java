package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.PageResponse;
import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.security.CurrentUserService;
import com.digitalhouse.rentacarnow.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;
    private final CurrentUserService currentUserService;

    public CarController(CarService carService, CurrentUserService currentUserService) {
        this.carService = carService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ApiResponse<PageResponse<Car>> findAll(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String feature,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Car> page = ownerId != null
                ? carService.findByOwner(ownerId, pageable)
                : carService.findAll(available, category, q, feature, pageable);
        return ApiResponse.success(PageResponse.from(page));
    }

    @GetMapping("/random")
    public ApiResponse<List<Car>> findRandom(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String feature) {
        return ApiResponse.success(carService.findRandom(limit, available, category, q, feature));
    }

    @GetMapping("/{plate}")
    public ApiResponse<Car> findByPlate(@PathVariable String plate) {
        return ApiResponse.success(carService.findByPlate(plate));
    }

    @PostMapping
    public ApiResponse<Car> createCar(@Valid @RequestBody Car car,
                                      @RequestParam(required = false) Long ownerId) {
        Set<Long> featureIds = car.getFeatures() != null
                ? car.getFeatures().stream().map(f -> f.getId()).collect(java.util.stream.Collectors.toSet())
                : Set.of();
        return ApiResponse.success(carService.createCar(car.getPlate(), car.getBrand(), car.getModel(),
                car.getYear(), car.getPricePerDay(), car.getPricePerHour(), car.getAvailable(), car.getCategory(),
                featureIds, ownerId, currentUserService.currentUser()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id, currentUserService.currentUser());
        return ApiResponse.success(null);
    }

    @PutMapping("/{plate}")
    public ApiResponse<Car> updateCar(@PathVariable String plate,
                                      @RequestBody Car car) {
        Set<Long> featureIds = car.getFeatures() != null
                ? car.getFeatures().stream().map(f -> f.getId()).collect(java.util.stream.Collectors.toSet())
                : Set.of();
        return ApiResponse.success(carService.updateCar(plate, car.getBrand(), car.getModel(),
                car.getYear(), car.getPricePerDay(), car.getPricePerHour(), car.getAvailable(),
                car.getCategory(), featureIds, currentUserService.currentUser()));
    }

    @PostMapping("/{plate}/images")
    public ApiResponse<Car> uploadImage(@PathVariable String plate,
                                        @RequestParam("file") MultipartFile file) {
        return ApiResponse.success(carService.uploadImage(plate, file, currentUserService.currentUser()));
    }

    @DeleteMapping("/{plate}/images")
    public ApiResponse<Car> deleteImage(@PathVariable String plate,
                                        @RequestParam String imagePath) {
        carService.deleteImage(plate, imagePath, currentUserService.currentUser());
        return ApiResponse.success(carService.findByPlate(plate));
    }
}