package com.ln.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ln.SecurityUtils;
import com.ln.domain.LoginUser;
import com.ln.domain.ReturnResponse;
import com.ln.entity.CustomUserDetails;
import com.ln.service.LnUserService;

@RestController
public class LnUserController {

	@Autowired
	private LnUserService lnUserService;

	@RequestMapping(value = "/user/user-details", method = RequestMethod.GET)
	public Object getUserDetails() throws Exception {
		CustomUserDetails user = SecurityUtils.getUser();
		if (user != null) {
			LoginUser luser = new LoginUser();
			luser.setUserId(user.getId());
			luser.setEmailAuthenticated(user.getEmailAuthenticated());
			luser.setEmailId(user.getEmailId());
			luser.setName(user.getName());
			luser.setPhoneNum(user.getPhoneNum());
			luser.setRoles(user.getAuthorities().stream().map(userAuth -> userAuth.getAuthority())
					.collect(Collectors.toList()));
			return ReturnResponse.getHttpStatusResponse("User details retrieved successfully", HttpStatus.OK, luser);
		}
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public ReturnResponse<?> getUsers(@RequestParam(required = false) String search) throws Exception {
		List<LoginUser> users = lnUserService.getUsers(search);
		return ReturnResponse.getHttpStatusResponse("Users retrieved successfully", HttpStatus.OK, users);
	}

	@RequestMapping(value = "/admin/paid-users", method = RequestMethod.GET)
	public ReturnResponse<?> getPaidUsers(@RequestParam(required = false) String search) throws Exception {
		List<LoginUser> users = lnUserService.getPaidUsers(search);
		return ReturnResponse.getHttpStatusResponse("Paid users retrieved successfully", HttpStatus.OK, users);
	}

	@RequestMapping(value = "/admin/paid-user/{userId}", method = RequestMethod.POST)
	public ReturnResponse<?> paidUser(@PathVariable String userId) throws Exception {
		lnUserService.paidUser(userId);
		return ReturnResponse.getHttpStatusResponse("User role updated successfully", HttpStatus.OK, null);
	}

}
