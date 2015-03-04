package com.cloudzon.huddle.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;








//import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.AccessTokenContainer;
import com.cloudzon.huddle.dto.AccountVerificationToken;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.EmployeeDetailDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto.RestForgotPassword;
import com.cloudzon.huddle.dto.GroupDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.ResponseMessageDto;
import com.cloudzon.huddle.dto.RolePermissionDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.SignupUser.RestSignUpUser;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.dto.UserRoleDTO;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.RolePermission;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.service.UserService;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/rest/user")
public class RestUserController {

	@Resource(name = Constant.SERVICE_USER)
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(RestUserController.class);

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

	/**
	 * login allow anonymous user to logged in in to system
	 * 
	 * @param loginDto
	 *            contain credentials for user
	 * @param request
	 *            contains request data
	 * @param response
	 *            object of HttpServletResponse to send response code etc.
	 * @return object of Response contains token information or error
	 *         information
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AccessTokenContainer login(@Valid @RequestBody UserLoginDto loginDto) {
		logger.info("login");
		return this.userService.getAccessTokenContainer(this.userService
				.login(loginDto));
	}

	/**
	 * signupUser used to signup into system
	 * 
	 * @param signupUser
	 *            contains signiup user data
	 * @param response
	 *            object of HttpServletResponse to send response code etc.
	 * @return object of Response contains token information or error
	 *         information
	 * @throws Exception
	 * @throws MessagingException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws DefaultRoleNotSetException
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto signupUser(
			@Validated(RestSignUpUser.class) @RequestBody SignupUser signupUser,
			// @RequestParam("fileinput") MultipartFile file,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		logger.info("registerNewUser start");
		this.userService.signupUser(signupUser, servletRequest);
		return new ResponseMessageDto("Please Check Your Mail For Confirmation");
	}

	/**
	 * sendEmailVerificationToken send email for verification of email addess
	 * 
	 * @param emailVerificationRequest
	 * @return object of Response
	 * @throws IOException
	 * @throws TemplateException
	 * @throws MessagingException
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendEmailVerificationToken", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto sendEmailVerificationToken(
			@Valid @RequestBody EmailVerificationRequest emailVerificationRequest)
			throws IOException, TemplateException, MessagingException {
		logger.info("sendEmailVerificationToken start");
		this.userService.sendEmailVerificationToken(emailVerificationRequest);
		return new ResponseMessageDto("Please check your mail.");
	}

	/**
	 * verifyAccount method verify user account based on token
	 * 
	 * @param accountVerificationToken
	 * @return object of Response
	 */
	@RequestMapping(value = "verifyAccount", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto verifyUserAccount(
			@Valid @RequestBody AccountVerificationToken accountVerificationToken) {
		logger.info("verifyUserAccount start");
		this.userService.verifyUserAccount(accountVerificationToken);
		return new ResponseMessageDto("Account Successfully Activated.");
	}

	/**
	 * forgotPassword help to send email for change password to user
	 * 
	 * @param forgotPasswordModel
	 * @param response
	 * @return
	 * @throws MessagingException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto forgotPassword(
			@RequestBody @Validated(value = { RestForgotPassword.class }) ForgotPasswordDto forgotPasswordModel)
			throws IOException, TemplateException, MessagingException {
		logger.info("userForgotPassword");
		this.userService.forgotPassword(forgotPasswordModel);
		return new ResponseMessageDto(
				"Please check your mail for reset password");
	}

	/**
	 * resetPassword
	 * 
	 * @param resetPasswordDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto resetPassword(
			@RequestBody @Valid ResetPasswordDTO resetPasswordDto) {
		logger.info("resetPassword");
		this.userService.resetPassword(resetPasswordDto);
		// Map<String, String> responseData = new HashMap<String, String>();
		// responseData.put("message", "Password Reset Successfully");
		return new ResponseMessageDto("Password Reset Successfully");
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
		this.userService.changePassword(changePasswordDto, getUserDetail()
				.getUserName());
		return new ResponseMessageDto("Password change Successfully");
	}

	@RequestMapping(value = "logout", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto logout(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String access_token = null;
		if (StringUtils.hasText(request.getHeader("Authorization"))) {
			access_token = request.getHeader("Authorization");
		} else {
			access_token = URLEncoder.encode(
					request.getParameter("access_token"), Constant.UTF8);
		}
		if (StringUtils.hasText(access_token)) {
			this.userService.logout(access_token);
			return new ResponseMessageDto("Successfully logged out");
		}
		return null;
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

}
