package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Set<String> REGISTRATION_ROLES = Set.of("USER", "OWNER");
    private static final Set<String> ALL_ROLES = Set.of("USER", "OWNER", "EMPLOYEE", "ADMIN");

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<User> findAll(User requester) {
        if ("ADMIN".equals(requester.getRole())) {
            return userRepository.findAll();
        }
        if ("OWNER".equals(requester.getRole())) {
            return userRepository.findByOwner_Id(requester.getId());
        }
        throw new AccessDeniedException("No tenés permiso para listar usuarios.");
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
    public void deleteById(Integer id, User requester) {
        User user = userRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (!"ADMIN".equals(requester.getRole()) && !canManage(requester, user)) {
            throw new AccessDeniedException("No tenés permiso para eliminar este usuario.");
        }
        if (user.getPhotoPath() != null) {
            fileStorageService.deleteFile(user.getPhotoPath());
        }
        userRepository.deleteById(id.longValue());
    }

    @Override
    public User createUser(String name, String lastName, String email, String password, String role, MultipartFile file) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Ya existe un usuario con ese email.");
        }
        String resolvedRole = role == null || role.isBlank() ? "USER" : role.toUpperCase();
        if (!REGISTRATION_ROLES.contains(resolvedRole)) {
            throw new AccessDeniedException("Solo se puede registrar como USER u OWNER.");
        }
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(resolvedRole);
        user.setVerified("USER".equals(resolvedRole));
        if (file != null && !file.isEmpty()) {
            user.setPhotoPath(fileStorageService.saveFile(file));
        }
        return userRepository.save(user);
    }

    @Override
    public User createEmployee(String name, String lastName, String email, String password, MultipartFile file, User requester) {
        if ("OWNER".equals(requester.getRole()) && !Boolean.TRUE.equals(requester.getVerified())) {
            throw new AccessDeniedException("Tu cuenta de OWNER debe estar verificada para crear empleados.");
        }
        if (!Set.of("OWNER", "ADMIN").contains(requester.getRole())) {
            throw new AccessDeniedException("Solo un OWNER o ADMIN puede crear empleados.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Ya existe un usuario con ese email.");
        }
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("EMPLOYEE");
        user.setVerified(true);
        user.setOwner("OWNER".equals(requester.getRole()) ? requester : null);
        if (file != null && !file.isEmpty()) {
            user.setPhotoPath(fileStorageService.saveFile(file));
        }
        return userRepository.save(user);
    }

    @Override
    public User verifyUser(Long id) {
        User user = findById(id);
        user.setVerified(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User newUser, User requester) {
        User user = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + newUser.getId()));
        if (!"ADMIN".equals(requester.getRole()) && !canManage(requester, user)) {
            throw new AccessDeniedException("No tenés permiso para modificar este usuario.");
        }
        user.setName(newUser.getName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        if (newUser.getRole() != null && "ADMIN".equals(requester.getRole())
                && ALL_ROLES.contains(newUser.getRole().toUpperCase())) {
            user.setRole(newUser.getRole().toUpperCase());
        }
        if (newUser.getVerified() != null && "ADMIN".equals(requester.getRole())) {
            user.setVerified(newUser.getVerified());
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

    private boolean canManage(User requester, User target) {
        return "OWNER".equals(requester.getRole())
                && "EMPLOYEE".equals(target.getRole())
                && target.getOwner() != null
                && target.getOwner().getId().equals(requester.getId());
    }
}