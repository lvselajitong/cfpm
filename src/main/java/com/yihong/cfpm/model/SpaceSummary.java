package com.yihong.cfpm.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiong.cfpm.model.base.CloudFoundryResources;

public class SpaceSummary {
	@JsonProperty("guid")
	private String guid;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("apps")
	private CloudFoundryResources<Application> apps;
	
	@JsonProperty("services")
	private CloudFoundryResources<Service> services;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CloudFoundryResources<Application> getApps() {
		return apps;
	}

	public void setApps(CloudFoundryResources<Application> apps) {
		this.apps = apps;
	}

	public CloudFoundryResources<Service> getServices() {
		return services;
	}

	public void setServices(CloudFoundryResources<Service> services) {
		this.services = services;
	}
	
	
}
