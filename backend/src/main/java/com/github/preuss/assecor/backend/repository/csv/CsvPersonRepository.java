package com.github.preuss.assecor.backend.repository.csv;

import org.springframework.stereotype.Repository;

import com.github.preuss.assecor.backend.model.FavoriteColor;
import com.github.preuss.assecor.backend.model.Person;
import com.github.preuss.assecor.backend.repository.PersonRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CSV-based implementation of {@link PersonRepository}.
 *
 * Each physical line in the CSV file is treated as a separate record.
 * Incomplete records are tolerated and parsed with missing fields set to null.
 * No attempt is made to repair or merge broken CSV lines.
 */



public class CsvPersonRepository implements PersonRepository {

    private List<Person> persons;


    public CsvPersonRepository(InputStream csvInputStream) {
        this.persons = List.copyOf(loadCsv(csvInputStream));
    }

    @Override
    public List<Person> findAll(){
        return persons.stream()
                .sorted(Comparator.comparingLong(Person::getId))
                .toList();
    }

    @Override
    public Optional<Person> findById(long id){
        return persons.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public List<Person> findByColor(String color){
        if (color == null || color.isBlank()) {
            return List.of();
        }
        return persons.stream()
                .filter(p -> p.getFavoriteColor() != null)
                .filter(p -> p.getFavoriteColor().equalsIgnoreCase(color))
                .sorted(Comparator.comparingLong(Person::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Person save(Person person){
        throw new UnsupportedOperationException("CSV repository is read-only");
    }

    // --------------CSV Parsing--------------------

    private List<Person> loadCsv(InputStream inputStream) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {
            List<Person> result = new ArrayList<>();
            String line;
            long lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                parseLine(line, lineNumber++).ifPresent(result::add);
            }
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("Could not read CSV file", e);
        }
    }

    private Optional<Person> parseLine(String line, long id){
        if (line == null || line.isBlank()) {
            return Optional.empty();
        }

        String[] parts = line.split(",", -1);

        // We need at least: last name and first name
        if (parts.length < 2) {
            return Optional.empty();
        }
        String lastName = parts[0].trim();
        String firstName = parts[1].trim();

        if (lastName.isEmpty() || firstName.isEmpty()) {
            return Optional.empty();
        }
        String zip = null;
        String city = null;
        if (parts.length >= 3) {
            String[] zipCity = parts[2].trim().split(" ", 2);
            if (zipCity.length == 2) {
                zip = zipCity[0];
                city = zipCity[1];
            }
        }

        String color = null;
        if (parts.length >= 4) {
            try {
                Integer colorId = Integer.valueOf(parts[3].trim());
                color = FavoriteColor.fromId(colorId);
            } catch (NumberFormatException ignored) {
                // invalid color id -> color stays null
            }
        }
        return Optional.of(new Person(
                id,
                firstName,
                lastName,
                zip,
                city,
                color
        ));
    }


}
