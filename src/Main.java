import service.AuthService;
import java.util.Scanner;
import model.Room;
import model.RoomStatus;
import model.RoomType;
import service.AuthService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();

        boolean running = true;


        Room room = new Room("105",RoomStatus.AVAILABLE,new BigDecimal("4000"),4,RoomType.DOUBLE);
        System.out.println("\n===== ROOM TEST =====");
        System.out.println("Room number: " + room.getRoomNumber());
        System.out.println("Type: " + room.getType());
        System.out.println("Capacity: " + room.getCapacity());
        System.out.println("Price: " + room.getPricePerNight() + " MAD");
        System.out.println("Status: " + room.getStatus());

        while (running) {

            System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
            System.out.println("         Hotel Booking");
            System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume Enter

            switch (choice) {

                case 1:

                    System.out.println("\n-------- REGISTER -----");

                    System.out.print("Full name: ");
                    String fullname = scanner.nextLine();

                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    authService.register(
                            fullname,
                            phone,
                            email,
                            password
                    );

                    break;


                case 2:

                    System.out.println("\n--------- LOGIN -----");

                    System.out.print("Email: ");
                    String loginEmail = scanner.nextLine();

                    System.out.print("Password: ");
                    String loginPassword = scanner.nextLine();

                    authService.login(
                            loginEmail,
                            loginPassword
                    );


                    break;


                case 0:

                    System.out.println("Goodbye");
                    running = false;

                    break;


                default:

                    System.out.println("Invalid choice");

                    break;
            }
        }

        scanner.close();
    }
}

