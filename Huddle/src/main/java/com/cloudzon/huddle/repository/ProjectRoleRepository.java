package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.model.MeetingRole;
import com.cloudzon.huddle.model.ProjectRole;
import com.cloudzon.huddle.model.Projects;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;

public interface ProjectRoleRepository extends BaseRepository<ProjectRole>
{
	@Query("SELECT role.id FROM ProjectRole AS projectRole JOIN projectRole.projects AS project JOIN projectRole.role AS role where projectRole.active=true AND project.id =:projectId")
	public List<Long> getRolesByProject(@Param("projectId")Long projectId);
	
	@Query("SELECT projectRole FROM ProjectRole AS projectRole JOIN projectRole.projects AS project JOIN projectRole.role AS role where project.id =:projectId")
	public List<ProjectRole> getProjectRolesByProject(@Param("projectId") Long projectId);
	
	@Query("SELECT projectRole FROM ProjectRole AS projectRole WHERE projectRole.projects =:project")
	public List<ProjectRole> getProjectRolesByProject(@Param("project") Projects project);
	
	@Query("SELECT projectRole FROM ProjectRole AS projectRole JOIN projectRole.projects AS project JOIN projectRole.role AS role where project.id =:projectId AND role.id =:roleId")
	public ProjectRole getProjectByProjectRole(@Param("projectId") Long projectId,@Param("roleId")Long roleId);
}
