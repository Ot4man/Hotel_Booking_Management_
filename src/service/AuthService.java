package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private List<User> users = new ArrayList<>();

    private User currentUser;

    public void register(String fullname, String phone, String email, String password) {


        for (User user : users) {
            if (user.getEmail().equals(email)) {
                System.out.println("Email already exists");
                return;
            }
        }

        User user = new User(fullname, phone, email, password);

        users.add(user);

        System.out.println("Registration successful");
    }

    public void login(String email, String password) {

        for (User user : users) {

            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)) {

                currentUser = user;

                System.out.println("Login successful");
                System.out.println("Welcome " + user.getFullname());

                return;
            }
        }

        System.out.println("Invalid email or password");
    }

    public void logout() {

        currentUser = null;

        System.out.println("Logout successful");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
