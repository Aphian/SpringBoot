package com.example.SBB.question;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParm;
import org.springframework.web.bind.annotation.BindingResult;
//import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequestMapping
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	
	// URL 매핑
	// @GetMapping("/question/list")
	// Prefix 설정
	@GetMapping("/list")
	public String list(Model model) {
		// List<Question> questionList = this.questionRepository.findAll();
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}

	// 상세 페이지
	// @GetMapping("/question/detail/{id}")
	@GetMapping(value = "/detail/{id}")
	public String detail(Model.model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	@GetMapping("/create")
	// public String questionCreate(@RequestParm(value="subject") String subject, @RequestParm(value="content") String content) {
	// public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
	public String questionCreate(QuestionForm questionForm) {
		if (bindingResult.hasErros) {
			return "question_form";
		}
		// this.questionService.create(subject, content);
		this.questionService(QuestionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}

}
