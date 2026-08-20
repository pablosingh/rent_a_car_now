package com.digitalhouse.rentacarnow.service;

import java.util.List;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.User;

public interface ReservationService {

  List<Reservation> findAll(User requester);

  List<Reservation> findMy(User requester);

  Reservation findById(Long id, User requester);

  Reservation createReservation(Integer durationInDays, Long car_id, Long user_id, User requester);

  Reservation updateReservation(Long id, Integer durationInDays, Long car_id, Long user_id, User requester);

  void deleteReservationById(Long id, User requester);
}