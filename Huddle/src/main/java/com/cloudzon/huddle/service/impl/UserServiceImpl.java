package com.cloudzon.huddle.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

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

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.dto.AccessTokenContainer;
import com.cloudzon.huddle.dto.AccountVerificationToken;
import com.cloudzon.huddle.dto.ChangePasswordDto;
import com.cloudzon.huddle.dto.EmailVerificationRequest;
import com.cloudzon.huddle.dto.ForgotPasswordDto;
import com.cloudzon.huddle.dto.ResetPasswordDTO;
import com.cloudzon.huddle.dto.SignupUser;
import com.cloudzon.huddle.dto.UserLoginDto;
import com.cloudzon.huddle.dto.ValidationErrorDTO;
import com.cloudzon.huddle.exception.AlreadyVerifiedException;
import com.cloudzon.huddle.exception.AlreadyVerifiedException.AlreadyVerifiedExceptionType;
import com.cloudzon.huddle.exception.AuthenticationException;
import com.cloudzon.huddle.exception.AuthorizationException;
import com.cloudzon.huddle.exception.FieldErrorException;
import com.cloudzon.huddle.exception.NotFoundException;
import com.cloudzon.huddle.exception.NotFoundException.NotFound;
import com.cloudzon.huddle.exception.TokenHasExpiredException;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;
import com.cloudzon.huddle.model.VerificationToken;
import com.cloudzon.huddle.model.VerificationToken.VerificationTokenType;
import com.cloudzon.huddle.repository.RoleRepository;
import com.cloudzon.huddle.repository.UserRepository;
import com.cloudzon.huddle.repository.UserRoleRepository;
import com.cloudzon.huddle.repository.VerificationTokenRepository;
import com.cloudzon.huddle.security.CustomUserDetail;
import com.cloudzon.huddle.security.InMemoryTokenStore;
import com.cloudzon.huddle.service.SendMailService;
import com.cloudzon.huddle.service.UserService;
import com.cloudzon.huddle.util.DateUtil;

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
	public void signupUser(SignupUser signupUser) throws IOException,
			TemplateException, MessagingException {

		List<Role> objRoles = this.roleRepository.getDefaultRoles();

		if (objRoles != null && objRoles.size() > 0) {

			// save user master in database
			User objUser = new User();
			objUser.setIsVerified(false);
			objUser.setUserName(signupUser.getUserName());
			objUser.setEmail(signupUser.getEmail());
			objUser.setPassword(this.bCryptPasswordEncode.encode(signupUser
					.getPassword()));
			this.userRepository.saveAndFlush(objUser);

			// save user role in database
			for (Role role : objRoles) {
				UserRole objUserRole = new UserRole();
				objUserRole.setUser(objUser);
				objUserRole.setRole(role);
				objUserRole.setActive(true);
				objUserRole.setCreatedBy(objUser);
				this.userRoleRepository.save(objUserRole);
			}

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

}
