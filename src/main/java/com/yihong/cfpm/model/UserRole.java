package com.yihong.cfpm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRole {
	@JsonProperty("active")
	private int active;
	@JsonProperty("admin")
	private int admin;
	
	@JsonProperty("audited_spaces_url")
	private String audited_spaces_url;
	
	@JsonProperty("billing_managed_organizations_url")
	private String billing_managed_organizations_url;
	
	@JsonProperty("default_space_guid")
	private String default_space_guid;
	
	@JsonProperty("managed_organizations_url")
	private String managed_organizations_url;
	
	@JsonProperty("managed_spacees_url")
	private String managed_spaces_url;
	
	@JsonProperty("organization_roles")
	private List<String> organization_roles;
	
	@JsonProperty("organization_url")
	private String organization_url;
	
	@JsonProperty("spaces_url")
	private String spaces_url;
	
	@JsonProperty("username")
	private String username;

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getAudited_spaces_url() {
		return audited_spaces_url;
	}

	public void setAudited_spaces_url(String audited_spaces_url) {
		this.audited_spaces_url = audited_spaces_url;
	}

	public String getBilling_managed_organizations_url() {
		return billing_managed_organizations_url;
	}

	public void setBilling_managed_organizations_url(String billing_managed_organizations_url) {
		this.billing_managed_organizations_url = billing_managed_organizations_url;
	}

	public String getDefault_space_guid() {
		return default_space_guid;
	}

	public void setDefault_space_guid(String default_space_guid) {
		this.default_space_guid = default_space_guid;
	}

	public String getManaged_organizations_url() {
		return managed_organizations_url;
	}

	public void setManaged_organizations_url(String managed_organizations_url) {
		this.managed_organizations_url = managed_organizations_url;
	}

	public String getManaged_spaces_url() {
		return managed_spaces_url;
	}

	public void setManaged_spaces_url(String managed_spaces_url) {
		this.managed_spaces_url = managed_spaces_url;
	}

	public List<String> getOrganization_roles() {
		return organization_roles;
	}

	public void setOrganization_roles(List<String> organization_roles) {
		this.organization_roles = organization_roles;
	}

	public String getOrganization_url() {
		return organization_url;
	}

	public void setOrganization_url(String organization_url) {
		this.organization_url = organization_url;
	}

	public String getSpaces_url() {
		return spaces_url;
	}

	public void setSpaces_url(String spaces_url) {
		this.spaces_url = spaces_url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
