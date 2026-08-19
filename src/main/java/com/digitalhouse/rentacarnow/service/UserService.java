package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByEmail(String email);

    User findById(Long id);

    void deleteById(Integer id);

    User createUser(String name, String lastName, String email, String password, MultipartFile file);

    User updateUser(User newUser);

    User uploadPhoto(Long id, MultipartFile file);

    User deletePhoto(Long id);
}
