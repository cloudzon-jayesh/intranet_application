package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.ProjectListDTO;
import com.cloudzon.huddle.model.Meetings;
import com.cloudzon.huddle.model.Projects;

public interface ProjectsRepository extends BaseRepository<Projects>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.ProjectListDTO(project.id,project.projectName,project.description,project.projectPath,project.url,project.document,project.video) FROM ProjectRole AS projectRole JOIN projectRole.projects AS project JOIN projectRole.role AS role where projectRole.active=true AND project.active=true AND role.id IN :roleId")
	public List<ProjectListDTO> getProjectsByRole(@Param("roleId")List<Long> roleId);
	
	@Query("SELECT project FROM Projects As project where project.active = true AND project.id =:id")
	public Projects getProjectsById(@Param("id")Long id);
	
	@Query("SELECT DISTINCT NEW com.cloudzon.huddle.dto.ProjectListDTO(project.id,project.projectName,project.description,project.projectPath,project.url,project.document,project.video) FROM ProjectRole AS projectRole JOIN projectRole.projects AS project where projectRole.active=true AND project.active=true")
	public List<ProjectListDTO> getAllProjectsByRole();
}
