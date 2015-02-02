package com.cloudzon.huddle.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.ForgotPasswordDto.ForgotPassword;
import com.cloudzon.huddle.dto.LoginResponse;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO.RestResetpassword;
import com.cloudzon.huddle.dto.ResponseMessageDto;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.SignupUser.MvcSignUpUser;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.exception.AuthorizationException;
import com.cloudzon.huddle.exception.BaseWebApplicationException;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.service.UserService;

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
		return new LoginResponse(objUser.getUsername());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseMessageDto signupUser(
			@Validated(MvcSignUpUser.class) @RequestBody SignupUser user,
			HttpServletRequest request) throws IOException, TemplateException,
			MessagingException, Exception {
		StringBuilder objSB = new StringBuilder();
		objSB.append(request.getScheme()).append("://")
				.append(request.getServerName()).append(":")
				.append(request.getServerPort())
				.append(request.getContextPath());
		user.setRedirectURL(objSB.toString());
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

}
