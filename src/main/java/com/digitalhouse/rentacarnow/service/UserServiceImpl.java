package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           FileStorageService fileStorageService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
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
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteById(Integer id) {
        User user = userRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (user.getPhotoPath() != null) {
            fileStorageService.deleteFile(user.getPhotoPath());
        }
        userRepository.deleteById(id.longValue());
    }

    @Override
    public User createUser(String name, String lastName, String email, String password, MultipartFile file) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Ya existe un usuario con ese email.");
        }
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        if (file != null && !file.isEmpty()) {
            user.setPhotoPath(fileStorageService.saveFile(file));
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User newUser) {
        User user = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + newUser.getId()));
        user.setName(newUser.getName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        if (newUser.getRole() != null) {
            user.setRole(newUser.getRole());
        }
        return userRepository.save(user);
    }

    @Override
    public User uploadPhoto(Long id, MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (user.getPhotoPath() != null) {
            fileStorageService.deleteFile(user.getPhotoPath());
        }
        user.setPhotoPath(fileStorageService.saveFile(file));
        return userRepository.save(user);
    }

    @Override
    public User deletePhoto(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (user.getPhotoPath() != null) {
            fileStorageService.deleteFile(user.getPhotoPath());
            user.setPhotoPath(null);
            return userRepository.save(user);
        }
        return user;
    }
}
