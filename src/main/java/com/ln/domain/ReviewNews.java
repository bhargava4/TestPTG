package com.ln.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ln.entity.ImageChunk;
import com.ln.entity.ImageFile;

@JsonAutoDetect
public class ReviewNews extends PostNews {

	@NotBlank
	private String id;

	@NotBlank
	@JsonInclude(Include.NON_NULL)
	private String reviewerId;

	@NotBlank
	@Pattern(regexp = "^(?:A|R)$")
	private String status;

	@JsonInclude(Include.NON_NULL)
	private String publishedBy;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

}
