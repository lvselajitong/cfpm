package com.yihong.cfpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EventMetadata {
	
	@JsonProperty("request")
	private EventRequest request;

	public EventRequest getRequest() {
		return request;
	}

	public void setRequest(EventRequest request) {
		this.request = request;
	}
	
}
