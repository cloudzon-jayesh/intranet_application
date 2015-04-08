package com.cloudzon.huddle.service.impl;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.naming.event.EventDirContext;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.io.DocumentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.AccessTokenContainer;
import com.cloudzon.huddle.dto.AccountVerificationToken;
import com.cloudzon.huddle.dto.ActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.CommentDTO;
import com.cloudzon.huddle.dto.DiscussionCommentDTO;
import com.cloudzon.huddle.dto.DiscussionDTO;
import com.cloudzon.huddle.dto.DiscussionListDTO;
import com.cloudzon.huddle.dto.DocumentDTO;
import com.cloudzon.huddle.dto.DocumentListDTO;
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EventImagesDTO;
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
import com.cloudzon.huddle.dto.ProjectImagesDTO;
import com.cloudzon.huddle.dto.ProjectListDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.RoleActivityPermissionDTO;
import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.dto.RolePermissionDTO;
import com.cloudzon.huddle.dto.SetUserPermissionDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.dto.UserRoleDTO;
import com.cloudzon.huddle.dto.ValidationErrorDTO;
import com.cloudzon.huddle.exception.AlreadyVerifiedException;
import com.cloudzon.huddle.exception.AlreadyVerifiedException.AlreadyVerifiedExceptionType;
import com.cloudzon.huddle.exception.AuthenticationException;
import com.cloudzon.huddle.exception.AuthorizationException;
import com.cloudzon.huddle.exception.FieldErrorException;
import com.cloudzon.huddle.exception.NotFoundException;
import com.cloudzon.huddle.exception.NotFoundException.NotFound;
import com.cloudzon.huddle.exception.TokenHasExpiredException;
import com.cloudzon.huddle.model.Activity;
import com.cloudzon.huddle.model.ActivityRolePermission;
import com.cloudzon.huddle.model.Discussion;
import com.cloudzon.huddle.model.DiscussionComment;
import com.cloudzon.huddle.model.DiscussionRole;
import com.cloudzon.huddle.model.DocumentRole;
import com.cloudzon.huddle.model.Documents;
import com.cloudzon.huddle.model.EventImages;
import com.cloudzon.huddle.model.Events;
import com.cloudzon.huddle.model.MeetingRole;
import com.cloudzon.huddle.model.Meetings;
import com.cloudzon.huddle.model.Permission;
import com.cloudzon.huddle.model.ProjectImages;
import com.cloudzon.huddle.model.ProjectRole;
import com.cloudzon.huddle.model.Projects;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.RolePermission;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;
import com.cloudzon.huddle.model.VerificationToken;
import com.cloudzon.huddle.model.VerificationToken.VerificationTokenType;
import com.cloudzon.huddle.repository.ActivityRepository;
import com.cloudzon.huddle.repository.ActivityRolePermissionRepository;
import com.cloudzon.huddle.repository.DiscussionCommentRepository;
import com.cloudzon.huddle.repository.DiscussionRepository;
import com.cloudzon.huddle.repository.DiscussionRoleRepository;
import com.cloudzon.huddle.repository.DocumentsRepository;
import com.cloudzon.huddle.repository.DocumentsRoleRepository;
import com.cloudzon.huddle.repository.EventImagesRepository;
import com.cloudzon.huddle.repository.EventsRepository;
import com.cloudzon.huddle.repository.MeetingRoleRepository;
import com.cloudzon.huddle.repository.MeetingsRepository;
import com.cloudzon.huddle.repository.PermissionRepository;
import com.cloudzon.huddle.repository.ProjectImagesRepository;
import com.cloudzon.huddle.repository.ProjectRoleRepository;
import com.cloudzon.huddle.repository.ProjectsRepository;
import com.cloudzon.huddle.repository.RolePermissionRepository;
import com.cloudzon.huddle.repository.RoleRepository;
import com.cloudzon.huddle.repository.UserRepository;
import com.cloudzon.huddle.repository.UserRoleRepository;
import com.cloudzon.huddle.repository.VerificationTokenRepository;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.security.InMemoryTokenStore;
import com.cloudzon.huddle.service.SendMailService;
import com.cloudzon.huddle.service.UserService;
import com.cloudzon.huddle.util.DateUtil;
import com.cloudzon.huddle.util.FileUtils;
import com.cloudzon.huddle.util.ImageUtils;

import freemarker.template.TemplateException;

