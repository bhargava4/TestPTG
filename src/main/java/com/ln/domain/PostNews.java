package com.ln.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ln.entity.Channel;

@JsonAutoDetect
public class PostNews {

	private String id;

	@NotBlank
	private String title;

	@NotBlank
	private String description;

	@URL
	private String refLink;

	@Size(max = 5, min = 1)
	private Object[] location;

	@NotBlank
	private String newsDate;

	@NotBlank
	private String editorId;

	private Channel channelData;

	private Object currentLocation;

	@NotBlank
	private String language;

	@NotBlank
	private String channel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefLink() {
		return refLink;
	}

	public void setRefLink(String refLink) {
		this.refLink = refLink;
	}

	public Object[] getLocation() {
		return location;
	}

	public void setLocation(Object[] location) {
		this.location = location;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public Object getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Object currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Channel getChannelData() {
		return channelData;
	}

	public void setChannelData(Channel channelData) {
		this.channelData = channelData;
	}

}
