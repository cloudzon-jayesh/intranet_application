package com.cloudzon.huddle.dto;

public class ProjectImagesDTO {
	
	private Long id;
	private String images;
	private Long projectId;
	
	public ProjectImagesDTO(Long id, String images, Long projectId) {
		super();
		this.id = id;
		this.images = images;
		this.projectId = projectId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
