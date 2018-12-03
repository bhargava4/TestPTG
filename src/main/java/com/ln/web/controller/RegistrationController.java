package com.ln.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ln.domain.RegistrationUser;
import com.ln.domain.ReturnResponse;
import com.ln.entity.User;
import com.ln.service.RecaptchaService;
import com.ln.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private RecaptchaService recaptchaService;

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value = "/validate-user", method = RequestMethod.GET)
	public Object registrationStatus(@RequestParam(required = false) String emailId,
			@RequestParam(required = false) String phoneNum) {
		StringBuffer errorMsg = verifyUser(emailId, phoneNum);
		if (StringUtils.isBlank(errorMsg))
			return ReturnResponse.getHttpStatusResponse("Id does not exist", HttpStatus.OK, null);
		return ResponseEntity.badRequest().body(errorMsg.toString());
	}

	private StringBuffer verifyUser(String emailId, String phoneNum) {
		StringBuffer errorMsg = new StringBuffer();
		User user = null;
		if (StringUtils.isNotBlank(emailId)) {
			user = registrationService.findUserByEmailId(emailId);
			if (user != null)
				errorMsg.append("EmailId already exists.");
		}
		if (StringUtils.isNotBlank(phoneNum)) {
			user = registrationService.findUserByPhoneNum(phoneNum);
			if (user != null)
				errorMsg.append("Phone number already exists.");
		}
		return errorMsg;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public Object registerUser(@Valid @RequestBody RegistrationUser lUser, HttpServletRequest request) {

		// Verify recaptcha
		String captchaVerifyMessage = recaptchaService.verifyRecaptcha(request.getRemoteAddr(),
				lUser.getRecaptchaResp());
		if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
			return ResponseEntity.badRequest().body(captchaVerifyMessage);
		}
		// Verify recaptcha done

		// varify user exists with phone and email
		StringBuffer errorMsg = verifyUser(lUser.getEmailId(), lUser.getPhoneNum());
		if (StringUtils.isNotBlank(errorMsg))
			return ResponseEntity.badRequest().body(errorMsg.toString());

		// Map User object
		User user = new User();
		user.setEmailId(lUser.getEmailId());
		user.setPassword(DigestUtils.md5DigestAsHex(lUser.getPassword().getBytes()));
		user.setPhoneNum(lUser.getPhoneNum());
		user.setName(lUser.getName());

		// create user
		registrationService.createUser(user);

		// confirmation email logic
		if (StringUtils.isNotBlank(user.getEmailId()))
			publishVerificationEmail(user, request.getContextPath());

		return ReturnResponse.getHttpStatusResponse("User registered successfully", HttpStatus.OK, null);
	}

	private void publishVerificationEmail(User user, String url) {
		String token = UUID.randomUUID().toString();
		registrationService.updateRegTokenAndStatus(user.getEmailId(), token, false);

		String confirmationUrl = url + "/verify-registration?token=" + token;

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("bhargava4@gmail.com");
		email.setTo(user.getEmailId());
		email.setSubject("Registration Confirmation");
		email.setText("User registered successfully. Please click on link to activate. "
				+ "http://localhost:8080/api-ln" + confirmationUrl);

		mailSender.send(email);
	}

	@RequestMapping(value = "/verify-registration", method = RequestMethod.GET)
	public Object verifyRegistration(@RequestParam String token) throws Exception {
		User user = registrationService.getUserByToken(token);
		if (user == null)
			return ResponseEntity.badRequest().body("User does not exist or user already verfied");
		registrationService.updateRegTokenAndStatus(user.getEmailId(), null, true);
		return ReturnResponse.getHttpStatusResponse("User verified successfully", HttpStatus.OK, null);
	}

}
