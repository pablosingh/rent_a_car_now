package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<User>> findAll() {
        return ApiResponse.success(userService.findAll());
    }

    @GetMapping("/{email}")
    public ApiResponse<User> findByEmail(@PathVariable String email) {
        return ApiResponse.success(userService.findByEmail(email));
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        return ApiResponse.success(userService.createUser(user.getName(), user.getLastName(),
                user.getEmail(), user.getPassword()));
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
