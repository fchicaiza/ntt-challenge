package com.ntt.banking.domain.customer;

import java.util.Objects;

public class Customer {
    private final CustomerId id;
    private String name;
    private String gender;
    private final String identification;
    private String address;
    private String phone;
    private String password;
    private boolean active;

    public Customer(
            CustomerId id,
            String name,
            String gender,
            String identification,
            String address,
            String phone,
            String password,
            boolean active) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.gender = gender;
        this.identification = identification;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.active = active;
    }

    public static Customer create(
            String name,
            String gender,
            String identification,
            String address,
            String phone,
            String password) {
        return new Customer(
                CustomerId.generate(),
                name,
                gender,
                identification,
                address,
                phone,
                password,
                true);
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(String name, String gender, String address, String phone, String password) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    // getters
    public CustomerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getIdentification() {
        return identification;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }
}
