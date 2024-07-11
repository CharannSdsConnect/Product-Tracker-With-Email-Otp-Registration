package com.charann.producttrackerapi.controller;

import java.sql.Date;
import java.util.List;

import com.charann.producttrackerapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charann.producttrackerapi.entity.Product;
import jakarta.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductService expenseService;
	
	@GetMapping("/products")
	public List<Product> getAllExpenses(Pageable page) {
		return expenseService.getAllProduct(page).toList();
	}
	
	@GetMapping("/products/{id}")
	public Product getExpenseById(@PathVariable Long id){
		return expenseService.getProductById(id);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/products")
	public void deleteExpenseById(@RequestParam Long id) {
		expenseService.deleteProductById(id);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/products")
	public Product saveExpenseDetails(@Valid @RequestBody Product product) {
		return expenseService.saveProductDetails(product);
	}
	
	@PutMapping("/products/{id}")
	public Product updateExpenseDetails(@RequestBody Product product, @PathVariable Long id){
		return expenseService.updateProductDetails(id, product);
	}
	
	@GetMapping("/products/category")
	public List<Product> getExpensesByCategory(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page);
	}
	
	@GetMapping("/products/name")
	public List<Product> getExpensesByName(@RequestParam String keyword, Pageable page) {
		return expenseService.readByName(keyword, page);
	}
	
	@GetMapping("/products/date")
	public List<Product> getExpensesByDates(@RequestParam(required = false) Date startDate,
											@RequestParam(required = false) Date endDate,
											Pageable page) {
		return expenseService.readByDate(startDate, endDate, page);
	}
}






















