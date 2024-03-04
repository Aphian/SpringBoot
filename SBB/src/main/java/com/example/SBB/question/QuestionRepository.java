package com.example.SBB.question;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// 페이징 구현 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	// 사용할 메서드 작성
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);

	// 페이징
	Page<Question> findAll(Pageable, pageable);

}
