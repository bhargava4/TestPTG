package com.ln.entity;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "channel_subscribes")
public class ChannelSubscribes {

	@Id
	private String id;

	@NotBlank
	private ObjectId channelId;

	@NotBlank
	private ObjectId userId;

	public ChannelSubscribes() {
		super();
	}

	public ChannelSubscribes(@NotBlank ObjectId channelId, @NotBlank ObjectId userId) {
		super();
		this.channelId = channelId;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectId getChannelId() {
		return channelId;
	}

	public void setChannelId(ObjectId channelId) {
		this.channelId = channelId;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

}
