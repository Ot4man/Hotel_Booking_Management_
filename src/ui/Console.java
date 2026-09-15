package ui;

import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;
import exception.InvalidReservationDateException;
import exception.ReservationAlreadyCancelledException;
import exception.ReservationNotFoundException;
import exception.RoomCapacityExceededException;
import exception.RoomNotFoundException;
import exception.RoomUnavailableException;
import exception.UnauthorizedReservationAccessException;
import model.Reservation;
import model.Room;
import service.AuthService;
import service.ReservationService;
import service.RoomService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Console {

    private final AuthService authService;
    private final Scanner scanner;
    private final RoomService roomService;
    private final ReservationService reservationService;

    public Console(
            AuthService authService,
            RoomService roomService,
            ReservationService reservationService
    ) {
        this.authService = authService;
        this.scanner = new Scanner(System.in);
        this.roomService = roomService;
        this.reservationService = reservationService;
    }


    // START


    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n==================================");
            System.out.println("          HOTEL BOOKING");
            System.out.println("==================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
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
    }


    // REGISTER


    private void register() {

        System.out.println("\n-------- REGISTER --------");

        System.out.print("Full name: ");
        String fullname = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {

            authService.register(
                    fullname,
                    phone,
                    email,
                    password
            );

            System.out.println("Registration successful");

        } catch (EmailAlreadyExistsException | IllegalArgumentException e) {

            System.err.println(
                    "Registration failed: " + e.getMessage()
            );
        }
    }


    // LOGIN


    private void login() {

        System.out.println("\n--------- LOGIN ---------");

        System.out.print("Email: ");
        String loginEmail = scanner.nextLine();

        System.out.print("Password: ");
        String loginPassword = scanner.nextLine();

        try {

            authService.login(
                    loginEmail,
                    loginPassword
            );

            System.out.println("Login successful");

            System.out.println(
                    "Welcome "
                            + authService.getCurrentUser().getFullname()
            );

            loggedInMenu();

        } catch (InvalidCredentialsException e) {

            System.err.println(
                    "Login failed: " + e.getMessage()
            );
        }
    }


    // LOGGED IN MENU


    private void loggedInMenu() {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n==================================");
            System.out.println("          USER DASHBOARD");
            System.out.println("==================================");
            System.out.println("1. View all rooms");
            System.out.println("2. Find room by number");
            System.out.println("3. Create reservation");
            System.out.println("4. My reservations");
            System.out.println("5. Update reservation");
            System.out.println("6. Cancel reservation");
            System.out.println("7. Update profile");
            System.out.println("8. Change password");
            System.out.println("9. Logout");

            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    viewAllRooms();
                    break;

                case 2:
                    findRoom();
                    break;

                case 3:
                    createReservation();
                    break;

                case 4:
                    showMyReservations();
                    break;

                case 5:
                    updateReservation();
                    break;

                case 6:
                    cancelReservation();
                    break;

                case 7:
                    updateProfile();
                    break;

                case 8:
                    changePassword();
                    break;

                case 9:
                    authService.logout();
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


    // VIEW ALL ROOMS


    private void viewAllRooms() {

        System.out.println("\n---------- ALL ROOMS ----------");

        List<Room> rooms = roomService.getAllRooms();

        for (Room room : rooms) {
            System.out.println(room);
        }
    }


    // FIND ROOM


    private void findRoom() {

        System.out.println("\n---------- FIND ROOM ----------");

        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        try {

            Room foundRoom =
                    roomService.findRoom(roomNumber);

            System.out.println("\nRoom found:");
            System.out.println(foundRoom);

        } catch (RoomNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }


    // CREATE RESERVATION


    private void createReservation() {

        System.out.println("\n------ CREATE RESERVATION ------");

        System.out.print("Room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Check-in date (YYYY-MM-DD): ");
        LocalDate checkIn = readDate();

        System.out.print("Check-out date (YYYY-MM-DD): ");
        LocalDate checkOut = readDate();

        System.out.print("Number of guests: ");
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine();

        try {

            Reservation reservation =
                    reservationService.createReservation(
                            authService.getCurrentUser(),
                            roomNumber,
                            checkIn,
                            checkOut,
                            numberOfGuests
                    );

            System.out.println("\nReservation created successfully");

            System.out.println("--------------------------------");
            System.out.println("Reservation code: "
                    + reservation.getReservationCode());
            System.out.println("Room: "
                    + reservation.getRoomNumber());
            System.out.println("Check-in: "
                    + reservation.getCheckIn());
            System.out.println("Check-out: "
                    + reservation.getCheckOut());
            System.out.println("Guests: "
                    + reservation.getNumberOfGuests());
            System.out.println("Nights: "
                    + reservation.getNumberOfNights());
            System.out.println("Total price: "
                    + reservation.getTotalPrice() + "Dh");
            System.out.println("Status: "
                    + reservation.getStatus());
            System.out.println("--------------------------------");

        } catch (
                RoomNotFoundException
                | RoomUnavailableException
                | RoomCapacityExceededException
                | InvalidReservationDateException e
        ) {

            System.out.println(
                    "Reservation failed: " + e.getMessage()
            );
        }
    }


    // MY RESERVATIONS


    private void showMyReservations() {

        System.out.println("\n------- MY RESERVATIONS -------");

        List<Reservation> reservations =
                reservationService.getMyReservations(
                        authService.getCurrentUser()
                );

        if (reservations.isEmpty()) {

            System.out.println("You have no reservations.");

            return;
        }

        for (Reservation reservation : reservations) {

            System.out.println("--------------------------------");
            System.out.println("Code: "
                    + reservation.getReservationCode());
            System.out.println("Room: "
                    + reservation.getRoomNumber());
            System.out.println("Check-in: "
                    + reservation.getCheckIn());
            System.out.println("Check-out: "
                    + reservation.getCheckOut());
            System.out.println("Guests: "
                    + reservation.getNumberOfGuests());
            System.out.println("Nights: "
                    + reservation.getNumberOfNights());
            System.out.println("Total: "
                    + reservation.getTotalPrice());
            System.out.println("Status: "
                    + reservation.getStatus());
        }

        System.out.println("--------------------------------");
    }


    // UPDATE RESERVATION


    private void updateReservation() {

        System.out.println("\n------- UPDATE RESERVATION -------");

        System.out.print("Reservation code: ");
        String reservationCode = scanner.nextLine();

        System.out.print("New room number: ");
        String newRoomNumber = scanner.nextLine();

        System.out.print("New check-in date (YYYY-MM-DD): ");
        LocalDate newCheckIn = readDate();

        System.out.print("New check-out date (YYYY-MM-DD): ");
        LocalDate newCheckOut = readDate();

        System.out.print("New number of guests: ");
        int newNumberOfGuests = scanner.nextInt();
        scanner.nextLine();

        try {

            Reservation reservation =
                    reservationService.updateReservation(
                            authService.getCurrentUser(),
                            reservationCode,
                            newRoomNumber,
                            newCheckIn,
                            newCheckOut,
                            newNumberOfGuests
                    );

            System.out.println(
                    "\nReservation updated successfully"
            );

            System.out.println("--------------------------------");
            System.out.println("Code: "
                    + reservation.getReservationCode());
            System.out.println("Room: "
                    + reservation.getRoomNumber());
            System.out.println("Check-in: "
                    + reservation.getCheckIn());
            System.out.println("Check-out: "
                    + reservation.getCheckOut());
            System.out.println("Guests: "
                    + reservation.getNumberOfGuests());
            System.out.println("Nights: "
                    + reservation.getNumberOfNights());
            System.out.println("Total: "
                    + reservation.getTotalPrice());
            System.out.println("--------------------------------");

        } catch (
                ReservationNotFoundException
                | UnauthorizedReservationAccessException
                | ReservationAlreadyCancelledException
                | InvalidReservationDateException
                | RoomNotFoundException
                | RoomUnavailableException
                | RoomCapacityExceededException e
        ) {

            System.out.println(
                    "Update failed: " + e.getMessage()
            );
        }
    }


    // CANCEL RESERVATION


    private void cancelReservation() {

        System.out.println("\n------- CANCEL RESERVATION -------");

        System.out.print("Reservation code: ");
        String reservationCode = scanner.nextLine();

        try {

            reservationService.cancelReservation(
                    authService.getCurrentUser(),
                    reservationCode
            );

            System.out.println(
                    "Reservation cancelled successfully."
            );

        } catch (
                ReservationNotFoundException
                | UnauthorizedReservationAccessException
                | ReservationAlreadyCancelledException e
        ) {

            System.out.println(
                    "Cancellation failed: " + e.getMessage()
            );
        }
    }


    // UPDATE PROFILE


    private void updateProfile() {
        System.out.println("\n------- UPDATE PROFILE -------");
        
        System.out.println("Current Name: " + authService.getCurrentUser().getFullname());
        System.out.print("New Full Name: ");
        String fullname = scanner.nextLine();

        System.out.println("Current Phone: " + authService.getCurrentUser().getPhone());
        System.out.print("New Phone: ");
        String phone = scanner.nextLine();

        System.out.println("Current Email: " + authService.getCurrentUser().getEmail());
        System.out.print("New Email: ");
        String email = scanner.nextLine();

        try {
            authService.updateProfile(fullname, phone, email);
            System.out.println("Profile updated successfully.");
        } catch (EmailAlreadyExistsException | IllegalArgumentException e) {
            System.err.println("Profile update failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }


    // CHANGE PASSWORD


    private void changePassword() {
        System.out.println("\n------- CHANGE PASSWORD -------");

        System.out.print("Old Password: ");
        String oldPassword = scanner.nextLine();

        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        try {
            authService.changePassword(oldPassword, newPassword);
            System.out.println("Password changed successfully.");
        } catch (InvalidCredentialsException | IllegalArgumentException e) {
            System.err.println("Password change failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }


    // READ DATE


    private LocalDate readDate() {

        while (true) {

            try {

                return LocalDate.parse(
                        scanner.nextLine()
                );

            } catch (Exception e) {

                System.out.print(
                        "Invalid date. Use YYYY-MM-DD: "
                );
            }
        }
    }
}
