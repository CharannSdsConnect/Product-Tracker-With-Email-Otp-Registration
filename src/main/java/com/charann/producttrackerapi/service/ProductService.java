package com.charann.producttrackerapi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.charann.producttrackerapi.entity.Product;

public interface ProductService {
	
	Page<Product> getAllProduct(Pageable page);
	
	Product getProductById(Long id);
	
	void deleteProductById(Long id);

	Product saveProductDetails(Product product);
	
	Product updateProductDetails(Long id, Product product);
	
	List<Product> readByCategory(String category, Pageable page);
	
	List<Product> readByName(String keyword, Pageable page);
	
	List<Product> readByDate(Date startDate, Date endDate, Pageable page);
}
