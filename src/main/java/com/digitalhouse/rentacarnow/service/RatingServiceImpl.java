package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.dto.RatingRequest;
import com.digitalhouse.rentacarnow.entity.Rating;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.ReservationStatus;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.RatingRepository;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ReservationRepository reservationRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ReservationRepository reservationRepository) {
        this.ratingRepository = ratingRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public Rating create(RatingRequest request, User requester) {
        Reservation reservation = reservationRepository.findById(request.reservationId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + request.reservationId()));
        if (!reservation.getUser().getId().equals(requester.getId()) && !"ADMIN".equals(requester.getRole())) {
            throw new AccessDeniedException("Solo podés puntuar tus propias reservas.");
        }
        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new ConflictException("Solo podés puntuar reservas COMPLETED. Estado actual: " + reservation.getStatus());
        }
        if (ratingRepository.existsByReservation_Id(reservation.getId())) {
            throw new ConflictException("Esta reserva ya fue puntuada.");
        }
        if (request.score() == null || request.score() < 1 || request.score() > 5) {
            throw new ConflictException("El puntaje debe estar entre 1 y 5.");
        }
        Rating rating = new Rating();
        rating.setUser(reservation.getUser());
        rating.setCar(reservation.getCar());
        rating.setReservation(reservation);
        rating.setScore(request.score());
        rating.setComment(request.comment());
        return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public Rating update(Long id, Integer score, String comment, User requester) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puntuación no encontrada con id: " + id));
        if (!rating.getUser().getId().equals(requester.getId()) && !"ADMIN".equals(requester.getRole())) {
            throw new AccessDeniedException("Solo podés editar tu propia puntuación.");
        }
        if (score != null) {
            if (score < 1 || score > 5) throw new ConflictException("El puntaje debe estar entre 1 y 5.");
            rating.setScore(score);
        }
        rating.setComment(comment);
        return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void delete(Long id, User requester) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puntuación no encontrada con id: " + id));
        if (!rating.getUser().getId().equals(requester.getId()) && !"ADMIN".equals(requester.getRole())) {
            throw new AccessDeniedException("Solo podés borrar tu propia puntuación.");
        }
        ratingRepository.delete(rating);
    }

    @Override
    public List<Rating> findByCar(Long carId) {
        return ratingRepository.findByCar_Id(carId);
    }

    @Override
    public List<Rating> findMy(User requester) {
        return ratingRepository.findByUser_Id(requester.getId());
    }

    @Override
    public Rating findByReservation(Long reservationId, User requester) {
        Rating rating = ratingRepository.findByReservation_Id(reservationId)
                .orElseThrow(() -> new RuntimeException("Puntuación no encontrada para la reserva: " + reservationId));
        if (!rating.getUser().getId().equals(requester.getId()) && !"ADMIN".equals(requester.getRole())) {
            Long carOwnerId = rating.getCar() != null && rating.getCar().getOwner() != null ? rating.getCar().getOwner().getId() : null;
            boolean isStaffOwner = "OWNER".equals(requester.getRole()) && carOwnerId != null && carOwnerId.equals(requester.getId());
            boolean isStaffEmployee = "EMPLOYEE".equals(requester.getRole()) && requester.getOwner() != null && carOwnerId != null && carOwnerId.equals(requester.getOwner().getId());
            if (!isStaffOwner && !isStaffEmployee) {
                throw new AccessDeniedException("No tenés permiso para ver esta puntuación.");
            }
        }
        return rating;
    }

    @Override
    public Map<String, Object> averageByCar(Long carId) {
        Double avg = ratingRepository.findAverageByCarId(carId);
        long count = ratingRepository.countByCarId(carId);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("average", avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
        map.put("count", count);
        return map;
    }
}
