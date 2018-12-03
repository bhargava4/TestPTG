package com.ln.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.LnUserDao;
import com.ln.domain.LoginUser;
import com.ln.entity.User;

@Service
public class LnUserService {

	@Autowired
	private LnUserDao lnUserDao;

	public List<LoginUser> getUsers(String search) {
		List<User> users = lnUserDao.getUsers(search);
		List<LoginUser> fUsers = new ArrayList<>();
		users.stream().forEach(user -> {
			LoginUser lUser = new LoginUser();
			lUser.setEmailId(user.getEmailId());
			lUser.setName(user.getName());
			lUser.setPhoneNum(user.getPhoneNum());
			lUser.setUserId(user.getId());
			lUser.getRoles().add("ROLE_USER");
			if (user.getRoles() != null && user.getRoles().size() > 0) {
				user.getRoles().forEach(sr -> {
					lUser.getRoles().add("ROLE_" + sr);
				});
			}
			fUsers.add(lUser);
		});
		return fUsers;
	}

	public List<LoginUser> getPaidUsers(String search) {
		List<User> users = lnUserDao.getPaidUsers(search);
		List<LoginUser> fUsers = new ArrayList<>();
		users.stream().forEach(user -> {
			LoginUser lUser = new LoginUser();
			lUser.setEmailId(user.getEmailId());
			lUser.setName(user.getName());
			lUser.setPhoneNum(user.getPhoneNum());
			lUser.setUserId(user.getId());
			lUser.getRoles().add("ROLE_USER");
			if (user.getRoles() != null && user.getRoles().size() > 0) {
				user.getRoles().forEach(sr -> {
					lUser.getRoles().add("ROLE_" + sr);
				});
			}
			fUsers.add(lUser);
		});
		return fUsers;
	}

	public void paidUser(String userId) {
		lnUserDao.paidUser(new ObjectId(userId));
	}

}
