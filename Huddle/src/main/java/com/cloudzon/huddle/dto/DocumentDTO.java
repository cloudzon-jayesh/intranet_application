package com.cloudzon.huddle.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;



public class DocumentDTO 
{
	private Long id;
	private String documentName;
	private String description;
	private MultipartFile documentPath;
	private List<Long> rolesId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(MultipartFile documentPath) {
		this.documentPath = documentPath;
	}
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
}
