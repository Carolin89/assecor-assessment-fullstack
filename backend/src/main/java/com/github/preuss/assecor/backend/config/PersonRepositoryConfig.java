package com.github.preuss.assecor.backend.config;

import com.github.preuss.assecor.backend.repository.CompositePersonRepository;
import com.github.preuss.assecor.backend.repository.InMemoryPersonRepository;
import com.github.preuss.assecor.backend.repository.PersonRepository;
import com.github.preuss.assecor.backend.repository.CsvPersonRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PersonRepositoryConfig {

     @Bean
        public CsvPersonRepository csvPersonRepository(
                @Value("classpath:sample-input.csv") Resource csvResource
        ) throws IOException {
            return new CsvPersonRepository(csvResource.getInputStream());
        }

        @Bean
        public InMemoryPersonRepository inMemoryPersonRepository() {
            return new InMemoryPersonRepository();
        }

        @Bean
        public PersonRepository personRepository(
                CsvPersonRepository csvRepository,
                InMemoryPersonRepository memoryRepository
        ) {
            return new CompositePersonRepository(csvRepository, memoryRepository);
        }
    }


