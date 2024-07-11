package com.charann.producttrackerapi.service;

import com.charann.producttrackerapi.entity.*;
import com.charann.producttrackerapi.repository.OtpRepository;
import com.charann.producttrackerapi.security.CustomUserDetailsService;
import com.charann.producttrackerapi.util.EmailUtil;
import com.charann.producttrackerapi.util.JwtTokenUtil;
import com.charann.producttrackerapi.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.charann.producttrackerapi.exceptions.ItemExistsException;
import com.charann.producttrackerapi.exceptions.ResourceNotFoundException;
import com.charann.producttrackerapi.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpUtil otpUtil;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private OtpRepository otpRepository;

//	@Autowired
//	private AuthModel authModel;

	@Override
	public String createUser(UserModel user) {
		OtpModel otpModel = new OtpModel();



//		System.out.println(user.isActive());
		//		if (userRepository.existsByEmail(user.getEmail()) && !user.isActive()) {
		//			System.out.println(user.isActive());
		//			deleteUser(user);
		//		}
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new ItemExistsException("User is already register with email:"+user.getEmail());
		}


//			authModel.setActive(true);
//			System.out.println(authModel.isActive());

//		OtpModel otpModel =  otpRepository.findByEmail(user.getEmail())
//				.orElseThrow(()->new RuntimeException("User not found with this email: " + user.getEmail()));
		System.out.println(otpRepository.findByEmail(user.getEmail()).get().getOtp());
//		System.out.println(user.getOtp()+" "+otpRepository.findByEmail(user.getEmail()).get().getOtp());
		if (user.getOtp().equals(otpRepository.findByEmail(user.getEmail()).get().getOtp())){
			User newUser = new User();
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setEmail(user.getEmail());
			newUser.setAge(user.getAge());
			newUser.setOtp(user.getOtp());
			newUser.setCreatedAt(LocalDateTime.now());
			user.setActive(true);
			newUser.setActive(user.isActive());
			System.out.println(user);
			userRepository.save(newUser);
			return "OTP verified!!!";
		}


		return "Please regenerate otp and try again";

//		final UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
//		System.out.println(userDetails.isActive());
//		final String token = jwtTokenUtil.generateToken(userDetails);
//		System.out.println(token);
//		return userRepository.save(newUser);
	}


	public String verifyAccount(UserModel userModel) {

		String otp = otpUtil.generateOtp();

//		User user = userRepository.findByEmail(userModel.getEmail())
//				.orElseThrow(() -> new RuntimeException("User not found with this email: " + otpModel.getEmail()));
//		AuthModel authModel = new AuthModel();

		try {
			emailUtil.sendOtpEmail(userModel.getEmail(), otp);
			saveOtp(userModel.getEmail(), otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		return "Email Sent";

	}
	//new java.sql.TimeStamp(set.getTimestamp("regdate"), YOUR_CERT_PATH)


//	public String login(AuthModel authModel) {
//		User user = userRepository.findByEmail(authModel.getEmail())
//				.orElseThrow(
//						() -> new RuntimeException("User not found with this email: " + authModel.getEmail()));
//		if (!authModel.getPassword().equals(user.getPassword())) {
//			return "Password is incorrect";
//		} else if (!user.getActive()) {
//			return "your account is not verified";
//		}
//		return "Login successful";
//	}
	@Override
	public String saveOtp(String email, String otp) {
		Otp otpTable = new Otp();

		if (userRepository.existsByEmail(email)) {
			throw new ItemExistsException("User is already registered with email:"+email);
		}

		if(otpRepository.existsByEmail(email)) {
//			otpRepository.delete(otpRepository.findByEmail(email)
//					.orElseThrow(()));
			Otp otp1 = otpRepository.findByEmail(email).get();
			otp1.setOtp(otp);
			otp1.setGeneratedAt(LocalDateTime.now());
//			otpTable.setOtp(otp);
//			otpTable.setGeneratedAt(LocalDateTime.now());
			otpRepository.save(otp1);

			return "Already in db";
		}

		otpTable.setEmail(email);
		otpTable.setOtp(otp);
		otpTable.setGeneratedAt(LocalDateTime.now());
		otpRepository.save(otpTable);
		return "Otp saved successfully!";

	}

	public String regenerateOtp(RegenerateOtpModel regenerateOtpModel) {
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
		return "Email sent... please verify account within 1 minute";
	}

	@Override
	public User readUser() {
		Long userId = getLoggedInUser().getId();
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the id:"+userId));
	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser = readUser();
		existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
		existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
		existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
		existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(UserModel user) {
		User existingUser = userRepository.findByEmail(user.getEmail())
				.orElseThrow(()->new UsernameNotFoundException("User not found"));
		userRepository.deleteById(existingUser.getId());
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email"+email));
	}


}

























