package com.project.vending.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.vending.entity.Product;
import com.project.vending.entity.Transaction;
import com.project.vending.model.Cash;
import com.project.vending.model.ProductRequest;
import com.project.vending.service.VendingService;

@RestController
@RequestMapping("/vending")
public class VendingController {
	
	@Autowired
	private VendingService vendingService;
	
	
	 @GetMapping("/products")
	    public List<Product> getAvailableProducts() {
	        return vendingService.getAvailableProducts();
	    }
	 
	 @PostMapping("/cash")
	    public ResponseEntity<String> insertCash(@RequestBody Cash cash) {
	        String response = vendingService.insertCash(cash.getInsertedAmount());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @PostMapping("/select")
	    public ResponseEntity<Transaction> selectProduct(@RequestBody ProductRequest product) {
	        try {
	            com.project.vending.entity.Transaction transaction = vendingService.selectProduct(product.getId(),product.getQuantity());
	            return new ResponseEntity<>(transaction, HttpStatus.OK);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	        }
	    }

}
