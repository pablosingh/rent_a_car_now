package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByTitle(String title);
}
