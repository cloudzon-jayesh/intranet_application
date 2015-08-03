package com.cloudzon.huddle.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	public static Boolean uploadProject(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadProject");

		GetMultipartFile multipartFile = null;
		StringBuilder localProjectPath = null;
		byte[] responseFile = null;
		Boolean uploadSucceeded = Boolean.FALSE;
		try {
			localProjectPath = new StringBuilder();
			responseFile = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload File location path:-" + webAppRoot);
				logger.info("Project location path:-" + webAppRoot);
				multipartFile = new GetMultipartFile(responseFile);
				localProjectPath.delete(0, localProjectPath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("projects").append(File.separator)
						.append("project").append(File.separator)
						.append(fileName);
				multipartFile.transferTo(new File(localProjectPath.toString()));
				uploadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start uploadProject");

		}
		return uploadSucceeded;

	}
	
	public static Boolean uploadProjectDocument(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadProject");

		GetMultipartFile multipartFile = null;
		StringBuilder localProjectPath = null;
		byte[] responseFile = null;
		Boolean uploadSucceeded = Boolean.FALSE;
		try {
			localProjectPath = new StringBuilder();
			responseFile = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload File location path:-" + webAppRoot);
				logger.info("Project location path:-" + webAppRoot);
				localProjectPath.delete(0, localProjectPath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("projects").append(File.separator)
						.append("document").append(File.separator)
						.append(fileName);
				multipartFile = new GetMultipartFile(responseFile);
				multipartFile.transferTo(new File(localProjectPath.toString()));
				uploadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start uploadProject");

		}
		return uploadSucceeded;

	}
	
	public static Boolean uploadProjectVideo(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadProject");

		GetMultipartFile multipartFile = null;
		StringBuilder localProjectPath = null;
		byte[] responseFile = null;
		Boolean uploadSucceeded = Boolean.FALSE;
		try {
			localProjectPath = new StringBuilder();
			responseFile = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload File location path:-" + webAppRoot);
				logger.info("Project location path:-" + webAppRoot);
				localProjectPath.delete(0, localProjectPath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("projects").append(File.separator)
						.append("videos").append(File.separator)
						.append(fileName);
				multipartFile = new GetMultipartFile(responseFile);
				multipartFile.transferTo(new File(localProjectPath.toString()));
				uploadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start uploadProject");

		}
		return uploadSucceeded;

	}
	
	public static Boolean uploadDocument(String fileName, MultipartFile multipart, HttpServletRequest servletRequest)
			throws IllegalStateException, IOException {
		logger.info("start uploadProject");

		GetMultipartFile multipartFile = null;
		StringBuilder localProjectPath = null;
		byte[] responseFile = null;
		Boolean uploadSucceeded = Boolean.FALSE;
		try {
			localProjectPath = new StringBuilder();
			responseFile = multipart.getBytes();
				String webAppRoot = new File(servletRequest.getSession()
						.getServletContext().getRealPath("/")).getAbsolutePath();
				System.out.println("Upload File location path:-" + webAppRoot);
				logger.info("Document location path:-" + webAppRoot);
				localProjectPath.delete(0, localProjectPath.length())
						.append(webAppRoot).append(File.separator)
						.append("resources").append(File.separator)
						.append("documents").append(File.separator)
						.append(fileName);
				multipartFile = new GetMultipartFile(responseFile);
				multipartFile.transferTo(new File(localProjectPath.toString()));
				uploadSucceeded = Boolean.TRUE;
		
			
			

		} finally {
			logger.info("start upload Document");

		}
		return uploadSucceeded;

	}
	

}