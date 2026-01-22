package com.github.preuss.assecor.backend.repository.csv;

import com.github.preuss.assecor.backend.model.Person;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvPersonRepositoryTest{

    @Test
    void loads_all_tolerable_records_from_csv() throws Exception {
        Path csv = Path.of("src/test/resources/test-input.csv");
        CsvPersonRepository repo = createRepository();

        List<Person> persons = repo.findAll();

        assertThat(persons).hasSize(11);
    }


    @Test
    void findByColor_filters_case_insensitive() throws Exception{
        Path csv = Path.of("src/test/resources/test-input.csv");
        CsvPersonRepository repo = createRepository();

        List<Person> result = repo.findByColor("grün");

        assertThat(result).isNotEmpty();
        assertThat(result)
                .allMatch(p -> "grün".equalsIgnoreCase(p.getFavoriteColor()));
    }


    @Test
    void findByColor_returns_empty_list_for_unknown_color() throws Exception{
        Path csv = Path.of("src/test/resources/test-input.csv");
        CsvPersonRepository repo = createRepository();

        List<Person> result = repo.findByColor("pink");

        assertThat(result).isEmpty();
    }

    @Test
    void findById_returns_person_when_present() throws Exception{
        Path csv = Path.of("src/test/resources/test-input.csv");
        CsvPersonRepository repo = createRepository();

        Person person = repo.findById(1).orElseThrow();

        assertThat(person.getFirstName()).isEqualTo("Hans");
    }

    @Test
    void findById_returns_empty_when_not_present() throws Exception {
        Path csv = Path.of("src/test/resources/test-input.csv");
        CsvPersonRepository repo = createRepository();

        assertThat(repo.findById(999)).isEmpty();
    }

    private CsvPersonRepository createRepository() throws Exception {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("test-input.csv");

        assertThat(is).isNotNull();
        return new CsvPersonRepository(is);
    }
}
