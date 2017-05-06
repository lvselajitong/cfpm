package com.yihong.cfpm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yihong.cfpm.model.Application;
import com.yihong.cfpm.model.Route;
import com.yihong.cfpm.repositories.RestRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;


@RestController
@RequestMapping(value = "/api")
public class RouteController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private RestRepository restRepository;
	
	@Autowired
    protected RestTemplate restTemplate;
	
	private static final String V2_ROUTES = "v2/routes";
	
	
	@RequestMapping(value = "/routes", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Route>> getRoutes(@RequestHeader("Authorization") final String token) {
		CloudFoundryResources<Route> routes = restRepository.list(token, V2_ROUTES, 1, true);
		return routes.getResources();
    }
		
	@RequestMapping(value = "/routes/{id}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<Route> getRoute(@RequestHeader("Authorization") String token, @PathVariable("id") final String id) {
		CloudFoundryResource<Route> route = restRepository.one(token, V2_ROUTES, id, 1);
		return route;
    }
	
	@RequestMapping(value = "/routes/{host}/{domainId}/exists", method = RequestMethod.GET)
    public @ResponseBody boolean checkIfRouteNameExists(
    		@RequestHeader("Authorization") final String token, @PathVariable("host") final String host,
    		@PathVariable("domainId") final String domainId) {
		boolean nameExists = false;
		
		CloudFoundryResources<Route> routes = restRepository.list(token, V2_ROUTES.concat("?q=host:").concat(host.toLowerCase()), 2, false);
		if(routes.getTotalResults() > 0)
			nameExists = true;
		
		return nameExists;
    }
	
	@RequestMapping(value = "/routes/{host}/{domainId}", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Route>> searchRoutes(
    		@RequestHeader("Authorization") final String token, @PathVariable("host") final String host,
    		@PathVariable("domainId") final String domainId) {
		
		CloudFoundryResources<Route> routes = restRepository.list(token, V2_ROUTES.concat("?q=host:").concat(host.toLowerCase()), 2, false);
		
		return routes.getResources();
    }
	
	@RequestMapping(value = "/routes", method = RequestMethod.POST)
    public @ResponseBody CloudFoundryResource<Route> createRoute(
    		@RequestHeader("Authorization") final String token, @RequestBody Route route) {		
		return restRepository.save(token, V2_ROUTES, new CloudFoundryResource<Route>(route));
    }
    
    @RequestMapping(value = "/routes/{routeId}", method = RequestMethod.PUT)
    public @ResponseBody CloudFoundryResource<Route> updateRoute(@RequestHeader("Authorization") final String token, 
    		@PathVariable("routeId") final String routeId, @RequestBody CloudFoundryResource<Route> route) {
    	return restRepository.update(token, V2_ROUTES.concat("/").concat(routeId), route);
    }

    @RequestMapping(value = "/routes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRouteById(@RequestHeader("Authorization") final String token, @PathVariable("id") final String id) {
        restRepository.delete(token, V2_ROUTES, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/routes/{routeId}/apps/{appId}", method = RequestMethod.PUT)
    public CloudFoundryResource<Route> associateRouteWithApplication(@RequestHeader("Authorization") final String token, 
    		@PathVariable("routeId") String routeId, @PathVariable("appId") String appId) {		
		return restRepository.update(token, V2_ROUTES.concat("/").concat(routeId).concat("/apps/").concat(appId), new CloudFoundryResource<>());
    }
    
    @RequestMapping(value = "/routes/{routeId}/apps/{appId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> disassociateRouteWithApplication(@RequestHeader("Authorization") final String token, 
    		@PathVariable("routeId") String routeId, @PathVariable("appId") String appId) {		
		restRepository.delete(token, V2_ROUTES.concat("/").concat(routeId).concat("/apps/"), appId);
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/routes/{routeId}/apps", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<Application>> getAppsForRoute(@RequestHeader("Authorization") final String token,  @PathVariable("routeId") String routeId){
    	CloudFoundryResources<Application> apps = restRepository.list(token, V2_ROUTES.concat(routeId).concat("/apps"), 0, false);
    	return apps.getResources();
    }
    

}
