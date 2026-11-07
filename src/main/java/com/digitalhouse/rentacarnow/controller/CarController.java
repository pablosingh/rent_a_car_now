package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.AvailabilitySlot;
import com.digitalhouse.rentacarnow.dto.CarRequest;
import com.digitalhouse.rentacarnow.dto.CarUpdateRequest;
import com.digitalhouse.rentacarnow.dto.OwnerContactResponse;
import com.digitalhouse.rentacarnow.dto.PageResponse;
import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.security.CurrentUserService;
import com.digitalhouse.rentacarnow.service.CarService;
import com.digitalhouse.rentacarnow.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;
    private final ReservationService reservationService;
    private final CurrentUserService currentUserService;

    public CarController(CarService carService, ReservationService reservationService, CurrentUserService currentUserService) {
        this.carService = carService;
        this.reservationService = reservationService;
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

    @GetMapping("/{carId}/availability")
    public ApiResponse<List<AvailabilitySlot>> availability(
            @PathVariable Long carId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {
        return ApiResponse.success(reservationService.findAvailability(carId, from, to));
    }

    @GetMapping("/{carId}/contact")
    public ApiResponse<OwnerContactResponse> contact(@PathVariable Long carId) {
        return ApiResponse.success(reservationService.getContactByCar(carId, currentUserService.currentUser()));
    }

    @PostMapping
    public ApiResponse<Car> createCar(@Valid @RequestBody CarRequest request,
                                      @RequestParam(required = false) Long ownerId) {
        Set<Long> featureIds = request.features() != null
                ? request.features().stream().map(f -> f.id()).collect(Collectors.toSet())
                : Set.of();
        Long categoryId = request.category() != null ? request.category().id() : null;
        return ApiResponse.success(carService.createCar(request.plate(), request.brand(), request.model(),
                request.year(), request.pricePerDay(), request.pricePerHour(), request.available(), categoryId,
                featureIds, ownerId, currentUserService.currentUser()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id, currentUserService.currentUser());
        return ApiResponse.success(null);
    }

    @PutMapping("/{plate}")
    public ApiResponse<Car> updateCar(@PathVariable String plate,
                                      @Valid @RequestBody CarUpdateRequest request) {
        Set<Long> featureIds = request.features() != null
                ? request.features().stream().map(f -> f.id()).collect(Collectors.toSet())
                : Set.of();
        Long categoryId = request.category() != null ? request.category().id() : null;
        return ApiResponse.success(carService.updateCar(plate, request.brand(), request.model(),
                request.year(), request.pricePerDay(), request.pricePerHour(), request.available(),
                categoryId, featureIds, currentUserService.currentUser()));
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
