package com.cloudzon.huddle.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.cloudzon.huddle.model.User;

import freemarker.template.TemplateException;

public interface SendMailService {

	public void sendUserSignupMail(User user, String emailConfirmUrl,
			String token) throws MessagingException, IOException,
			TemplateException;

	public void sendForgotPasswordMail(User user, String redirectUrl,
			String token) throws MessagingException, IOException,
			TemplateException;

}
