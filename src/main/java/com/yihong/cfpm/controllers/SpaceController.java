package com.yihong.cfpm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihong.cfpm.model.Application;
import com.yihong.cfpm.model.Organization;
import com.yihong.cfpm.model.Route;
import com.yihong.cfpm.model.Service;
import com.yihong.cfpm.model.ServiceInstance;
import com.yihong.cfpm.model.Space;
import com.yihong.cfpm.model.SpaceSummary;
import com.yihong.cfpm.model.UserRole;
import com.yihong.cfpm.repositories.RestRepository;
import com.yihong.cfpm.repositories.UserRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;

@RestController
@RequestMapping(value = "/api")
public class SpaceController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RestRepository restRepository;
	
	private static final String V2_SPACES = "v2/spaces";

    @RequestMapping(value = "/spaces/{id}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<Space> getSpaceById(@RequestHeader("Authorization") final String token, @PathVariable("id") final String id) {
    	CloudFoundryResource<Space> space = restRepository.one(token, V2_SPACES, id, 2);
    	return space;
    }
    
    @RequestMapping(value = "/spaces", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Space>> getSpaces(@RequestHeader("Authorization") final String token) {
    	CloudFoundryResources<Space> spaces = restRepository.list(token, V2_SPACES, 0, true);
		return spaces.getResources();
    }

    @RequestMapping(value = "/organizations/{id}/spaces", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Space>> getSpacesByOrganizationId(@RequestHeader("Authorization") final String token, 
    		@PathVariable("id") final String id) {
    	CloudFoundryResources<Space> spaces =  restRepository.list(token, "v2/organizations/".concat(id).concat("/spaces"), 2, true);
    	return spaces.getResources();
    }

    @RequestMapping(value = "/spaces", method = RequestMethod.POST)
    public @ResponseBody CloudFoundryResource<Space> createSpace(@RequestHeader("Authorization") String token, 
    		@RequestBody Space space) {
        return restRepository.save(token, V2_SPACES, new CloudFoundryResource<Space>(space));
    }
    
    @RequestMapping(value = "/spaces/{id}", method = RequestMethod.PUT)
    public @ResponseBody CloudFoundryResource<Space> updateSpace(@RequestHeader("Authorization") String token, @PathVariable("id") String id, 
    		@RequestBody CloudFoundryResource<Space> space) {
    	 return restRepository.update(token, V2_SPACES.concat("/").concat(id).concat("?collection-method=add"), space);
    }
    
    @RequestMapping(value = "/spaces/{spaceId}/services", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Service>> getServicesOfSpace(@RequestHeader("Authorization") String token, 
    		@PathVariable("spaceId") String spaceId) {
    	String adminToken = userRepository.login();
    	CloudFoundryResources<Service> services = restRepository.list(adminToken, V2_SPACES.concat("/").concat(spaceId).concat("/services"), 1, true);
    	return services.getResources();
    }
    
    @RequestMapping(value = "/spaces/{id}/service_instances", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServiceInstance>> summary(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
    	CloudFoundryResources<ServiceInstance> serviceInstances =  restRepository.list(token, V2_SPACES.concat("/").concat(id).concat("/service_instances"), 1, true);
    	return serviceInstances.getResources();
    }

    @RequestMapping(value = "/spaces/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSpaceById(@RequestHeader("Authorization") final String token, @PathVariable("id") final String id) {
    	restRepository.delete(token, V2_SPACES, id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/spaces/{spaceId}/routes", method = RequestMethod.GET)
    public List<CloudFoundryResource<Route>> getRoutesForSpace(@RequestHeader("Authorization") final String token, @PathVariable("spaceId") String spaceId) {		
		CloudFoundryResources<Route> routes = restRepository.list(token, V2_SPACES.concat("/").concat(spaceId).concat("/routes"), 0, false);
        return routes.getResources();
    }
    
    @RequestMapping(value="/spaces/{id}/user_roles", method = RequestMethod.GET)
    public List<CloudFoundryResource<UserRole>> getRoleForSpace(@RequestHeader("Authorization") final String token, @PathVariable("id") String id) {
    	CloudFoundryResources<UserRole> roles = restRepository.list(token, V2_SPACES.concat("/").concat(id).concat("/user_roles"), 0, false);
    	return roles.getResources();
    }
    
    @RequestMapping(value="/spaces/{id}/apps", method = RequestMethod.GET)
    public List<CloudFoundryResource<Application>> getAppsForSpace(@RequestHeader("Authorization") final String token, @PathVariable("id") String id) {
    	CloudFoundryResources<Application> apps = restRepository.list(token, V2_SPACES.concat("/").concat(id).concat("/apps"), 0, false);
    	return apps.getResources();
    }
    
    @RequestMapping(value="/spaces/{spaceId}/developers/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> disassociateDevelopersWithSpace(@RequestHeader("Authorization") final String token, @PathVariable("spaceId") final String spaceId, @PathVariable("userId") final String userId) {
    	restRepository.delete(token, V2_SPACES.concat("/").concat(spaceId).concat("/developers/").concat(userId));
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/spaces/{spaceId}/managers/{userId}", method=RequestMethod.PUT)
    public @ResponseBody CloudFoundryResource<Space> associateManagerWithSpace(@RequestHeader("Authorization") final String token, @PathVariable("spaceId") final String spaceId, @PathVariable("userId") String userId) {
    	CloudFoundryResource<Space> space = restRepository.update(token, V2_SPACES.concat("/").concat(spaceId).concat("/managers/").concat(userId), new CloudFoundryResource<Space>());
    	return space;
    }
    
    @RequestMapping(value="/spaces/{spaceId}/developers/{userId}", method=RequestMethod.PUT)
    public @ResponseBody CloudFoundryResource<Space> associateDeveloperWithSpace(@RequestHeader("Authorization") final String token, @PathVariable("spaceId") final String spaceId, @PathVariable("userId") String userId) {
    	CloudFoundryResource<Space> space = restRepository.update(token, V2_SPACES.concat("/").concat(spaceId).concat("/developers/").concat(userId), new CloudFoundryResource<Space>());
    	return space;
    }
    
    @RequestMapping(value="/spaces/{spaceId}/auditors/{userId}", method = RequestMethod.PUT)
    public CloudFoundryResource<Space> associateAuditorsWithSpace(@RequestHeader("Authorization") final String token, @PathVariable("spaceId") final String spaceId, @PathVariable("userId") final String userId) {
    	CloudFoundryResource<Space> space = restRepository.update(token, V2_SPACES.concat("/").concat(spaceId).concat("/auditors/").concat(userId), new CloudFoundryResource<Space>());
    	return space;
    }

}
