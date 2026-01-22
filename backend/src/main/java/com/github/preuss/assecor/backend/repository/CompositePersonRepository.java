package com.github.preuss.assecor.backend.repository;

import com.github.preuss.assecor.backend.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CompositePersonRepository implements PersonRepository {

    private final CsvPersonRepository csvRepository;
    private final InMemoryPersonRepository memoryRepository;

    public CompositePersonRepository(
            CsvPersonRepository csvRepository,
            InMemoryPersonRepository memoryRepository
    ) {
        this.csvRepository = csvRepository;
        this.memoryRepository = memoryRepository;
    }

    @Override
    public List<Person> findAll() {
        return Stream.concat(
                        csvRepository.findAll().stream(),
                        memoryRepository.findAll().stream()
                )
                .sorted(Comparator.comparingLong(Person::getId))
                .toList();
    }

    @Override
    public Optional<Person> findById(long id) {
        return memoryRepository.findById(id)
                .or(() -> csvRepository.findById(id));
    }

    @Override
    public List<Person> findByColor(String color) {
        return Stream.concat(
                        csvRepository.findByColor(color).stream(),
                        memoryRepository.findByColor(color).stream()
                )
                .sorted(Comparator.comparingLong(Person::getId))
                .toList();
    }

    @Override
    public Person save(Person person) {
        return memoryRepository.save(person);
    }
}
