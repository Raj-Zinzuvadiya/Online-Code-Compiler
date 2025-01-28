package com.onlinecodecompiler.code_execution_service.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "execution_results")
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID executionId;
    @Column(nullable = false, unique = true)
    private UUID submissionId;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false, unique = true)
    private String codeHashKey;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionStatus executionStatus;
    private String output;

    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getExecutionId() {
        return executionId;
    }

    public void setExecutionId(UUID executionId) {
        this.executionId = executionId;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCodeHashKey() {
        return codeHashKey;
    }

    public void setCodeHashKey(String codeHashKey) {
        this.codeHashKey = codeHashKey;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
