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

    Page<Car> findByCategory_Name(String category, Pageable pageable);

    Page<Car> findByCategory_NameAndAvailable(String category, Boolean available, Pageable pageable);

    Page<Car> findByOwner_Id(Long ownerId, Pageable pageable);

    boolean existsByCategory_Id(Long categoryId);

    boolean existsByCategory_Name(String categoryName);

    @Query(value = "SELECT DISTINCT c.* FROM car c JOIN car_features cf ON c.id = cf.car_id JOIN feature f ON cf.feature_id = f.id WHERE f.name = :feature",
            countQuery = "SELECT COUNT(DISTINCT c.id) FROM car c JOIN car_features cf ON c.id = cf.car_id JOIN feature f ON cf.feature_id = f.id WHERE f.name = :feature",
            nativeQuery = true)
    Page<Car> findByFeatureName(@Param("feature") String feature, Pageable pageable);

    @Query(value = "SELECT c.* FROM car c JOIN category cat ON c.category_id = cat.id WHERE (:category IS NULL OR cat.name = :category) AND (:available IS NULL OR c.available = :available) ORDER BY random() LIMIT :limit",
            nativeQuery = true)
    List<Car> findRandom(@Param("limit") int limit, @Param("available") Boolean available, @Param("category") String category);

    @Query(value = "SELECT DISTINCT c.* FROM car c JOIN car_features cf ON c.id = cf.car_id JOIN feature f ON cf.feature_id = f.id JOIN category cat ON c.category_id = cat.id WHERE f.name = :feature AND (:category IS NULL OR cat.name = :category) AND (:available IS NULL OR c.available = :available) ORDER BY random() LIMIT :limit",
            nativeQuery = true)
    List<Car> findRandomByFeature(@Param("limit") int limit, @Param("available") Boolean available, @Param("category") String category, @Param("feature") String feature);

    @Query(value = "SELECT c.* FROM car c JOIN category cat ON c.category_id = cat.id WHERE (:q IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(c.model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR cat.name = :category) AND (:available IS NULL OR c.available = :available) AND (:feature IS NULL OR c.id IN (SELECT cf.car_id FROM car_features cf JOIN feature f ON cf.feature_id = f.id WHERE f.name = :feature))",
            countQuery = "SELECT COUNT(*) FROM car c JOIN category cat ON c.category_id = cat.id WHERE (:q IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(c.model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR cat.name = :category) AND (:available IS NULL OR c.available = :available) AND (:feature IS NULL OR c.id IN (SELECT cf.car_id FROM car_features cf JOIN feature f ON cf.feature_id = f.id WHERE f.name = :feature))",
            nativeQuery = true)
    Page<Car> search(@Param("q") String q, @Param("category") String category, @Param("available") Boolean available, @Param("feature") String feature, Pageable pageable);

    @Query(value = "SELECT c.* FROM car c JOIN category cat ON c.category_id = cat.id WHERE (:q IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(c.model) LIKE LOWER(CONCAT('%', :q, '%'))) AND (:category IS NULL OR cat.name = :category) AND (:available IS NULL OR c.available = :available) AND (:feature IS NULL OR c.id IN (SELECT cf.car_id FROM car_features cf JOIN feature f ON cf.feature_id = f.id WHERE f.name = :feature)) ORDER BY random() LIMIT :limit",
            nativeQuery = true)
    List<Car> findRandomWithSearch(@Param("limit") int limit, @Param("available") Boolean available, @Param("category") String category, @Param("q") String q, @Param("feature") String feature);
}
