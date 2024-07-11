package com.charann.producttrackerapi.entity;

import java.time.LocalDateTime;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Long age;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private String otp;

	private boolean active;

}














