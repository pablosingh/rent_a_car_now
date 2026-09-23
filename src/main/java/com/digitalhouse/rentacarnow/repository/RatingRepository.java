package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByCar_Id(Long carId);

    List<Rating> findByUser_Id(Long userId);

    Optional<Rating> findByReservation_Id(Long reservationId);

    boolean existsByReservation_Id(Long reservationId);

    void deleteByCar_Id(Long carId);

    void deleteByReservation_Id(Long reservationId);

    @Query("SELECT COALESCE(AVG(r.score), 0) FROM Rating r WHERE r.car.id = :carId")
    Double findAverageByCarId(@Param("carId") Long carId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.car.id = :carId")
    long countByCarId(@Param("carId") Long carId);
}
