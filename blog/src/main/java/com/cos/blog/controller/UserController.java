package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증 안 된 사용자들이 출입할 수 있는 경로는 /auth/~로 허용
//또는 그냥 주소가  /이면  index.jsp로 갈텐데 그것도 허용
//static 이하에 있는 /js~ 파일이나 css/ ~ image/ ~ 등도 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {	
		return "user/loginForm";
	}
}
