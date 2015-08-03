package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.ProjectTasksDTO;
import com.cloudzon.huddle.model.ProjectTasks;

public interface ProjectTasksRepository extends BaseRepository<ProjectTasks>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.ProjectTasksDTO(projectTasks.id,projectTasks.tasks,projectTasks.startDate,projectTasks.endDate,projectTasks.complete) FROM ProjectTasks AS projectTasks JOIN projectTasks.projects AS project where projectTasks.active = true AND project.id =:id")
	public List<ProjectTasksDTO> getAllTasksByProjectId(@Param("id")Long id);
	
	@Query("SELECT projectTasks FROM ProjectTasks AS projectTasks JOIN projectTasks.projects AS project where projectTasks.id =:id")
	public ProjectTasks getTasksById(@Param("id")Long id);
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.ProjectTasksDTO(projectTasks.id,projectTasks.tasks,projectTasks.startDate,projectTasks.endDate,projectTasks.complete) FROM ProjectTasks AS projectTasks JOIN projectTasks.projects AS project where projectTasks.active = true AND project.id =:id AND projectTasks.complete = true")
	public List<ProjectTasksDTO> getAllCompletedTasksByProjectId(@Param("id")Long id);
}
