package com.onlinecodecompiler.io_service.service;


import com.onlinecodecompiler.io_service.dto.LanguageDTO;
import com.onlinecodecompiler.io_service.entities.Language;
import com.onlinecodecompiler.io_service.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LanguageRepository languageRepository;

    public LanguageDTO addLanguage(LanguageDTO languageDTO)
    {
        Language language = new Language();
        language.setLanguageName(languageDTO.getLanguageName());
        language  = languageRepository.save(language);

        languageDTO = modelMapper.map(language, LanguageDTO.class);

        return languageDTO;
    }
    public List<String> getLanguages()
    {

        List<String> languages = new ArrayList<>();
        languageRepository.findAll()
        .forEach(language -> languages.add(language.getLanguageName()));
        return languages;
    }

}
