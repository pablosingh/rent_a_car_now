package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.RatingRequest;
import com.digitalhouse.rentacarnow.entity.Rating;
import com.digitalhouse.rentacarnow.security.CurrentUserService;
import com.digitalhouse.rentacarnow.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final CurrentUserService currentUserService;

    public RatingController(RatingService ratingService, CurrentUserService currentUserService) {
        this.ratingService = ratingService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ApiResponse<Rating> create(@Valid @RequestBody RatingRequest request) {
        return ApiResponse.success(ratingService.create(request, currentUserService.currentUser()));
    }

    @PutMapping("/{id}")
    public ApiResponse<Rating> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer score = body.get("score") != null ? Integer.valueOf(body.get("score").toString()) : null;
        String comment = body.get("comment") != null ? body.get("comment").toString() : null;
        return ApiResponse.success(ratingService.update(id, score, comment, currentUserService.currentUser()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        ratingService.delete(id, currentUserService.currentUser());
        return ApiResponse.success(null);
    }

    @GetMapping("/car/{carId}")
    public ApiResponse<List<Rating>> findByCar(@PathVariable Long carId) {
        return ApiResponse.success(ratingService.findByCar(carId));
    }

    @GetMapping("/car/{carId}/average")
    public ApiResponse<Map<String, Object>> averageByCar(@PathVariable Long carId) {
        return ApiResponse.success(ratingService.averageByCar(carId));
    }

    @GetMapping("/my")
    public ApiResponse<List<Rating>> findMy() {
        return ApiResponse.success(ratingService.findMy(currentUserService.currentUser()));
    }

    @GetMapping("/reservation/{reservationId}")
    public ApiResponse<Rating> findByReservation(@PathVariable Long reservationId) {
        return ApiResponse.success(ratingService.findByReservation(reservationId, currentUserService.currentUser()));
    }
}
