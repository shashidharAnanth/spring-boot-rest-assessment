package com.echidna.assessment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.echidna.assessment.domain.Author;
import com.echidna.assessment.service.AuthorRepository;

@SpringBootApplication
public class AssessmentApplication {

	static List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
			new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
			new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
			new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
			new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)));

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(AuthorRepository authorRepository) {
		return args -> {
			for(Author author:authors) {
				authorRepository.save(author);
			}
		};
	}
}
