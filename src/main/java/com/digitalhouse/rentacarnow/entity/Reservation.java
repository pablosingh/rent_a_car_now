package com.digitalhouse.rentacarnow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "timestamptz")
  private Instant startAt;

  @Column(nullable = false, columnDefinition = "timestamptz")
  private Instant endAt;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalPrice;

  @Column(nullable = false, columnDefinition = "timestamptz")
  private Instant createdAt = Instant.now();

  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
