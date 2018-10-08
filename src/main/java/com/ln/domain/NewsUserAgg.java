package com.ln.domain;

import java.util.Date;
import java.util.List;

import com.ln.entity.ImageChunk;
import com.ln.entity.ImageFile;
import com.ln.entity.Location;
import com.ln.entity.User;

public class NewsUserAgg {

	private String id;

	private String title;

	private String description;

	private String refLink;

	private String[] location;

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

	private List<ImageFile> imageFiles;

	private List<ImageChunk> imageChunks;

	private List<Location> locationData;

	private int myLike;

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

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
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

	public List<ImageFile> getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(List<ImageFile> imageFiles) {
		this.imageFiles = imageFiles;
	}

	public List<ImageChunk> getImageChunks() {
		return imageChunks;
	}

	public void setImageChunks(List<ImageChunk> imageChunks) {
		this.imageChunks = imageChunks;
	}

	public List<Location> getLocationData() {
		return locationData;
	}

	public void setLocationData(List<Location> locationData) {
		this.locationData = locationData;
	}

}
