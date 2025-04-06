package com.gox.domain.entity;

public class User {
    private Long id;            // PK in our database
    private String name;
    private String email;       // Unique email (key)
    private UserRole role;      // ADMIN, CUSTOMER, ...
    private LoyaltyLevel loyaltyLevel; // STANDARD, SILVER, GOLD
    private String address;     // e.g. postal address
    private String phone;       // phone number

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LoyaltyLevel getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public void setLoyaltyLevel(LoyaltyLevel loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
