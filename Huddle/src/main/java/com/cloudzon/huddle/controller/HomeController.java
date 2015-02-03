package com.cloudzon.huddle.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.service.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Resource(name = Constant.SERVICE_USER)
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("home");
		return "login";
	}

}
