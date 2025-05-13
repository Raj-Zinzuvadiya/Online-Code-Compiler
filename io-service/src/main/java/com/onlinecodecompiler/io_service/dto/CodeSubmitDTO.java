package com.onlinecodecompiler.io_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CodeSubmitDTO {
    private UUID submissionId;
    private String emailId;
    private String languageName;
    private String code;
    private String codeHashKey;
    private LocalDateTime createdAt;

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

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeHashKey() {
        return codeHashKey;
    }

    public void setCodeHashKey(String codeHashKey) {
        this.codeHashKey = codeHashKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}