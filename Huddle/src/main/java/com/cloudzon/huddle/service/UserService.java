package com.cloudzon.huddle.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.cloudzon.huddle.dto.AccessTokenContainer;
import com.cloudzon.huddle.dto.AccountVerificationToken;
import com.cloudzon.huddle.dto.ActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.EmployeeDetailDTO;
import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GetRolePermissionDTO;
import com.cloudzon.huddle.dto.GroupDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.RolePermissionDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.dto.UserRoleDTO;

import freemarker.template.TemplateException;

public interface UserService {
	/**
	 * 
	 * @param loginDto
	 * @return
	 */
	public UserDetails login(UserLoginDto loginDto);

	/**
	 * 
	 * @param token
	 * @return
	 */
	public void logout(String token);

	/**
	 * 
	 * 
	 * @param forgotPasswordModel
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 * @throws MessagingException
	 */
	public void forgotPassword(ForgotPasswordDto forgotPasswordModel)
			throws IOException, TemplateException, MessagingException;

	/**
	 * 
	 * @param resetPasswordDto
	 * @return
	 */
	public void resetPassword(ResetPasswordDTO resetPasswordDto);

	/**
	 * signupUser
	 * 
	 * @param signupUser
	 * @return
	 * @throws MessagingException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws DefaultRoleNotSetException
	 */
	public void signupUser(SignupUser signupUser, HttpServletRequest httpServletRequest) throws IOException,
			TemplateException, MessagingException, ParseException;

	/**
	 * sendEmailVerificationToken send email verification token to user
	 * 
	 * @param emailVerificationRequest
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 * @throws MessagingException
	 */
	public void sendEmailVerificationToken(
			EmailVerificationRequest emailVerificationRequest)
			throws IOException, TemplateException, MessagingException;

	/**
	 * verifyUserAccount verify user send succes or fail message
	 * 
	 * @param accountVerificationToken
	 * @return
	 */
	public void verifyUserAccount(
			AccountVerificationToken accountVerificationToken);

	public AccessTokenContainer getAccessTokenContainer(UserDetails userDetails);

	public List<String> getUserRole(String userNameOrEmail);

	public void changePassword(ChangePasswordDto changePasswordDto,
			String userNameOrEmail);
	
	//TODO Start :- Colin
	public List<EmployeeDetailDTO> getEmployee();
	
	public List<UserRoleDTO> getUserRole();
	
	public List<GroupPermissionDTO> getGroupPermission();
	
	public List<GetRolePermissionDTO> getRolePermission();
	
	public EditEmployeeDTO editEmployeeList(SignupUser signupUser);
	
	public void editEmployee(SignupUser signupUser) throws IOException,
	TemplateException, MessagingException, ParseException ; 
	
	public void uploadImage(String email, MultipartFile multipartFile, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException ; 
	
	public void addGroup(GroupDTO groupDTO)throws IOException,
	TemplateException, MessagingException;
	
	public void addGroupPermission(RolePermissionDTO rolePermissionDTO)throws IOException,
	TemplateException, MessagingException;
	
	public void addActivityPermission(ActivityRolePermissionDTO activityRolePermissionDTO)throws IOException,
	TemplateException, MessagingException;
	
	public List<ActivityDTO> getActivity();
	
	public List<GetActivityRolePermissionDTO> getPermissionRoleActivity();
	//End
}
