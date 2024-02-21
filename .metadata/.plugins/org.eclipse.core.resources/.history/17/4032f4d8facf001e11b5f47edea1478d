package com.example.SBB;

import lombok.Getter;
//import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
//@Setter
// Lombok 생성자
@RequiredArgsConstructor
public class HelloLombok {
//	private String hello;
//	private int lombok;
	
	// final은 뒤에 따라오는 자료형과 변수 등을 변경할 수 없게 만드는 키워드
	private final String hello;
	private final int lombok;
	
	public static void main(String[] args) {
		HelloLombok helloLombok = new HelloLombok("헬로", 5);
//		helloLombok.setHello("헬로");
//		helloLombok.setLombok(5);
		
		System.out.println(helloLombok.getHello());
		System.out.println(helloLombok.getLombok());
	}

}
