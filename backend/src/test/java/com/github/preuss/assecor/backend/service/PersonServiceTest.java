package com.github.preuss.assecor.backend.service;

import com.github.preuss.assecor.backend.model.Person;
import com.github.preuss.assecor.backend.repository.PersonRepository;
import com.github.preuss.assecor.backend.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void getAllPersons_returnsAllPersonsFromRepository() {
        List<Person> persons = List.of(
                new Person(1, "Hans", "Müller", "67742", "Lauterecken", "blau"),
                new Person(2, "Peter", "Petersen", null, null, "grün")
        );

        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personService.getAllPersons();

        assertThat(result)
                .hasSize(2)
                .containsExactlyElementsOf(persons);

        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void getPersonById_returnsPerson_whenFound() {
        Person person = new Person(1, "Hans", "Müller", null, null, "blau");

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.getPersonById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("Hans");

        verify(personRepository).findById(1L);
    }


    @Test
    void getPersonById_returnsEmpty_whenNotFound() {
        when(personRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Person> result = personService.getPersonById(99L);

        assertThat(result).isEmpty();

        verify(personRepository).findById(99L);
    }

    @Test
    void getPersonsByColor_returnsMatchingPersons() {
        List<Person> persons = List.of(
                new Person(1, "Hans", "Müller", null, null, "blau")
        );

        when(personRepository.findByColor("blau")).thenReturn(persons);

        List<Person> result = personService.getPersonsByColor("blau");

        assertThat(result)
                .hasSize(1)
                .allMatch(p -> "blau".equals(p.getFavoriteColor()));

        verify(personRepository).findByColor("blau");
    }

    @Test
    void getPersonsByColor_returnsEmptyList_whenNoMatches() {
        when(personRepository.findByColor("pink")).thenReturn(List.of());

        List<Person> result = personService.getPersonsByColor("pink");

        assertThat(result).isEmpty();

        verify(personRepository).findByColor("pink");
    }

}
