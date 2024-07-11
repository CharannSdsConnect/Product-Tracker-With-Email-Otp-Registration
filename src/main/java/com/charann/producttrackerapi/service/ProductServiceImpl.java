package com.charann.producttrackerapi.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.charann.producttrackerapi.entity.Product;
import com.charann.producttrackerapi.exceptions.ResourceNotFoundException;
import com.charann.producttrackerapi.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository expenseRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Page<Product> getAllProduct(Pageable page) {
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Product getProductById(Long id){
		Optional<Product> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if (expense.isPresent()) {
			return expense.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id "+id);
	}

	@Override
	public void deleteProductById(Long id) {
		Product product = getProductById(id);
		expenseRepo.delete(product);
	}

	@Override
	public Product saveProductDetails(Product product) {
		product.setUser(userService.getLoggedInUser());
		return expenseRepo.save(product);
	}

	@Override
	public Product updateProductDetails(Long id, Product product){
		Product existingProduct = getProductById(id);
		existingProduct.setName(product.getName() != null ? product.getName() : existingProduct.getName());
		existingProduct.setDescription(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription());
		existingProduct.setCategory(product.getCategory() != null ? product.getCategory() : existingProduct.getCategory());
		existingProduct.setDate(product.getDate() != null ? product.getDate() : existingProduct.getDate());
		existingProduct.setAmount(product.getAmount() != null ? product.getAmount() : existingProduct.getAmount());
		return expenseRepo.save(existingProduct);
	}

	@Override
	public List<Product> readByCategory(String category, Pageable page) {
		return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
	}

	@Override
	public List<Product> readByName(String keyword, Pageable page) {
		return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
	}

	@Override
	public List<Product> readByDate(Date startDate, Date endDate, Pageable page) {
		
		if (startDate == null) {
			startDate = new Date(0);
		}
		
		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		
		return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
	}

}



























