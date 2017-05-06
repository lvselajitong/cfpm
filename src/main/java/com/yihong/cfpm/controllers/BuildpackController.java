package com.yihong.cfpm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihong.cfpm.model.Buildpack;
import com.yihong.cfpm.model.Space;
import com.yihong.cfpm.repositories.RestRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;

@RestController
@RequestMapping(value = "/api")
public class BuildpackController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestRepository restRepository;
	
	private static final String V2_BUILDPACKS = "v2/buildpacks";
	
	@RequestMapping(value = "/buildpacks", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Buildpack>> getAllBuildpacks(@RequestHeader("Authorization") String token){
     CloudFoundryResources<Buildpack> buildpacks = restRepository.list(token, V2_BUILDPACKS, 0, false);
     return buildpacks.getResources();
	}
	
}
