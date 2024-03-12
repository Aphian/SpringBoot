package com.example.SBB.question;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.example.SBB.DataNotFoundException;
import com.example.SBB.user.SiteUser;

import org.springframework.stereotype.Service;

// 페이징 서비스
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.questionRepository.findAll(pageable);
    }
    
    public void modify(Question question, String subject, String content) {
    	question.setSubject(subject);
    	question.setContent(content);
    	question.setModifyData(LocalDateTime.now());
    	this.questionRepository.save(question);
    }
    
    public void delete(Question question) {
    	this.questionRepository.delete(question);
    }
    
    public void vote(Question question, SiteUser siteUser) {
    	question.getVoter().add(siteUser);
    	this.questionRepository.save(question);
    }
    
}