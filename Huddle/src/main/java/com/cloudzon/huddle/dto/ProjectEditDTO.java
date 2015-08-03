package com.cloudzon.huddle.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProjectEditDTO 
{
	private Long id;
	private String projectName;
	private String description;
	private String url;
	private MultipartFile projectPath;
	private MultipartFile document;
	private MultipartFile video;
	//private List<MultipartFile> images;
	//private List<Long> imageIds;
	private List<Long> rolesId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MultipartFile getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(MultipartFile projectPath) {
		this.projectPath = projectPath;
	}
	public MultipartFile getDocument() {
		return document;
	}
	public void setDocument(MultipartFile document) {
		this.document = document;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
	/*public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public List<Long> getImageIds() {
		return imageIds;
	}
	public void setImageIds(List<Long> imageIds) {
		this.imageIds = imageIds;
	}*/
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
}
