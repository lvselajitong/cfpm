package com.yihong.cfpm.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihong.cfpm.model.EsQuery;
import com.yihong.cfpm.model.Log;
import com.yihong.cfpm.services.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
    SearchService searchService;
    private static final Logger logger = Logger.getLogger(SearchController.class);
    
    @RequestMapping(method = RequestMethod.GET, value="/index/{index}/type/{type}")
    public @ResponseBody List<Log> search(@PathVariable("index") String index, @PathVariable("type") String type, @RequestParam("q") String query) throws UnknownHostException {
        logger.info("index:"+ index);
        logger.info("type:" + type);
//        EsQuery esQuery = new EsQuery();
//        esQuery.setFrom(0);
//        esQuery.setSize(10);
//        esQuery.setQueryString(query);
    	List<Log> esResult = searchService.searchLogs(index, type, query); 
    	logger.info("ip address"+InetAddress.getLocalHost().getHostAddress());
 
        return esResult;
    }
    
    @RequestMapping(method=RequestMethod.DELETE,value="/index/{index}/type/{type}")
    public boolean deleteIndex(@PathVariable("index") String index, @PathVariable("type") String type, @RequestParam("id") String id){
    	return searchService.deleteLogsById(index, type, id);
    }
    
}

