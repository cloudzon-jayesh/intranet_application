package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.model.ActivityRolePermission;

public interface ActivityRolePermissionRepository extends BaseRepository<ActivityRolePermission>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.GetActivityRolePermissionDTO( activity.id, permission.id, role.id) FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true")
	public List<GetActivityRolePermissionDTO> getAllActivityRolePermission();
	
	@Query("Select rolePermission FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true AND role.id =:roleId AND activity.id =:activityId")
	public List<ActivityRolePermission> getPermissionRoleActivityList(@Param("roleId") Long roleId, @Param("activityId") Long activityId); 
	
	@Query("Select rolePermission FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where role.id =:roleId AND activity.id =:activityId And permission.id =:permissionId")
	public ActivityRolePermission getPermissionRoleActivity(@Param("roleId") Long roleId, @Param("activityId") Long activityId, @Param("permissionId") Long permissionId); 

}
