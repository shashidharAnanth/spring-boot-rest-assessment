package com.echidna.assessment.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.echidna.assessment.domain.Author;
import com.echidna.assessment.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AuthorService authorService;

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void givenAuthors_whenGetAuthors_thenReturnJsonArray() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
        new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
        new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
        new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)));

    given(authorService.listAll()).willReturn(authors);

    mvc.perform(get("/authors")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)))
        .andExpect(jsonPath("$[0].name", is(authors.get(0).getName())))
        .andExpect(jsonPath("$[0].description", is(authors.get(0).getDescription())))
        .andExpect(jsonPath("$[0].dateOfBirth", is(authors.get(0).getDateOfBirth().format(
            DateTimeFormatter.ISO_LOCAL_DATE))));
  }

  @Test
  public void givenAuthors_whenGetOneAuthor_thenReturnJsonObject() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
        new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
        new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
        new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)));

    given(authorService.getAuthor(1)).willReturn(authors.get(0));
    given(authorService.getAuthor(2)).willReturn(authors.get(1));
    given(authorService.getAuthor(3)).willReturn(authors.get(2));
    given(authorService.getAuthor(4)).willReturn(authors.get(3));
    given(authorService.getAuthor(5)).willReturn(authors.get(4));

    mvc.perform(get("/authors/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(authors.get(0).getName())))
        .andExpect(jsonPath("$.description", is(authors.get(0).getDescription())))
        .andExpect(jsonPath("$.dateOfBirth", is(authors.get(0).getDateOfBirth().format(
            DateTimeFormatter.ISO_LOCAL_DATE))));

    mvc.perform(get("/authors/5")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(authors.get(4).getName())))
        .andExpect(jsonPath("$.description", is(authors.get(4).getDescription())))
        .andExpect(jsonPath("$.dateOfBirth", is(authors.get(4).getDateOfBirth().format(
            DateTimeFormatter.ISO_LOCAL_DATE))));
  }

  @Test
  public void givenAuthors_whenGetMissingAuthor_thenReturnNotFound() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
        new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
        new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
        new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)));

    given(authorService.getAuthor(1)).willReturn(authors.get(0));
    given(authorService.getAuthor(2)).willReturn(authors.get(1));
    given(authorService.getAuthor(3)).willReturn(authors.get(2));
    given(authorService.getAuthor(4)).willReturn(authors.get(3));
    given(authorService.getAuthor(5)).willReturn(authors.get(4));
    given(authorService.listAll()).willReturn(authors);

    mvc.perform(get("/authors")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));

    mvc.perform(get("/authors/6")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void givenNoAuthors_whenGetAuthors_thenReturnEmptyJsonArray() throws Exception {
    List<Author> authors = Collections.emptyList();

    given(authorService.listAll()).willReturn(authors);

    mvc.perform(get("/authors")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void givenAuthors_whenSaveAnAuthor_thenReturnJsonObject() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
        new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
        new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
        new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)));

    given(authorService.save(authors.get(0))).willReturn(authors.get(0));
    given(authorService.save(authors.get(1))).willReturn(authors.get(1));
    given(authorService.save(authors.get(2))).willReturn(authors.get(2));
    given(authorService.save(authors.get(3))).willReturn(authors.get(3));
    given(authorService.save(authors.get(4))).willReturn(authors.get(4));

    String author_0_json = mapper.writeValueAsString(authors.get(0));
    String author_4_json = mapper.writeValueAsString(authors.get(4));

    System.out.println(author_0_json);
    System.out.println(author_4_json);

    mvc.perform(post("/authors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(author_0_json))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("/authors/1")))
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(authors.get(0).getName())))
        .andExpect(jsonPath("$.description", is(authors.get(0).getDescription())))
        .andExpect(jsonPath("$.dateOfBirth", is(authors.get(0).getDateOfBirth().format(
            DateTimeFormatter.ISO_LOCAL_DATE))));

    mvc.perform(post("/authors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(author_4_json))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("/authors/5")))
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(authors.get(4).getName())))
        .andExpect(jsonPath("$.description", is(authors.get(4).getDescription())))
        .andExpect(jsonPath("$.dateOfBirth", is(authors.get(4).getDateOfBirth().format(
            DateTimeFormatter.ISO_LOCAL_DATE))));
  }

  @Test
  public void givenAuthors_whenSaveInvalidAuthor_thenReturnError() throws Exception {
    int len = 2000;
    String str = "a";
    String longName = IntStream.range(0, len).mapToObj(i -> str).collect(Collectors.joining(""));
    Author inputAuthor = new Author(1, longName, "Test Author", LocalDate.of(2018,1,1));
    String author_0_json = mapper.writeValueAsString(inputAuthor);

    given(authorService.save(inputAuthor)).willReturn(inputAuthor);

    mvc.perform(post("/authors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(author_0_json))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$.message", is("Validation failed")))
        .andExpect(jsonPath("$.details", is("Name should have min of 2 characters and max of 255")));
  }

  @Test
  public void givenAuthors_whenSaveAnAuthor_withMissingName_thenReturn4xxClientError() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days", LocalDate.of(1906, 10, 10)),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali", LocalDate.of(1861, 5, 7)),
        new Author(3, "Leo Tolstoy", "Russian writer", LocalDate.of(1828, 9, 9)),
        new Author(4, "Premchand", "Author in Hindi-Urdu", LocalDate.of(1880, 7, 31)),
        new Author(5, "Ruskin Bond", "Author in English", LocalDate.of(1934, 5, 19)),
        new Author(6, "", "Author in French", LocalDate.of(1985, 11, 11)));

    given(authorService.save(authors.get(0))).willReturn(authors.get(0));
    given(authorService.save(authors.get(1))).willReturn(authors.get(1));
    given(authorService.save(authors.get(2))).willReturn(authors.get(2));
    given(authorService.save(authors.get(3))).willReturn(authors.get(3));
    given(authorService.save(authors.get(4))).willReturn(authors.get(4));
    given(authorService.save(authors.get(5))).willReturn(authors.get(5));

    String author_5_json = mapper.writeValueAsString(authors.get(5));

    mvc.perform(post("/authors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(author_5_json))
        .andExpect(status().isBadRequest());
  }
}
