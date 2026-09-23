package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.dto.RatingRequest;
import com.digitalhouse.rentacarnow.entity.Rating;
import com.digitalhouse.rentacarnow.entity.User;

import java.util.List;
import java.util.Map;

public interface RatingService {

    Rating create(RatingRequest request, User requester);

    Rating update(Long id, Integer score, String comment, User requester);

    void delete(Long id, User requester);

    List<Rating> findByCar(Long carId);

    List<Rating> findMy(User requester);

    Rating findByReservation(Long reservationId, User requester);

    Map<String, Object> averageByCar(Long carId);
}
