package com.cloudzon.huddle.dto;

public class ProjectDocumentDTO 
{
	private Long id;
	private String documentName;
	private String documents;
	
	
	public ProjectDocumentDTO(Long id, String documentName, String documents) {
		super();
		this.id = id;
		this.documentName = documentName;
		this.documents = documents;
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
	public String getDocuments() {
		return documents;
	}
	public void setDocuments(String documents) {
		this.documents = documents;
	}	
}
