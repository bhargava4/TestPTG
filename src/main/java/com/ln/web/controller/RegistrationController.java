package com.ln.web.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ln.domain.LoginUser;
import com.ln.domain.RegistrationUser;
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
	public ResponseEntity registrationStatus(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String emailId, @RequestParam(required = false) String phoneNum) {
		StringBuffer errorMsg = new StringBuffer();
		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = registrationService.findUserById(userId);
			if (user != null)
				errorMsg.append("UserId already exists.");
		}
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
		if (StringUtils.isBlank(errorMsg))
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().body(errorMsg);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity registerUser(@Valid @RequestBody RegistrationUser lUser, HttpServletRequest request) {

		// Verify recaptcha
		String captchaVerifyMessage = recaptchaService.verifyRecaptcha(request.getRemoteAddr(),
				lUser.getRecaptchaResp());
		if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
			Map<String, Object> response = new HashMap<>();
			response.put("error", "Captcha error");
			response.put("message", captchaVerifyMessage);
			return ResponseEntity.badRequest().body(response);
		}
		// Verify recaptcha done

		// varify user exists with userid
		User dbUser = registrationService.findUserById(lUser.getUserId());
		if (dbUser != null)
			return ResponseEntity.badRequest().body("UserId already exists");

		// Map User object
		User user = new User();
		user.setUserId(lUser.getUserId());
		user.setEmailId(lUser.getEmailId());
		user.setPassword(DigestUtils.md5DigestAsHex(lUser.getPassword().getBytes()));
		user.setPhoneNum(lUser.getPhoneNum());
		user.setName(lUser.getName());

		// create user
		registrationService.createUser(user);

		// confirmation email logic
		// publishVerificationEmail(user, request.getContextPath());

		return ResponseEntity.ok("User registered successfully.");
	}

	private void publishVerificationEmail(User user, String url) {
		String token = UUID.randomUUID().toString();
		registrationService.updateRegTokenAndStatus(user.getUserId(), token, false);

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
	public ResponseEntity verifyRegistration(@RequestParam String token) throws Exception {

		User user = registrationService.getUserByToken(token);
		if (user == null)
			return ResponseEntity.badRequest().body("User does not exist or user already verfied");

		registrationService.updateRegTokenAndStatus(user.getUserId(), null, true);

		return ResponseEntity.ok("User verified successfully");
	}

	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity verifyLoginUser(@Valid @RequestBody LoginUser user) throws Exception {

		user = registrationService.getUserByIdPwd(user.getUserId(),
				DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid UserId or Password");

		return ResponseEntity.ok(user);
	}*/

}
