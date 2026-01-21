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
}
