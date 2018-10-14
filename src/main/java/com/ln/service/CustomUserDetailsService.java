package com.ln.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ln.dao.RegistrationDao;
import com.ln.entity.CustomUserDetails;
import com.ln.entity.User;

@Service(value = "userService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private RegistrationDao registrationDao;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = registrationDao.findUserById(userId);
		Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
		return new CustomUserDetails(user);
	}

}