@Service(Constant.SERVICE_USER)
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Resource(name = Constant.SERVICE_SEND_MAIL)
	private SendMailService sendMailService;

	@Resource
	private VerificationTokenRepository verificationTokenRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserRoleRepository userRoleRepository;

	@Resource
	private PermissionRepository permissionRepository;
	
	@Resource
	private RolePermissionRepository rolePermissionRepository;
	
	@Resource
	private ActivityRepository activityRepository;
	
	@Resource
	private ActivityRolePermissionRepository activityRolePermissionRepository;
	
	@Resource
	private EventsRepository eventsRepository;
	
	@Resource
	private EventImagesRepository eventImagesRepository;
	
	@Resource
	private MeetingsRepository meetingsRepository;
	
	@Resource 
	private MeetingRoleRepository meetingRoleRepository;
	
	@Resource 
	private ProjectsRepository projectsRepository;
	
	@Resource 
	private ProjectImagesRepository projectImagesRepository;
	
	@Resource 
	private ProjectRoleRepository projectRoleRepository;
	
	@Resource 
	private DocumentsRepository documentsRepository;
	
	@Resource 
	private DocumentsRoleRepository documentRoleRepository;
	
	@Resource 
	private DiscussionRepository discussionRepository;
	
	@Resource 
	private DiscussionRoleRepository discussionRoleRepository;
	
	@Resource 
	private DiscussionCommentRepository discussionCommentRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncode;

	@Resource
	private RoleRepository roleRepository;

	@Autowired
	private InMemoryTokenStore tokenStore;

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public UserDetails login(UserLoginDto loginDto) {
		logger.info("login Start");
		User objUser = this.userRepository.getUserByUserNameOrEmail(loginDto
				.getUserName());
		if (objUser != null) {
			//if (objUser.getIsVerified()) {
				if (this.bCryptPasswordEncode.matches(loginDto.getPassword(),
						objUser.getPassword())) {
					List<String> userRoles = this.userRoleRepository
							.getUserRolesByUserNameOrEmail(loginDto
									.getUserName());
					// put all user role in list
					if (null != userRoles && userRoles.size() > 0) {
						ArrayList<GrantedAuthority> objAuthorities = new ArrayList<GrantedAuthority>();
						for (String role : userRoles) {
							SimpleGrantedAuthority objAuthority = new SimpleGrantedAuthority(
									role);
							objAuthorities.add(objAuthority);
						}
						return new CustomUserDetail(objUser.getUserName(),
								objUser.getPassword(), true, true, true, true,
								objAuthorities, objUser);
					} else {
						throw new NotFoundException(NotFound.UserNotFound);
					}
				} else {
					throw new AuthenticationException();
				}
			/*} else {
				throw new AuthenticationException(
						"Please confirm your account", "User not verified");
			}*/
		} else {
			throw new NotFoundException(NotFound.UserNotFound);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<String> getUserRole(String userNameOrEmail) {
		logger.info("getUserRole");
		if (StringUtils.hasText(userNameOrEmail))
			return this.userRoleRepository
					.getUserRolesByUserNameOrEmail(userNameOrEmail);
		else
			return Collections.emptyList();
	}

	@Override
	public void logout(String token) {
		UserDetails user = this.tokenStore.readAccessToken(token);
		if (null != user) {
			this.tokenStore.removeToken(token);
		} else {
			throw new AuthorizationException(Constant.ERROR_LOGIN_MESSAGE);
		}
	}

	// @Override
	// public User getUserByAccessToken(String token) {
	// return this.tokenStore.readAccessToken(token);
	// }

	@Override
	public AccessTokenContainer getAccessTokenContainer(UserDetails userDetails) {
		return this.tokenStore.generateAccessToken(userDetails);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class })
	public void forgotPassword(ForgotPasswordDto forgotPasswordModel)
			throws IOException, TemplateException, MessagingException {

		User objUser = this.userRepository
				.getUserByUserNameOrEmail(forgotPasswordModel
						.getUserNameOrEmail());

		// check user available in data base or not
		if (null != objUser) {
			//if (objUser.getIsVerified()) {
				String verificationToken = this.verificationTokenRepository
						.getVerifactionTokenByEmail(objUser.getEmail(),
								VerificationTokenType.lostPassword);

				if (!StringUtils.hasText(verificationToken)) {
					// create verification token
					VerificationToken objVerificationToken = new VerificationToken(
							objUser, VerificationTokenType.lostPassword,
							Constant.LOST_PASSWORD_EMAIL_LINK_EXPIRE_TIME);
					objVerificationToken.setRedirectUrl(forgotPasswordModel
							.getRedirectUrl());
					objVerificationToken.setCreatedBy(objUser);
					objVerificationToken.setIsUsed(false);
					this.verificationTokenRepository.save(objVerificationToken);
					verificationToken = objVerificationToken.getToken();
				}

				// send mail
				this.sendMailService
						.sendForgotPasswordMail(objUser,
								forgotPasswordModel.getRedirectUrl(),
								verificationToken);
				// return responseData;
			}/* else {
				throw new AuthenticationException(
						"Please confirm your account", "User not verified");
			}
		} */else {
			// if no data found send error message
			throw new NotFoundException(NotFound.UserNotFound);
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = { Exception.class })
	@Override
	public void resetPassword(ResetPasswordDTO resetPasswordDto) {
		VerificationToken objVerificationToken = this.verificationTokenRepository
				.getVerifactionTokenByToken(resetPasswordDto.getToken(),
						VerificationTokenType.lostPassword);
		if (objVerificationToken != null) {
			if (objVerificationToken.getIsUsed()) {
				throw new AlreadyVerifiedException(
						AlreadyVerifiedExceptionType.Token);
			} else {
				if (objVerificationToken.hasExpired()) {
					throw new TokenHasExpiredException();
				} else {
					User objUser = objVerificationToken.getUser();
					if (objUser != null) {
						objUser.setPassword(this.bCryptPasswordEncode
								.encode(resetPasswordDto.getRetypePassword()));
						objUser.setModifiedBy(objUser);
						objUser.setModifiedDate(DateUtil.getCurrentDate());
						// update
						this.userRepository.save(objUser);
						objVerificationToken.setIsUsed(true);
						// update
						this.verificationTokenRepository
								.save(objVerificationToken);
					} else {
						throw new NotFoundException(NotFound.TokenNotFound);
					}
				}
			}
		} else {
			throw new NotFoundException(NotFound.TokenNotFound);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void changePassword(ChangePasswordDto changePasswordDto,
			String userNameOrEmail) {
		User objUser = this.userRepository
				.getUserByUserNameOrEmail(userNameOrEmail);
		if (objUser != null) {
			if (this.bCryptPasswordEncode.matches(
					changePasswordDto.getCurrentPassword(),
					objUser.getPassword())) {
				if (!changePasswordDto.getCurrentPassword().equalsIgnoreCase(
						changePasswordDto.getRetypeNewPassword())) {
					objUser.setPassword(this.bCryptPasswordEncode
							.encode(changePasswordDto.getRetypeNewPassword()));
					objUser.setModifiedBy(objUser);
					objUser.setModifiedDate(DateUtil.getCurrentDate());
					// update
					this.userRepository.save(objUser);
				} else {
					ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
					validationErrorDTO.addFieldError("newPassword",
							"Password cant be same as current.");
					throw new FieldErrorException(validationErrorDTO);
				}
			} else {
				ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
				validationErrorDTO.addFieldError("currentPassword",
						"Password not match");
				throw new FieldErrorException(validationErrorDTO);
			}
		} else {
			throw new AuthorizationException(Constant.ERROR_LOGIN_MESSAGE);
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void signupUser(SignupUser signupUser) throws IOException,
			TemplateException, MessagingException, ParseException {

		SimpleDateFormat dateFormat = null;
		List<Role> objRoles = this.roleRepository.getDefaultRoles();

		if (objRoles != null && objRoles.size() > 0) {

			dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
			// save user master in database
			User objUser = new User();
			//objUser.setIsVerified(false);
			objUser.setUserName(signupUser.getUserName());
			objUser.setFirstName(signupUser.getFirstName());
			objUser.setLastName(signupUser.getLastName());
			objUser.setEmail(signupUser.getEmail());
			objUser.setMobileNumber(signupUser.getMobileNumber());
			objUser.setPassword(this.bCryptPasswordEncode.encode(signupUser
					.getPassword()));
			objUser.setDob(dateFormat.parse(signupUser.getDob()));
			objUser.setJoiningDate(dateFormat.parse(signupUser.getJoiningDate()));
			objUser.setProfilePic(signupUser.getProfilePic());
			this.userRepository.saveAndFlush(objUser);

			// save user role in database
			for (Long roleId : signupUser.getRolesId()) {
				Role objRole = this.roleRepository.getRoleByRoleId(roleId);
				if (objRole != null) {
					UserRole objUserRole = new UserRole();
					objUserRole.setUser(objUser);
					objUserRole.setRole(objRole);
					objUserRole.setActive(true);
					objUserRole.setCreatedBy(objUser);
					this.userRoleRepository.save(objUserRole);
				} else {
					throw new NotFoundException(NotFound.DefaultRoleNotSet);
				}
			}
			/*
			 * for (Role role : objRoles) { UserRole objUserRole = new
			 * UserRole(); objUserRole.setUser(objUser);
			 * objUserRole.setRole(role); objUserRole.setActive(true);
			 * objUserRole.setCreatedBy(objUser);
			 * this.userRoleRepository.save(objUserRole); }
			 */

			// create token and save in database
			VerificationToken objVerificationToken = new VerificationToken(
					objUser, VerificationTokenType.emailRegistration,
					Constant.REGISTER_EMAIL_LINK_EXPIRE_TIME);
			objVerificationToken.setRedirectUrl(signupUser.getRedirectURL());
			objVerificationToken.setCreatedBy(objUser);
			objVerificationToken.setIsUsed(false);
			this.verificationTokenRepository.save(objVerificationToken);

			// send registration mail
			this.sendMailService.sendUserSignupMail(objUser,
					signupUser.getRedirectURL(),
					objVerificationToken.getToken());
		} else {
			throw new NotFoundException(NotFound.DefaultRoleNotSet);
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void sendEmailVerificationToken(
			EmailVerificationRequest emailVerificationRequest)
			throws IOException, TemplateException, MessagingException {

		User objUser = this.userRepository
				.getUserByUserNameOrEmail(emailVerificationRequest.getEmail());
		if (objUser != null) {
			//if (!objUser.getIsVerified()) {
				String objToken = this.verificationTokenRepository
						.getVerifactionTokenByEmail(
								emailVerificationRequest.getEmail(),
								VerificationTokenType.emailRegistration);
				if (!StringUtils.hasText(objToken)) {
					// create verification token
					VerificationToken objVerificationToken = new VerificationToken(
							objUser, VerificationTokenType.emailRegistration,
							Constant.REGISTER_EMAIL_LINK_EXPIRE_TIME);
					objVerificationToken
							.setRedirectUrl(emailVerificationRequest
									.getRedirectUrl());
					objVerificationToken.setCreatedBy(objUser);
					objVerificationToken.setIsUsed(false);
					this.verificationTokenRepository.save(objVerificationToken);
					objToken = objVerificationToken.getToken();
				}
				// send mail
				this.sendMailService.sendUserSignupMail(objUser,
						emailVerificationRequest.getRedirectUrl(), objToken);
			} /*else {
				throw new AlreadyVerifiedException(
						AlreadyVerifiedExceptionType.User);
			}
		} */else {
			throw new NotFoundException(NotFound.UserNotFound);
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void verifyUserAccount(
			AccountVerificationToken accountVerificationToken) {
		VerificationToken objVerificationToken = this.verificationTokenRepository
				.getVerifactionTokenByToken(
						accountVerificationToken.getToken(),
						VerificationTokenType.emailRegistration);
		if (objVerificationToken != null) {
			if (objVerificationToken.getIsUsed()) {
				throw new AlreadyVerifiedException(
						AlreadyVerifiedExceptionType.Token);
			} else if (objVerificationToken.hasExpired()) {
				throw new TokenHasExpiredException();
			} else {
				User objUser = objVerificationToken.getUser();
				//objUser.setIsVerified(true);
				objUser.setModifiedDate(DateUtil.getCurrentDate());
				objUser.setModifiedBy(objUser);
				// update
				this.userRepository.save(objUser);
				objVerificationToken.setIsUsed(true);
				// update
				this.verificationTokenRepository.save(objVerificationToken);
			}
		} else {
			throw new NotFoundException(NotFound.TokenNotFound);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<EmployeeDetailDTO> getEmployee() {
		logger.info("getEmployee");
		return this.userRepository.getEmployee();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<UserRoleDTO> getUserRole() {
		logger.info("getUserRole");
		return this.roleRepository.getAllUserRole();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<GroupPermissionDTO> getGroupPermission() {
		logger.info("getUserRole");
		return this.permissionRepository.getAllPermissionName();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<GetRolePermissionDTO> getRolePermission(){
		logger.info("getRolePermission");
		return this.rolePermissionRepository.getRolePermission();
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public EditEmployeeDTO editEmployeeList(SignupUser signupUser) {
		logger.info("getEmployee");
		User objUser = userRepository.getUserByEmail(signupUser.getEmail());
		List<RoleDTO> roles = null;
		EditEmployeeDTO objEditEmployeeList = null;
		if (objUser != null) {
			roles = this.roleRepository.getRoleByUserId(objUser.getId());
			if (roles != null && roles.size() > 0) {
				objEditEmployeeList = new EditEmployeeDTO();
				objEditEmployeeList.setFirstName(objUser.getFirstName());
				objEditEmployeeList.setLastName(objUser.getLastName());
				objEditEmployeeList.setMobileNumber(objUser.getMobileNumber());
				objEditEmployeeList.setEmail(objUser.getEmail());
				objEditEmployeeList.setPassword(objUser.getPassword());
				objEditEmployeeList.setRetypePassword(objUser.getPassword());
				objEditEmployeeList.setDob(objUser.getDob());
				objEditEmployeeList.setJoiningDate(objUser.getJoiningDate());
				objEditEmployeeList.setRoles(roles);
			}
		}
		// this.userRepository.getEmployeeByEmail(signupUser.getEmail());
		return objEditEmployeeList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void editEmployee(SignupUser signupUser) throws IOException,
			TemplateException, MessagingException, ParseException {
		SimpleDateFormat dateFormat = null;
		// save user master in database
		User objUser = null;
		List<UserRole> userRoles = null;
		UserRole objUserRole = null;
		try {
			objUser = this.userRepository.getUserByEmail(signupUser.getEmail());
			if (objUser != null) {
				dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
				// objUser.setIsVerified(false);

				objUser.setUserName(signupUser.getUserName());
				objUser.setFirstName(signupUser.getFirstName());
				objUser.setLastName(signupUser.getLastName());
				// objUser.setEmail(signupUser.getEmail());
				objUser.setMobileNumber(signupUser.getMobileNumber());
				objUser.setPassword(signupUser.getPassword());
				objUser.setDob(dateFormat.parse(signupUser.getDob()));
				objUser.setJoiningDate(dateFormat.parse(signupUser
						.getJoiningDate()));
				objUser.setProfilePic(signupUser.getProfilePic());
				this.userRepository.saveAndFlush(objUser);
				
				userRoles = this.roleRepository.getUserRolesByUserId(objUser);
				if (userRoles != null && userRoles.size() > 0) {
					for (UserRole tempUserRole : userRoles) {
						tempUserRole.setActive(false);
						this.userRoleRepository.saveAndFlush(tempUserRole);
					}
				}

				for (Long tempRoleId : signupUser.getRolesId()) {
					objUserRole = this.roleRepository.getRoleIdUserId(
							objUser.getId(), tempRoleId);

					if (objUserRole != null) {
						// Update Role
						objUserRole.setActive(true);
						this.userRoleRepository.saveAndFlush(objUserRole);
					} else {
						// Insert Role
							Role objRole = this.roleRepository.getRoleByRoleId(tempRoleId);
							if (objRole != null) {
								objUserRole = new UserRole();
								objUserRole.setUser(objUser);
								objUserRole.setRole(objRole);
								objUserRole.setActive(true);
								objUserRole.setCreatedBy(objUser);
								this.userRoleRepository.save(objUserRole);
							} else {
								throw new NotFoundException(NotFound.DefaultRoleNotSet);
							}
						
					}
				}

			}
		} finally {

		}

	}

	public void uploadImage(String email, MultipartFile multipartFile,
			HttpServletRequest servletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {
		User objUser = null;
		StringBuffer profilePictureId = null;
		try {
			objUser = this.userRepository.getUserByEmail(email);

			if (objUser != null) {
				System.out.println("call");
				if (ImageUtils.downloadSocialProfilePicture(objUser,
						multipartFile, servletRequest)) {
					profilePictureId = new StringBuffer();
					profilePictureId.delete(0, profilePictureId.length())
							.append(objUser.getId()).append(".png");
					objUser.setProfilePic(profilePictureId.toString());
					this.userRepository.save(objUser);
				}
			} else {
				// throw new NotFoundException(NotFound.UserNotFound);
				System.out.println("not found image");
			}
		} finally {

		}
	}
	
	public void addGroup(GroupDTO groupDTO)throws IOException,
	TemplateException, MessagingException
	{
		Role objRole = null;
		objRole = this.roleRepository.getRoleByRoleId(groupDTO.getId());
		if(objRole!=null)
		{
			objRole.setActive(true);
			objRole.setIsDefault(true);
			objRole.setRoleName(groupDTO.getRoleName());
			this.roleRepository.saveAndFlush(objRole);
		}
		else
		{
			objRole = new Role();
			objRole.setActive(true);
			objRole.setIsDefault(true);
			objRole.setRoleName(groupDTO.getRoleName());
			this.roleRepository.saveAndFlush(objRole);
		}
	}
	public void addGroupPermission(RolePermissionDTO rolePermissionDTO)throws IOException,
	TemplateException, MessagingException
	{
		for(Long temppermissionId : rolePermissionDTO.getPermissionId())
		{
			Permission objPermission = permissionRepository.getPermissionById(temppermissionId);
			Role objRole = roleRepository.getRoleByRoleId(rolePermissionDTO.getId());
			RolePermission objRolePermission = new RolePermission();
			objRolePermission.setActive(true);
			objRolePermission.setPermission(objPermission);
			objRolePermission.setRole(objRole);
			this.rolePermissionRepository.saveAndFlush(objRolePermission);
		}
	}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<ActivityDTO> getActivity() {
		logger.info("getUserRole");
		return this.activityRepository.getAllActivity();
	}
	public void addActivityPermission(ActivityRolePermissionDTO activityRolePermissionDTO)throws IOException,
	TemplateException, MessagingException
	{
		
		
			ActivityRolePermission objActivityRolePermission = null;
			
			List<ActivityRolePermission> objActivityRolePermissionsList =this.activityRolePermissionRepository.getPermissionRoleActivityList(activityRolePermissionDTO.getGroupId(), activityRolePermissionDTO.getActivityId());
			
			if(objActivityRolePermissionsList !=null && objActivityRolePermissionsList.size() > 0)
			{
				for(ActivityRolePermission tempObjActivityRolePermission : objActivityRolePermissionsList)
				{
					tempObjActivityRolePermission.setActive(false);
					this.activityRolePermissionRepository.saveAndFlush(tempObjActivityRolePermission);
				}
			}
			
			for(Long tempPermissionId : activityRolePermissionDTO.getPermissionIds())
			{
				Role objRole = this.roleRepository.getRoleByRoleId(activityRolePermissionDTO.getGroupId());
				Activity objActivity = this.activityRepository.getActivityByActivityId(activityRolePermissionDTO.getActivityId());
				Permission objPermission = this.permissionRepository.getPermissionById(tempPermissionId);
				objActivityRolePermission = this.activityRolePermissionRepository.getPermissionRoleActivity(objRole.getId(), objActivity.getId(), tempPermissionId);
				if(objActivityRolePermission!=null)
				{
					//Update Existing Record
					objActivityRolePermission.setActive(true);
					this.activityRolePermissionRepository.saveAndFlush(objActivityRolePermission);
				}
				else
				{
					// Insert New Record
					if (objActivity != null && objActivity != null
						&& objPermission != null) {
						objActivityRolePermission = new ActivityRolePermission();
						objActivityRolePermission.setRole(objRole);
						objActivityRolePermission.setActivity(objActivity);
						objActivityRolePermission.setPermission(objPermission);
						objActivityRolePermission.setActive(true);
						this.activityRolePermissionRepository
							.saveAndFlush(objActivityRolePermission);
					}
				}
			}
		
	}
	
	public List<GetActivityRolePermissionDTO> getPermissionRoleActivity()
	{
		List<GetActivityRolePermissionDTO> objPermission = this.activityRolePermissionRepository.getAllActivityRolePermission();
		return objPermission;
	}
	public SetUserPermissionDTO getPermissionData(UserLoginDto loginDto)
	{
		SetUserPermissionDTO objSetUserPermissionDTO = null;
		List<Long> objRoleDTOs = null;
		RoleActivityPermissionDTO objRoleActivityPermissionDTO = null;
		List<ActivityDTO> objActivityDTOs = null;
		List<RoleActivityPermissionDTO> list = new ArrayList<RoleActivityPermissionDTO>();
		User objUser = this.userRepository.getUserByUserNameOrEmail(loginDto
				.getUserName());
		if(objUser!=null)
		{
				objSetUserPermissionDTO = new SetUserPermissionDTO();
				objSetUserPermissionDTO.setFirstName(objUser.getFirstName());
				objSetUserPermissionDTO.setLastName(objUser.getLastName());
				objSetUserPermissionDTO.setUserName(objUser.getUserName());
				
				objRoleDTOs = this.roleRepository.getRoleIdByUserId(objUser.getId());
				objSetUserPermissionDTO.setRoleIds(objRoleDTOs);
				objActivityDTOs = this.activityRolePermissionRepository.getActivityPermissionList(objSetUserPermissionDTO.getRoleIds());
				if(objActivityDTOs != null  && objActivityDTOs.size() > 0)
				{
					for(ActivityDTO objActivityDTO : objActivityDTOs)
					{
						objRoleActivityPermissionDTO = new RoleActivityPermissionDTO();
						objRoleActivityPermissionDTO.setActivityLink(objActivityDTO.getActivityLink());
						objRoleActivityPermissionDTO.setPermissions(this.activityRolePermissionRepository.getActivityPermissions(objActivityDTO.getId(), objSetUserPermissionDTO.getRoleIds()));
						list.add(objRoleActivityPermissionDTO);
					}
				}
				objSetUserPermissionDTO.setRoleActivityPermissionDTOs(list);
			}
		else {
			throw new NotFoundException(NotFound.UserNotFound);
		}
		return objSetUserPermissionDTO;
	}
	
	public void addActivity(Activity objActivity)throws IOException,
	TemplateException, MessagingException
	{
		Activity tempActivity = null;
		tempActivity = this.activityRepository.getActivityByActivityId(objActivity.getId());
		if(tempActivity!=null)
		{
			tempActivity.setActive(true);
			tempActivity.setActivityName(objActivity.getActivityName());
			tempActivity.setActivityLink(objActivity.getActivityLink());
			this.activityRepository.saveAndFlush(tempActivity);
		}
		else
		{
			tempActivity = new Activity();
			tempActivity.setActive(true);
			tempActivity.setActivityName(objActivity.getActivityName());
			tempActivity.setActivityLink(objActivity.getActivityLink());
			this.activityRepository.saveAndFlush(tempActivity);
		}
		
	}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public ActivityDTO editActivityList(Activity objActivity) {
		logger.info("get activity list");
		return this.activityRepository.getActivityById(objActivity.getId());
	}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public RoleDTO editGroupList(Role objRole) {
		logger.info("get Role list");
		return this.roleRepository.getRoleById(objRole.getId());
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteGroup(Role objRole)
	{
		Role tempRole = null;
		tempRole = this.roleRepository.getRoleByRoleId(objRole.getId());
		if(tempRole!=null)
		{
			tempRole.setActive(false);
			this.roleRepository.saveAndFlush(tempRole);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteActivity(Activity objActivity)
	{
		Activity tempActivity = null;
		tempActivity = this.activityRepository.getActivityByActivityId(objActivity.getId());
		if(tempActivity!=null)
		{
			tempActivity.setActive(false);
			this.activityRepository.saveAndFlush(tempActivity);
		}
	}
	
	public void addEvent(EventsDTO objEvents, MultipartFile[] multipartFiles, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException 
	{
		SimpleDateFormat dateFormat = null;
		SimpleDateFormat timeFormat = null;
		dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
		timeFormat = new SimpleDateFormat("HH:mm");
		
		Events tempEvents = null;
		tempEvents = new Events();
		tempEvents.setActive(true);
		tempEvents.setEventName(objEvents.getEventName());
		tempEvents.setDescription(objEvents.getDescription());
		tempEvents.setDate(dateFormat.parse(objEvents.getDate()));
		tempEvents.setTime(timeFormat.parse(objEvents.getTime()));
		this.eventsRepository.saveAndFlush(tempEvents);
		StringBuffer eventImageId = null;
		EventImages objEventImages = null;
		int  n = 1;
		String fileName = "";
		try {
			if (multipartFiles != null) {
				System.out.println("call");
				for(MultipartFile tempMultipartFile : multipartFiles)
				{
					if(tempMultipartFile != null)
					{
						fileName =tempEvents.getId() +"_event_image_"+ (n++);
						if (ImageUtils.uploadEventImage(fileName,
								tempMultipartFile, servletRequest)) {
								objEventImages = new EventImages();
								eventImageId = new StringBuffer();
								eventImageId.delete(0, eventImageId.length())
										.append(fileName).append(".png");
								objEventImages.setActive(true);
								objEventImages.setImages(eventImageId.toString());
								objEventImages.setEvents(tempEvents);
								this.eventImagesRepository.saveAndFlush(objEventImages);
							}
					}
					
				}
				
				
			} else {
				// throw new NotFoundException(NotFound.UserNotFound);
				System.out.println("not found image");
			}
		} finally {

		}
	}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<EventsListDTO> getAllEvents(){
		logger.info("get Events list");
		return this.eventsRepository.getAllEvents();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public EventsListDTO editEventList(Events objEvents) {
		logger.info("get Events list");
		EventsListDTO eventsListDTO = new EventsListDTO();
		eventsListDTO = this.eventsRepository.getEventsById(objEvents.getId()); 
		List<EventImagesDTO> eventImages = this.eventImagesRepository.getImagesofEvent(objEvents.getId());
		eventsListDTO.setEventImagesDTOs(eventImages);
		return eventsListDTO; 
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteEvent(Events objEvents)
	{
		Events tempEvents = null;
		tempEvents = this.eventsRepository.getEventById(objEvents.getId());
		if(tempEvents!=null)
		{
			tempEvents.setActive(false);
			this.eventsRepository.saveAndFlush(tempEvents);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void editEvent(EventsDTO eventsDTO,HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException
	{
		SimpleDateFormat dateFormat = null;
		SimpleDateFormat timeFormat = null;
		dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
		timeFormat = new SimpleDateFormat("HH:mm");
		
		Events tempEvents = null;
		tempEvents = this.eventsRepository.getEventById(eventsDTO.getId());
		if(tempEvents != null)
		{
			tempEvents.setActive(true);
			tempEvents.setEventName(eventsDTO.getEventName());
			tempEvents.setDescription(eventsDTO.getDescription());
			tempEvents.setDate(dateFormat.parse(eventsDTO.getDate()));
			tempEvents.setTime(timeFormat.parse(eventsDTO.getTime()));
			this.eventsRepository.saveAndFlush(tempEvents);
		}
		StringBuffer eventImageId = null;
		List<EventImages> objEventImagesList = null;
		List<EventImages> eventImages = this.eventImagesRepository.getImagesByEventID(eventsDTO.getId());
		if(eventImages != null && eventImages.size() > 0)
		{
			for(EventImages objImages : eventImages)
			{
				objImages.setActive(false);
				this.eventImagesRepository.saveAndFlush(objImages);
			}
		}
		List<Long> imageIds = eventsDTO.getImageIds();
		
		if(imageIds !=null && imageIds.size() > 0)
		{
			for(Long tempId : imageIds)
			{
				
				objEventImagesList = this.eventImagesRepository.getImagesByIdEventID(tempId, eventsDTO.getId());
				if(objEventImagesList  != null)
				{
					for(EventImages tempImages : objEventImagesList)
					{
						tempImages.setActive(true);
						this.eventImagesRepository.saveAndFlush(tempImages);
					}
				}
			}
		}
		
		EventImages objEventImages= null;
		int  n = eventImages.size();
		String fileName = "";
		try {
			if (eventsDTO.getImages() != null && eventsDTO.getImages().size() > 0) {
				System.out.println("call");
				for(MultipartFile tempMultipartFile : eventsDTO.getImages())
				{
					if(tempMultipartFile != null)
					{
						fileName =tempEvents.getId() +"_event_image_"+ (n++);
						if (ImageUtils.uploadEventImage(fileName,
								tempMultipartFile, servletRequest)) {
								objEventImages = new EventImages();
								eventImageId = new StringBuffer();
								eventImageId.delete(0, eventImageId.length())
										.append(fileName).append(".png");
								objEventImages.setActive(true);
								objEventImages.setImages(eventImageId.toString());
								objEventImages.setEvents(tempEvents);
								this.eventImagesRepository.save(objEventImages);
							}
					}
					
				}
			} else {
				// throw new NotFoundException(NotFound.UserNotFound);
				System.out.println("not found image");
			}
		} finally {

		}

	}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void addMeeting(MeetingDTO meetingDTO)throws IOException,
	TemplateException, MessagingException, ParseException
	{
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		Role objRole = null;
		Meetings objMeetings = null;
		MeetingRole objMeetingRole = null;
		objMeetings = new Meetings();
		objMeetings.setActive(true);
		objMeetings.setMeetingName(meetingDTO.getMeetingName());
		objMeetings.setDescription(meetingDTO.getDescription());
		objMeetings.setDateAndTime(dateFormat.parse(meetingDTO.getDateAndTime()));
		this.meetingsRepository.saveAndFlush(objMeetings);
		List<Long> getRoles = meetingDTO.getRolesId();
		for(Long tempRoles : getRoles)
		{
			objRole = this.roleRepository.getRoleByRoleId(tempRoles);
			objMeetingRole = new MeetingRole();
			objMeetingRole.setActive(true);
			objMeetingRole.setMeetings(objMeetings);
			objMeetingRole.setRole(objRole);
			this.meetingRoleRepository.saveAndFlush(objMeetingRole);			
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<MeetingListDTO> getAllMeetings(SignupUser signupUser){
		logger.info("get Meeting list");
		User objUser = this.userRepository.getUserByUserName(signupUser.getUserName());
		List<Long> objRole = this.roleRepository.getRoleIdByUserId(objUser.getId());
		List<MeetingListDTO> meetingListDTOs = null;
		if(objRole.contains(1L))
		{
			meetingListDTOs = this.meetingRoleRepository.getAllMeetingsByRoles();
		}
		else
		{
			meetingListDTOs = this.meetingRoleRepository.getMeetingsByRoles(objRole);
		}
		return meetingListDTOs;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteMeeting(Meetings meetings)
	{
		Meetings objMeetings = null;
		objMeetings = this.meetingsRepository.getMeetingsById(meetings.getId());
		
		if(objMeetings!=null)
		{
			objMeetings.setActive(false);
			this.meetingsRepository.saveAndFlush(objMeetings);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public MeetingDTO editMeetingList(Meetings meetings) {
		logger.info("getMeeting");
		Meetings objMeetings = meetingsRepository.getMeetingsById(meetings.getId());
		List<Long> roles = null;
		MeetingDTO objMeetingDTO = null;
		if (objMeetings != null) {

			objMeetingDTO = new MeetingDTO();
			objMeetingDTO.setId(objMeetings.getId());
			objMeetingDTO.setMeetingName(objMeetings.getMeetingName());
			objMeetingDTO.setDescription(objMeetings.getDescription());
			objMeetingDTO.setDateAndTime(String.valueOf(objMeetings.getDateAndTime()));
			roles = this.meetingRoleRepository.getRolesByMeeting(objMeetingDTO.getId()); 
			objMeetingDTO.setRolesId(roles);
		}
		return objMeetingDTO;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void editMeeting(MeetingDTO meetingDTO) throws IOException,
	TemplateException, MessagingException, ParseException  
	{
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		Meetings objMeetings = null;
		Role objRole = null;
		List<MeetingRole> meetingRoles = null;
		MeetingRole objMeetingRole = null;
		try {
			objMeetings = this.meetingsRepository.getMeetingsById(meetingDTO.getId());
			if (objMeetings != null) {
				objMeetings.setActive(true);
				objMeetings.setMeetingName(meetingDTO.getMeetingName());
				objMeetings.setDescription(meetingDTO.getDescription());
				objMeetings.setDateAndTime(dateFormat.parse(meetingDTO.getDateAndTime()));
				this.meetingsRepository.saveAndFlush(objMeetings);
				
				meetingRoles = this.meetingRoleRepository.getMeetingRolesByMeeting(objMeetings.getId());
				if (meetingRoles != null && meetingRoles.size() > 0) {
					for (MeetingRole tempMeetingRole : meetingRoles) {
						tempMeetingRole.setActive(false);
						this.meetingRoleRepository.saveAndFlush(tempMeetingRole);
					}
				}

				for (Long tempRoleId : meetingDTO.getRolesId()) {
					objMeetingRole = this.meetingRoleRepository.getRolesByIds(objMeetings.getId(), tempRoleId);
					if (objMeetingRole != null) {
						// Update Role
						objMeetingRole.setActive(true);
						this.meetingRoleRepository.saveAndFlush(objMeetingRole);
					} else {
						// Insert Role
							objRole = this.roleRepository.getRoleByRoleId(tempRoleId);
							if (objRole != null) {
								objMeetingRole = new MeetingRole();
								objMeetingRole.setActive(true);
								objMeetingRole.setMeetings(objMeetings);
								objMeetingRole.setRole(objRole);
								this.meetingRoleRepository.saveAndFlush(objMeetingRole);
							} else {
								throw new NotFoundException(NotFound.DefaultRoleNotSet);
							}
						
					}
				}

			}
		} finally {

		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void addProject(ProjectDTO projectDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException
	{
		Projects tempProjects = null;
		tempProjects = new Projects();
		tempProjects.setActive(true);
		tempProjects.setProjectName(projectDTO.getProjectName());
		tempProjects.setDescription(projectDTO.getDescription());
		tempProjects.setUrl(projectDTO.getUrl());
		this.projectsRepository.saveAndFlush(tempProjects);
		StringBuffer projectId = null;
		if(projectDTO.getProjectPath() != null)
		{
			String ext = projectDTO.getProjectPath().getOriginalFilename().substring(projectDTO.getProjectPath().getOriginalFilename().lastIndexOf(".")+1);
			String fileName = tempProjects.getId()+"_project."+ext;
			if(FileUtils.uploadProject(fileName, projectDTO.getProjectPath(), servletRequest))
			{
				projectId = new StringBuffer();
				projectId.delete(0, projectId.length())
					.append(fileName);
				tempProjects.setProjectPath(projectId.toString());
			}
		}
		
		StringBuffer documentId = null;
		if(projectDTO.getDocument() != null)
		{
			String ext = projectDTO.getDocument().getOriginalFilename().substring(projectDTO.getDocument().getOriginalFilename().lastIndexOf(".")+1);
			String fileName = tempProjects.getId()+"_document."+ext;
			if(FileUtils.uploadProjectDocument(fileName, projectDTO.getDocument(), servletRequest))
			{
				documentId = new StringBuffer();
				documentId.delete(0, documentId.length())
					.append(fileName);
				tempProjects.setDocument(documentId.toString());
			}
		}
		
		StringBuffer videoId = null;
		if(projectDTO.getVideo() != null)
		{
			String ext = projectDTO.getVideo().getOriginalFilename().substring(projectDTO.getVideo().getOriginalFilename().lastIndexOf(".")+1);
			String fileName = tempProjects.getId()+"_video."+ext;
			if(FileUtils.uploadProjectVideo(fileName, projectDTO.getDocument(), servletRequest))
			{
				videoId = new StringBuffer();
				videoId.delete(0, videoId.length())
					.append(fileName);
				tempProjects.setVideo(videoId.toString());
			}
		}
		
		this.projectsRepository.saveAndFlush(tempProjects);
		
		
		StringBuffer projectImageId = null;
		ProjectImages objProjectImages = null;
		int  n = 1;
		String fileName = "";
		try {
			if (projectDTO.getImages() != null && projectDTO.getImages().size() > 0) {
				System.out.println("call");
				for(MultipartFile tempMultipartFile : projectDTO.getImages())
				{
					if(tempMultipartFile != null)
					{
						fileName =tempProjects.getId() +"_image_"+(n++);
						if (ImageUtils.uploadProjectImage(fileName,
								tempMultipartFile, servletRequest)) {
								objProjectImages = new ProjectImages();
								projectImageId = new StringBuffer();
								projectImageId.delete(0, projectImageId.length())
										.append(fileName).append(".png");
								objProjectImages.setActive(true);
								objProjectImages.setImages(projectImageId.toString());
								objProjectImages.setProjects(tempProjects);
								this.projectImagesRepository.save(objProjectImages);
							}
					}
					
				}
			} else {
				// throw new NotFoundException(NotFound.UserNotFound);
				System.out.println("not found image");
			}
		} finally {

		}
		Role objRole = null;
		ProjectRole objProjectRole = null;
		List<Long> getRoles = projectDTO.getRolesId();
		for(Long tempRoles : getRoles)
		{
			objRole = this.roleRepository.getRoleByRoleId(tempRoles);
			objProjectRole = new ProjectRole();
			objProjectRole.setActive(true);
			objProjectRole.setProjects(tempProjects);
			objProjectRole.setRole(objRole);
			this.projectRoleRepository.saveAndFlush(objProjectRole);		
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<ProjectListDTO> getAllProjects(SignupUser signupUser){
		logger.info("get Projects list");
		User objUser = this.userRepository.getUserByUserName(signupUser.getUserName());
		List<Long> objRole = this.roleRepository.getRoleIdByUserId(objUser.getId());
		List<ProjectListDTO> projectListDTOs = null;
		if(objRole != null && objRole.size() > 0)
		{
			
				if(objRole.contains(1L))
				{
					projectListDTOs = this.projectsRepository.getAllProjectsByRole();
				}
				else
				{
					projectListDTOs = this.projectsRepository.getProjectsByRole(objRole);
				}
				
			
		}
		return projectListDTOs;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteProject(Projects  projects)
	{
		Projects objProjects = null;
		objProjects = this.projectsRepository.getProjectsById(projects.getId());
		if(objProjects!=null)
		{
			objProjects.setActive(false);
			this.projectsRepository.saveAndFlush(objProjects);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public ProjectListDTO editProjectList(ProjectListDTO projectListDTO) {
		logger.info("getMeeting");
		Projects objProjects = this.projectsRepository.getProjectsById(projectListDTO.getId());
		List<Long> roles = null;
		List<ProjectImagesDTO> projectImagesDTOs = null;
		ProjectListDTO objProjectListDTO = null;
		if (objProjects != null) {
			objProjectListDTO = new ProjectListDTO();
			objProjectListDTO.setId(objProjects.getId());
			objProjectListDTO.setProjectName(objProjects.getProjectName());
			objProjectListDTO.setDescription(objProjects.getDescription());
			objProjectListDTO.setUrl(objProjects.getUrl());
			objProjectListDTO.setProjectPath(objProjects.getProjectPath());
			objProjectListDTO.setDocument(objProjects.getDocument());
			objProjectListDTO.setVideo(objProjects.getVideo());
			roles = this.projectRoleRepository.getRolesByProject(objProjects.getId());
			projectImagesDTOs = this.projectImagesRepository.getImagesofProject(objProjects.getId());
			objProjectListDTO.setProjectImagesDTO(projectImagesDTOs);
			objProjectListDTO.setRolesId(roles);
		}
		return objProjectListDTO;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void editProject(ProjectEditDTO projectEditDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException
	{
		Projects tempProjects = null;
		tempProjects = this.projectsRepository.getProjectsById(projectEditDTO.getId());
		if(tempProjects != null)
		{
			tempProjects.setActive(true);
			tempProjects.setProjectName(projectEditDTO.getProjectName());
			tempProjects.setDescription(projectEditDTO.getDescription());
			tempProjects.setUrl(projectEditDTO.getUrl());

			StringBuffer projectId = null;
			if(projectEditDTO.getProjectPath() != null)
			{
				String ext = projectEditDTO.getProjectPath().getOriginalFilename().substring(projectEditDTO.getProjectPath().getOriginalFilename().lastIndexOf(".")+1);
				String fileName = tempProjects.getId()+"_project."+ext;
				if(FileUtils.uploadProject(fileName, projectEditDTO.getProjectPath(), servletRequest))
				{
					projectId = new StringBuffer();
					projectId.delete(0, projectId.length())
						.append(fileName);
					tempProjects.setProjectPath(projectId.toString());
				}
			}
			
			StringBuffer documentId = null;
			if(projectEditDTO.getDocument() != null)
			{
				String ext = projectEditDTO.getDocument().getOriginalFilename().substring(projectEditDTO.getDocument().getOriginalFilename().lastIndexOf(".")+1);
				String fileName = tempProjects.getId()+"_document."+ext;
				if(FileUtils.uploadProjectDocument(fileName, projectEditDTO.getDocument(), servletRequest))
				{
					documentId = new StringBuffer();
					documentId.delete(0, documentId.length())
						.append(fileName);
					tempProjects.setDocument(documentId.toString());
				}
			}
			
			StringBuffer videoId = null;
			if(projectEditDTO.getVideo() != null)
			{
				String ext = projectEditDTO.getVideo().getOriginalFilename().substring(projectEditDTO.getVideo().getOriginalFilename().lastIndexOf(".")+1);
				String fileName = tempProjects.getId()+"_video."+ext;
				if(FileUtils.uploadProjectVideo(fileName, projectEditDTO.getDocument(), servletRequest))
				{
					videoId = new StringBuffer();
					videoId.delete(0, videoId.length())
						.append(fileName);
					tempProjects.setVideo(videoId.toString());
				}
			}
		}
		this.projectsRepository.saveAndFlush(tempProjects);
		
		StringBuffer projectImageId = null;
		List<ProjectImages> objProjectImagesList = null;
		List<ProjectImages> projectImages = this.projectImagesRepository.getImagesByProjectID(projectEditDTO.getId());
		if(projectImages != null && projectImages.size() > 0)
		{
			for(ProjectImages objImages : projectImages)
			{
				objImages.setActive(false);
				this.projectImagesRepository.saveAndFlush(objImages);
			}
		}
		List<Long> imageIds = projectEditDTO.getImageIds();
		
		if(imageIds !=null && imageIds.size() > 0)
		{
			for(Long tempId : imageIds)
			{
				objProjectImagesList = this.projectImagesRepository.getImagesByIdProjectID(tempId, projectEditDTO.getId());
				if(objProjectImagesList != null)
				{
					for(ProjectImages tempImages : objProjectImagesList)
					{
						tempImages.setActive(true);
						this.projectImagesRepository.saveAndFlush(tempImages);
					}
				}
			}
		}
		
		ProjectImages objProjectImages = null;
		int  n = projectImages.size();
		String fileName = "";
		try {
			if (projectEditDTO.getImages() != null && projectEditDTO.getImages().size() > 0) {
				System.out.println("call");
				for(MultipartFile tempMultipartFile : projectEditDTO.getImages())
				{
					if(tempMultipartFile != null)
					{
						fileName =tempProjects.getId() +"_image_"+(n++);
						if (ImageUtils.uploadProjectImage(fileName,
								tempMultipartFile, servletRequest)) {
								objProjectImages = new ProjectImages();
								projectImageId = new StringBuffer();
								projectImageId.delete(0, projectImageId.length())
										.append(fileName).append(".png");
								objProjectImages.setActive(true);
								objProjectImages.setImages(projectImageId.toString());
								objProjectImages.setProjects(tempProjects);
								this.projectImagesRepository.save(objProjectImages);
							}
					}
					
				}
			} else {
				// throw new NotFoundException(NotFound.UserNotFound);
				System.out.println("not found image");
			}
		} finally {

		}
		List<ProjectRole> projectRoles = null;
			projectRoles = this.projectRoleRepository.getProjectRolesByProject(tempProjects);
			if (projectRoles != null && projectRoles.size() > 0) {
				for (ProjectRole tempProjectRole : projectRoles) {
					tempProjectRole.setActive(false);
					this.projectRoleRepository.saveAndFlush(tempProjectRole);
				}
			}
			ProjectRole objProjectRole = null;
			Role objRole = null;
			for (Long tempRoleId : projectEditDTO.getRolesId()) {
				objProjectRole = this.projectRoleRepository.getProjectByProjectRole(projectEditDTO.getId(), tempRoleId);
				if (objProjectRole != null) {
					// Update Role
					objProjectRole.setActive(true);
					this.projectRoleRepository.saveAndFlush(objProjectRole);
				} else {
					// Insert Role
						objRole = this.roleRepository.getRoleByRoleId(tempRoleId);
						if (objRole != null) {
							objProjectRole = new ProjectRole();
							objProjectRole.setActive(true);
							objProjectRole.setProjects(tempProjects);
							objProjectRole.setRole(objRole);
							this.projectRoleRepository.saveAndFlush(objProjectRole);
						} else {
							throw new NotFoundException(NotFound.DefaultRoleNotSet);
						}
					
				}
			}
			
		}
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void addDocument(DocumentDTO documentDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException
	{
		Documents objDocuments = new Documents();
		objDocuments.setActive(true);
		objDocuments.setDocumentName(documentDTO.getDocumentName());
		objDocuments.setDescription(documentDTO.getDescription());
		
		this.documentsRepository.saveAndFlush(objDocuments);
		
		StringBuffer documentId = null;
		if(documentDTO.getDocumentPath() != null)
		{
			String ext = documentDTO.getDocumentPath().getOriginalFilename().substring(documentDTO.getDocumentPath().getOriginalFilename().lastIndexOf(".")+1);
			String fileName = objDocuments.getId()+"_document."+ext;
			if(FileUtils.uploadDocument(fileName, documentDTO.getDocumentPath(), servletRequest))
			{
				documentId = new StringBuffer();
				documentId.delete(0, documentId.length())
					.append(fileName);
				objDocuments.setDocumentPath(documentId.toString());
			}
			this.documentsRepository.saveAndFlush(objDocuments);
		}
		Role objRole = null;
		DocumentRole objDocumentRole = null;
		List<Long> getRoles = documentDTO.getRolesId();
		for(Long tempRoles : getRoles)
		{
			objRole = this.roleRepository.getRoleByRoleId(tempRoles);
			objDocumentRole = new DocumentRole();
			objDocumentRole.setActive(true);
			objDocumentRole.setDocuments(objDocuments);
			objDocumentRole.setRole(objRole);
			this.documentRoleRepository.saveAndFlush(objDocumentRole);		
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<DocumentListDTO> getAllDocuments(SignupUser signupUser){
		logger.info("get Document list");
		User objUser = this.userRepository.getUserByUserName(signupUser.getUserName());
		List<Long> objRole = this.roleRepository.getRoleIdByUserId(objUser.getId());
		List<DocumentListDTO> documentListDTOs = null;
		if(objRole != null && objRole.size() > 0)
		{
			
				if(objRole.contains(1L))
				{
					documentListDTOs = this.documentsRepository.getAllDocumentsByRole();
				}
				else
				{
					documentListDTOs = this.documentsRepository.getDocumentByRole(objRole);
				}
				
			
		}
		return documentListDTOs;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteDocument(Documents documents)
	{
		Documents objDocuments = null;
		objDocuments = this.documentsRepository.getDocumentsById(documents.getId());
		if(objDocuments!=null)
		{
			objDocuments.setActive(false);
			this.documentsRepository.saveAndFlush(objDocuments);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public DocumentListDTO editDocumentList(DocumentListDTO documentListDTO) {
		logger.info("getDocument");
		Documents objDocuments = this.documentsRepository.getDocumentsById(documentListDTO.getId());
		List<Long> roles = null;
		DocumentListDTO objDocumentListDTO = null;
		
		if (objDocuments != null) {
			objDocumentListDTO = new DocumentListDTO();
			objDocumentListDTO.setId(objDocuments.getId());
			objDocumentListDTO.setDocumentName(objDocuments.getDocumentName());
			objDocumentListDTO.setDescription(objDocuments.getDescription());
			objDocumentListDTO.setDocumentPath(objDocuments.getDocumentPath());
			roles = this.documentRoleRepository.getRolesByDocument(objDocuments.getId());
			objDocumentListDTO.setRolesId(roles);
		}
		return objDocumentListDTO;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void editDocument(DocumentDTO documentDTO, HttpServletRequest servletRequest) throws IOException,
	TemplateException, MessagingException, ParseException
	{
		Documents objDocuments = this.documentsRepository.getDocumentsById(documentDTO.getId());
		objDocuments.setActive(true);
		objDocuments.setDocumentName(documentDTO.getDocumentName());
		objDocuments.setDescription(documentDTO.getDescription());
		
		this.documentsRepository.saveAndFlush(objDocuments);
		
		if(documentDTO.getDocumentPath() != null && documentDTO.getDocumentPath().getSize() > 0)
		{
			StringBuffer documentId = null;
			if(documentDTO.getDocumentPath() != null)
			{
				String ext = documentDTO.getDocumentPath().getOriginalFilename().substring(documentDTO.getDocumentPath().getOriginalFilename().lastIndexOf(".")+1);
				String fileName = objDocuments.getId()+"_document."+ext;
				if(FileUtils.uploadDocument(fileName, documentDTO.getDocumentPath(), servletRequest))
				{
					documentId = new StringBuffer();
					documentId.delete(0, documentId.length())
						.append(fileName);
					objDocuments.setDocumentPath(documentId.toString());
				}
				this.documentsRepository.saveAndFlush(objDocuments);
			}
		}
		List<DocumentRole> documentRoles = null;
		documentRoles = this.documentRoleRepository.getDocumentRolesById(objDocuments.getId());
		if (documentRoles != null && documentRoles.size() > 0) {
			for (DocumentRole tempDocumentRole : documentRoles) {
				tempDocumentRole.setActive(false);
				this.documentRoleRepository.saveAndFlush(tempDocumentRole);
			}
		}
		DocumentRole objDocumentRole = null;
		Role objRole = null;
		for (Long tempRoleId : documentDTO.getRolesId()) {
			objDocumentRole = this.documentRoleRepository.getDocumnetByIdRole(documentDTO.getId(), tempRoleId);
			if (objDocumentRole != null) {
				// Update Role
				objDocumentRole.setActive(true);
				this.documentRoleRepository.saveAndFlush(objDocumentRole);
			} else {
				// Insert Role
					objRole = this.roleRepository.getRoleByRoleId(tempRoleId);
					if (objRole != null) {
						objDocumentRole = new DocumentRole();
						objDocumentRole.setActive(true);
						objDocumentRole.setDocuments(objDocuments);
						objDocumentRole.setRole(objRole);
						this.documentRoleRepository.saveAndFlush(objDocumentRole);
					} else {
						throw new NotFoundException(NotFound.DefaultRoleNotSet);
					}
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public List<DiscussionListDTO> getAllDiscussion(SignupUser signupUser){
		logger.info("get Discussion list");
		User objUser = this.userRepository.getUserByUserName(signupUser.getUserName());
		List<Long> objRole = this.roleRepository.getRoleIdByUserId(objUser.getId());
		List<DiscussionListDTO> discussionListDTOs = null;
		if(objRole != null && objRole.size() > 0)
		{
			
				if(objRole.contains(1L))
				{
					discussionListDTOs = this.discussionRepository.getAllDiscussionByRole();
				}
				else
				{
					discussionListDTOs = this.discussionRepository.getDiscussionByRole(objRole);
				}
				
			
		}
		return discussionListDTOs;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void addDiscussion(DiscussionDTO discussionDTO)throws IOException,
	TemplateException, MessagingException, ParseException
	{
		Role objRole = null;
		Discussion objDiscussion = null;
		DiscussionRole objDiscussionRole = null;
		objDiscussion = new Discussion();
		objDiscussion.setActive(true);
		objDiscussion.setDiscussionTopic(discussionDTO.getDiscussionTopic());
		User objUser = this.userRepository.getUserByUserName(discussionDTO.getUserName());
		objDiscussion.setUser(objUser);
		this.discussionRepository.saveAndFlush(objDiscussion);
		List<Long> getRoles = discussionDTO.getRolesId();
		for(Long tempRoles : getRoles)
		{
			
			objRole = this.roleRepository.getRoleByRoleId(tempRoles);
			objDiscussionRole = new DiscussionRole();
			objDiscussionRole.setActive(true);
			objDiscussionRole.setDiscussion(objDiscussion);
			objDiscussionRole.setRole(objRole);
			this.discussionRoleRepository.saveAndFlush(objDiscussionRole);			
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void deleteDiscussion(Discussion discussion)
	{
		Discussion objDiscussion = this.discussionRepository.getDiscussionById(discussion.getId());
		if(objDiscussion!=null)
		{
			objDiscussion.setActive(false);
			this.discussionRepository.saveAndFlush(objDiscussion);
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public DiscussionCommentDTO getDiscussion(Discussion discussion)
	{
		logger.info("get Discussion");
		DiscussionCommentDTO objDiscussionCommentDTO = new DiscussionCommentDTO();
		Discussion objDiscussion = this.discussionRepository.getDiscussionById(discussion.getId());
		User objUser = objDiscussion.getUser();
		objDiscussionCommentDTO.setUserName(objUser.getUserName());
		objDiscussionCommentDTO.setProfilePic(objUser.getProfilePic());
		objDiscussionCommentDTO.setId(objDiscussion.getId());
		objDiscussionCommentDTO.setDisscussionTopic(objDiscussion.getDiscussionTopic());
		List<CommentDTO> objCommentDTOs = this.discussionCommentRepository.getAllCommentsByDiscssionId(discussion.getId());
		if(objCommentDTOs !=null && objCommentDTOs.size() > 0)
		{
			objDiscussionCommentDTO.setCommentDTO(objCommentDTOs);
		}
		return objDiscussionCommentDTO;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.READ_COMMITTED)
	public void addComment(CommentDTO commentDTO)throws IOException,
	TemplateException, MessagingException, ParseException
	{
		User objUser = this.userRepository.getUserByUserName(commentDTO.getUserName());
		DiscussionComment objDiscussionComment = null;
		Discussion objDiscussion = null;
		if(objUser != null)
		{
			objDiscussionComment = new DiscussionComment();
			objDiscussionComment.setActive(true);
			objDiscussionComment.setComment(commentDTO.getComment());
			objDiscussion = this.discussionRepository.getDiscussionById(commentDTO.getId());
			objDiscussionComment.setDiscussion(objDiscussion);
			objDiscussionComment.setUser(objUser);
			this.discussionCommentRepository.saveAndFlush(objDiscussionComment);
		}
	}
}
