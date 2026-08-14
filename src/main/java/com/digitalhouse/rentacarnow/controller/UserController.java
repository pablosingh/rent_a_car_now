package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.dto.LoginRequest;
import com.digitalhouse.rentacarnow.dto.LoginResponse;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.security.JwtService;
import com.digitalhouse.rentacarnow.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ApiResponse<List<User>> findAll() {
        return ApiResponse.success(userService.findAll());
    }

    @GetMapping("/{email}")
    public ApiResponse<User> findByEmail(@PathVariable String email) {
        return ApiResponse.success(userService.findByEmail(email));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());
        String token = jwtService.generateToken(userDetails);
        return ApiResponse.success(new LoginResponse(token, user));
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user.getName(), user.getLastName(),
                user.getEmail(), user.getPassword());
        created.setPassword(null);
        return ApiResponse.success(created);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return ApiResponse.success(userService.updateUser(user));
    }
}