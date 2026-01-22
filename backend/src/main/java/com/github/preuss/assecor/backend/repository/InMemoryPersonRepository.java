package com.github.preuss.assecor.backend.repository;

import com.github.preuss.assecor.backend.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPersonRepository implements PersonRepository{
    private final List<Person> persons = new ArrayList<>();

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Optional<Person> findById(long id) {
        return persons.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public List<Person> findByColor(String color) {
        return persons.stream()
                .filter(p -> p.getFavoriteColor() != null)
                .filter(p -> p.getFavoriteColor().equalsIgnoreCase(color))
                .toList();
    }

    @Override
    public Person save(Person person) {
        persons.add(person);
        return person;
    }
}
