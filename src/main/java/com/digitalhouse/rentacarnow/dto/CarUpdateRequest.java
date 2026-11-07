package com.digitalhouse.rentacarnow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CarUpdateRequest(
        @NotBlank(message = "La marca es obligatoria.")
        @Size(max = 100, message = "La marca no puede superar los 100 caracteres.")
        String brand,
        @NotBlank(message = "El modelo es obligatorio.")
        @Size(max = 100, message = "El modelo no puede superar los 100 caracteres.")
        String model,
        @NotNull(message = "El año es obligatorio.")
        @Min(value = 1950, message = "El año no puede ser anterior a 1950.")
        @Max(value = 2030, message = "El año no puede ser posterior a 2030.")
        Integer year,
        @NotNull(message = "El precio por día es obligatorio.")
        @DecimalMin(value = "0.01", message = "El precio por día debe ser mayor a 0.")
        Double pricePerDay,
        @NotNull(message = "El precio por hora es obligatorio.")
        @DecimalMin(value = "0.01", message = "El precio por hora debe ser mayor a 0.")
        Double pricePerHour,
        @NotNull(message = "El campo disponible es obligatorio.")
        Boolean available,
        @NotNull(message = "La categoría es obligatoria.")
        @Valid
        CategoryRef category,
        @Valid
        List<FeatureRef> features
) {
    public record CategoryRef(
            @NotNull(message = "El id de la categoría es obligatorio.")
            Long id
    ) {
    }

    public record FeatureRef(
            @NotNull(message = "El id de la feature es obligatorio.")
            Long id
    ) {
    }
}
