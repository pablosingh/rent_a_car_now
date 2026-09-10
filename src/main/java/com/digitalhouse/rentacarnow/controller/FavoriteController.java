package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.Favorite;
import com.digitalhouse.rentacarnow.security.CurrentUserService;
import com.digitalhouse.rentacarnow.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final CurrentUserService currentUserService;

    public FavoriteController(FavoriteService favoriteService, CurrentUserService currentUserService) {
        this.favoriteService = favoriteService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/{carId}")
    public ApiResponse<Favorite> add(@PathVariable Long carId) {
        return ApiResponse.success(favoriteService.add(carId, currentUserService.currentUser()));
    }

    @DeleteMapping("/{carId}")
    public ApiResponse<Void> remove(@PathVariable Long carId) {
        favoriteService.remove(carId, currentUserService.currentUser());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<List<Favorite>> listMy() {
        return ApiResponse.success(favoriteService.listMy(currentUserService.currentUser()));
    }

    @GetMapping("/{carId}/check")
    public ApiResponse<Map<String, Boolean>> check(@PathVariable Long carId) {
        boolean fav = favoriteService.isFavorite(carId, currentUserService.currentUser());
        return ApiResponse.success(Map.of("favorite", fav));
    }
}
