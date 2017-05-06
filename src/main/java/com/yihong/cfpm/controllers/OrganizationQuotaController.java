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

import com.yihong.cfpm.model.OrganizationQuota;
import com.yihong.cfpm.repositories.RestRepository;
import com.yiong.cfpm.model.base.CloudFoundryResource;
import com.yiong.cfpm.model.base.CloudFoundryResources;


@RestController
@RequestMapping(value = "/api")
public class OrganizationQuotaController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private RestRepository restRepository;
	
	private static final String V2_ORGANIZATIONS_QUOTAS = "v2/quota_definitions";

	@RequestMapping(value = "/organizationQuotas/{id}", method = RequestMethod.GET)
    public @ResponseBody CloudFoundryResource<OrganizationQuota> getOrganization(@RequestHeader("Authorization") String token, @PathVariable("id") final String id) {
		CloudFoundryResource<OrganizationQuota> organizationQuota = restRepository.one(token, V2_ORGANIZATIONS_QUOTAS, id, 1);
        return organizationQuota;
    }

    @RequestMapping(value = "/organizationQuotas", method = RequestMethod.GET)
    public @ResponseBody List<CloudFoundryResource<OrganizationQuota>> getOrganizations(@RequestHeader("Authorization") final String token) {
        CloudFoundryResources<OrganizationQuota> organizationQuotas = restRepository.list(token, V2_ORGANIZATIONS_QUOTAS, 1, true);
        return organizationQuotas.getResources();
    }
    
    @RequestMapping(value = "/organizationQuotas", method = RequestMethod.POST)
    public @ResponseBody CloudFoundryResource<OrganizationQuota> createOrganization(@RequestHeader("Authorization") String token, 
    		@RequestBody OrganizationQuota organizationQuota) {
    	return restRepository.save(token, V2_ORGANIZATIONS_QUOTAS, new CloudFoundryResource<OrganizationQuota>(organizationQuota));
    }
    
    @RequestMapping(value = "/organizationQuotas/{id}", method = RequestMethod.PUT)
    public @ResponseBody CloudFoundryResource<OrganizationQuota> updateOrganization(@RequestHeader("Authorization") String token, 
    		@PathVariable("id") String id, @RequestBody CloudFoundryResource<OrganizationQuota> organizationQuota) {
       return restRepository.update(token, V2_ORGANIZATIONS_QUOTAS.concat(id), organizationQuota);
    }

    @RequestMapping(value = "/organizationQuotas/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOrganizationById(@RequestHeader("Authorization") final String token, @PathVariable("id") final String id) {
    	restRepository.delete(token, V2_ORGANIZATIONS_QUOTAS, id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    


}
