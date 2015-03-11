package com.cloudzon.huddle.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import com.cloudzon.huddle.dto.EditEmployeeDTO;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.EmployeeDetailDTO;
import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GetRolePermissionDTO;
import com.cloudzon.huddle.dto.GroupDTO;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.dto.RolePermissionDTO;
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
import com.cloudzon.huddle.model.Permission;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.RolePermission;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;
import com.cloudzon.huddle.model.VerificationToken;
import com.cloudzon.huddle.model.VerificationToken.VerificationTokenType;
import com.cloudzon.huddle.repository.ActivityRepository;
import com.cloudzon.huddle.repository.ActivityRolePermissionRepository;
import com.cloudzon.huddle.repository.PermissionRepository;
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
			if (objUser.getIsVerified()) {
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
			} else {
				throw new AuthenticationException(
						"Please confirm your account", "User not verified");
			}
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
			if (objUser.getIsVerified()) {
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
			} else {
				throw new AuthenticationException(
						"Please confirm your account", "User not verified");
			}
		} else {
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
	public void signupUser(SignupUser signupUser,
			HttpServletRequest httpServletRequest) throws IOException,
			TemplateException, MessagingException, ParseException {

		SimpleDateFormat dateFormat = null;
		List<Role> objRoles = this.roleRepository.getDefaultRoles();

		if (objRoles != null && objRoles.size() > 0) {

			dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
			// save user master in database
			User objUser = new User();
			objUser.setIsVerified(false);
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
			if (!objUser.getIsVerified()) {
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
			} else {
				throw new AlreadyVerifiedException(
						AlreadyVerifiedExceptionType.User);
			}
		} else {
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
				objUser.setIsVerified(true);
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
		Role objRole = new Role();
		objRole.setActive(true);
		objRole.setIsDefault(true);
		objRole.setRoleName(groupDTO.getRoleName());
		this.roleRepository.saveAndFlush(objRole);
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
}
