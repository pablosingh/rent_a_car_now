package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.service.CarService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }
}
