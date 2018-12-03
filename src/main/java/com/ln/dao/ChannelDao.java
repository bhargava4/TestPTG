package com.ln.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ln.entity.Channel;
import com.ln.entity.ChannelSubscribes;

@Repository
public class ChannelDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Channel> getChannels(Boolean publicChannel, String channelId) {
		Query query = new Query();
		if (publicChannel != null)
			query.addCriteria(Criteria.where("publicChannel").is(publicChannel));
		if (StringUtils.isNotBlank(channelId))
			query.addCriteria(Criteria.where("_id").is(channelId));
		return mongoTemplate.find(query, Channel.class);
	}

	public void createChannel(Channel channel) {
		mongoTemplate.save(channel);
	}

	public void subscribeChannel(ObjectId channelId, ObjectId userId) {
		mongoTemplate.save(new ChannelSubscribes(channelId, userId));
	}

	public void unsubscribeChannel(ObjectId channelId, ObjectId userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("channelId").is(channelId).and("userId").is(userId));
		mongoTemplate.remove(query, ChannelSubscribes.class);
	}

	public List<Channel> getChannels(ObjectId userId) {
		List<ChannelSubscribes> cs = getSubscribedChannels(userId);
		List<ObjectId> chnlIds = new ArrayList<>();
		if (cs != null && cs.size() > 0)
			chnlIds = cs.stream().map(chs -> chs.getChannelId()).collect(Collectors.toList());
		Criteria criteria = new Criteria().orOperator(Criteria.where("publicChannel").is(false).and("_id").in(chnlIds),
				Criteria.where("publicChannel").is(true));
		Query query = new Query();
		query.addCriteria(criteria);
		List<Channel> result = mongoTemplate.find(query, Channel.class);
		return result;
	}

	public List<Channel> getChannelsForReview(ObjectId reviewerId, boolean adminInd) {
		Query query = new Query();
		if (adminInd)
			query.addCriteria(Criteria.where("publicChannel").is(true));
		else
			query.addCriteria(Criteria.where("approvers").is(reviewerId));
		return mongoTemplate.find(query, Channel.class);
	}

	public List<ChannelSubscribes> getSubscribedChannels(ObjectId userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		return mongoTemplate.find(query, ChannelSubscribes.class);
	}

}
