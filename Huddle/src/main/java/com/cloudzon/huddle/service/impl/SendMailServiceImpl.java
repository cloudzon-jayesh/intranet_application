package com.cloudzon.huddle.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudzon.huddle.common.Constant;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.service.ApplicationMailerService;
import com.cloudzon.huddle.service.SendMailService;

import freemarker.template.TemplateException;

@Service(value = Constant.SERVICE_SEND_MAIL)
public class SendMailServiceImpl implements SendMailService {

	@Resource(name = Constant.SERVICE_APPLICATION_MAIL)
	private ApplicationMailerService applicationMailerService;

	private static final Logger logger = LoggerFactory
			.getLogger(SendMailServiceImpl.class);

	@Override
	public void sendUserSignupMail(User user, String redirectUrl, String token)
			throws MessagingException, IOException, TemplateException {
		Map<String, Object> objMapTemplet = null;
		StringBuilder objSB = null;
		try {
			logger.info("sendUserSignupMail start");
			// mail
			objMapTemplet = new HashMap<String, Object>();
			objMapTemplet.put("name", user.getUserName());
			objSB = new StringBuilder();
			objSB.delete(0, objSB.length()).append(redirectUrl).append("?key=")
					.append(token);
			objMapTemplet.put("activeAccountUrl", objSB.toString());
			Map<String, String> images = new HashMap<String, String>();
			images.put("orsEmailLogo", "orsEmailLogo.png");
			images.put("OSR_emailActive", "OSR_emailActive.png");
			// send mail to user
			this.applicationMailerService.sendTemplateMail(user.getEmail(),
					Constant.EMAIL_HEADER_CONFIRM_EMAIL,
					Constant.TEMPLATE_EMAIL_CREATE_ACCOUNT, objMapTemplet,
					images);
			logger.info("sendUserSignupMail end");
		} finally {
			objMapTemplet = null;
			objSB = null;
		}
	}

	@Override
	public void sendForgotPasswordMail(User user, String redirectUrl,
			String token) throws MessagingException, IOException,
			TemplateException {
		Map<String, Object> objMapTemplet = null;
		StringBuilder objSB = null;
		try {
			logger.info("sendForgotPasswordMail start");
			// get user data from list
			objMapTemplet = new HashMap<String, Object>();
			objMapTemplet.put("name", user.getUserName());
			objSB = new StringBuilder();
			objSB.delete(0, objSB.length()).append(redirectUrl).append("?key=")
					.append(token);
			objMapTemplet.put("resetPasswordUrl", objSB.toString());
			Map<String, String> images = new HashMap<String, String>();
			images.put("orsEmailLogo", "orsEmailLogo.png");
			images.put("emailResetPW", "emailResetPW.png");

			// send mail to user
			this.applicationMailerService.sendTemplateMail(user.getEmail(),
					Constant.EMAIL_HEADER_RESET_PASSWORD,
					Constant.TEMPLATE_EMAIL_RESET_PASSWORD, objMapTemplet,
					images);
			logger.info("sendForgotPasswordMail end");
		} finally {
			objMapTemplet = null;
			objSB = null;
		}
	}

}
