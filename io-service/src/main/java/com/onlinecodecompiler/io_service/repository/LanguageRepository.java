package com.onlinecodecompiler.io_service.repository;

import com.onlinecodecompiler.io_service.entities.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {

    public Optional<Language> findByLanguageName(String languageName);
}
