package com.pritam.fabrication.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pritam.fabrication.ecommerce.response.ApiResponse;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<ApiResponse>homeController(){
		ApiResponse res = new ApiResponse("Welcome to Pritam Fabrication - your one-stop shop for high-quality iron, aluminum, and steel household items. Discover a wide range of durable and stylish products to enhance your home's functionality and aesthetic appeal. Welcome to convenience and quality - welcome to Pritam Fabrication!", true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
