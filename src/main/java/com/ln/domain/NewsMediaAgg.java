package com.ln.domain;

import java.util.List;

import com.ln.entity.ImageChunk;
import com.ln.entity.ImageFile;

public class NewsMediaAgg {

	private String id;

	private List<ImageFile> imageFiles;

	private List<ImageChunk> imageChunks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
