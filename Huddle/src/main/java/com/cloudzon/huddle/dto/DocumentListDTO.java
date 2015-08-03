package com.cloudzon.huddle.dto;

import java.util.List;

public class DocumentListDTO 
{
	private Long id;
	private String documentName;
	private String description;
	private String documentPath;
	private List<Long> rolesId;
	
	
	public DocumentListDTO() {
		super();
	}
	public DocumentListDTO(Long id, String documentName, String description,
			String documentPath) {
		super();
		this.id = id;
		this.documentName = documentName;
		this.description = description;
		this.documentPath = documentPath;
	}
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
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
}
