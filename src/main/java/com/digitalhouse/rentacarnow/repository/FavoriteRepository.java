package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser_Id(Long userId);

    Optional<Favorite> findByUser_IdAndCar_Id(Long userId, Long carId);

    boolean existsByUser_IdAndCar_Id(Long userId, Long carId);

    long countByCar_Id(Long carId);

    void deleteByUser_IdAndCar_Id(Long userId, Long carId);
}
