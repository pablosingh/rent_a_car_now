package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Car;
import com.digitalhouse.rentacarnow.entity.Reservation;
import com.digitalhouse.rentacarnow.entity.ReservationStatus;
import com.digitalhouse.rentacarnow.entity.User;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.ReservationRepository;
import com.digitalhouse.rentacarnow.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Duration BUFFER = Duration.ofHours(1);
    private static final Duration DISCOUNT_THRESHOLD = Duration.ofHours(48);
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public ReservationServiceImpl(ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository, EntityManager entityManager) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Reservation> findAll(User requester) {
        String role = requester.getRole();
        if ("ADMIN".equals(role)) {
            return reservationRepository.findAll();
        }
        if ("OWNER".equals(role)) {
            return reservationRepository.findByCar_Owner_Id(requester.getId());
        }
        if ("EMPLOYEE".equals(role)) {
            if (requester.getOwner() == null) {
                throw new AccessDeniedException("Tu cuenta de EMPLOYEE no tiene OWNER asignado.");
            }
            return reservationRepository.findByCar_Owner_Id(requester.getOwner().getId());
        }
        return findMy(requester);
    }

    @Override
    public List<Reservation> findMy(User requester) {
        return reservationRepository.findByUserId(requester.getId());
    }

    @Override
    public Page<Reservation> findHistory(Long userId, ReservationStatus status, Instant from, Instant to, Pageable pageable, User requester) {
        Long targetUserId = userId != null ? userId : requester.getId();
        if (!targetUserId.equals(requester.getId()) && !"ADMIN".equals(requester.getRole())) {
            throw new AccessDeniedException("Solo ADMIN puede ver el historial de otro usuario.");
        }
        if (from != null && to != null && from.isAfter(to)) {
            throw new ConflictException("from debe ser anterior a to.");
        }
        if (!userRepository.existsById(targetUserId)) {
            throw new RuntimeException("User not found with id: " + targetUserId);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("user").get("id"), targetUserId));
        if (status != null) predicates.add(cb.equal(root.get("status"), status));
        if (from != null) predicates.add(cb.greaterThanOrEqualTo(root.get("startAt"), from));
        if (to != null) predicates.add(cb.lessThanOrEqualTo(root.get("startAt"), to));
        cq.where(predicates.toArray(new Predicate[0]));

        if (pageable.getSort().isSorted()) {
            List<jakarta.persistence.criteria.Order> jakartaOrders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                String prop = order.getProperty();
                if (order.isAscending()) {
                    jakartaOrders.add(cb.asc(root.get(prop)));
                } else {
                    jakartaOrders.add(cb.desc(root.get(prop)));
                }
            }
            cq.orderBy(jakartaOrders);
        } else {
            cq.orderBy(cb.desc(root.get("startAt")));
        }

        TypedQuery<Reservation> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Reservation> content = query.getResultList();

        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Reservation> countRoot = countCq.from(Reservation.class);
        countCq.select(cb.count(countRoot));
        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.equal(countRoot.get("user").get("id"), targetUserId));
        if (status != null) countPredicates.add(cb.equal(countRoot.get("status"), status));
        if (from != null) countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("startAt"), from));
        if (to != null) countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("startAt"), to));
        countCq.where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<com.digitalhouse.rentacarnow.dto.AvailabilitySlot> findAvailability(Long carId, Instant from, Instant to) {
        if (!carRepository.existsById(carId)) {
            throw new RuntimeException("Car not found with id: " + carId);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("car").get("id"), carId));
        predicates.add(cb.notEqual(root.get("status"), ReservationStatus.CANCELLED));
        if (from != null) predicates.add(cb.greaterThan(root.get("endAt"), from));
        if (to != null) predicates.add(cb.lessThan(root.get("startAt"), to));
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("startAt")));
        List<Reservation> list = entityManager.createQuery(cq).getResultList();
        return list.stream().map(com.digitalhouse.rentacarnow.dto.AvailabilitySlot::from).toList();
    }

    @Override
    public Reservation findById(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        return reservation;
    }

    @Override
    @Transactional
    public Reservation createReservation(Instant startAt, Instant endAt, Long car_id, Long user_id, User requester) {
        if ("USER".equals(requester.getRole()) && !requester.getId().equals(user_id)) {
            throw new AccessDeniedException("Un usuario solo puede reservar para sí mismo.");
        }
        validateDates(startAt, endAt);
        Car car = carRepository.findById(car_id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + car_id));
        if (Boolean.FALSE.equals(car.getAvailable())) {
            throw new ConflictException("El auto no está disponible para reservas.");
        }
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        checkOverlap(car_id, startAt, endAt, null);

        BigDecimal totalPrice = calculateTotalPrice(car.getPricePerHour(), startAt, endAt);

        Reservation reservation = new Reservation();
        reservation.setStartAt(startAt);
        reservation.setEndAt(endAt);
        reservation.setTotalPrice(totalPrice);
        reservation.setCreatedAt(Instant.now());
        reservation.setCar(car);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationById(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Reservation dispatchReservation(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        assertStaff(requester);
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ConflictException("Solo reservas PENDING pueden despacharse. Estado actual: " + reservation.getStatus());
        }
        reservation.setStatus(ReservationStatus.DISPATCHED);
        reservation.setDispatchedAt(Instant.now());
        reservation.setDispatchedBy(requester);
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation completeReservation(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        assertStaff(requester);
        if (reservation.getStatus() != ReservationStatus.DISPATCHED) {
            throw new ConflictException("Solo reservas DISPATCHED pueden completarse. Estado actual: " + reservation.getStatus());
        }
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.setCompletedAt(Instant.now());
        reservation.setCompletedBy(requester);
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation cancelReservation(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        if (reservation.getStatus() == ReservationStatus.COMPLETED || reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ConflictException("No se puede cancelar una reserva " + reservation.getStatus());
        }
        if ("USER".equals(requester.getRole()) && reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ConflictException("Como USER solo podés cancelar reservas PENDING. Estado actual: " + reservation.getStatus());
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancelledAt(Instant.now());
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation revertReservation(Long id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        assertStaff(requester);
        if (reservation.getStatus() == ReservationStatus.DISPATCHED) {
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setDispatchedAt(null);
            reservation.setDispatchedBy(null);
            return reservationRepository.save(reservation);
        }
        if (reservation.getStatus() == ReservationStatus.COMPLETED) {
            reservation.setStatus(ReservationStatus.DISPATCHED);
            reservation.setCompletedAt(null);
            reservation.setCompletedBy(null);
            return reservationRepository.save(reservation);
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setCancelledAt(null);
            return reservationRepository.save(reservation);
        }
        throw new ConflictException("Solo reservas DISPATCHED/COMPLETED/CANCELLED pueden revertirse. Estado actual: " + reservation.getStatus());
    }

    private void assertStaff(User requester) {
        String role = requester.getRole();
        if ("OWNER".equals(role) || "EMPLOYEE".equals(role) || "ADMIN".equals(role)) {
            return;
        }
        throw new AccessDeniedException("Solo OWNER, EMPLOYEE o ADMIN pueden realizar esta acción.");
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long id, Instant startAt, Instant endAt, Long car_id, Long user_id, User requester) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        assertCanManage(reservation, requester);
        if ("USER".equals(requester.getRole()) && !requester.getId().equals(user_id)) {
            throw new AccessDeniedException("Un usuario solo puede reservar para sí mismo.");
        }
        validateDates(startAt, endAt);
        Car car = carRepository.findById(car_id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + car_id));
        if (Boolean.FALSE.equals(car.getAvailable())) {
            throw new ConflictException("El auto no está disponible para reservas.");
        }
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

        checkOverlap(car_id, startAt, endAt, id);

        BigDecimal totalPrice = calculateTotalPrice(car.getPricePerHour(), startAt, endAt);

        reservation.setStartAt(startAt);
        reservation.setEndAt(endAt);
        reservation.setTotalPrice(totalPrice);
        reservation.setCar(car);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }

    private void validateDates(Instant startAt, Instant endAt) {
        if (startAt == null || endAt == null) {
            throw new ConflictException("startAt y endAt son obligatorios.");
        }
        if (!endAt.isAfter(startAt)) {
            throw new ConflictException("endAt debe ser posterior a startAt.");
        }
        if (startAt.isBefore(Instant.now())) {
            throw new ConflictException("La reserva no puede iniciar en el pasado.");
        }
        if (startAt.getEpochSecond() % 1800 != 0 || endAt.getEpochSecond() % 1800 != 0) {
            throw new ConflictException("Las reservas son cada media hora (minutos 00 o 30).");
        }
        Duration duration = Duration.between(startAt, endAt);
        if (duration.toMinutes() < 60) {
            throw new ConflictException("La duración mínima es de 1 hora.");
        }
    }

    private void checkOverlap(Long carId, Instant startAt, Instant endAt, Long excludeId) {
        Instant effectiveStart = startAt.minus(BUFFER);
        Instant effectiveEnd = endAt.plus(BUFFER);
        boolean overlap = reservationRepository.existsOverlapWithBuffer(carId, effectiveStart, effectiveEnd, excludeId);
        if (overlap) {
            throw new ConflictException("El auto ya tiene una reserva en ese rango (incluye 1h de limpieza).");
        }
    }

    private BigDecimal calculateTotalPrice(Double pricePerHour, Instant startAt, Instant endAt) {
        if (pricePerHour == null) {
            throw new ConflictException("El auto no tiene tarifa por hora configurada.");
        }
        long minutes = Duration.between(startAt, endAt).toMinutes();
        long hours = (minutes + 59) / 60;
        if (hours <= 0) hours = 1;
        BigDecimal price = BigDecimal.valueOf(pricePerHour).multiply(BigDecimal.valueOf(hours));
        Duration duration = Duration.between(startAt, endAt);
        if (duration.compareTo(DISCOUNT_THRESHOLD) > 0) {
            BigDecimal discount = price.multiply(DISCOUNT_RATE);
            price = price.subtract(discount);
        }
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    private void assertCanManage(Reservation reservation, User requester) {
        String role = requester.getRole();
        if ("ADMIN".equals(role)) {
            return;
        }
        if ("USER".equals(role)) {
            if (reservation.getUser() != null && reservation.getUser().getId().equals(requester.getId())) {
                return;
            }
            throw new AccessDeniedException("No tenés permiso para gestionar esta reserva.");
        }
        Long carOwnerId = reservation.getCar() != null && reservation.getCar().getOwner() != null
                ? reservation.getCar().getOwner().getId()
                : null;
        if ("OWNER".equals(role) && carOwnerId != null && carOwnerId.equals(requester.getId())) {
            return;
        }
        if ("EMPLOYEE".equals(role)
                && requester.getOwner() != null
                && carOwnerId != null
                && carOwnerId.equals(requester.getOwner().getId())) {
            return;
        }
        throw new AccessDeniedException("No tenés permiso para gestionar esta reserva.");
    }
}
