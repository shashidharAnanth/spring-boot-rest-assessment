package com.echidna.assessment.web;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class AuthorRestController {

  @RequestMapping("/")
  String home() {
    return "Hello World!";
  }

}