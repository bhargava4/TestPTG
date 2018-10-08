package com.ln.dao;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ln.entity.User;

@Repository
public class RegistrationDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void createUser(User user) {
		mongoTemplate.save(user);
	}

	public User findUserById(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		return mongoTemplate.findOne(query, User.class);
	}

	public void updateRegTokenAndStatus(String userId, String token, boolean status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));

		Update update = new Update();
		update.set("token", token);
		update.set("authenticated", status);
		update.set("updateDate", new Date());

		mongoTemplate.updateFirst(query, update, User.class);
	}

	public User getUserByToken(String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("token").is(token));
		return mongoTemplate.findOne(query, User.class);
	}

	public @Valid User getUserByIdPwd(String userId, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		query.addCriteria(Criteria.where("password").is(password));
		query.fields().include("userId").include("name").include("emailId").include("phoneNum").include("approver");
		return mongoTemplate.findOne(query, User.class);
	}

	public User findUserByEmailId(String emailId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("emailId").is(emailId));
		return mongoTemplate.findOne(query, User.class);
	}

	public User findUserByPhoneNum(String phoneNum) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phoneNum").is(phoneNum));
		return mongoTemplate.findOne(query, User.class);
	}

}
