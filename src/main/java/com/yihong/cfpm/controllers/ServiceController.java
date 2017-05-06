package com.yihong.cfpm.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

import com.yihong.cfpm.model.Service;
import com.yihong.cfpm.model.ServiceBinding;
import com.yihong.cfpm.model.ServiceInstance;
import com.yihong.cfpm.model.ServicePlan;
import com.yihong.cfpm.repositories.RestRepository;
import com.yihong.cfpm.repositories.UserRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;

@RestController
@RequestMapping("/api")
public class ServiceController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private RestRepository restRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	private static String V2_SERVICES = "v2/services";
	private static String V2_SERVICE_PLANS = "v2/service_plans";
	private static String V2_SERVICE_INSTANCES = "v2/service_instances";
	private static String V2_SERVICE_BINDINGS = "v2/service_bindings";
	
	
    @RequestMapping(value = "/services", method = GET)
    public @ResponseBody List<CloudFoundryResource<Service>> getServices(@RequestHeader("Authorization") String token) {
//    	String adminToken = userRepository.login();
        CloudFoundryResources<Service> services = restRepository.list(token, V2_SERVICES, 1, true);
        return services.getResources();
    }
    
    @RequestMapping(value = "/services/{serviceId}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<Service> getServiceById(@RequestHeader("Authorization") final String token, 
    		@PathVariable("serviceId") final String serviceId) {
    	String adminToken = userRepository.login();
    	return restRepository.one(adminToken, V2_SERVICES, serviceId, 1);
    }
    
    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public @ResponseBody CloudFoundryResource<Service> createService(@RequestHeader("Authorization") String token, 
    		@RequestBody Service service) {
    	String adminToken = userRepository.login();
        return restRepository.save(adminToken, V2_SERVICES, new CloudFoundryResource<Service>(service));
    }
    
    @RequestMapping(value = "/services/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteServiceById(@RequestHeader("Authorization") final String token, @PathVariable("id") final String id) {
    	restRepository.delete(token, V2_SERVICES, id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/services/{id}/service_plans", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServicePlan>> getServicePlansForService(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") final String id) {
    	String adminToken = userRepository.login();
    	CloudFoundryResources<ServicePlan> servicePlans = restRepository.list(adminToken, V2_SERVICES.concat("/").concat(id).concat("/service_plans"), 1, true);
    	return servicePlans.getResources();
    }
    
   
    @RequestMapping(value = "/service_plans/{planId}/service_instances", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServiceInstance>> getServiceInstancesForServicePlan(@RequestHeader("Authorization") String token,
    		@PathVariable("planId") final String planId) {
    	String adminToken = userRepository.login();
    	CloudFoundryResources<ServiceInstance> serviceInstances = restRepository.list(adminToken, V2_SERVICE_PLANS.concat("/").concat(planId).concat("/service_instances"), 1, true);
    	return serviceInstances.getResources();
    }
    /*
     * add by yihong
     */
    @RequestMapping(value = "/service_plans", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServicePlan>> getServicePlans(@RequestHeader("Authorization") String token) {
//    	String adminToken = userRepository.login();
    	CloudFoundryResources<ServicePlan> servicePlans = restRepository.list(token, V2_SERVICE_PLANS, 1, true);
    	return servicePlans.getResources();
    }
    
    @RequestMapping(value = "/service_plans/{id}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<ServicePlan> getServicePlanById(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
    	CloudFoundryResource<ServicePlan> serviceplan = restRepository.one(token, V2_SERVICE_PLANS, id, 1);
    	return serviceplan;
    }
    
    @RequestMapping(value = "/service_instances/{instanceId}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<ServiceInstance> getServiceInstancesForId(@RequestHeader("Authorization") String token,
    		@PathVariable("instanceId") final String instanceId) {
    	String adminToken = userRepository.login();
    	CloudFoundryResource<ServiceInstance> serviceInstance = restRepository.one(adminToken, V2_SERVICE_INSTANCES, instanceId, 1);
    	return serviceInstance;
    }
    
        
    @RequestMapping(value = "/service_instances", method = RequestMethod.POST)
    public @ResponseBody CloudFoundryResource<ServiceInstance> createServiceInstanceFromServicePlan(@RequestHeader("Authorization") String token,
    		@RequestBody ServiceInstance instance) {
    	//String adminToken = userRepository.login();
		return restRepository.save(token, V2_SERVICE_INSTANCES.concat("?accepts_incomplete=true"), new CloudFoundryResource<ServiceInstance>(instance));
    }
    
    @RequestMapping(value = "/service_instances/{instanceId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteServiceInstanceById(@RequestHeader("Authorization") final String token, 
    		@PathVariable("instanceId") final String instanceId) {
    	restRepository.delete(token, V2_SERVICE_INSTANCES, instanceId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/service_instances/org/{orgId}", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServiceInstance>> getServiceInstancesForOrganization(@RequestHeader("Authorization") String token,
    		@PathVariable("orgId") final String orgId) {
    	String adminToken = userRepository.login();
    	CloudFoundryResources<ServiceInstance> serviceInstances = restRepository.list(adminToken, V2_SERVICE_INSTANCES.concat("?q=organization_guid:").concat(orgId), 1, false);
    	
    	return serviceInstances.getResources();
    }
    
    @RequestMapping(value = "/service_instances/{instanceId}/service_bindings", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<ServiceBinding>> getServiceBindingsForServiceInstance(@RequestHeader("Authorization") String token,@PathVariable("instanceId") String instanceId) {
    	CloudFoundryResources<ServiceBinding> serviceBindings = restRepository.list(token, V2_SERVICE_INSTANCES.concat("/").concat("/service_bindings"), 0, false);
    	return serviceBindings.getResources();
    }
    
    @RequestMapping(value = "/service_bindings/{bindingId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteServiceBindingById(@RequestHeader("Authorization") final String token,
    		@PathVariable("bindingId") final String bindingId) {
    	restRepository.delete(token, V2_SERVICE_BINDINGS, bindingId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
