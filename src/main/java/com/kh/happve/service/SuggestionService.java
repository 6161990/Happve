package com.kh.happve.service;


import com.kh.happve.dto.SuggestionForm;
import com.kh.happve.entity.Suggest;
import com.kh.happve.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional

public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    public Long save(SuggestionForm suggestionForm){
        Suggest saved = Suggest.builder()
                .content(suggestionForm.getContent())
                .email(suggestionForm.getEmail())
                .build();

        Suggest savedFinally = suggestionRepository.save(saved);

        return savedFinally.getSuggestId();

    }

}