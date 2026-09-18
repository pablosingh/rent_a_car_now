package com.digitalhouse.rentacarnow.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ReservationRequest(
        @NotNull(message = "startAt es obligatorio") Instant startAt,
        @NotNull(message = "endAt es obligatorio") Instant endAt,
        @NotNull(message = "carId es obligatorio") Long carId,
        @NotNull(message = "userId es obligatorio") Long userId
) {}
