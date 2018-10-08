package com.ln.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ln.entity.ImageChunk;
import com.ln.entity.ImageFile;

@JsonAutoDetect
public class PublicNews extends PostNews {

	private String publishedBy;

	private int likesCount;

	private int dislikesCount;

	private int mylike;

	private List<ImageFile> imageFiles;

	private List<ImageChunk> imageChunks;

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
