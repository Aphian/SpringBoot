package com.example.SBB.answer;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import com.example.SBB.question.Question;
import com.example.SBB.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    // @PostMapping("/create/{id}")
    // public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestMapping(value = "content") String content) {
    //     Question question = this.questionService.getQuestion(id);
    //     // 답변 저장
    //     this.answerService.create(question, content);
    //     return String.format("redirect:/question/detail/%s", id);
    // }
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm AnswerForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
}