package com.ln.domain;

import java.util.Date;
import java.util.List;

import com.ln.entity.Channel;
import com.ln.entity.User;

public class NewsUserAgg {

	private String id;

	private String title;

	private String description;

	private String refLink;

	private Object[] location;

	private String newsDate;

	private String editorId;

	private Date publishDate = new Date();

	private String reviewerId;

	private Date reviewDate;

	private String status;

	private Date createDate = new Date();

	private Date updateDate = new Date();

	private int likesCount;

	private int dislikesCount;

	private List<User> editors;

	private Channel channelData;

	private String language;

	private String channel;

	private Object currentLocation;

	private int myLike;

	private Object distance;

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

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
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

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public int getDislikesCount() {
		return dislikesCount;
	}

	public void setDislikesCount(int dislikesCount) {
		this.dislikesCount = dislikesCount;
	}

	public List<User> getEditors() {
		return editors;
	}

	public void setEditors(List<User> editors) {
		this.editors = editors;
	}

	public int getMyLike() {
		return myLike;
	}

	public void setMyLike(int myLike) {
		this.myLike = myLike;
	}

	public Channel getChannelData() {
		return channelData;
	}

	public void setChannelData(Channel channelData) {
		this.channelData = channelData;
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

	public Object getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Object currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Object getDistance() {
		return distance;
	}

	public void setDistance(Object distance) {
		this.distance = distance;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NewsUserAgg))
			return false;
		NewsUserAgg castOther = (NewsUserAgg) other;
		return ((this.getId() == castOther.getId())
				|| (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())));
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		return result;
	}

}
