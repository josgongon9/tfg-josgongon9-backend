package com.josgongon9.tfgwebbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	 @GetMapping("/sayhello")
	    public String sayHelloWorld(){
	        return "Hello World!";
	    }


}
