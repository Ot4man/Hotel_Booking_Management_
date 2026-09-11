package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private String reservationCode;
    private UUID userId;
    private String roomNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;
    private long numberOfNights;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDateTime createdAt;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(long numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Reservation(UUID id, LocalDateTime createdAt, BigDecimal totalPrice, ReservationStatus status, long numberOfNights, int numberOfGuests, LocalDate checkOut, LocalDate checkIn, String roomNumber, UUID userId, String reservationCode) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.status = status;
        this.numberOfNights = numberOfNights;
        this.numberOfGuests = numberOfGuests;
        this.checkOut = checkOut;
        this.checkIn = checkIn;
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.reservationCode = reservationCode;
    }
}