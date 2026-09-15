package repository.impl;

import model.Room;
import repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRoomRepository implements RoomRepository {

    private List<Room> rooms = new ArrayList<>();

    public void save(Room room) {
        rooms.add(room);
    }

    @Override
    public Room findByRoomNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        return rooms;
    }

}