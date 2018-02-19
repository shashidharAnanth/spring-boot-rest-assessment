package com.echidna.assessment.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.echidna.assessment.domain.Author;

@Component
public class AuthorServiceImpl implements AuthorService {

  static List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days"),
      new Author(2, "Rabindranath Tagore", "Author of Gitanjali"),
      new Author(3, "Leo Tolstoy", "Russian writer"),
      new Author(4, "Premchand", "Author in Hindi-Urdu"));

  @Override
  public Author getAuthor(Integer authorId) {
    return authors
        .stream()
        .filter(author -> author.getId().equals(authorId))
        .findFirst()
        .get();
  }

  @Override
  public List<Author> listAll() {
    return authors;
  }
}
