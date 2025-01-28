package com.onlinecodecompiler.io_service.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping()
    public ResponseEntity<String> healthCheck()
    {
        return new ResponseEntity<>("IO Service Health OK", HttpStatus.OK);
    }
}
