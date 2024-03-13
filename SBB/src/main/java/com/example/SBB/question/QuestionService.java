package com.example.SBB.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.data.domain.Sort;

import com.example.SBB.DataNotFoundException;
import com.example.SBB.user.SiteUser;
import com.example.SBB.answer.Answer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

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
    
    // 목록
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
    
    // 생성
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    
    // 페이징
//    public Page<Question> getList(int page) {
//    	List<Sort.Order> sorts = new ArrayList<>();
//    	sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(page, 10);
//        return this.questionRepository.findAll(pageable);
//    }
    
    // 검색기능 페이징
    public Page<Question> getList(int page, String kw) {
    	List<Sort.Order> sorts = new ArrayList<>();
    	sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10);
//        Specification<Question> spec = search(kw);
//        return this.questionRepository.findAll(pageable);
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }
    
    // 수정
    public void modify(Question question, String subject, String content) {
    	question.setSubject(subject);
    	question.setContent(content);
    	question.setModifyData(LocalDateTime.now());
    	this.questionRepository.save(question);
    }
    
    // 삭제
    public void delete(Question question) {
    	this.questionRepository.delete(question);
    }
    
    // 추천기능
    public void vote(Question question, SiteUser siteUser) {
    	question.getVoter().add(siteUser);
    	this.questionRepository.save(question);
    }
    
    // 검색기능
    private Specification<Question> search(String kw) {
    	return new Specification<>() {
    		private static final long SerialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 중복제거
				query.distinct(true);
				
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%"+ kw + "%"),	    // 내용
						cb.like(u1.get("username"), "%"+ kw + "%"),	    // 질문 작성자
						cb.like(a.get("content"), "%"+ kw + "%"),	    // 답변 내용
						cb.like(u2.get("username"), "%"+ kw + "%"));	// 답변 작성자
			}
		};
    }
    
}