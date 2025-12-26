package model;

public class Pharmacist {
    private String name;
    private String email;
    private String password;

    public Pharmacist(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}