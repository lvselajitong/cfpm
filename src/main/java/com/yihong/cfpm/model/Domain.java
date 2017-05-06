package com.yihong.cfpm.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domain {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("owning_organization_guid")
	private UUID owningOrganizationGuid;
	
	@JsonProperty("owning_organization_url")
	private String owningOrganizationUrl;
	
	@JsonProperty("wildcard")
	private boolean wildcard;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getOwningOrganizationGuid() {
		return owningOrganizationGuid;
	}

	public void setOwningOrganizationGuid(UUID owningOrganizationGuid) {
		this.owningOrganizationGuid = owningOrganizationGuid;
	}

	public String getOwningOrganizationUrl() {
		return owningOrganizationUrl;
	}

	public void setOwningOrganizationUrl(String owningOrganizationUrl) {
		this.owningOrganizationUrl = owningOrganizationUrl;
	}

	public boolean isWildcard() {
		return wildcard;
	}

	public void setWildcard(boolean wildcard) {
		this.wildcard = wildcard;
	}

	
}

