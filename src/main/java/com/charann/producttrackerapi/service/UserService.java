package com.charann.producttrackerapi.service;

import com.charann.producttrackerapi.entity.OtpModel;
import com.charann.producttrackerapi.entity.RegenerateOtpModel;
import com.charann.producttrackerapi.entity.User;
import com.charann.producttrackerapi.entity.UserModel;

public interface UserService {
	
	String createUser(UserModel user);
	
	User readUser();
	
	User updateUser(UserModel user);
	
	void deleteUser(UserModel user);
	
	User getLoggedInUser();

	String verifyAccount(UserModel userModel);

	String regenerateOtp(RegenerateOtpModel regenerateOtpModel);

	String saveOtp(String email, String otp);

//	String sendOtpMail(UserModel user);
}
