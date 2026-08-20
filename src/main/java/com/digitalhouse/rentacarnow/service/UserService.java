package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> findAll(User requester);

    User findByEmail(String email);

    User findById(Long id);

    void deleteById(Integer id, User requester);

    User createUser(String name, String lastName, String email, String password, String role, MultipartFile file);

    User createEmployee(String name, String lastName, String email, String password, MultipartFile file, User requester);

    User verifyUser(Long id);

    User updateUser(User newUser, User requester);

    User uploadPhoto(Long id, MultipartFile file);

    User deletePhoto(Long id);
}