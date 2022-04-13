package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


//사용자가 요청할 때 응답 해주는 게 컨트롤러인데 데이터를 응답해줄 것.
//만약 html을 응답해주는 컨트롤러는 @Controller를 붙이면 됨.
@RestController
public class HttpControllerTest {

	
	// 브라우저에서는 get요청만 가능함
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest() {
		return "get 요청";
	}

	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
	public String postTest() {
		return "post 요청";
	}
	
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
		
}
