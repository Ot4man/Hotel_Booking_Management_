package model;

import java.math.BigDecimal;

public class Room {
    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal pricePerNight;
    private RoomStatus status;

    public Room(String roomNumber, RoomStatus status, BigDecimal pricePerNight, int capacity, RoomType type) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.type = type;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + "\n" +
                "Type: " + type + "\n" +
                "Capacity: " + capacity + "\n" +
                "Price/night: " + pricePerNight + " MAD\n" +
                "Status: " + status + "\n";
    }
}