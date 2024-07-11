package com.charann.producttrackerapi.entity;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class UserModel {
	
	@NotBlank(message = "Name should not be empty")
	private String name;

	@NotNull(message = "Email should not be empty")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String email;
	
	@NotNull(message = "Password should not be empty")
	@Size(min = 5, message = "Password should be atleast 5 characters")
	private String password;

	@Min(1)
	@Max(100)
	private Long age = 0L;

	private LocalDateTime createdAt;

//	@NotNull(message = "Otp is mandatory for registration")
	private String otp;

	private boolean active= false;
}
