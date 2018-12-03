package com.ln.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class PublicNews extends PostNews {

	private String publishedBy;

	private int likesCount;

	private int dislikesCount;

	private int mylike;

	public String getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
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

	public int getMylike() {
		return mylike;
	}

	public void setMylike(int mylike) {
		this.mylike = mylike;
	}

}
