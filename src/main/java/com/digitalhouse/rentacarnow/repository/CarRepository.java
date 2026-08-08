package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByPlate(String plate);

    Page<Car> findByAvailable(Boolean available, Pageable pageable);
}
