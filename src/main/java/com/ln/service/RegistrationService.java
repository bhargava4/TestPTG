package com.ln.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.RegistrationDao;
import com.ln.entity.User;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationDao registrationDao;

	public void createUser(User user) {
		registrationDao.createUser(user);
	}

	public void updateRegTokenAndStatus(String emailId, String token, boolean status) {
		registrationDao.updateRegTokenAndStatus(emailId, token, status);
	}

	public User getUserByToken(String token) {
		return registrationDao.getUserByToken(token);
	}

	public User findUserByEmailId(String emailId) {
		return registrationDao.findUserByEmailId(emailId);
	}

	public User findUserByPhoneNum(String phoneNum) {
		return registrationDao.findUserByPhoneNum(phoneNum);
	}

}
