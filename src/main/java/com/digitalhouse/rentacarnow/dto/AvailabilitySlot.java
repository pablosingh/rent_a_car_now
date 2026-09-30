package com.digitalhouse.rentacarnow.dto;

import com.digitalhouse.rentacarnow.entity.ReservationStatus;

import java.time.Instant;

public record AvailabilitySlot(
        Instant startAt,
        Instant endAt,
        ReservationStatus status
) {
    public static AvailabilitySlot from(com.digitalhouse.rentacarnow.entity.Reservation r) {
        return new AvailabilitySlot(r.getStartAt(), r.getEndAt(), r.getStatus());
    }
}
