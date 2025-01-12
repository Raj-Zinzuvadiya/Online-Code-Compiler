package com.onlinecodecompiler.code_execution_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;


@Service
public class CodeExecutionService {
    ObjectMapper objectMapper = new ObjectMapper();

    public void executeCode(String languageName, String code) throws Exception
    {
        if(languageName.equalsIgnoreCase("python"))
        {
            executePythonCode(code);
        }
    }
    private void executePythonCode(String code) throws Exception {
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
        System.out.println("Status Code: " + statusCode);

        // Get response body
        String responseBody = response.body().string();
        System.out.println("Response Body: " + responseBody);






    }
}
