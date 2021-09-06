package com.kh.happve.controller;

import com.kh.happve.dto.SuggestionForm;
import com.kh.happve.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("")
    public String suggestionForm(Model model) {
        model.addAttribute(new SuggestionForm());
        return "suggestion";
    }

    @PostMapping("")
    public String saveSuggestion(@Validated SuggestionForm suggestionForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "suggestion";
        }
        Long savedSuggestionId = suggestionService.save(suggestionForm);
        if(savedSuggestionId > 0) {
            redirectAttributes.addAttribute("status",true);
        }
        return "redirect:/suggestion";
    }


}
