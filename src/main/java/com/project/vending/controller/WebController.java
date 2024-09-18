package com.project.vending.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	  @GetMapping("/vendingmachine")
	    public String showVendingMachineUI() {
	        return "index";  
	    }
	
}
