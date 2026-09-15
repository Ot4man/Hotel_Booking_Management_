import repository.UserRepository;
import repository.RoomRepository;
import repository.ReservationRepository;

import repository.impl.InMemoryRoomRepository;
import repository.impl.InMemoryReservationRepository;

import service.AuthService;
import service.RoomService;
import service.ReservationService;

import ui.Console;
import util.DataSet;


public class Main {

    public static void main(String[] args) {
        //Repositories
        UserRepository userRepository = new UserRepository();
        RoomRepository roomRepository = new InMemoryRoomRepository();
        ReservationRepository reservationRepository =
                new InMemoryReservationRepository();
        //Services
        AuthService authService = new AuthService(userRepository);
        RoomService roomService = new RoomService(roomRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, roomRepository);
        // Initialize data
        DataSet.initializer(userRepository, roomRepository);
        // ui menu
        Console consoleUI = new Console(authService, roomService, reservationService);
        consoleUI.start();
    }
}


