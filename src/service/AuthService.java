package service;

import model.User;
import repository.UserRepository;
import util.ValidationUtils;

import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;

import java.util.Optional;

public class AuthService {

    private UserRepository userRepository;
    private User currentUser;
    private ValidationUtils validationUtils = new ValidationUtils();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String fullname, String phone, String email, String password) throws IllegalArgumentException, EmailAlreadyExistsException {

        if (!validationUtils.isValidName(fullname)) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!validationUtils.isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone");
        }

        if (!validationUtils.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!validationUtils.isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User(fullname, phone, email, password);

        userRepository.save(user);
    }

    public void login(String email, String password) throws InvalidCredentialsException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            currentUser = optionalUser.get();
            return;
        }

        throw new InvalidCredentialsException("Invalid email or password");
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

    public void updateProfile(String fullname, String phone, String email) throws EmailAlreadyExistsException, IllegalArgumentException {
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        if (!validationUtils.isValidName(fullname)) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (!validationUtils.isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone");
        }
        if (!validationUtils.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!currentUser.getEmail().equals(email)) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists");
            }
        }

        currentUser.setFullname(fullname);
        currentUser.setPhone(phone);
        currentUser.setEmail(email);
    }

    public void changePassword(String oldPassword, String newPassword) throws InvalidCredentialsException, IllegalArgumentException {
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        if (!currentUser.getPassword().equals(oldPassword)) {
            throw new InvalidCredentialsException("Incorrect old password");
        }
        if (!validationUtils.isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        
        currentUser.setPassword(newPassword);
    }
}


