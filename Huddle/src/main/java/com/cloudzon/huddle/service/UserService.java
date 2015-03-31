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
import com.cloudzon.huddle.dto.DocumentDTO;
import com.cloudzon.huddle.dto.DocumentListDTO;
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EventsListDTO;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.EmployeeDetailDTO;
import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.dto.EventsDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GetRolePermissionDTO;
import com.cloudzon.huddle.dto.GroupDTO;
import com.cloudzon.huddle.dto.MeetingDTO;
import com.cloudzon.huddle.dto.MeetingListDTO;
import com.cloudzon.huddle.dto.ProjectDTO;
import com.cloudzon.huddle.dto.ProjectEditDTO;
import com.cloudzon.huddle.dto.ProjectListDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.dto.RolePermissionDTO;
import com.cloudzon.huddle.dto.SetUserPermissionDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.dto.UserRoleDTO;
import com.cloudzon.huddle.model.Activity;
import com.cloudzon.huddle.model.Documents;
import com.cloudzon.huddle.model.Events;
import com.cloudzon.huddle.model.Meetings;
import com.cloudzon.huddle.model.Projects;
import com.cloudzon.huddle.model.Role;

import freemarker.template.TemplateException;

public interface UserService {
	/**
	 * 
	 * @param loginDto
	 * @return
	 */
	public UserDetails login(UserLoginDto loginDto);
	
	public SetUserPermissionDTO getPermissionData(UserLoginDto loginDto);
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
	public void signupUser(SignupUser signupUser) throws IOException,
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
	
	public void addActivity(Activity objActivity)throws IOException,
	TemplateException, MessagingException;
	
	public ActivityDTO editActivityList(Activity objActivity);
	
	public RoleDTO editGroupList(Role objRole);
	
	public void deleteGroup(Role objRole);
	
	public void deleteActivity(Activity objActivity);
	
	public void addEvent(EventsDTO objEvents, MultipartFile[] multipartFiles, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException;
	//End
	
	public List<EventsListDTO> getAllEvents();
	
	public EventsListDTO editEventList(Events objEvents);
	
	public void deleteEvent(Events objEvents);
	
	public void editEvent(EventsDTO eventsDTO) throws IOException,
	TemplateException, MessagingException, ParseException ; 
	
	public void addMeeting(MeetingDTO meetingDTO)throws IOException,
	TemplateException, MessagingException, ParseException;
	
	public List<MeetingListDTO> getAllMeetings(SignupUser signupUser);
	
	public void deleteMeeting(Meetings meetings);
	
	public MeetingDTO editMeetingList(Meetings meetings);
	
	public void editMeeting(MeetingDTO meetingDTO) throws IOException,
	TemplateException, MessagingException, ParseException ; 
	
	public void addProject(ProjectDTO projectDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException;
	
	public List<ProjectListDTO> getAllProjects(SignupUser signupUser);
	
	public void deleteProject(Projects projects);
	
	public ProjectListDTO editProjectList(ProjectListDTO projectListDTO);
	
	public void editProject(ProjectEditDTO projectEditDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException;
	
	public void addDocument(DocumentDTO documentDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException;
	
	public List<DocumentListDTO> getAllDocuments(SignupUser signupUser);
	
	public void deleteDocument(Documents documents);
	
	public DocumentListDTO editDocumentList(DocumentListDTO documentListDTO);
	
	public void editDocument(DocumentDTO documentDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException;
}
