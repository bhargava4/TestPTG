package com.ln.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "fs.chunks")
public class ImageChunk {

	@Id
	private String id;

	private long n;

	@Field("files_id")
	private String filesId;

	private Binary data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getN() {
		return n;
	}

	public void setN(long n) {
		this.n = n;
	}

	public String getFilesId() {
		return filesId;
	}

	public void setFilesId(String filesId) {
		this.filesId = filesId;
	}

	public Binary getData() {
		return data;
	}

	public void setData(Binary data) {
		this.data = data;
	}

}
