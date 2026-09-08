package model;

import java.util.UUID;

public class User {

    private UUID id;
    private String fullname;
    private String email;
    private String phone;
    private String password;

    public User(String fullname, String phone, String email, String password) {
        this.id = UUID.randomUUID();
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
