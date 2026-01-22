package com.github.preuss.assecor.backend.service;

import com.github.preuss.assecor.backend.model.Person;
import com.github.preuss.assecor.backend.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(long id){
        return personRepository.findById(id);
    }

    public List<Person> getPersonsByColor(String color){
        return personRepository.findByColor(color);
    }

    public Person create(Person incoming) {
        long nextId = personRepository.findAll().stream()
                .mapToLong(Person::getId)
                .max()
                .orElse(0L) + 1;

        Person created = new Person(
                nextId,
                incoming.getFirstName(),
                incoming.getLastName(),
                incoming.getZipCode(),
                incoming.getCity(),
                incoming.getFavoriteColor()
        );

        return personRepository.save(created);
    }
}
