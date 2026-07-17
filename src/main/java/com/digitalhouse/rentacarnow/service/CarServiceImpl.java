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
        return carRepository.findAll();
    }

    @Override
    public Car findByPlate(String plate) {
        return carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
    }

    @Override
    public Car createCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        Car car = new Car();
        car.setPlate(plate);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        return carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    @Override
    public Car updateCar(String plate, String brand, String model, Integer year, Double pricePerDay, Double pricePerHour, Boolean available) {
        Car car = carRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Car not found with plate: " + plate));
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setPricePerDay(pricePerDay);
        car.setPricePerHour(pricePerHour);
        car.setAvailable(available);
        return carRepository.save(car);
    }
}
