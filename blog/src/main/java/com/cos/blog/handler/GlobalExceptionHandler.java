package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice //exception 오면 전부 여기로 오게 함
@RestController
public class GlobalExceptionHandler {

	//IllegalArgumentException 이 오류가 발생 시 이것이 리턴 됨
	//@ExceptionHandler(value=IllegalArgumentException.class)
	//public String handelArgumentException(IllegalArgumentException e) {
	@ExceptionHandler(value=Exception.class) //Exception 발생 시 이것이 리턴 됨
	public ResponseDto<String> handelArgumentException(Exception e) {
		return new  ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		
		
	}
	
	
	
	
	
}
