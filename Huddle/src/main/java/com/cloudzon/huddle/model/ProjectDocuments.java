package com.cloudzon.huddle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_project_documents")
@DynamicUpdate
public class ProjectDocuments extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="document_name", length= 1000 )
	private String documentName;
	
	@Column(name="documents", length= 1000 )
	private String documents;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Projects.class)
	@JoinColumn(name = "project_id", nullable = false)
	private Projects projects;
	
	@Column(name="active")
	private Boolean active;

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

	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

		
}
