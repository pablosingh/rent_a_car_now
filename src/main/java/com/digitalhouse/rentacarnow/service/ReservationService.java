package com.digitalhouse.rentacarnow.service;

import java.util.List;
import com.digitalhouse.rentacarnow.entity.Reservation;

public interface ReservationService {
  
  List<Reservation> findAll();

  Reservation findById(Long id);

  Reservation createReservation(Integer durationInDays, Long car_id, Long user_id);

  Reservation updateReservation(Integer durationInDays, Long car_id, Long user_id);

  void deleteReservationById(Long id);
}
