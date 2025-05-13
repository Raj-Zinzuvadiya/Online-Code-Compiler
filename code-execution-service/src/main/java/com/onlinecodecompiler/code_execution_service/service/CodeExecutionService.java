package com.onlinecodecompiler.code_execution_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecodecompiler.code_execution_service.dto.CodeSubmitDTO;
import com.onlinecodecompiler.code_execution_service.entities.CodeExecutionResultRedis;
import com.onlinecodecompiler.code_execution_service.entities.ExecutionResult;
import com.onlinecodecompiler.code_execution_service.entities.ExecutionStatus;
import com.onlinecodecompiler.code_execution_service.repository.ExecutionResultRepository;
import com.onlinecodecompiler.code_execution_service.repository.RedisRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import okhttp3.*;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class CodeExecutionService {
    final private ObjectMapper objectMapper;
    final private RedisRepository redisRepository;
    final private ModelMapper modelMapper;

    final private ExecutionResultRepository executionResultRepository;
    public CodeExecutionService(ObjectMapper objectMapper, RedisRepository redisRepository, ModelMapper modelMapper, ExecutionResultRepository executionResultRepository) {
        this.objectMapper = objectMapper;
        this.redisRepository = redisRepository;
        this.modelMapper = modelMapper;
        this.executionResultRepository = executionResultRepository;
    }


    @KafkaListener(topics = "code-execution-topic", groupId = "code-execution")
    public void executeCodeKafkaListener(ConsumerRecord<String, String> record) throws Exception
    {
        String jsonValue = record.value();

        CodeSubmitDTO codeSubmitDTO = objectMapper.readValue(jsonValue, CodeSubmitDTO.class);
        String codeHashKey = codeSubmitDTO.getCodeHashKey();

        CodeExecutionResultRedis codeExecutionResultRedis =  redisRepository.getExecutionResult(codeSubmitDTO.getSubmissionId());

        if(codeExecutionResultRedis != null) {
            return;
        };

        Map<String, Object> map = new HashMap<>();

        if(codeSubmitDTO.getLanguageName().equalsIgnoreCase("python"))
            map = executePythonCode(codeSubmitDTO.getCode());

        redisRepository.save(codeSubmitDTO.getSubmissionId(), new CodeExecutionResultRedis((ExecutionStatus) map.get("executionStatus"),(String) map.get("result")));

        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setExecutionStatus((ExecutionStatus) map.get("executionStatus"));
        executionResult.setCodeHashKey(codeHashKey);
        executionResult.setEmailId(codeSubmitDTO.getEmailId());
        executionResult.setOutput((String) map.get("output"));
        executionResult.setSubmissionId(codeSubmitDTO.getSubmissionId());

        executionResultRepository.save(executionResult);
    }
    public CodeExecutionResultRedis getExecutionResult(UUID submissionId)
    {
        System.out.println(submissionId);
        CodeExecutionResultRedis codeExecutionResultRedis =  redisRepository.getExecutionResult(submissionId);

        if(codeExecutionResultRedis != null) {
            return codeExecutionResultRedis;
        }
        ExecutionResult executionResult = executionResultRepository.findBySubmissionId(submissionId).orElseThrow(() -> new RuntimeException("Submission not found"));
        System.out.println(executionResult.getExecutionStatus().toString());
        System.out.println(executionResult.getOutput());

        codeExecutionResultRedis = new CodeExecutionResultRedis(executionResult.getExecutionStatus(), executionResult.getOutput());
        redisRepository.save(executionResult.getSubmissionId(), codeExecutionResultRedis);

        return codeExecutionResultRedis;
    }


    private Map<String, Object> executePythonCode(String code) throws Exception {
        String API_GATEWAY_URL = "https://ryupdktxui.execute-api.ap-south-1.amazonaws.com/v1/python-executor";

        OkHttpClient client= new OkHttpClient();
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        String requestBody = objectMapper.writeValueAsString(map);

        RequestBody body = RequestBody.create(requestBody, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(API_GATEWAY_URL)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        int statusCode = response.code();
        System.out.println("statusCode: "+statusCode);
        if(statusCode != 200){
            throw new Exception("Lambda Response"+statusCode);
        };

        String responseBody = response.body().string();
        Map<String, Object> responseBodyMap = objectMapper.readValue(responseBody, Map.class);

        Map<String, Object> bodyMap = objectMapper.readValue((String) responseBodyMap.get("body"), Map.class);
        System.out.println("bodyMap: "+bodyMap);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("executionStatus", ExecutionStatus.FAILED);

        if(bodyMap.get("result").equals("SUCCESS"))
        {
            responseMap.put("executionStatus", ExecutionStatus.SUCCESS);
        }
        responseMap.put("result", bodyMap.get("output").toString().strip());

        return responseMap;
    }
}
