package com.digitalhouse.rentacarnow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalhouse.rentacarnow.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
  
}
