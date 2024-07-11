package com.charann.producttrackerapi.controller;


import com.charann.producttrackerapi.entity.*;
import com.charann.producttrackerapi.repository.UserRepository;
import com.charann.producttrackerapi.security.CustomUserDetailsService;
import com.charann.producttrackerapi.service.UserService;
import com.charann.producttrackerapi.util.EmailUtil;
import com.charann.producttrackerapi.util.JwtTokenUtil;
import com.charann.producttrackerapi.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
@CrossOrigin("*")
@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpUtil otpUtil;

	@Autowired
	private EmailUtil emailUtil;

	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {


		System.out.println(authModel.isActive());
//		if (authModel.isActive()){
//			return new ResponseEntity<JwtResponse>(new JwtResponse(), HttpStatus.BAD_REQUEST);
//		}

		authenticate(authModel.getEmail(), authModel.getPassword(), authModel.isActive());
		System.out.println(authModel.isActive());
		System.out.println(authModel.getEmail()+" "+ authModel.getPassword());
		//we need to generate the jwt token

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
//		System.out.println(userDetails.isActive());

		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);

	}


	private void authenticate(String email, String password, boolean active) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//			if(!active);
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
//		} catch (BadCredentialsException e) {
//			throw new Exception("Verify your account");
//		}
		
	}

	@PostMapping("/register")
	public ResponseEntity<String> save(@RequestBody UserModel user) {

		return new ResponseEntity<String>(userService.createUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/verify-account")
	public ResponseEntity<String> verifyAccount(@RequestBody UserModel userModel) {
		return new ResponseEntity<>(userService.verifyAccount(userModel), HttpStatus.OK);
	}

	@PostMapping("/regenerate-otp")
	public String regenerateOtp(@RequestBody RegenerateOtpModel regenerateOtpModel) {
		User user = userRepository.findByEmail(regenerateOtpModel.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + regenerateOtpModel.getEmail()));
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(regenerateOtpModel.getEmail(), otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		user.setOtp(otp);
		user.setCreatedAt(LocalDateTime.now());
		userRepository.save(user);
		return "Email sent... please verify account within 2 minute";
//		return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
	}
//	@PostMapping("/save-otp-details")
//	public ResponseEntity<String> saveOtp(@RequestBody OtpModel otpModel) {
//		return new ResponseEntity<String>(userService.saveOtp(otpModel), HttpStatus.OK);
//	}
}


















