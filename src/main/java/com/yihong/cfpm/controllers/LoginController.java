package com.yihong.cfpm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yihong.cfpm.model.AccessToken;
import com.yihong.cfpm.repositories.UserRepository;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/api")
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody AccessToken login(@RequestParam("username") String username, @RequestParam("password")  String password) {
    	System.out.println(username + "," + password);
        return userRepository.login(username, password);
    }

}
