package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reservation> findAll(User requester) {
        String role = requester.getRole();
        if ("ADMIN".equals(role)) {
            return reservationRepository.findAll();
        }
        if ("OWNER".equals(role)) {
            return reservationRepository.findByCar_Owner_Id(requester.getId());
        }
        if ("EMPLOYEE".equals(role)) {
            if (requester.getOwner() == null) {
                throw new AccessDeniedException("Tu cuenta de EMPLOYEE no tiene OWNER asignado.");
            }
            return reservationRepository.findByCar_Owner_Id(requester.getOwner().getId());
        }
        return findMy(requester);
    }

    @Override
    public List<Reservation> findMy(User requester) {
        return reservationRepository.findByUserId(requester.getId());
    }

    @Override
    public Reservation findById(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        return reservation;
    }

    @Override
    public Reservation createReservation(Integer durationInDays, Long car_id, Long user_id, User requester) {
        if ("USER".equals(requester.getRole()) && !requester.getId().equals(user_id)) {
            throw new AccessDeniedException("Un usuario solo puede reservar para sí mismo.");
        }
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
    public void deleteReservationById(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        reservationRepository.deleteById(id);
    }

    @Override
    public Reservation updateReservation(Long id, Integer durationInDays, Long car_id, Long user_id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        if ("USER".equals(requester.getRole()) && !requester.getId().equals(user_id)) {
            throw new AccessDeniedException("Un usuario solo puede reservar para sí mismo.");
        }
        Car car = carRepository.findById(car_id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + car_id));
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        reservation.setDurationInDays(durationInDays);
        reservation.setCar(car);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }

    private void assertCanManage(Reservation reservation, User requester) {
        String role = requester.getRole();
        if ("ADMIN".equals(role)) {
            return;
        }
        if ("USER".equals(role)) {
            if (reservation.getUser() != null && reservation.getUser().getId().equals(requester.getId())) {
                return;
            }
            throw new AccessDeniedException("No tenés permiso para gestionar esta reserva.");
        }
        Long carOwnerId = reservation.getCar() != null && reservation.getCar().getOwner() != null
                ? reservation.getCar().getOwner().getId()
                : null;
        if ("OWNER".equals(role) && carOwnerId != null && carOwnerId.equals(requester.getId())) {
            return;
        }
        if ("EMPLOYEE".equals(role)
                && requester.getOwner() != null
                && carOwnerId != null
                && carOwnerId.equals(requester.getOwner().getId())) {
            return;
        }
        throw new AccessDeniedException("No tenés permiso para gestionar esta reserva.");
    }
}