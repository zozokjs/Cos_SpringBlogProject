package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data  //getter, setter 만들어줌
//@AllArgsConstructor // 생성자 만들어 줌
//@RequiredArgsConstructor  //final 붙은 변수에 대한 생성자 만들어 줌
@NoArgsConstructor // 텅 빈 생성자를 만들어 줌
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	//매개변수가 생략된 생성자를 자동으로 만들어줌
	//아래처럼 사용함 
	//Member m1 = Member.builder().password("1234").email("zo").build();
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

 
	
	
}
