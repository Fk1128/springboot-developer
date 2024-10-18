package com.example.demo.di4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//코딩을 하려면 컴퓨터가 필요하다
@Component
@RequiredArgsConstructor
@Getter

public class Coding {

	// final 이나 @NonNull 붙이고 
	
	private final Computer computer;
	
//	@Autowired
//	private Computer computer;
//	
//	//생성자 주입 (Constructor injection)
//	// 생성자가 호출되는 시점 -> 객체가 만들어질 때 매개변수에 객체가 주입이 된다 .
//	
////	public Coding (Computer computer) { 
////		this.computer =computer;
////	}
//	
////	//setter 주입(setter injection)
////	@Autowired
////	public void setComputer(Computer computer) {
////		this.computer = computer;
////	}
////	
//	
//	public Computer getComputer() {
//		return computer;
//	}
//	

}
