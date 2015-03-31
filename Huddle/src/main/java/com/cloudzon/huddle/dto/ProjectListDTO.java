package com.cloudzon.huddle.dto;

import java.util.List;


public class ProjectListDTO 
{
	private Long id;
	private String projectName;
	private String description;
	private String projectPath;
	private String url;
	private String document;
	private String video;
	private List<Long> rolesId;
	private List<ProjectImagesDTO> projectImagesDTO;
	
	public ProjectListDTO() {
		super();
	}

	public ProjectListDTO(Long id, String projectName, String description,
			String projectPath, String url, String document, String video) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.description = description;
		this.projectPath = projectPath;
		this.url = url;
		this.document = document;
		this.video = video;
	}


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
	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<Long> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}

	public List<ProjectImagesDTO> getProjectImagesDTO() {
		return projectImagesDTO;
	}

	public void setProjectImagesDTO(List<ProjectImagesDTO> projectImagesDTO) {
		this.projectImagesDTO = projectImagesDTO;
	}
}
