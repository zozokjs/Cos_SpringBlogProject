package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//스프링이 스캔해서 특정 어노테이션이 붙어 있는 클래스 파일을 ioc에서 관리한다.
public class BlogControllerTest {

	//  http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String Hello() {
		return "<h1>Hello spring boot</h1>";
	}
}
