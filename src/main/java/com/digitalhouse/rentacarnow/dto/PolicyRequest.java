package com.digitalhouse.rentacarnow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PolicyRequest(
        @NotBlank String title,
        @NotBlank String slug,
        @NotBlank String content,
        @NotNull Integer displayOrder
) {
}
