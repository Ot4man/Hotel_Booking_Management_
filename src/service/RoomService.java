package service;

import model.Room;
import repository.RoomRepository;
import repository.impl.InMemoryRoomRepository;

import java.util.List;

public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void addRoom(Room room) {

        if (room == null) {
            System.out.println("Room cannot be null");
            return;
        }

        if (roomRepository.findByRoomNumber(room.getRoomNumber()) != null) {
            System.out.println("Room already exists");
            return;
        }

        roomRepository.save(room);
        System.out.println("Room added successfully");
    }

    public Room findRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room == null) {
            throw new exception.RoomNotFoundException("Room " + roomNumber + " not found");
        }
        return room;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();

    }
}
