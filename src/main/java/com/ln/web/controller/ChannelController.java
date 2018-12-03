package com.ln.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ln.SecurityUtils;
import com.ln.domain.ReturnResponse;
import com.ln.entity.Channel;
import com.ln.entity.CustomUserDetails;
import com.ln.service.ChannelService;

@RestController
public class ChannelController {

	@Autowired
	ChannelService channelService;

	@RequestMapping(value = "/public/channels", method = RequestMethod.GET)
	public ReturnResponse<?> getChannels(@RequestParam(required = false) Boolean publicChannel,
			@RequestParam(required = false) String channelId) {
		List<Channel> channels = channelService.getChannels(publicChannel, channelId);
		return ReturnResponse.getHttpStatusResponse("Channels retrieved successfully", HttpStatus.OK, channels);
	}

	@RequestMapping(value = "/admin/channel", method = RequestMethod.POST)
	public Object createChannel(@RequestBody Channel channel) {
		if (SecurityUtils.getUserRoles().contains("PUSER1")) {
			channel.setPublicChannel(false);
		}
		if (!channel.getPublicChannel() && (channel.getApprovers() == null || channel.getApprovers().length == 0)) {
			return ResponseEntity.badRequest().body("Approver is mandatory");
		}
		channelService.createChannel(channel);
		return ReturnResponse.getHttpStatusResponse("Channel created successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/channel/subscribe/{channelId}", method = RequestMethod.POST)
	public ReturnResponse<?> subscribeChannel(@PathVariable String channelId) {
		channelService.subscribeChannel(channelId, SecurityUtils.getUserId());
		return ReturnResponse.getHttpStatusResponse("Subscribed to channel successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/channel/unsubscribe/{channelId}", method = RequestMethod.DELETE)
	public ReturnResponse<?> unsubscribeChannel(@PathVariable String channelId) {
		channelService.unsubscribeChannel(channelId, SecurityUtils.getUserId());
		return ReturnResponse.getHttpStatusResponse("Unsubscribed to channel successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/subscribed-channels", method = RequestMethod.GET)
	public ReturnResponse<?> getChannels() {
		List<Channel> channels = channelService.getChannels(SecurityUtils.getUserId());
		return ReturnResponse.getHttpStatusResponse("Channels retrieved successfully", HttpStatus.OK, channels);
	}

	@RequestMapping(value = "/admin/review-channels", method = RequestMethod.GET)
	public ReturnResponse<?> getReviewChannels() {
		CustomUserDetails loggedInUser = SecurityUtils.getUser();
		boolean admin = false;
		if (loggedInUser.getRoles().contains("ADMIN"))
			admin = true;
		List<Channel> channels = channelService.getReviewChannels(loggedInUser.getId(), admin);
		return ReturnResponse.getHttpStatusResponse("Channels retrieved successfully", HttpStatus.OK, channels);
	}

}
