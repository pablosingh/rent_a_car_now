package com.digitalhouse.rentacarnow.service;

import org.springframework.stereotype.Service;

import java.util.List;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService{
  private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public Reservation createReservation(Integer durationInDays, Long car_id, Long user_id) {
        return null;
    }

    @Override
    public void deleteReservationById(Long id) {

    }

    @Override
    public Reservation updateReservation(Integer durationInDays, Long car_id, Long user_id) {
        return null;
    }
}
