package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository){
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @Override
    public Reservation createReservation(Integer durationInDays, Long car_id, Long user_id) {
        Car car = carRepository.findById(car_id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + car_id));
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        Reservation reservation = new Reservation();
        reservation.setDurationInDays(durationInDays);
        reservation.setCar(car);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public Reservation updateReservation(Long id, Integer durationInDays, Long car_id, Long user_id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        Car car = carRepository.findById(car_id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + car_id));
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        reservation.setDurationInDays(durationInDays);
        reservation.setCar(car);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }
}
