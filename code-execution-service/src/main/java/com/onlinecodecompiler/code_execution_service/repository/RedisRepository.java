package com.onlinecodecompiler.code_execution_service.repository;

import com.onlinecodecompiler.code_execution_service.entities.CodeExecutionResultRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String KEY = "CODE-EXECUTION-RESULT";

    public void save(UUID submissionId, CodeExecutionResultRedis codeExecutionResultRedis)
    {
        this.redisTemplate.opsForHash().put(KEY, submissionId, codeExecutionResultRedis);
    }
    public CodeExecutionResultRedis getExecutionResult(UUID submissionId)
    {
        return (CodeExecutionResultRedis)(this.redisTemplate.opsForHash().get(KEY, submissionId));
    }
}
