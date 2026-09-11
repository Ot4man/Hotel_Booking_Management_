
package util;

import model.Room;
import model.RoomStatus;
import model.RoomType;
import model.User;
import repository.RoomRepository;
import repository.UserRepository;

import java.math.BigDecimal;

public class DataSet {

    public static void initializer(UserRepository userRepository, RoomRepository roomRepository) {
        //User

        User guest1 = new User("otman", "0987654321", "otman@gmail.com", "password123");

        userRepository.save(guest1);

        //  Rooms
        Room room101 = new Room("101", RoomStatus.AVAILABLE, new BigDecimal("100.00"), 1, RoomType.SINGLE);
        Room room102 = new Room("102", RoomStatus.AVAILABLE, new BigDecimal("150.00"), 2, RoomType.DOUBLE);
        Room room201 = new Room("201", RoomStatus.AVAILABLE, new BigDecimal("250.00"), 4, RoomType.SUITE);
        Room room202 = new Room("202", RoomStatus.MAINTENANCE, new BigDecimal("150.00"), 2, RoomType.DOUBLE);

        roomRepository.save(room101);
        roomRepository.save(room102);
        roomRepository.save(room201);
        roomRepository.save(room202);

        System.out.println("Initial data loaded successfully.");
    }
}
