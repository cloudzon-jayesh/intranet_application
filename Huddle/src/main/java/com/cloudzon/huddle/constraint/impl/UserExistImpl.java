package com.cloudzon.huddle.constraint.impl;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import com.cloudzon.huddle.constraint.UserExist;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.repository.UserRepository;

public class UserExistImpl implements ConstraintValidator<UserExist, Object> {

	@Resource
	private UserRepository userRepository;

	private String userName;

	private String email;

	private String emailProperty;

	private String userNameProperty;

	private String emailExistError;

	private String userNameExistError;

	private String emailEmptyError;

	private String userNameEmptyError;

	private String invalidEmailError;

	private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	private static String DOMAIN = ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	/**
	 * Regular expression for the local part of an email address (everything
	 * before '@')
	 */
	private final Pattern localPattern = java.util.regex.Pattern.compile(ATOM
			+ "+(\\." + ATOM + "+)*", CASE_INSENSITIVE);

	/**
	 * Regular expression for the domain part of an email address (everything
	 * after '@')
	 */
	private final Pattern domainPattern = java.util.regex.Pattern.compile(
			DOMAIN + "|" + IP_DOMAIN, CASE_INSENSITIVE);

	@Override
	public void initialize(UserExist constraintAnnotation) {
		userName = constraintAnnotation.userName();
		email = constraintAnnotation.email();
		emailProperty = constraintAnnotation.emailProperty();
		userNameProperty = constraintAnnotation.userNameProperty();
		emailExistError = constraintAnnotation.emailExistError();
		userNameExistError = constraintAnnotation.userNameExistError();
		emailEmptyError = constraintAnnotation.emailEmptyError();
		userNameEmptyError = constraintAnnotation.userNameEmptyError();
		invalidEmailError = constraintAnnotation.invalidEmailError();
	}

	@Override
	public boolean isValid(Object user, ConstraintValidatorContext context) {
		User objUser = null;
		Boolean isValid = Boolean.TRUE;
		String strEmail = null;
		String strUserName = null;
		Boolean isValidEmail = Boolean.TRUE;
		try {
			// get field value
			strEmail = BeanUtils.getProperty(user, this.email);
			strUserName = BeanUtils.getProperty(user, this.userName);
			if (!StringUtils.hasText(strEmail)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(emailEmptyError)
						.addPropertyNode(emailProperty)
						.addConstraintViolation();
				isValid = Boolean.FALSE;
			} else {
				// split email at '@' and consider local and domain part
				// separately;
				// note a split limit of 3 is used as it causes all characters
				// following to an (illegal) second @ character to
				// be put into a separate array element, avoiding the regex
				// application in this case since the resulting array
				// has more than 2 elements
				String[] emailParts = strEmail.toString().split("@", 3);
				if (emailParts.length != 2) {
					isValidEmail = Boolean.FALSE;
				}

				// if we have a trailing dot in local or domain part we have an
				// invalid email address.
				// the regular expression match would take care of this, but
				// IDN.toASCII drops trailing the trailing '.'
				// (imo a bug in the implementation)
				if (isValidEmail
						&& (emailParts[0].endsWith(".") || emailParts[1]
								.endsWith("."))) {
					isValidEmail = Boolean.FALSE;
				}

				if (isValidEmail && !matchPart(emailParts[0], localPattern)) {
					isValidEmail = Boolean.FALSE;
				}
				if (isValidEmail && !matchPart(emailParts[1], domainPattern)) {
					isValidEmail = Boolean.FALSE;
				}
				// TODO require for registartion
				if (isValidEmail && !matchDomain(emailParts[1])) {
					isValidEmail = Boolean.FALSE;
				}
				if (!isValidEmail) {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(
							invalidEmailError).addPropertyNode(emailProperty)
							.addConstraintViolation();
					isValid = Boolean.FALSE;
				}
			}
			if (!StringUtils.hasText(strUserName)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(userNameEmptyError)
						.addPropertyNode(userNameProperty)
						.addConstraintViolation();
				isValid = Boolean.FALSE;
			}
			if (isValid) {
				objUser = this.userRepository.getUserByUserNameOrEmail(
						strUserName, strEmail);
				if (objUser != null) {
					if (strUserName.equalsIgnoreCase(objUser.getUserName())) {
						context.disableDefaultConstraintViolation();
						context.buildConstraintViolationWithTemplate(
								userNameExistError)
								.addPropertyNode(userNameProperty)
								.addConstraintViolation();
						isValid = Boolean.FALSE;
					}
					// check email
					if (strEmail.equalsIgnoreCase(objUser.getEmail())) {
						context.disableDefaultConstraintViolation();
						context.buildConstraintViolationWithTemplate(
								emailExistError).addPropertyNode(emailProperty)
								.addConstraintViolation();
						isValid = Boolean.FALSE;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return isValid;
	}

	private boolean matchPart(String part, Pattern pattern) {
		try {
			part = IDN.toASCII(part);
		} catch (IllegalArgumentException e) {
			// occurs when the label is too long (>63, even though it should
			// probably be 64 - see
			// http://www.rfc-editor.org/errata_search.php?rfc=3696,
			// practically that should not be a problem)
			return false;
		}
		Matcher matcher = pattern.matcher(part);
		return matcher.matches();
	}

	private boolean matchDomain(String domain) {
		String domainRegx = "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		try {
			domain = IDN.toASCII(domain);
		} catch (IllegalArgumentException e) {
			// occurs when the label is too long (>63, even though it should
			// probably be 64 - see
			// http://www.rfc-editor.org/errata_search.php?rfc=3696,
			// practically that should not be a problem)
			return false;
		}
		Pattern pattern = Pattern.compile(domainRegx);
		Matcher matcher = pattern.matcher(domain);
		return matcher.matches();
	}

}
