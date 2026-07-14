package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
