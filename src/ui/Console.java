package ui;

import service.AuthService;
import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;

import java.util.Scanner;

public class Console {
    private AuthService authService;
    private Scanner scanner;

    public Console(AuthService authService) {
        this.authService = authService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
            System.out.println("         Hotel Booking");
            System.out.println("¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
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

    private void register() {
        System.out.println("\n-------- REGISTER -----");
        System.out.print("Full name: ");
        String fullname = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            authService.register(fullname, phone, email, password);
            System.out.println("Registration successful");
        } catch (EmailAlreadyExistsException | IllegalArgumentException e) {
            System.err.println("Registration failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("\n--------- LOGIN -----");
        System.out.print("Email: ");
        String loginEmail = scanner.nextLine();
        System.out.print("Password: ");
        String loginPassword = scanner.nextLine();

        try {
            authService.login(loginEmail, loginPassword);
            System.out.println("Login successful");
            System.out.println("Welcome " + authService.getCurrentUser().getFullname());


        } catch (InvalidCredentialsException e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }
}
