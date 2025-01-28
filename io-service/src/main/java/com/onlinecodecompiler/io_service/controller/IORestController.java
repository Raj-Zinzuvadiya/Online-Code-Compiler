package com.onlinecodecompiler.io_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecodecompiler.io_service.dto.CodeSubmitDTO;
import com.onlinecodecompiler.io_service.service.CodeSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping(value = "/code-submit")
public class IORestController {

    @Autowired
    private CodeSubmitService codeSubmitService;

    @PostMapping("")
    public ResponseEntity<UUID> submitCode(@RequestBody CodeSubmitDTO codeSubmitDTO) throws JsonProcessingException {

        UUID submissionId = codeSubmitService.produceCodeSubmitEvent(codeSubmitDTO);
        return new ResponseEntity<>(submissionId, HttpStatus.OK);
    }
    @GetMapping("/result/{submission_id}")
    public ResponseEntity<Map<String, String>> getResult(@PathVariable("submission_id") UUID submissionId) throws JsonProcessingException {
        Map<String, String> map = codeSubmitService.getResult(submissionId);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
