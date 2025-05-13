package com.onlinecodecompiler.io_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecodecompiler.io_service.dto.CodeSubmitDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecodecompiler.io_service.entities.CodeExecutionResultRedis;
import com.onlinecodecompiler.io_service.entities.CodeSubmit;
import com.onlinecodecompiler.io_service.entities.Language;
import com.onlinecodecompiler.io_service.repository.CodeSubmitRespository;
import com.onlinecodecompiler.io_service.repository.LanguageRepository;
import com.onlinecodecompiler.io_service.repository.RedisRepository;
import com.onlinecodecompiler.io_service.utility.UniqueKeyGenerator;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CodeSubmitService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;
    private final LanguageRepository languageRepository;
    private final CodeSubmitRespository codeSubmitRespository;

    public CodeSubmitService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, RedisRepository redisRepository, LanguageRepository languageRepository, CodeSubmitRespository codeSubmitRespository) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.redisRepository = redisRepository;
        this.languageRepository = languageRepository;
        this.codeSubmitRespository = codeSubmitRespository;
    }
    public UUID produceCodeSubmitEvent(CodeSubmitDTO codeSubmitDTO) throws JsonProcessingException {

        String data = codeSubmitDTO.getCode()+":"+ codeSubmitDTO.getEmailId();
        String codeHashKey = UniqueKeyGenerator.generateHash(data);
        codeSubmitDTO.setCodeHashKey(codeHashKey);

        Language language = languageRepository.findByLanguageName(codeSubmitDTO.getLanguageName()).orElseThrow(() -> new RuntimeException("Language Not Found"));


        CodeSubmit codeSubmit = new CodeSubmit();
        codeSubmit.setEmail_id(codeSubmitDTO.getEmailId());
        codeSubmit.setLanguage(language);
        codeSubmit.setCode(codeSubmitDTO.getCode());
        codeSubmit.setCodeHashKey(codeHashKey);
        codeSubmit = codeSubmitRespository.save(codeSubmit);

        codeSubmitDTO.setSubmissionId(codeSubmit.getSubmissionId());

        String value = objectMapper.writeValueAsString(codeSubmitDTO);
        kafkaTemplate.send("code-execution-topic", value);

        return codeSubmit.getSubmissionId();
    }

    public Map<String, String> getResult(UUID submissionId)
    {
        Map<String, String> map = new HashMap<>();
        map.put("executionStatus", "PENDING............");
        map.put("result", "");

        CodeExecutionResultRedis codeExecutionResultRedis = redisRepository.getExecutionResult(submissionId.toString());

        if(codeExecutionResultRedis == null)
        {
            return map;
        }

        map.put("output", codeExecutionResultRedis.getResult());
        map.put("result", codeExecutionResultRedis.getExecutionStatus().toString());
        return map;
    }
}
