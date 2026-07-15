package com.digitalhouse.rentacarnow.controller;

import org.springframework.stereotype.Controller;

import com.digitalhouse.rentacarnow.service.ReservationService;

@Controller
public class ReservationController {
  private final ReservationService reservationService;
  
  public ReservationController(ReservationService reservationService){
    this.reservationService = reservationService;
  }
}
