package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import ch.qos.logback.core.net.SyslogOutputStream;
import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;

@RestController
public class UserApiController {
	    
	@Autowired
	private UserService userService;

	
	//회원가입 로직
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController save 호출됨");		
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바 오브젝트를 json으로 변환해서 리턴 해줌
	};
	
	
	
	
	
	
	
	
	/* 시큐리티 안 쓰고 로그인하는 법
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
		
		System.out.println("UserApiController login 호출됨");
		User principal = userService.로그인(user); // principal(접근주체)

		if(principal != null) {
			//세션 생성됨
			
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	*/
	
	
	
}
