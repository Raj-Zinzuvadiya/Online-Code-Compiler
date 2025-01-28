package com.onlinecodecompiler.io_service.controller;

import com.onlinecodecompiler.io_service.dto.LanguageDTO;
import com.onlinecodecompiler.io_service.repository.LanguageRepository;
import com.onlinecodecompiler.io_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;


    @PostMapping("/languages/add")
    public ResponseEntity<LanguageDTO> addLanguage(@RequestBody LanguageDTO languageDTO)
    {
        LanguageDTO dto = adminService.addLanguage(languageDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getLanguages()
    {
        List<String> languageName = adminService.getLanguages();
        return new ResponseEntity<List<String>>(languageName, HttpStatus.OK);
    }



}
