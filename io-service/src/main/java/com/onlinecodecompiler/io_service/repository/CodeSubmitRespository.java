package com.onlinecodecompiler.io_service.repository;

import com.onlinecodecompiler.io_service.entities.CodeSubmit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodeSubmitRespository extends CrudRepository<CodeSubmit, UUID> {

}
