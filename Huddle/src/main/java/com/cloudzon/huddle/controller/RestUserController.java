package com.cloudzon.huddle.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.AccessTokenContainer;
import com.cloudzon.huddle.dto.AccountVerificationToken;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.ForgotPasswordDto.RestForgotPassword;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.ResponseMessageDto;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.SignupUser.RestSignUpUser;
import com.cloudzon.huddle.dto.UserLoginDto;
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
			@Validated(RestSignUpUser.class) @RequestBody SignupUser signupUser)
			throws IOException, TemplateException, MessagingException {
		logger.info("registerNewUser start");
		this.userService.signupUser(signupUser);
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

}
