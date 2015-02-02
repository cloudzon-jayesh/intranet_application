package com.cloudzon.huddle.common;

public interface Constant {

	Double TRANSCATION_LIMIT = 10000D;

	// TODO mvc
	String SESSION_USER = "sessionUser";
	// TODO mvc end

	String DATE_FORMAT = "dd/MM/yy";
	String DATE_FORMAT_WITH_TIME = "dd/MM/yyyy HH:mm:ss.SSS";

	// template name for email
	String TEMPLATE_EMAIL_CREATE_ACCOUNT = "createAccount.ftl";
	String TEMPLATE_EMAIL_CHANGE_PASSWORD = "changePassword.ftl";
	String TEMPLATE_EMAIL_RESET_PASSWORD = "resetPassword.ftl";

	// pattern
	String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// common
	String UTF8 = "UTF-8";

	// Mail
	String EMAIL_HEADER_CONFIRM_EMAIL = "Confirm Your Email Address";
	String EMAIL_HEADER_RESET_PASSWORD = "Reset Password";

	// SERVICE CLASS NAME
	String SERVICE_COMMON = "commonService";
	String SERVICE_ROLE_MASTER = "roleMasterService";
	String SERVICE_USER_ROLE = "userRoleService";
	String SERVICE_APPLICATION_MAIL = "applicationMailerService";

	String SERVICE_SEND_MAIL = "sendMailService";
	String SERVICE_VERIFICATION_TOKEN = "verificationTokenService";

	String SERVICE_PERMISSION = "permissionService";
	String SERVICE_ROLE = "roleService";
	String SERVICE_ROLE_PERMISSION = "rolePermissionService";

	String SERVICE_USER = "userService";

	Integer REGISTER_EMAIL_LINK_EXPIRE_TIME = 12 * 24;
	Integer LOST_PASSWORD_EMAIL_LINK_EXPIRE_TIME = 12 * 24;

	int ERROR_LOGIN_CODE = 1;
	int ERROR_ROOM_TIME_OUT = 5;
	int ERROR_PAYMENT_CODE = 2;
	String ERROR_LOGIN_MESSAGE = "Please Login...";

}
