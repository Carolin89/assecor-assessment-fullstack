package com.github.preuss.assecor.backend.controller;

import com.github.preuss.assecor.backend.model.Person;
import com.github.preuss.assecor.backend.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/persons", produces = "application/json")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    /**
     * GET /persons
     */
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    /**
     * GET /persons/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /persons/color/{color}
     */
    @GetMapping("/color/{color}")
    public List<Person> getPersonsByColor(@PathVariable String color) {
        return personService.getPersonsByColor(color);
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person created = personService.create(person);
        return ResponseEntity.status(201).body(created);
    }


}
