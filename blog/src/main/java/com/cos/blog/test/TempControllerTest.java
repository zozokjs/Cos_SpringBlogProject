package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	// http:// localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		// 파일 기본 리턴 경로 : src/main/resources/static
		// 현재 리턴 명 : /home.html
		// 전체 경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String testJsp() {
		//yml 파일에서 
		//prefix : /WEB-INF/views/
		//suffic : .jsp
		//전체경로 :  /WEB-INF/views/리턴되는것.jsp	
		System.out.println("");
		return "test";
	}
	
}
