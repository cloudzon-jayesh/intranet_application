package com.cloudzon.huddle.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.cloudzon.huddle.model.User;

public class ImageUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ImageUtils.class);

	public static Boolean downloadSocialProfilePicture(User user,
			MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start downloadSocialProfilePicture");

		GetMultipartFile multipartFile = null;
		StringBuilder localImagePath = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		byte[] responseImage = null;
		int n = 0;
		Boolean downloadSucceeded = Boolean.FALSE;
		try {
			localImagePath = new StringBuilder();
			responseImage = multipart.getBytes();
			String webAppRoot = new File(servletRequest.getSession()
					.getServletContext().getRealPath("/")).getAbsolutePath();
			System.out.println("profilePicture location path:-" + webAppRoot);
			logger.info("profilePicture location path:-" + webAppRoot);
			localImagePath.delete(0, localImagePath.length())
					.append(webAppRoot).append(File.separator)
					.append("resources").append(File.separator)
					.append("images").append(File.separator)
					.append("profilePicture").append(File.separator)
					.append(user.getId()).append(".png");
			multipartFile = new GetMultipartFile(responseImage);
			multipartFile.transferTo(new File(localImagePath.toString()));
			downloadSucceeded = Boolean.TRUE;

		} finally {
			logger.info("start downloadSocialProfilePicture");

		}
		return downloadSucceeded;

	}
	
	
	public static Boolean uploadEventImage(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadEventImage");

		GetMultipartFile multipartFile = null;
		StringBuilder localImagePath = null;
		byte[] responseImage = null;
		Boolean downloadSucceeded = Boolean.FALSE;
		try {
			localImagePath = new StringBuilder();
				responseImage = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload image location path:-" + webAppRoot);
				logger.info("profilePicture location path:-" + webAppRoot);
				localImagePath.delete(0, localImagePath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("images").append(File.separator)
						.append("events").append(File.separator)
						.append(fileName).append(".png");
				multipartFile = new GetMultipartFile(responseImage);
				multipartFile.transferTo(new File(localImagePath.toString()));
				downloadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start uploadEventImage");

		}
		return downloadSucceeded;

	}
	
	public static Boolean uploadProjectImage(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadProjectImage");

		GetMultipartFile multipartFile = null;
		StringBuilder localImagePath = null;
		byte[] responseImage = null;
		Boolean downloadSucceeded = Boolean.FALSE;
		try {
			localImagePath = new StringBuilder();
				responseImage = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload image location path:-" + webAppRoot);
				logger.info("profilePicture location path:-" + webAppRoot);
				localImagePath.delete(0, localImagePath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("projects").append(File.separator)
						.append("images").append(File.separator)
						.append(fileName).append(".png");
				multipartFile = new GetMultipartFile(responseImage);
				multipartFile.transferTo(new File(localImagePath.toString()));
				downloadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start uploadProjectImage");

		}
		return downloadSucceeded;

	}
}