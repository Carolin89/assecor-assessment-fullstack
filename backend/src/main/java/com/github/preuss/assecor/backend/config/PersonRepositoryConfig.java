package com.github.preuss.assecor.backend.config;

import com.github.preuss.assecor.backend.repository.PersonRepository;
import com.github.preuss.assecor.backend.repository.csv.CsvPersonRepository;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class PersonRepositoryConfig {

        @Bean
        public PersonRepository personRepository(
                @Value("classpath:sample-input.csv") Resource csvResource
        ) throws IOException {

            return new CsvPersonRepository(csvResource.getInputStream());
        }
    }
