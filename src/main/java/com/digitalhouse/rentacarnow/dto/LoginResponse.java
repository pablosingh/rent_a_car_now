package com.digitalhouse.rentacarnow.dto;

import com.digitalhouse.rentacarnow.entity.User;

public record LoginResponse(
        String token,
        User user
) {}