package com.github.preuss.assecor.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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


    @JsonCreator
    public Person(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("zipCode") String zipCode,
            @JsonProperty("city") String city,
            @JsonProperty("favoriteColor") String favoriteColor
    ) {
        this(0L, firstName, lastName, zipCode, city, favoriteColor);
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


