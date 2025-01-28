package com.onlinecodecompiler.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class SpringSecurityController {

    @GetMapping("/health_check")
    public ResponseEntity<String> healthCheck(HttpServletRequest request) {
        return new ResponseEntity<>("Spring Security Service", HttpStatus.OK);
    }
}