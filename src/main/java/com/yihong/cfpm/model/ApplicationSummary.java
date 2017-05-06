package com.yihong.cfpm.model;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationSummary {
	@JsonProperty("guid")
	private UUID guid;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("apps")
	private List<Application> applications;
	
	@JsonProperty("services")
	private List<Service> services;

	public UUID getGuid() {
		return guid;
	}

	public void setGuid(UUID guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	
}
