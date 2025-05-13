package com.onlinecodecompiler.io_service.repository;

import com.onlinecodecompiler.io_service.entities.CodeExecutionResultRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String KEY = "CODE-EXECUTION-RESULT";

    public CodeExecutionResultRedis getExecutionResult(String codeHashKey)
    {
        return (CodeExecutionResultRedis)(this.redisTemplate.opsForHash().get(KEY, codeHashKey));
    }
}
