package repository;

import model.Room;
import java.util.List;

public interface RoomRepository {

    void save(Room room);

    Room findByRoomNumber(String roomNumber);

    List<Room> findAll();
}
