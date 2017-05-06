package com.yihong.cfpm.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Buildpack {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("filename")
	private String filename;
	
	@JsonProperty("enabled")
	private boolean enabled;
	
	@JsonProperty("locked")
	private boolean locked;
	
	@JsonProperty("position")
	private int position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
