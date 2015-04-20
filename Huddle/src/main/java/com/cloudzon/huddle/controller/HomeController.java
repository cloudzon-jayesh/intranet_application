package com.cloudzon.huddle.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.service.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	//change by jayesh
	@Resource(name = Constant.SERVICE_USER)
	private UserService userService;
	
	private User getUserDetail() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication.getPrincipal() instanceof UserDetails) {
			CustomUserDetail userDetails = (CustomUserDetail) authentication
					.getPrincipal();
			if (userDetails != null && userDetails.getUser() != null) {
				return userDetails.getUser();
			}
		}
		return null;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("home");
		return "login";
	}
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String register() {
		logger.info("registration");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "registration";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "huddle", method = RequestMethod.GET)
	public String dashboard() {
		logger.info("huddle");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "huddle";
		}
		else
		{
			return "login";
		}
		
	}
	@RequestMapping(value = "company", method = RequestMethod.GET)
	public String company() {
		logger.info("company");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "company";
		}
		else
		{
			return "login";
		}
		
	}
	@RequestMapping(value = "careers", method = RequestMethod.GET)
	public String careers() {
		logger.info("careers");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "careers";
		}
		else
		{
			return "login";
		}
		
	}
	@RequestMapping(value = "feed", method = RequestMethod.GET)
	public String feed() {
		logger.info("feed");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "feed";
		}
		else
		{
			return "login";
		}
		
	}
	@RequestMapping(value = "employee", method = RequestMethod.GET)
	public String employee() {
		logger.info("employee");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "employee";
		}
		else
		{
			return "login";
		}
		
	}
	@RequestMapping(value = "edit_registration", method = RequestMethod.GET)
	public String edit_registration() {
		logger.info("edit_registration.jsp");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "edit_registration";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "imageUpload", method = RequestMethod.GET)
	public String imageUpload() {
		logger.info("fileUploadSignUp");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "fileUploadSignUp";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setGroup", method = RequestMethod.GET)
	public String setGroup() {
		logger.info("Set groupPage");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "groupPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setPermission", method = RequestMethod.GET)
	public String setPermission() {
		logger.info("Set Permission");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "permision";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setActivity", method = RequestMethod.GET)
	public String setActivity() {
		logger.info("Set activityPage");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "activityPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setEvent", method = RequestMethod.GET)
	public String setEvent() {
		logger.info("Set setEvent");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "eventsPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setMeeting", method = RequestMethod.GET)
	public String setMeeting() {
		logger.info("Set Meeting");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "meetingsPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setProject", method = RequestMethod.GET)
	public String setProject() {
		logger.info("Set Project");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "projectsPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setDocument", method = RequestMethod.GET)
	public String setDocument() {
		logger.info("Set Document");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "documentPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "setDiscussion", method = RequestMethod.GET)
	public String setDiscussion() {
		logger.info("Set Discussion");
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "discussionPage";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String setDashbord() {
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "dashboard";
		}
		else
		{
			return "login";
		}
	}
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		User objUser = getUserDetail();
		if(objUser != null)
		{
			return "test";
		}
		else
		{
			return "login";
		}
	}
}
