package com.github.preuss.assecor.backend.model;

public class Person {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String zipCode;
    private final String city;
    private final String favoriteColor;

    public Person(long id,
                  String firstName,
                  String lastName,
                  String zipCode,
                  String city,
                  String favoriteColor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.favoriteColor = favoriteColor;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }
}


