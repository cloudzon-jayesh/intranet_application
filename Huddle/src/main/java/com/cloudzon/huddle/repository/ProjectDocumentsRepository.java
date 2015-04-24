package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.ProjectDocumentDTO;
import com.cloudzon.huddle.dto.ProjectTasksDTO;
import com.cloudzon.huddle.model.ProjectDocuments;
import com.cloudzon.huddle.model.ProjectTasks;

public interface ProjectDocumentsRepository extends BaseRepository<ProjectDocuments>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.ProjectDocumentDTO(projectDocuments.id,projectDocuments.documentName,projectDocuments.documents) FROM ProjectDocuments AS projectDocuments JOIN projectDocuments.projects AS project where projectDocuments.active = true AND project.id =:id")
	public List<ProjectDocumentDTO> getAllDocumentsByProjectId(@Param("id")Long id);
	
	@Query("SELECT projectDocuments FROM ProjectDocuments AS projectDocuments JOIN projectDocuments.projects AS project where projectDocuments.id =:id")
	public ProjectDocuments getAllDocumentsById(@Param("id")Long id);
}
