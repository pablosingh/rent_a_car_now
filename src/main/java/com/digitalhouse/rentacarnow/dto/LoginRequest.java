package com.digitalhouse.rentacarnow.dto;

public record LoginRequest(
        String email,
        String password
) {}