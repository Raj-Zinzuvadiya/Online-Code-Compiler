package com.onlinecodecompiler.code_execution_service.repository;

import com.onlinecodecompiler.code_execution_service.entities.ExecutionResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExecutionResultRepository extends CrudRepository<ExecutionResult, UUID> {
    Optional<ExecutionResult> findBySubmissionId(UUID submissionId);

}
