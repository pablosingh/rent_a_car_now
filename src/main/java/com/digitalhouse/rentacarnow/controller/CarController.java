package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.PageResponse;
import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<PageResponse<Car>> findAll(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Car> page = carService.findAll(available, category, pageable);
        return ApiResponse.success(PageResponse.from(page));
    }

    @GetMapping("/random")
    public ApiResponse<List<Car>> findRandom(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String category) {
        return ApiResponse.success(carService.findRandom(limit, available, category));
    }

    @GetMapping("/{plate}")
    public ApiResponse<Car> findByPlate(@PathVariable String plate) {
        return ApiResponse.success(carService.findByPlate(plate));
    }

    @PostMapping
    public ApiResponse<Car> createCar(@RequestBody Car car) {
        return ApiResponse.success(carService.createCar(car.getPlate(), car.getBrand(), car.getModel(),
                car.getYear(), car.getPricePerDay(), car.getPricePerHour(), car.getAvailable(), car.getCategory()));
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
                                      @RequestParam Boolean available,
                                      @RequestParam String category) {
        return ApiResponse.success(carService.updateCar(plate, brand, model, year, pricePerDay, pricePerHour, available, category));
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
