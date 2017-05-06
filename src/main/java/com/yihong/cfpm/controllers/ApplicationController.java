package com.yihong.cfpm.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihong.cfpm.model.Application;
import com.yihong.cfpm.model.ApplicationCreate;
import com.yihong.cfpm.model.ApplicationStatus;
import com.yihong.cfpm.model.ApplicationSummary;
import com.yihong.cfpm.model.Environment;
import com.yihong.cfpm.model.Event;
import com.yihong.cfpm.model.Instance;
import com.yihong.cfpm.model.Route;
import com.yihong.cfpm.model.ServiceBinding;
import com.yihong.cfpm.repositories.RestRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;


@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/api")
public class ApplicationController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestRepository restRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String V2_APPS = "v2/apps";
	private static final String V3_APPS = "v3/apps";
	private static final String V2_STACKS = "v2/stacks";
	private static final String V2_EVENTS = "v2/events";
	
	private static final String V2_SERVICE_INSTANCES = "v2/service_instances";
	
	@RequestMapping(value="/applications", method = GET)
	public @ResponseBody List<CloudFoundryResource<Application>> getApplications(@RequestHeader("Authorization") String token) {
		CloudFoundryResources<Application> apps = restRepository.list(token, V2_APPS, 0, false);
		return apps.getResources();
	}

	@RequestMapping(value = "/applications/{id}", method = GET)    
    public @ResponseBody CloudFoundryResource<Application> getApplicationById(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id) {
		System.out.println("app id:" + id);
		CloudFoundryResource<Application> application = restRepository.one(token, V2_APPS, id, 1);
        return application;
    }
	
	@RequestMapping(value = "/apps/{orgId}", method = RequestMethod.GET)
	public @ResponseBody List<CloudFoundryResource<Application>> getApplicationsForOrganization(@RequestHeader("Authorization") String token,
			@PathVariable("orgId") String orgId) {
		CloudFoundryResources<Application> apps = restRepository.list(token, V2_APPS.concat("?q=organization_guid:").concat(orgId), 1, false);
		return apps.getResources();
	}
		
	@RequestMapping(value = "/applications/{id}", method = RequestMethod.PUT)
    public CloudFoundryResource<Application> updateApplication(@RequestHeader("Authorization") final String token, 
    		@PathVariable("id") final String id, @RequestBody CloudFoundryResource<Application> application) {
		logger.info("application name");
		logger.info(application.getEntity().getName());
		return restRepository.update(token, V2_APPS.concat("/").concat(id), application);
    }
	
	@RequestMapping(value = "/applications/{id}/start", method = RequestMethod.PUT)
    public Application startApplication(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id, @RequestBody CloudFoundryResource<Application> application) {
		return restRepository.update(token, V3_APPS.concat("/").concat(id).concat("/start"), application).getEntity();
    }

	
	@RequestMapping(value = "/applications/{id}/stop", method = RequestMethod.PUT)
    public Application stopApplication(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id, @RequestBody CloudFoundryResource<Application> application) {	
		return restRepository.update(token, V3_APPS.concat("/").concat(id).concat("/stop"), application).getEntity();
    }
	
	@RequestMapping(value = "/applications/{id}/instances", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Instance>> getApplicationInstances(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id) {
		
		Map<String, Object> values = restRepository.customList(token, V2_APPS.concat("/").concat(id).concat("/instances"), 1);
		List<CloudFoundryResource<Instance>> instances = new ArrayList<>(); 
		for (Object value : values.values()) {
			instances.add(new CloudFoundryResource<Instance>(objectMapper.convertValue(value, Instance.class)));
		}
		return instances;
    }
	
	@RequestMapping(value = "/applications/{appId}/stats", method = RequestMethod.GET)
	public @ResponseBody List<CloudFoundryResource<ApplicationStatus>> getApplicationStats(@RequestHeader("Authorization") String token, 
			@PathVariable("appId") String appId) {
		
		Map<String, Object> values = restRepository.customList(token, V2_APPS.concat("/").concat(appId).concat("/stats"), 1);
		List<CloudFoundryResource<ApplicationStatus>> appStats = new ArrayList<>(); 
		for (Object value : values.values()) {
			appStats.add(new CloudFoundryResource<ApplicationStatus>(objectMapper.convertValue(value, ApplicationStatus.class)));
		}
		return appStats;
	}
	
	@RequestMapping(value = "/applications/{appId}/service_bindings", method = RequestMethod.GET)
	public @ResponseBody List<CloudFoundryResource<ServiceBinding>> getApplicationServiceBindings(@RequestHeader("Authorization") String token,
			@PathVariable("appId") final String appId) {
		CloudFoundryResources<ServiceBinding> serviceBindings = restRepository.list(token, V2_APPS.concat("/").concat(appId).concat("/service_bindings"), 1, true);
		return serviceBindings.getResources();
	}
		
	// TODO: rename binding
	@RequestMapping(value = "/applications/{id}/bindings/{bindingId}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<Instance> getApplicationInstances(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id, @PathVariable("bindingId") String bindingId) {
		
		CloudFoundryResource<Instance> instance = restRepository.one(token, V2_SERVICE_INSTANCES, bindingId, 1);
		
		return instance;
    }
	 
	@RequestMapping(value = "/applications/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteApplicationById(@RequestHeader("Authorization") final String token, 
    		@PathVariable("id") final String id) {
    	restRepository.delete(token, V2_APPS, id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/applications/{id}/routes", method = RequestMethod.GET)
    public List<CloudFoundryResource<Route>> getRoutesForApps(@RequestHeader("Authorization") final String token, 
    		@PathVariable("id") final String id) {
    	CloudFoundryResources<Route> routes = restRepository.list(token, V2_APPS.concat("/").concat(id).concat("/routes"), 0, false);
    	return routes.getResources();
    }
	
	@RequestMapping(value = "/applications/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestBody MultipartFile file){
		
		
		return "";
	}
	
	@RequestMapping(value = "/applications", method = RequestMethod.POST)
	public @ResponseBody CloudFoundryResource<Application> createApplication(@RequestHeader("Authorization") String token, @RequestBody Application app){
		CloudFoundryResource<Application> resource = new CloudFoundryResource<Application>();
		resource.setEntity(app);
		CloudFoundryResource<Application> application = restRepository.save(token, V2_APPS, resource);
		return application;
	}
	@RequestMapping(value = "/applications/{id}/env", method = RequestMethod.GET)
	public @ResponseBody Environment getEnvForApplication(@RequestHeader("Authorization") String token, @PathVariable("id") String id){
		Environment env = restRepository.customOne(token, V2_APPS.concat("/").concat(id).concat("/env"), new ParameterizedTypeReference<Environment>(){});
		return env;
	}
	
	@RequestMapping(value = "/applications/{id}/events", method = RequestMethod.GET)
	public @ResponseBody CloudFoundryResources<Event> getEventsForApp(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
		CloudFoundryResources<Event> events = restRepository.list(token, V2_EVENTS.concat("?order-direction=desc&q=actee:").concat(id).concat("&results-per-page=5"), 1, false);
		return events;
	}
	
	@RequestMapping(value = "/applications/{id}/summary", method = RequestMethod.GET)
	public @ResponseBody ApplicationSummary getApplicationSummary(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
		ApplicationSummary summary = restRepository.customOne(token, V2_APPS.concat("/").concat(id).concat("/summary"), new ParameterizedTypeReference<ApplicationSummary>() {});
		return summary;
	}
	
	@RequestMapping(value = "/applications/{appId}/routes/{routeId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> unmapRouteWithApp(@RequestHeader("Authorization") final String token,  @PathVariable("appId") final String appId, @PathVariable("routeId") final String routeId) {
		restRepository.delete(token, V2_APPS.concat("/").concat(appId).concat("/routes/").concat(routeId));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}