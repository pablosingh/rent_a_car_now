package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.service.CarService;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<Car> createCar(@RequestParam String plate,
                                      @RequestParam String brand,
                                      @RequestParam String model,
                                      @RequestParam Integer year,
                                      @RequestParam Double pricePerDay,
                                      @RequestParam Double pricePerHour,
                                      @RequestParam Boolean available) {
        return ApiResponse.success(carService.createCar(plate, brand, model, year, pricePerDay, pricePerHour, available));
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
}
