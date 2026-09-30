package com.digitalhouse.rentacarnow.service;

import java.time.Instant;
import java.util.List;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.ReservationStatus;
import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

  List<Reservation> findAll(User requester);

  List<Reservation> findMy(User requester);

  Page<Reservation> findHistory(Long userId, ReservationStatus status, Instant from, Instant to, Pageable pageable, User requester);

  List<com.digitalhouse.rentacarnow.dto.AvailabilitySlot> findAvailability(Long carId, Instant from, Instant to);

  Reservation findById(Long id, User requester);

  Reservation createReservation(Instant startAt, Instant endAt, Long car_id, Long user_id, User requester);

  Reservation updateReservation(Long id, Instant startAt, Instant endAt, Long car_id, Long user_id, User requester);

  void deleteReservationById(Long id, User requester);

  Reservation dispatchReservation(Long id, User requester);

  Reservation completeReservation(Long id, User requester);

  Reservation cancelReservation(Long id, User requester);

  Reservation revertReservation(Long id, User requester);
}