package com.github.preuss.assecor.backend.repository;

import com.github.preuss.assecor.backend.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> findAll();
    Optional<Person> findById(long id);
    List<Person> findByColor(String color);
    Person save(Person person);
}
