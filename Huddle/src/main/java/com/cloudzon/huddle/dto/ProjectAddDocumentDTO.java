package com.cloudzon.huddle.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProjectAddDocumentDTO 
{
	private Long id;
	private Long projectId;
	private String documentName;
	private MultipartFile documents;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public MultipartFile getDocuments() {
		return documents;
	}
	public void setDocuments(MultipartFile documents) {
		this.documents = documents;
	}
}
