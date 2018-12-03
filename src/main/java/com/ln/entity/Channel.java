package com.ln.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ln.domain.Coordinates;

@Document(collection = "channels")
public class Channel {

	@Id
	private String id;

	@NotBlank
	@Size(max = 15)
	private String name;

	@NotBlank
	@Size(max = 30)
	private String desc;

	@Size(max = 5)
	private Coordinates[] locations;

	@Size(max = 5)
	private ObjectId[] approvers;

	private boolean publicChannel = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Coordinates[] getLocations() {
		return locations;
	}

	public void setLocations(Coordinates[] locations) {
		this.locations = locations;
	}

	public String[] getApprovers() {
		if (approvers != null && approvers.length > 0) {
			List<String> apprs = Arrays.asList(approvers).stream().map(app -> app.toString())
					.collect(Collectors.toList());
			return apprs.toArray(new String[apprs.size()]);
		}
		return null;
	}

	public void setApprovers(ObjectId[] approvers) {
		this.approvers = approvers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getPublicChannel() {
		return publicChannel;
	}

	public void setPublicChannel(boolean publicChannel) {
		this.publicChannel = publicChannel;
	}

}
