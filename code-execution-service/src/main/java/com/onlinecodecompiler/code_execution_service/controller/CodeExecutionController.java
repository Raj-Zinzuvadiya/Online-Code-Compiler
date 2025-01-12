package com.onlinecodecompiler.code_execution_service.controller;


import com.onlinecodecompiler.code_execution_service.service.CodeExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code-execution-api")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @GetMapping
    public ResponseEntity<String> healthCheck()
    {
        return new ResponseEntity<>("Code Execution Service Health OK", HttpStatus.OK);
    }

    @GetMapping("/submit")
    public ResponseEntity<String> submit(@RequestParam String languageName,@RequestParam String code) throws Exception
    {
        codeExecutionService.executeCode(languageName, code);
        return new ResponseEntity<>("Code Execution Service Invoked", HttpStatus.OK);
    }




}
