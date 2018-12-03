package com.ln.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.ChannelDao;
import com.ln.entity.Channel;

@Service
public class ChannelService {

	@Autowired
	private ChannelDao channelDao;

	public List<Channel> getChannels(Boolean publicChannel, String channelId) {
		return channelDao.getChannels(publicChannel, channelId);
	}

	public void createChannel(Channel channel) {
		channelDao.createChannel(channel);
		Optional.ofNullable(channel)
		.filter(chnl -> !chnl.getPublicChannel()).ifPresent(chn -> {
			Optional.ofNullable(chn.getApprovers()).ifPresent(approvers -> {
				Arrays.asList(approvers).stream().forEach(approver -> {
					subscribeChannel(channel.getId(), approver);
				});
			});
		});
	}

	public void subscribeChannel(String channelId, String userId) {
		channelDao.subscribeChannel(new ObjectId(channelId), new ObjectId(userId));
	}

	public void unsubscribeChannel(String channelId, String userId) {
		channelDao.unsubscribeChannel(new ObjectId(channelId), new ObjectId(userId));
	}

	public List<Channel> getChannels(String userId) {
		return channelDao.getChannels(new ObjectId(userId));
	}

	public List<Channel> getReviewChannels(String reviewerId, boolean adminInd) {
		return channelDao.getChannelsForReview(new ObjectId(reviewerId), adminInd);
	}

}
