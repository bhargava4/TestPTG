package com.ln.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "publish_news")
public class PublishNews {

	@Id
	private String id;

	private String title;

	private String description;

	private String refLink;

	private Object[] location;

	private String newsDate;

	private ObjectId editorId;

	private Date publishDate = new Date();

	private ObjectId reviewerId;

	private Date reviewDate;

	private String status;

	private Date createDate = new Date();

	private Date updateDate = new Date();

	private String language;

	private Object currentLocation;

	private ObjectId channel;

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

	public ObjectId getEditorId() {
		return editorId;
	}

	public void setEditorId(ObjectId editorId) {
		this.editorId = editorId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public ObjectId getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(ObjectId reviewerId) {
		this.reviewerId = reviewerId;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Object getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Object currentLocation) {
		this.currentLocation = currentLocation;
	}

	public ObjectId getChannel() {
		return channel;
	}

	public void setChannel(ObjectId channel) {
		this.channel = channel;
	}

}
