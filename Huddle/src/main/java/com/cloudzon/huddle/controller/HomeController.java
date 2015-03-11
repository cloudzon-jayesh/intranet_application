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

	//change by jayesh
	@Resource(name = Constant.SERVICE_USER)
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("home");
		return "login";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String register() {
		logger.info("registration");
		return "registration";
	}
	@RequestMapping(value = "huddle", method = RequestMethod.GET)
	public String dashboard() {
		logger.info("huddle");
		return "huddle";
	}
	@RequestMapping(value = "company", method = RequestMethod.GET)
	public String company() {
		logger.info("company");
		return "company";
	}
	@RequestMapping(value = "careers", method = RequestMethod.GET)
	public String careers() {
		logger.info("careers");
		return "careers";
	}
	@RequestMapping(value = "feed", method = RequestMethod.GET)
	public String feed() {
		logger.info("feed");
		return "feed";
	}
	@RequestMapping(value = "employee", method = RequestMethod.GET)
	public String employee() {
		logger.info("employee");
		return "employee";
	}
	@RequestMapping(value = "edit_registration", method = RequestMethod.GET)
	public String edit_registration() {
		logger.info("edit_registration.jsp");
		return "edit_registration";
	}
	@RequestMapping(value = "imageUpload", method = RequestMethod.GET)
	public String imageUpload() {
		logger.info("fileUploadSignUp");
		return "fileUploadSignUp";
	}
	@RequestMapping(value = "setGroup", method = RequestMethod.GET)
	public String setGroup() {
		logger.info("Set groupPage");
		return "groupPage";
	}
	@RequestMapping(value = "setPermission", method = RequestMethod.GET)
	public String setPermission() {
		logger.info("Set Permission");
		return "permision";
	}

}
