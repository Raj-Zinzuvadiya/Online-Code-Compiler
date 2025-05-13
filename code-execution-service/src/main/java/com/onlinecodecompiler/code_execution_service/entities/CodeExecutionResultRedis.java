package com.onlinecodecompiler.code_execution_service.entities;

import java.io.Serializable;

public class CodeExecutionResultRedis implements Serializable {

    public CodeExecutionResultRedis(){}
    public CodeExecutionResultRedis(ExecutionStatus executionStatus, String result) {
        this.executionStatus = executionStatus;
        this.result = result;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private ExecutionStatus executionStatus;
    private String result;

}
