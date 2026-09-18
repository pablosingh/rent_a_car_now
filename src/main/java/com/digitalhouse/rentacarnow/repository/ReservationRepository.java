package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByCar_Owner_Id(Long ownerId);

    void deleteByCar_Id(Long carId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reservation r " +
           "WHERE r.car.id = :carId " +
           "AND r.startAt < :effectiveEnd " +
           "AND r.endAt > :effectiveStart " +
           "AND (:excludeId IS NULL OR r.id <> :excludeId)")
    boolean existsOverlapWithBuffer(@Param("carId") Long carId,
                                    @Param("effectiveStart") Instant effectiveStart,
                                    @Param("effectiveEnd") Instant effectiveEnd,
                                    @Param("excludeId") Long excludeId);
}
