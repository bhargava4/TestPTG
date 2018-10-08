package com.ln.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.RegistrationDao;
import com.ln.domain.LoginUser;
import com.ln.entity.User;

@Service
public class RegistrationService {

	@Autowired
	RegistrationDao registrationDao;

	public User findUserById(String userId) {
		return registrationDao.findUserById(userId);
	}

	public void createUser(User user) {
		registrationDao.createUser(user);
	}

	public void updateRegTokenAndStatus(String userId, String token, boolean status) {
		registrationDao.updateRegTokenAndStatus(userId, token, status);
	}

	public User getUserByToken(String token) {
		return registrationDao.getUserByToken(token);
	}

	public LoginUser getUserByIdPwd(String userId, String password) {
		User user = registrationDao.getUserByIdPwd(userId, password);
		if(user != null) {
			LoginUser luser = new LoginUser();
			luser.setApprover(user.getApprover());
			luser.setAuthenticated(user.getAuthenticated());
			luser.setEmailId(user.getEmailId());
			luser.setName(user.getName());
			luser.setPhoneNum(user.getPhoneNum());
			luser.setUserId(user.getUserId());
			return luser;
		}
		return null;
	}

	public User findUserByEmailId(String emailId) {
		return registrationDao.findUserByEmailId(emailId);
	}

	public User findUserByPhoneNum(String phoneNum) {
		return registrationDao.findUserByPhoneNum(phoneNum);
	}

}
