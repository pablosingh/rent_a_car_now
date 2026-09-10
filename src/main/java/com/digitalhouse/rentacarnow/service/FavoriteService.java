package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Favorite;
import com.digitalhouse.rentacarnow.entity.User;

import java.util.List;

public interface FavoriteService {
    Favorite add(Long carId, User requester);

    void remove(Long carId, User requester);

    List<Favorite> listMy(User requester);

    boolean isFavorite(Long carId, User requester);
}
