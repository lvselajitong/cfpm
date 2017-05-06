package com.yihong.cfpm.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationCreate {
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("memory")
	private int memory;
	
	@JsonProperty("space_guid")
	private UUID spaceGuid;
	
	@JsonProperty("buildpack")
	private String buildpack;
	
	public ApplicationCreate(String name, UUID space_guid, String buildpack, int memory){
		this.name = name;
		this.spaceGuid = space_guid;
		this.buildpack = buildpack;
		this.memory = memory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public UUID getSpaceGuid() {
		return spaceGuid;
	}

	public void setSpaceGuid(UUID spaceGuid) {
		this.spaceGuid = spaceGuid;
	}

	public String getBuildpack() {
		return buildpack;
	}

	public void setBuildpack(String buildpack) {
		this.buildpack = buildpack;
	}
	
}
