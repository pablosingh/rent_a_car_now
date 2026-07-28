package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.service.CarService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping
    public ApiResponse<List<Car>> findAll() {
        return ApiResponse.success(carService.findAll());
    }

    @GetMapping("/{plate}")
    public ApiResponse<Car> findByPlate(@PathVariable String plate) {
        return ApiResponse.success(carService.findByPlate(plate));
    }

    @PostMapping
    public ApiResponse<Car> createCar(@RequestBody Car car) {
        return ApiResponse.success(carService.createCar(car.getPlate(), car.getBrand(), car.getModel(),
                car.getYear(), car.getPricePerDay(), car.getPricePerHour(), car.getAvailable()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{plate}")
    public ApiResponse<Car> updateCar(@PathVariable String plate,
                                      @RequestParam String brand,
                                      @RequestParam String model,
                                      @RequestParam Integer year,
                                      @RequestParam Double pricePerDay,
                                      @RequestParam Double pricePerHour,
                                      @RequestParam Boolean available) {
        return ApiResponse.success(carService.updateCar(plate, brand, model, year, pricePerDay, pricePerHour, available));
    }

    @PostMapping("/{plate}/images")
    public ApiResponse<Car> uploadImage(@PathVariable String plate,
                                        @RequestParam("file") MultipartFile file) {
        return ApiResponse.success(carService.uploadImage(plate, file));
    }

    @DeleteMapping("/{plate}/images")
    public ApiResponse<Car> deleteImage(@PathVariable String plate,
                                        @RequestParam String imagePath) {
        carService.deleteImage(plate, imagePath);
        return ApiResponse.success(carService.findByPlate(plate));
    }
}
