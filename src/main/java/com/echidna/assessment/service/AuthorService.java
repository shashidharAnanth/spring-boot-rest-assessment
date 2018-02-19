package com.echidna.assessment.service;

import java.util.List;

import com.echidna.assessment.domain.Author;

public interface AuthorService {

  public Author getAuthor(Integer customerId);

  public List<Author> listAll();
}
