package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
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
    public ApiResponse<Reservation> createReservation(@RequestParam Integer durationInDays,
                                                      @RequestParam Long car_id,
                                                      @RequestParam Long user_id) {
        return ApiResponse.success(reservationService.createReservation(durationInDays, car_id, user_id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Reservation> updateReservation(@PathVariable Long id,
                                                      @RequestParam Integer durationInDays,
                                                      @RequestParam Long car_id,
                                                      @RequestParam Long user_id) {
        return ApiResponse.success(reservationService.updateReservation(id, durationInDays, car_id, user_id));
    }
}
