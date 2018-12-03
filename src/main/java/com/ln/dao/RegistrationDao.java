package com.ln.dao;

import java.util.Date;

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

	public User findUserById(String loginId) {
		Query query = new Query();
		query.addCriteria((new Criteria()).orOperator(Criteria.where("phoneNum").is(loginId),
				Criteria.where("emailId").is(loginId)));
		return mongoTemplate.findOne(query, User.class);
	}

	public void updateRegTokenAndStatus(String emailId, String token, boolean status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("emailId").is(emailId));

		Update update = new Update();
		update.set("token", token);
		update.set("emailAuthenticated", status);
		update.set("updateDate", new Date());

		mongoTemplate.updateFirst(query, update, User.class);
	}

	public User getUserByToken(String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("token").is(token));
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
