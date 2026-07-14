package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByEmail(String email);

    void deleteById(Integer id);

    User createUser(String name, String lastName, String email, String password);

    User updateUser(User newUser);
}
