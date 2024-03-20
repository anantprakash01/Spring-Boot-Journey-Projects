package com.anant.rest.services.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "hello1 world";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("hello world, %s", name));
	}
}
