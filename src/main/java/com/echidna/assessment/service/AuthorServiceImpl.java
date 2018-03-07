package com.echidna.assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echidna.assessment.domain.Author;

@Component
public class AuthorServiceImpl implements AuthorService {

  @Autowired
  private AuthorRepository authorRepository;

  @Override
  public Author save(Author author) {
    return authorRepository.saveAndFlush(author);
  }

  @Override
  public Author getAuthor(Integer authorId) {
    return authorRepository.findOne(authorId);
  }

  @Override
  public List<Author> listAll() {
    return authorRepository.findAll();
  }
}
