package com.cloudzon.huddle.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_documents")
@DynamicUpdate
public class Documents extends BaseEntity 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="document_name", length = 50)
	private String documentName;
	
	@Column(name="description", length = 250)
	private String description;
	
	@Column(name="document_path")
	private String documentPath;
	
	@Column(name="active")
	private boolean active;

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
