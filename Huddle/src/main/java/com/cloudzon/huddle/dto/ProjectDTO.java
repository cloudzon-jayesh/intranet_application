package com.cloudzon.huddle.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProjectDTO 
{
	
	private String projectName;
	private String description;
	private String url;
	private MultipartFile projectPath;
	private MultipartFile video;
	private List<MultipartFile> images;
	private List<Long> rolesId;
	private String userName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(MultipartFile projectPath) {
		this.projectPath = projectPath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
