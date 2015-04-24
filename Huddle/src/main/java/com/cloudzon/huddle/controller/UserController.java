package com.cloudzon.huddle.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.dto.ActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.CommentDTO;
import com.cloudzon.huddle.dto.DiscussionCommentDTO;
import com.cloudzon.huddle.dto.DiscussionDTO;
import com.cloudzon.huddle.dto.DiscussionListDTO;
import com.cloudzon.huddle.dto.DocumentDTO;
import com.cloudzon.huddle.dto.DocumentListDTO;
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EmployeeDetailDTO;
import com.cloudzon.huddle.dto.EventsListDTO;
import com.cloudzon.huddle.dto.EventsDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GetRolePermissionDTO;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.MeetingDTO;
import com.cloudzon.huddle.dto.MeetingListDTO;
import com.cloudzon.huddle.dto.ProjectAddDocumentDTO;
import com.cloudzon.huddle.dto.ProjectAddImagesDTO;
import com.cloudzon.huddle.dto.ProjectDTO;
import com.cloudzon.huddle.dto.ProjectDocumentDTO;
import com.cloudzon.huddle.dto.ProjectEditDTO;
import com.cloudzon.huddle.dto.ProjectImagesDTO;
import com.cloudzon.huddle.dto.ProjectListDTO;
import com.cloudzon.huddle.dto.ProjectStatusDTO;
import com.cloudzon.huddle.dto.ProjectTasksDTO;
import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.dto.RolePermissionDTO;
import com.cloudzon.huddle.dto.TaskDTO;
import com.cloudzon.huddle.dto.UserRoleDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto.ForgotPassword;
import com.cloudzon.huddle.dto.LoginResponse;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO.RestResetpassword;
import com.cloudzon.huddle.dto.GroupDTO;
import com.cloudzon.huddle.dto.ResponseMessageDto;
import com.cloudzon.huddle.dto.SetUserPermissionDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.SignupUser.MvcSignUpUser;
import com.cloudzon.huddle.dto.SignupUser.RestSignUpUser;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.exception.AuthorizationException;
import com.cloudzon.huddle.exception.BaseWebApplicationException;
import com.cloudzon.huddle.model.Activity;
import com.cloudzon.huddle.model.Discussion;
import com.cloudzon.huddle.model.Documents;
import com.cloudzon.huddle.model.Events;
import com.cloudzon.huddle.model.Meetings;
import com.cloudzon.huddle.model.Projects;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.service.UserService;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.core.DbxException;
import com.fasterxml.jackson.databind.deser.impl.SetterlessProperty;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

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

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public LoginResponse login(@Valid @RequestBody UserLoginDto loginDto,
			HttpSession session) {
		UserDetails objUser = this.userService.login(loginDto);
		session.setAttribute(Constant.SESSION_USER, objUser);
		SetUserPermissionDTO setUserPermissionDTO = this.userService.getPermissionData(loginDto);
		session.setAttribute("userPermission", setUserPermissionDTO);
		return new LoginResponse(objUser.getUsername());
	}

	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.SESSION_USER);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto signupUser(
			@Validated(MvcSignUpUser.class) @RequestBody SignupUser user) throws IOException, TemplateException,
			MessagingException, Exception {
		this.userService.signupUser(user);
		return new ResponseMessageDto("Please Check Your Mail For Confirmation");
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> forgotPassword(
			@RequestBody @Validated(value = { ForgotPassword.class }) ForgotPasswordDto forgotPasswordModel,
			HttpServletRequest request) throws UnsupportedEncodingException,
			Exception {
		logger.info("userForgotPassword");
		StringBuilder objSB = new StringBuilder();
		objSB.append(request.getScheme()).append("://")
				.append(request.getServerName()).append(":")
				.append(request.getServerPort())
				.append(request.getContextPath()).append("/user/resetPassword");
		forgotPasswordModel.setRedirectUrl(objSB.toString());
		this.userService.forgotPassword(forgotPasswordModel);
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put("message", "success");
		return responseData;
	}

	/**
	 * resetPassword
	 * 
	 * @param resetPasswordDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPasswordPre(@RequestParam("key") String token,
			ModelMap modelMap) {
		logger.info("resetPassword pre");
		if (StringUtils.hasText(token)) {
			ResetPasswordDTO resetPasswordDTO = (ResetPasswordDTO) modelMap
					.get("resetPassword");
			if (resetPasswordDTO == null)
				resetPasswordDTO = new ResetPasswordDTO();
			resetPasswordDTO.setToken(token);
			modelMap.put("resetPassword", resetPasswordDTO);
			return "user/resetPassword";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPasswordPost(
			@ModelAttribute("resetPassword") @Validated(RestResetpassword.class) ResetPasswordDTO resetPassword,
			BindingResult result, RedirectAttributes redirectAttributes) {
		logger.info("resetPassword post");
		try {
			if (StringUtils.hasText(resetPassword.getToken())) {
				if (result.hasErrors()) {
					redirectAttributes.addFlashAttribute(
							BindingResult.MODEL_KEY_PREFIX + "resetPassword",
							result);
					redirectAttributes.addFlashAttribute("resetPassword",
							resetPassword);
					return "redirect:/user/resetPassword?key="
							+ resetPassword.getToken();
				} else {
					this.userService.resetPassword(resetPassword);
					return "redirect:/";
				}
			}
			return "redirect:/";
		} catch (BaseWebApplicationException baseWebApplicationException) {
			redirectAttributes.addFlashAttribute("errorMessage",
					baseWebApplicationException.getErrorMessage());
			redirectAttributes
					.addFlashAttribute("resetPassword", resetPassword);
			return "redirect:/user/resetPassword?key="
					+ resetPassword.getToken();
		}
	}

	/**
	 * changePassword
	 * 
	 * @param resetPasswordDto
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto changePassword(
			@RequestBody @Valid ChangePasswordDto changePasswordDto) {
		logger.info("changePassword");
		User objUser = getUserDetail();
		if (objUser != null) {
			this.userService.changePassword(changePasswordDto,
					objUser.getUserName());
			return new ResponseMessageDto("Password change Successfully");
		} else {
			throw new AuthorizationException(Constant.ERROR_LOGIN_MESSAGE);
		}
	}
	
	/**
	 * get List for UserRole for SignUp user
	 * 
	 */
	@RequestMapping(value = "/getUserRole", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<UserRoleDTO> getUserRole()
			throws IOException, TemplateException, MessagingException {
		logger.info("getUserRole start");
		return this.userService.getUserRole();
	}
	


	/**
	 * employeeDetails used to get employee details into system
	 * 
	 */
	@RequestMapping(value = "/employeDetails", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<EmployeeDetailDTO> employeeDetails() throws IOException,
			TemplateException, MessagingException {
		logger.info("employeeDetails start");
		return this.userService.getEmployee();
	}

	/**
	 * get List for editSignupUser to Edit employee details into system
	 * 
	 */
	@RequestMapping(value = "/editEmployeList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public EditEmployeeDTO editEmployeeList(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException {
		logger.info("editEmployeeList start");
		return this.userService.editEmployeeList(signupUser);
	}

	/**
	 * editEmployee to Edit employee details into system
	 * 
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/editEmployee", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto editEmployee(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException,
			ParseException {
		logger.info("edit employee start");
		this.userService.editEmployee(signupUser);
		return new ResponseMessageDto("Update Successfully");
	}

	/**
	 * uploadProfile to Upload Profile Picture of employee details into system
	 * 
	 */
	@RequestMapping(value = "/uploadProfile", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String uploadProfile(
			@RequestParam("hemail") String email,
			@RequestParam("fileinput") MultipartFile multipartFile,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("upload image start");
		this.userService.uploadImage(email, multipartFile, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/employee";
	}
	
	/**
	 * Add Group of the Employee
	 * 
	 */
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addGroup(
			@Validated(RestSignUpUser.class) @RequestBody GroupDTO groupDTO) throws IOException,
			TemplateException, MessagingException {
		logger.info("New Group Added");
		this.userService.addGroup(groupDTO);
		return new ResponseMessageDto("Group is Added");
	}
	
	/**
	 * get List for UserGroup for user
	 * 
	 */
	@RequestMapping(value = "/getGroupPermission", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<GroupPermissionDTO> getGroupPermission()
			throws IOException, TemplateException, MessagingException {
		logger.info("getUserGroup start");
		return this.userService.getGroupPermission();
	}
	
	/**
	 * Add Group Permission of the Employee
	 * 
	 */
	@RequestMapping(value = "/addGroupPermission", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addGroupPermission(
			@Validated(RestSignUpUser.class) @RequestBody RolePermissionDTO rolePermissionDTO) throws IOException,
			TemplateException, MessagingException {
		logger.info("Set Group Permission");
		this.userService.addGroupPermission(rolePermissionDTO);
		return new ResponseMessageDto("Group Permission is Added");
	}
	/**
	 * get Permission List for Role of user
	 *
	 */
	@RequestMapping(value = "/getRolePermission", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<GetRolePermissionDTO> getRolePermission()
			throws IOException, TemplateException, MessagingException {
		logger.info("getUserGroup start");
		return this.userService.getRolePermission();
	}
	/**
	 * get Events List for Role of user
	 *
	 */
	
	@RequestMapping(value = "/getActivity", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ActivityDTO> getActivity()
			throws IOException, TemplateException, MessagingException {
		logger.info("getUserRole start");
		return this.userService.getActivity();
	}
	/**
	 * set Activity Permission of the Employee
	 * 
	 */
	@RequestMapping(value = "/addActivityPermission", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addActivityPermission(
			@RequestBody ActivityRolePermissionDTO activityRolePermissionDTO) throws IOException,
			TemplateException, MessagingException {
		logger.info("Set Group Permission");
		this.userService.addActivityPermission(activityRolePermissionDTO);
		return new ResponseMessageDto("Activity Permission is set");
	}
	
	/**
	 * get Permission of role and activity
	 *
	 */
	
	@RequestMapping(value = "/getPermissionRoleActivity", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<GetActivityRolePermissionDTO> getPermissionRoleActivity()
			throws IOException, TemplateException, MessagingException {
		logger.info("getPermissionRoleActivity start");
		return this.userService.getPermissionRoleActivity();
	}
	
	@RequestMapping(value = "/getPermissionData", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public SetUserPermissionDTO tryLogin(@Valid @RequestBody UserLoginDto loginDto, HttpServletRequest request)
			throws IOException, TemplateException, MessagingException,
			ParseException {
		logger.info("tryLogin");
		HttpSession session = request.getSession();
		session.setAttribute("permission",userService.getPermissionData(loginDto));
		return userService.getPermissionData(loginDto);
	}
	/**
	 * Add Activity
	 * 
	 */
	@RequestMapping(value = "/addActivity", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addActivity(
			 @RequestBody Activity objActivity) throws IOException,
			TemplateException, MessagingException {
		logger.info("New Activity Added");
		this.userService.addActivity(objActivity);
		return new ResponseMessageDto("Activity is Added");
	}
	
	/**
	 * get List of Activity from id
	 * 
	 */
	@RequestMapping(value = "/editActivityList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ActivityDTO editActivityList(@RequestBody Activity objActivity)
			throws IOException, TemplateException, MessagingException {
		logger.info("editActivityList start");
		return this.userService.editActivityList(objActivity);
	}
	
	/**
	 * get List of Role from id
	 * 
	 */
	@RequestMapping(value = "/editGroupList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public RoleDTO editGroupList(@RequestBody Role objRole )
			throws IOException, TemplateException, MessagingException {
		logger.info("editGroupList start");
		return this.userService.editGroupList(objRole);
	}
	
	/**
	 * Delete  Role from id
	 * 
	 */
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteGroup(@RequestBody Role objRole )
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteGroup start");
		this.userService.deleteGroup(objRole);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * Delete  Activity from id
	 * 
	 */
	@RequestMapping(value = "/deleteActivity", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteActivity(@RequestBody Activity objActivity )
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteActivity start");
		this.userService.deleteActivity(objActivity);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * Create new Event
	 * 
	 */
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String addEvent(
			@RequestParam("eventName") String eventName,
			@RequestParam("description") String descreption,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("fileinput") MultipartFile[] multipartFile,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Add Event Start");
		EventsDTO objEvents = new EventsDTO();
		objEvents.setEventName(eventName);
		objEvents.setDescription(descreption);
		objEvents.setDate(date);
		objEvents.setTime(time);
		this.userService.addEvent(objEvents, multipartFile, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/setEvent";
	}
	
	/**
	 * get List of Events from id
	 * 
	 */
	@RequestMapping(value = "/getAllEvents", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<EventsListDTO> getAllEvents()
			throws IOException, TemplateException, MessagingException {
		logger.info("getAllEvents start");
		return this.userService.getAllEvents();
	}
	
	/**
	 * get List of Events from id
	 * 
	 */
	@RequestMapping(value = "/editEventList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public EventsListDTO editEventList(@RequestBody Events objEvents)
			throws IOException, TemplateException, MessagingException {
		logger.info("editEventList start");
		return this.userService.editEventList(objEvents);
	}
	
	/**
	 * Delete  Role from id
	 * 
	 */
	@RequestMapping(value = "/deleteEvents", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteEvents(@RequestBody Events objEvents)
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteEvents start");
		this.userService.deleteEvent(objEvents);
		return new ResponseMessageDto("Delete Successfully");
	}
	/**
	 * Edit Events
	 * 
	 */
	@RequestMapping(value = "/editEvent", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto editEvent(
			@RequestParam("id") Long eventId,
			@RequestParam("eventName") String eventName,
			@RequestParam("description") String description,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam(value="images", required=false) MultipartFile[] images,
			@RequestParam("editImagesChecked") Long[] imageIds,
			HttpServletRequest servletRequest
			) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Edit Event");
		EventsDTO eventsDTO = new EventsDTO();
		eventsDTO.setId(eventId);
		eventsDTO.setEventName(eventName);
		eventsDTO.setDescription(description);
		eventsDTO.setDate(date);
		eventsDTO.setTime(time);
		eventsDTO.setImageIds(Arrays.asList(imageIds));
		eventsDTO.setImages(Arrays.asList(images));
		this.userService.editEvent(eventsDTO,servletRequest);
		return new ResponseMessageDto("Update Successfully");
	}
	
	/**
	 * Add Meeting
	 * 
	 */
	@RequestMapping(value = "/addMeeting", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addMeeting(
			 @RequestBody MeetingDTO meetingDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("New Meeting Added");
		this.userService.addMeeting(meetingDTO);
		return new ResponseMessageDto("Meeting is Added");
	}
	
	/**
	 * get List of Meetings
	 * 
	 */
	@RequestMapping(value = "/getAllMeetings", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<MeetingListDTO> getAllMeetings(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException, ParseException {
		logger.info("getAllMeetings start");
		return this.userService.getAllMeetings(signupUser);
	}
	/**
	 * Delete  Meeting from id
	 * 
	 */
	@RequestMapping(value = "/deleteMeetings", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteMeetings(@RequestBody Meetings objMeetings)
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteMeetings start");
		this.userService.deleteMeeting(objMeetings);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * get List for Edit Meeting details into system
	 * 
	 */
	@RequestMapping(value = "/editMeetingList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public MeetingDTO editMeetingList(@RequestBody Meetings meetings)
			throws IOException, TemplateException, MessagingException {
		logger.info("editMeetingList start");
		return this.userService.editMeetingList(meetings);
	}

	/**
	 * edit Meetings 
	 * 
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/editMeetings", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto editMeeting(@RequestBody MeetingDTO meetingDTO)
			throws IOException, TemplateException, MessagingException,
			ParseException {
		logger.info("edit meeting start");
		this.userService.editMeeting(meetingDTO);
		return new ResponseMessageDto("Update Successfully");
	}

	/**
	 * Add NEw Project
	 * 
	 */
	@RequestMapping(value = "/addProject", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	///@ResponseBody
	public String addProject(
			@RequestParam("projectName") String projectName,
			@RequestParam("description") String description,
			@RequestParam("url") String url, 
			@RequestParam(value="projectPath",  required=false) MultipartFile projectPath,
			@RequestParam(value = "video",  required=false) MultipartFile video,
			//@RequestParam(value="images",  required=false) MultipartFile[] images,
			@RequestParam("group") Long[] roles,
			@RequestParam("userName") String userName,
			//@RequestBody ProjectDTO projectDTO,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Add Project Start");
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectName(projectName);
		projectDTO.setDescription(description);
		projectDTO.setUrl(url);
		projectDTO.setProjectPath(projectPath);
		projectDTO.setVideo(video);
		//projectDTO.setImages(Arrays.asList(images));
		projectDTO.setRolesId(Arrays.asList(roles));
		projectDTO.setUserName(userName);
		this.userService.addProject(projectDTO, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/setProject";
	}
	
	/**
	 * get List of Projects
	 * 
	 */
	@RequestMapping(value = "/getAllProjects", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ProjectListDTO> getAllProjects(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException {
		logger.info("getAllProjects start");
		return this.userService.getAllProjects(signupUser);
	}
	
	/**
	 * Delete  Project from id
	 * 
	 */
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteProject(@RequestBody Projects projects)
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteProject start");
		this.userService.deleteProject(projects);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * get List for Edit Project details
	 * 
	 */
	@RequestMapping(value = "/editProjectList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ProjectListDTO editProjectList(@RequestBody ProjectListDTO projectListDTO)
			throws IOException, TemplateException, MessagingException {
		logger.info("editProjectList start");
		return this.userService.editProjectList(projectListDTO);
	}

	/**
	 * Edit Project
	 * 
	 */
	@RequestMapping(value = "/editProject", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public String editProject(
			@RequestParam("projectId") String projectId,
			@RequestParam("projectName") String projectName,
			@RequestParam("description") String description,
			@RequestParam("url") String url,
			@RequestParam(value="projectPath", required=false) MultipartFile projectPath,
			@RequestParam(value="document", required=false) MultipartFile document,
			@RequestParam(value="video", required=false) MultipartFile video,
			//@RequestParam(value="images", required=false) MultipartFile[] images,
			//@RequestParam("editImagesChecked") Long[] imageIds,
			@RequestParam("editGroupName") Long[] roles,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Edit Project Start");
		ProjectEditDTO projectDTO = new ProjectEditDTO();
		projectDTO.setId(Long.valueOf(projectId));
		projectDTO.setProjectName(projectName);
		projectDTO.setDescription(description);
		projectDTO.setUrl(url);
		if(projectPath !=null && !projectPath.isEmpty())
		{
			projectDTO.setProjectPath(projectPath);
		}
		else
		{
			projectDTO.setProjectPath(null);
		}
		if(document !=null && !document.isEmpty())
		{
			projectDTO.setDocument(document);
		}
		else
		{
			projectDTO.setDocument(null);
		}
		if(video !=null && !video.isEmpty())
		{
			projectDTO.setVideo(video);
		}
		else
		{
			projectDTO.setVideo(null);
		}
		/*List<MultipartFile> imageList = new ArrayList<MultipartFile>();
		if(images != null)
		{
			for(MultipartFile tempFile : images)
			{
				if(tempFile !=null && !tempFile.isEmpty())
				{
					imageList.add(tempFile);
				}
			}
			projectDTO.setImages(imageList);
		}
		else
		{
			projectDTO.setImages(null);
		}*/
		projectDTO.setRolesId(Arrays.asList(roles));
		//projectDTO.setImageIds(Arrays.asList(imageIds));
		this.userService.editProject(projectDTO, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/setProject";
	}
	/**
	 * get List for ADD Documents
	 * 
	 */
	@RequestMapping(value = "/getDocuments", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ProjectDocumentDTO> getDocuments(@RequestBody Projects projects)
			throws IOException, TemplateException, MessagingException {
		logger.info("getDocuments start");
		return this.userService.getDocuments(projects);
	}
	/**
	 * Add Project Documents
	 * 
	 */
	@RequestMapping(value = "/addProjectDocuments", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addProjectDocuments(
			@RequestParam("documents") MultipartFile documents,
			@RequestParam("documentName")String documentName,
			@RequestParam("projectId")String id,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("New Documents Added");
		ProjectAddDocumentDTO projectAddDocumentDTO = new ProjectAddDocumentDTO();
		projectAddDocumentDTO.setProjectId(Long.valueOf(id));
		projectAddDocumentDTO.setDocumentName(documentName);
		projectAddDocumentDTO.setDocuments(documents);
		this.userService.addProjectDocument(projectAddDocumentDTO, servletRequest);
		return new ResponseMessageDto("Project Document is Added");
	}
	
	/**
	 * delete Project Documents
	 * 
	 */
	@RequestMapping(value = "/deleteProjectDocument", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteProjectDocument(
			 @RequestBody ProjectAddDocumentDTO projectAddDocumentDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Delete Project Document");
		this.userService.deleteProjectDocument(projectAddDocumentDTO);
		return new ResponseMessageDto("Document is Deleted");
	}
	
	/**
	 * get List of Images of projects
	 * 
	 */
	@RequestMapping(value = "/getProjectImages", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ProjectImagesDTO> getProjectImages(/*@RequestParam(value="images",  required=false) MultipartFile[] images,
			@RequestParam("projectId")String id,
			HttpServletRequest servletRequest*/
			@RequestBody Projects projects)
			throws IOException, TemplateException, MessagingException {
		logger.info("getProjectImages start");
		
		return this.userService.getProjectImages(projects);
	}
	
	/**
	 * Add NEw Project Images
	 * 
	 */
		
	@RequestMapping(value = "/addProjectImages", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addProjectImages(
			@RequestParam(value="images",  required=false) MultipartFile[] images,
			@RequestParam("projectId")String id,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("addProjectImages start");
		ProjectAddImagesDTO projectAddImagesDTO = new ProjectAddImagesDTO();
		projectAddImagesDTO.setProjectId(Long.valueOf(id));
		projectAddImagesDTO.setImages(Arrays.asList(images));
		this.userService.addProjectImages(projectAddImagesDTO, servletRequest);
		return new ResponseMessageDto("Add Successfully");
		
	}
	
	/**
	 * delete Project Documents
	 * 
	 */
	@RequestMapping(value = "/deleteProjectImage", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteProjectImage(
			 @RequestBody ProjectAddImagesDTO projectAddImagesDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Delete Project Document");
		this.userService.deleteProjectImage(projectAddImagesDTO);
		return new ResponseMessageDto("Image is Deleted");
	}
	
	/**
	 * get List for ADD tasks
	 * 
	 */
	@RequestMapping(value = "/getTasks", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ProjectTasksDTO> getTasks(@RequestBody Projects projects)
			throws IOException, TemplateException, MessagingException {
		logger.info("getTasks start");
		return this.userService.getTasks(projects);
	}
	
	/**
	 * Add Project Tasks
	 * 
	 */
	@RequestMapping(value = "/addTasks", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addTasks(
			 @RequestBody TaskDTO taskDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("New Task Added");
		this.userService.addTasks(taskDTO);
		return new ResponseMessageDto("Task is Added");
	}
	
	/**
	 * make complete Project Tasks
	 * 
	 */
	@RequestMapping(value = "/setCompleteTask", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto setCompleteTask(
			 @RequestBody TaskDTO taskDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Task Completed");
		this.userService.setCompleteTask(taskDTO);
		return new ResponseMessageDto("Task is Completed");
	}
	
	/**
	 * delete Project Tasks
	 * 
	 */
	@RequestMapping(value = "/deleteTask", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteTask(
			 @RequestBody TaskDTO taskDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Delete Task");
		this.userService.deleteTask(taskDTO);
		return new ResponseMessageDto("Task is Deleted");
	}
	
	/**
	 * get Status of Project
	 * 
	 */
	@RequestMapping(value = "/getStatus", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ProjectStatusDTO getStatus(@RequestBody Projects projects)
			throws IOException, TemplateException, MessagingException {
		logger.info("getStatus start");
		return this.userService.getStatus(projects);
	}
	
	/**
	 * Add NEw Document
	 * 
	 */
	@RequestMapping(value = "/addDocument", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public String addDocument(
			@RequestParam("documentName") String documentName,
			@RequestParam("description") String description,
			@RequestParam("documentPath") MultipartFile documentPath,
			@RequestParam("group") Long[] roles,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Add Document Start");
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setDocumentName(documentName);
		documentDTO.setDescription(description);
		documentDTO.setDocumentPath(documentPath);
		documentDTO.setRolesId(Arrays.asList(roles));
		this.userService.addDocument(documentDTO, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/setDocument";
	}
	
	/**
	 * get List of Documents
	 * 
	 */
	@RequestMapping(value = "/getAllDocuments", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<DocumentListDTO> getAllDocuments(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException {
		logger.info("getAllDocuments start");
		return this.userService.getAllDocuments(signupUser);
	}
	
	/**
	 * Delete  Document from id
	 * 
	 */
	@RequestMapping(value = "/deleteDocument", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteDocument(@RequestBody Documents documents)
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteDocument start");
		this.userService.deleteDocument(documents);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * get List for Edit Document details
	 * 
	 */
	@RequestMapping(value = "/editDocumentList", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public DocumentListDTO editDocumentList(@RequestBody DocumentListDTO documentListDTO)
			throws IOException, TemplateException, MessagingException {
		logger.info("editProjectList start");
		return this.userService.editDocumentList(documentListDTO);
	}
	
	/**
	 * Edit Document
	 * 
	 */
	@RequestMapping(value = "/editDocument", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public String editDocument(
			@RequestParam("id")String id,
			@RequestParam("documentName") String documentName,
			@RequestParam("description") String description,
			@RequestParam(value="documentPath", required=false) MultipartFile documentPath,
			@RequestParam("group") Long[] roles,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("Add Document Start");
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setId(Long.valueOf(id));
		documentDTO.setDocumentName(documentName);
		documentDTO.setDescription(description);
		if(documentPath != null)
		{
			documentDTO.setDocumentPath(documentPath);
		}
		else
		{
			documentDTO.setDocumentPath(null);
		}
		documentDTO.setRolesId(Arrays.asList(roles));
		this.userService.editDocument(documentDTO, servletRequest);
		System.out.println("Upload successfully");
		//return new ResponseMessageDto("Upload Successfully");
		return "redirect:/setDocument";
	}

	/**
	 * get List of Discussion
	 * 
	 */
	@RequestMapping(value = "/getAllDiscussion", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<DiscussionListDTO> getAllDiscussion(@RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException {
		logger.info("getAll Discussion start");
		return this.userService.getAllDiscussion(signupUser);
	}

	/**
	 * Add Discussion
	 * 
	 */
	@RequestMapping(value = "/addDiscussion", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addDiscussion(
			 @RequestBody DiscussionDTO discussionDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("New Discussion Added");
		this.userService.addDiscussion(discussionDTO);
		return new ResponseMessageDto("Discussion is Added");
	}
	
	/**
	 * Delete  Discussion from id
	 * 
	 */
	@RequestMapping(value = "/deleteDiscussion", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto deleteDiscussion(@RequestBody Discussion discussion)
			throws IOException, TemplateException, MessagingException {
		logger.info("deleteDiscussion start");
		this.userService.deleteDiscussion(discussion);
		return new ResponseMessageDto("Delete Successfully");
	}
	
	/**
	 * get List for Edit Project details
	 * 
	 */
	@RequestMapping(value = "/getDiscuusion", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public DiscussionCommentDTO getDiscuusion(@RequestBody Discussion discussion)
			throws IOException, TemplateException, MessagingException {
		logger.info("getDiscuusion start");
		return this.userService.getDiscussion(discussion);
	}
	
	/**
	 * Add Discussion
	 * 
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto addComment(
			 @RequestBody CommentDTO commentDTO) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("New Comment Added");
		this.userService.addComment(commentDTO);
		return new ResponseMessageDto("Comment is Added");
	}
	
	/**
	 * Test
	 * @throws ParseException 
	 * @throws URISyntaxException 
	 * 
	 */
	@RequestMapping(value = "/testDropBox", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto testDropBox(@RequestParam("file") MultipartFile file) throws IOException,
			TemplateException, MessagingException, DropboxException,DbxException, ParseException, URISyntaxException {
		logger.info("Test Drop Box");
		return new ResponseMessageDto(this.userService.testDropBox(file));
	}
}
