package com.anant.testcom.hello;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@GetMapping(path="/hello")
	public String printHello() {
		return "Hello Word";
	}
}
