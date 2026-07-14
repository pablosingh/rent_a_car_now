package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

    @Override
    public User createUser(String name, String lastName, String email, String password) {
        return null;
    }

    @Override
    public User updateUser(User newUser) {
        return null;
    }
}
