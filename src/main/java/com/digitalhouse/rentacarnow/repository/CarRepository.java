package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByPlate(String plate);

    Page<Car> findByAvailable(Boolean available, Pageable pageable);

    Page<Car> findByCategory(String category, Pageable pageable);

    Page<Car> findByCategoryAndAvailable(String category, Boolean available, Pageable pageable);

    Page<Car> findByOwner_Id(Long ownerId, Pageable pageable);

    @Query(value = "SELECT * FROM car WHERE (:category IS NULL OR category = :category) AND (:available IS NULL OR available = :available) ORDER BY random() LIMIT :limit",
            nativeQuery = true)
    List<Car> findRandom(@Param("limit") int limit, @Param("available") Boolean available, @Param("category") String category);

    @Query(value = "SELECT * FROM car WHERE (:q IS NULL OR LOWER(brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR category = :category) AND (:available IS NULL OR available = :available)",
            countQuery = "SELECT COUNT(*) FROM car WHERE (:q IS NULL OR LOWER(brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR category = :category) AND (:available IS NULL OR available = :available)",
            nativeQuery = true)
    Page<Car> search(@Param("q") String q, @Param("category") String category, @Param("available") Boolean available, Pageable pageable);

    @Query(value = "SELECT * FROM car WHERE (:q IS NULL OR LOWER(brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR category = :category) AND (:available IS NULL OR available = :available) ORDER BY random() LIMIT :limit",
            nativeQuery = true)
    List<Car> findRandomWithSearch(@Param("limit") int limit, @Param("available") Boolean available, @Param("category") String category, @Param("q") String q);
}
