package com.ln.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ln.entity.Location;
import com.ln.entity.States;

@Repository
public class LocationDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void createLocation(Location location) {
		mongoTemplate.save(location);
	}

	public List<Location> findLocationsByState(String state) {
		Query query = new Query();
		if(StringUtils.isNotBlank(state))
			query.addCriteria(Criteria.where("state").is(state));
		return mongoTemplate.find(query, Location.class);
	}
	
	public List<States> findStates(){
		return mongoTemplate.findAll(States.class);
	}

}
