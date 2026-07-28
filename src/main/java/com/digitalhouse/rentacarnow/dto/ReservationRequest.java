package com.digitalhouse.rentacarnow.dto;

public record ReservationRequest(
        Integer durationInDays,
        Long carId,
        Long userId
) {}
