package util;

public class ValidationUtils {

    public boolean isValidName(String name) {
        return name != null && !name.isBlank() && name.matches("^[a-zA-Z]+$");
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches(".+@.+\\..+");
    }

    public boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]+") && phone.length() >= 8;
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}