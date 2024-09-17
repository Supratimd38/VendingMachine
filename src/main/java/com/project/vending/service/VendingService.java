package com.project.vending.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.vending.entity.Product;
import com.project.vending.entity.Transaction;
import com.project.vending.repository.ProductRepository;
import com.project.vending.repository.TransactionRepository;

@Service
public class VendingService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired 
	private TransactionRepository transactionRepository;
	
	
	  private double balance = 0.0;
	
	  public List<Product> getAvailableProducts() {
	        return productRepository.findAll();
	    }
	  

	    public String insertCash(double amount) {
	        if (amount <= 0) {
	            return "Invalid amount!";
	        }
	        balance += amount;
	        return "Inserted: " + amount;
	    }
	    
	    
	    public Transaction selectProduct(Long productId, int quantity) {
	        Product product = productRepository.findById(productId).orElseThrow(() -> 
	            new IllegalArgumentException("Invalid product selected!")
	        );

	        double totalPrice = product.getPrice()* quantity;
	        
	        if (product.getQuantity() == 0 && product.getQuantity()<quantity) {
	            throw new IllegalArgumentException("Product out of stock!");
	        }

	        if (balance < totalPrice) {
	            throw new IllegalArgumentException("Insufficient funds!");
	        }

	        product.setQuantity(product.getQuantity() - quantity);
	        productRepository.save(product);

	        double change = balance - totalPrice;
	        balance = 0;  // reset balance to 0 after calculating change
	        Transaction transaction = new Transaction(product, totalPrice, change, "SUCCESS");
	        transactionRepository.save(transaction);

	        return transaction;

}}
