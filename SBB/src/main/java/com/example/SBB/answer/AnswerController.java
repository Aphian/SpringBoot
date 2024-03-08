package com.example.SBB.answer;

import com.example.SBB.question.Question;
import com.example.SBB.question.QuestionService;
import com.example.SBB.user.SiteUser;
import com.example.SBB.user.UserService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

// 로그인한 사용자의 정보를 시큐리티에 제공하기 위한 라이브러리
import java.security.Principal;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    // @PostMapping("/create/{id}")
    // public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestMapping(value = "content") String content) {
    //     Question question = this.questionService.getQuestion(id);
    //     // 답변 저장
    //     this.answerService.create(question, content);
    //     return String.format("redirect:/question/detail/%s", id);
    // }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, 
    		@Valid AnswerForm answerForm, 
    		BindingResult bindingResult,
    		Principal principal) {
    	Question question = this.questionService.getQuestion(id);
    	SiteUser siteUser = this.userService.getUser(principal.getName());
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("question", question);
    		return "question_datail";
    	}
    	this.answerService.create(question, answerForm.getContent(), siteUser);
    	return String.format("redirect:/question/detail/%s", id);
    }
}