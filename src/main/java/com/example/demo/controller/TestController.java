package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController//http관련된 코드 및 요청/응답 매핑을 스프링이 알아서 해준다.
//@Controller + @ResponseBody를 합친거다.
//모든 메서드에 @ResponseBody가 적용되어있는 상태다.

@RequestMapping("test")//localhost:9090/test로 접속을 시도하면 이 컨트롤러로 요청이 들어와진다.
public class TestController {

	//HTTP메서드마다 요청을 받아오는 어노테이션
	@GetMapping("/testGetMapping") //get요청으로 들어오면 아래의 메서드를 실행시켜줘
	public String testController() {
		return "Hello World";
	}
	
	//제품별로 구분할 때
	//localhost:9090/test/1
	@GetMapping("/{id}")
	
	public String testControllerWidthPathVariable(@PathVariable(required=false) int id) {
		return "Hello Wrold! ID : "+ id;
	}
	//검색할 때 검색 필드
	//localhost:9090/test/testRequestParam?id=123
	@GetMapping("/testRequestParam")
	
	public String testControllerRequestParam(@RequestParam(required=false) int id) {
		return "Hello World! ID" + id;
	}
	
	//POST 요청할 때 
	//요청바디에 데이터가 넘어왔을 때 -> 자바객체로 변환 사용
	@GetMapping("/testRequestBody")
	public String testControllerRequestbody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "Hello World! ID : " + testRequestBodyDTO.getId() + 
				" Message : " + testRequestBodyDTO.getMessage();
	}
	
	//응답바디에 데이터를 넘기는것
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hellow World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	
	//ResPonseEntity
	//HTTP응답을 보다 세밀하게 제어할 수 있다.
	//HTTP상태코드,헤더,바디를 구성할 수 있다.
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String>list = new ArrayList<>();
		list.add("Hellow World! I'm ResponseEntity. And you got 400");
		ResponseDTO<String>response = ResponseDTO.<String>builder().data(list).build();
		//http status 400으로 변경
		return ResponseEntity.badRequest().body(response);
	}
	
	
	
	

}
