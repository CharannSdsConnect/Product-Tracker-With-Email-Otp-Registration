package com.charann.producttrackerapi.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charann.producttrackerapi.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findByUserIdAndCategory(Long userId, String category, Pageable page);
	
	Page<Product> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);
	
	Page<Product> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);
	
	Page<Product> findByUserId(Long userId, Pageable page);
	
	Optional<Product> findByUserIdAndId(Long userId, Long expenseId);

	
}
