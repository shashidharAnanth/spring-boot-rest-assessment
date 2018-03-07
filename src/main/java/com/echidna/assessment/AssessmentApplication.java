package com.echidna.assessment;

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

	static List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days"),
			new Author(2, "Rabindranath Tagore", "Author of Gitanjali"),
			new Author(3, "Leo Tolstoy", "Russian writer"),
			new Author(4, "Premchand", "Author in Hindi-Urdu"));

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
