package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id.longValue())) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id.longValue());
    }

    @Override
    public User createUser(String name, String lastName, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User newUser) {
        User user = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + newUser.getId()));
        user.setName(newUser.getName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
    }
}
