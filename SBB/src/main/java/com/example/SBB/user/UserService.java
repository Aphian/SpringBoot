package com.example.SBB.user;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		// Bean 으로 등록한 객체를 주입받아 사용하도록 수정
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPaaword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}

}
