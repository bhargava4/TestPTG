package com.ln.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ln.entity.Location;

@JsonAutoDetect
public class PostNews {

	private String id;

	@NotBlank
	private String title;

	@NotBlank
	private String description;

	@URL
	private String refLink;

	private String[] location;

	@NotBlank
	private String newsDate;

	@NotBlank
	private String editorId;

	private List<Location> locationData;

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

	public List<Location> getLocationData() {
		return locationData;
	}

	public void setLocationData(List<Location> locationData) {
		this.locationData = locationData;
	}

}
