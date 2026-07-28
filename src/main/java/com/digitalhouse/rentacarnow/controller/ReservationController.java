package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.ReservationRequest;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ApiResponse<List<Reservation>> findAll() {
        return ApiResponse.success(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Reservation> findById(@PathVariable Long id) {
        return ApiResponse.success(reservationService.findById(id));
    }

    @PostMapping
    public ApiResponse<Reservation> createReservation(@RequestBody ReservationRequest request) {
        return ApiResponse.success(reservationService.createReservation(
                request.durationInDays(), request.carId(), request.userId()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Reservation> updateReservation(@PathVariable Long id,
                                                      @RequestBody ReservationRequest request) {
        return ApiResponse.success(reservationService.updateReservation(
                id, request.durationInDays(), request.carId(), request.userId()));
    }
}
