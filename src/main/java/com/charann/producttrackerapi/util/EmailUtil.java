package com.charann.producttrackerapi.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
//        mimeMessageHelper.setText("Your Otp is: "+otp);
        mimeMessageHelper.setText("""
        <div>
            Your otp is:
            <h4> %s </h4>
          
        </div>
        """.formatted(otp), true);
//        <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>

        javaMailSender.send(mimeMessage);
    }
}
