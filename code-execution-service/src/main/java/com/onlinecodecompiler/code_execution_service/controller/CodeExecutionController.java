package com.onlinecodecompiler.code_execution_service.controller;


import com.onlinecodecompiler.code_execution_service.entities.CodeExecutionResultRedis;
import com.onlinecodecompiler.code_execution_service.service.CodeExecutionService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/code-execution-api")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @GetMapping
    public ResponseEntity<String> healthCheck()
    {
        return new ResponseEntity<>("Code Execution Service Health OK", HttpStatus.OK);
    }
    @GetMapping("/result/{submission_id}")
    public ResponseEntity<CodeExecutionResultRedis> getExecutionResult(@PathVariable("submission_id") UUID
                                                                                   submissionId)
    {
        CodeExecutionResultRedis codeExecutionResultRedis = codeExecutionService.getExecutionResult(submissionId);
        return new ResponseEntity<>(codeExecutionResultRedis, HttpStatus.OK);
    }





}
