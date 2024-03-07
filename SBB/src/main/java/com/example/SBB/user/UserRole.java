package com.example.SBB.user;

import lombok.Getter;

// 권한과 관련된 코드
@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
	
	private String value;
	
	UserRole(String value) {
		this.value = value;
	}
	

}
