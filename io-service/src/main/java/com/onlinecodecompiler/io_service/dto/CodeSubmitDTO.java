package com.onlinecodecompiler.io_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeSubmitDTO {
    private String code;
    private String languageName;
}
