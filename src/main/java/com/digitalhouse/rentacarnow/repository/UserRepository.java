package com.digitalhouse.rentacarnow.repository;

import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
