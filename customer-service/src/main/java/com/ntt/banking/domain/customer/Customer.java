package com.ntt.banking.domain.customer;

import java.util.Objects;

public class Customer {
    private final CustomerId id;
    private String name;
    private String gender;
    private final String identification;
    private String address;
    private String phone;
    private boolean active;

    public Customer(
            CustomerId id,
            String name,
            String gender,
            String identification,
            String address,
            String phone,
            boolean active) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.gender = gender;
        this.identification = identification;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }

    public static Customer create(
            String name,
            String gender,
            String identification,
            String address,
            String phone) {
        return new Customer(
                CustomerId.generate(),
                name,
                gender,
                identification,
                address,
                phone,
                true);
    }

    public void deactivate() {
        this.active = false;
    }

    public void updateContact(String address, String phone) {
        this.address = address;
        this.phone = phone;
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

    public boolean isActive() {
        return active;
    }
}
