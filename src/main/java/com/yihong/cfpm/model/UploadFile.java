package com.yihong.cfpm.model;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadFile {

	@JsonProperty("resources")
	private File resources;
}
