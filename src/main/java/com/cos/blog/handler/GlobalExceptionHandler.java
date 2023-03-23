package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice//모든 exception이 발생하면 이 클래스로 들어오라는 어노테이션
@RestController
public class GlobalExceptionHandler {
	
	//만약에 모든 exception를 받고싶으면 value=exception로 바꾸면됨
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>";
	}
}
