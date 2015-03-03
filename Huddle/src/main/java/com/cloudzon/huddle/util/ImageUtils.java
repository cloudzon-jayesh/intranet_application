package com.cloudzon.huddle.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.cloudzon.huddle.exception.NotFoundException;
import com.cloudzon.huddle.exception.NotFoundException.NotFound;
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
}