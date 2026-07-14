package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> findAll() {
        return List.of();
    }

    @Override
    public Car findByPlate(String plate) {
        return null;
    }

    @Override
    public Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        return null;
    }

    @Override
    public void deleteCarById(Long id) {

    }

    @Override
    public Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        return null;
    }
}
