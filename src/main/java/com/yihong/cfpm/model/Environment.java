package com.yihong.cfpm.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Environment {
	
	@JsonProperty("staging_env_json")
	private HashMap<Object, Object> staging_evn_json;
	
	@JsonProperty("running_env_json")
	private HashMap<Object, Object> running_evn_json;
	
	@JsonProperty("environment_json")
	private HashMap<Object, Object> environment_evn_json;
	
	@JsonProperty("system_env_json")
	private HashMap<Object, Object> system_evn_json;
	
	@JsonProperty("application_env_json")
	private HashMap<Object, Object> application_evn_json;

	public HashMap<Object, Object> getStaging_evn_json() {
		return staging_evn_json;
	}

	public void setStaging_evn_json(HashMap<Object, Object> staging_evn_json) {
		this.staging_evn_json = staging_evn_json;
	}

	public HashMap<Object, Object> getRunning_evn_json() {
		return running_evn_json;
	}

	public void setRunning_evn_json(HashMap<Object, Object> running_evn_json) {
		this.running_evn_json = running_evn_json;
	}

	public HashMap<Object, Object> getEnvironment_evn_json() {
		return environment_evn_json;
	}

	public void setEnvironment_evn_json(HashMap<Object, Object> environment_evn_json) {
		this.environment_evn_json = environment_evn_json;
	}

	public HashMap<Object, Object> getSystem_evn_json() {
		return system_evn_json;
	}

	public void setSystem_evn_json(HashMap<Object, Object> system_evn_json) {
		this.system_evn_json = system_evn_json;
	}

	public HashMap<Object, Object> getApplication_evn_json() {
		return application_evn_json;
	}

	public void setApplication_evn_json(HashMap<Object, Object> application_evn_json) {
		this.application_evn_json = application_evn_json;
	}
	
	
}
