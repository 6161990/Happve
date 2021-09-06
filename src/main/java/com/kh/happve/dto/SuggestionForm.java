package com.kh.happve.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SuggestionForm {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String content;
}
