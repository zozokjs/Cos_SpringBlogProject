package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
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

	@Autowired
	private AuthenticationManager authenticationManager;
	
	//회원가입 로직
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController save 호출됨");		
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바 오브젝트를 json으로 변환해서 리턴 해줌
	};
	
	@PutMapping("/user") 
	//json 받으려면 @RequestBody
	//x-www-form-urlencoded(key-value) 형태로 받으려면 @RequestBody 없어야 함.
	public ResponseDto<Integer> update(@RequestBody User user){
		
		//이 메소드가 끝나면서 트랜잭션이 종료 되었기에 db 값은 변경 됐음.
		//세션 값은 변경되지 않아서 ... 문제가 생긴다. 직접 세션 값을 변경해줘야 한다.
		//변경하는 방법 >  
		userService.회원수정(user);
			
		//세션 등록해주기 63번 영상34분 경
		//강제로 세션 등록해주는 과정임.
		//세션 등록하려면 필수적으로 username과 password가 필요하다.
		//근데 js에서 username을 안 받아왔었음...
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		
		/* 세션에 강제로 authentication 객체 넣기 안됨..
		//63번 영상 21분경
		Authentication authentication= new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication); //강제로 세션 넣었음...		
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);//세션에 AUTHENTICATION 객체 넣었음
		 */
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 

	}
	
	
	
	
	
	
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
