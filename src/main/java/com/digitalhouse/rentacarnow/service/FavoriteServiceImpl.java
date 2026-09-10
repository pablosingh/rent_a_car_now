package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Favorite;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CarRepository carRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, CarRepository carRepository) {
        this.favoriteRepository = favoriteRepository;
        this.carRepository = carRepository;
    }

    @Override
    @Transactional
    public Favorite add(Long carId, User requester) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + carId));
        if (favoriteRepository.existsByUser_IdAndCar_Id(requester.getId(), carId)) {
            throw new ConflictException("Ya está en favoritos.");
        }
        Favorite fav = new Favorite();
        fav.setUser(requester);
        fav.setCar(car);
        fav.setCreatedAt(Instant.now());
        return favoriteRepository.save(fav);
    }

    @Override
    @Transactional
    public void remove(Long carId, User requester) {
        Favorite fav = favoriteRepository.findByUser_IdAndCar_Id(requester.getId(), carId)
                .orElseThrow(() -> new RuntimeException("Favorito no encontrado."));
        favoriteRepository.delete(fav);
    }

    @Override
    public List<Favorite> listMy(User requester) {
        return favoriteRepository.findByUser_Id(requester.getId());
    }

    @Override
    public boolean isFavorite(Long carId, User requester) {
        return favoriteRepository.existsByUser_IdAndCar_Id(requester.getId(), carId);
    }
}
