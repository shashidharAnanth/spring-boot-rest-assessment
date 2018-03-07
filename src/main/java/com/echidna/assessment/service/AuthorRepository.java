package com.echidna.assessment.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.echidna.assessment.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

  List<Author> findByName(String fullName);
}
