package com.onlinecodecompiler.io_service.controller;


import com.onlinecodecompiler.io_service.dto.CodeSubmitDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/io-api")
public class IORestController {

    @GetMapping()
    public ResponseEntity<String> healthCheck()
    {
        return new ResponseEntity<>("IO Service Health OK", HttpStatus.OK);
    }


    @PostMapping("/submit")
    public ResponseEntity<String> submitCode(@RequestBody CodeSubmitDTO codeSubmitDTO){

        System.out.println(codeSubmitDTO.toString());

        return new ResponseEntity<>("Code Submit Successfully", HttpStatus.OK);
    }
}
