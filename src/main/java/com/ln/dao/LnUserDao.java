package com.ln.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ln.entity.User;

@Repository
public class LnUserDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> getUsers(String search) {
		Query query = new Query();
		if(StringUtils.isNotBlank(search))
			query.addCriteria((new Criteria()).orOperator(Criteria.where("phoneNum").regex(search, "i"),
				Criteria.where("emailId").regex(search, "i"), Criteria.where("name").regex(search, "i")));
		return mongoTemplate.find(query, User.class);
	}

	public List<User> getPaidUsers(String search) {
		Query query = new Query();
		Criteria crit = new Criteria();
		if(StringUtils.isNotBlank(search))
			crit.orOperator(Criteria.where("phoneNum").regex(search, "i"),
				Criteria.where("emailId").regex(search, "i"), Criteria.where("name").regex(search, "i"));
		crit.and("roles").is("PUSER1");
		query.addCriteria(crit);
		return mongoTemplate.find(query, User.class);
	}

	public void paidUser(ObjectId userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));

		Update update = new Update();
		update.push("roles", "PUSER1");
		mongoTemplate.updateFirst(query, update, User.class);
	}

}
