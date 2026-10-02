package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.OwnerContactResponse;
import com.digitalhouse.rentacarnow.dto.PageResponse;
import com.digitalhouse.rentacarnow.dto.ReservationRequest;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.ReservationStatus;
import com.digitalhouse.rentacarnow.security.CurrentUserService;
import com.digitalhouse.rentacarnow.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final CurrentUserService currentUserService;

    public ReservationController(ReservationService reservationService, CurrentUserService currentUserService) {
        this.reservationService = reservationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ApiResponse<List<Reservation>> findAll() {
        return ApiResponse.success(reservationService.findAll(currentUserService.currentUser()));
    }

    @GetMapping("/my")
    public ApiResponse<List<Reservation>> findMy() {
        return ApiResponse.success(reservationService.findMy(currentUserService.currentUser()));
    }

    @GetMapping("/history")
    public ApiResponse<PageResponse<Reservation>> history(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @PageableDefault(size = 10, sort = "startAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Reservation> page = reservationService.findHistory(userId, status, from, to, pageable, currentUserService.currentUser());
        return ApiResponse.success(PageResponse.from(page));
    }

    @GetMapping("/{id}")
    public ApiResponse<Reservation> findById(@PathVariable Long id) {
        return ApiResponse.success(reservationService.findById(id, currentUserService.currentUser()));
    }

    @GetMapping("/{id}/contact")
    public ApiResponse<OwnerContactResponse> contact(@PathVariable Long id) {
        return ApiResponse.success(reservationService.getContactByReservation(id, currentUserService.currentUser()));
    }

    @PostMapping
    public ApiResponse<Reservation> createReservation(@Valid @RequestBody ReservationRequest request) {
        return ApiResponse.success(reservationService.createReservation(
                request.startAt(), request.endAt(), request.carId(), request.userId(), currentUserService.currentUser()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id, currentUserService.currentUser());
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Reservation> updateReservation(@PathVariable Long id,
                                                      @Valid @RequestBody ReservationRequest request) {
        return ApiResponse.success(reservationService.updateReservation(
                id, request.startAt(), request.endAt(), request.carId(), request.userId(), currentUserService.currentUser()));
    }

    @PostMapping("/{id}/dispatch")
    public ApiResponse<Reservation> dispatch(@PathVariable Long id) {
        return ApiResponse.success(reservationService.dispatchReservation(id, currentUserService.currentUser()));
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<Reservation> complete(@PathVariable Long id) {
        return ApiResponse.success(reservationService.completeReservation(id, currentUserService.currentUser()));
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<Reservation> cancel(@PathVariable Long id) {
        return ApiResponse.success(reservationService.cancelReservation(id, currentUserService.currentUser()));
    }

    @PostMapping("/{id}/revert")
    public ApiResponse<Reservation> revert(@PathVariable Long id) {
        return ApiResponse.success(reservationService.revertReservation(id, currentUserService.currentUser()));
    }
}
