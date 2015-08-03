package com.cloudzon.huddle.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProjectAddImagesDTO {
	
	private List<MultipartFile> images;
	private Long projectId;
	
	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
		
}
