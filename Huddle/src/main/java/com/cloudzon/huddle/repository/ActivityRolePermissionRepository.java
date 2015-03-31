package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.dto.ActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GetActivityRolePermissionDTO;
import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.dto.RoleActivityPermissionDTO;
import com.cloudzon.huddle.dto.SetUserPermissionDTO;
import com.cloudzon.huddle.model.ActivityRolePermission;

public interface ActivityRolePermissionRepository extends BaseRepository<ActivityRolePermission>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.GetActivityRolePermissionDTO( activity.id, permission.id, role.id) FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true")
	public List<GetActivityRolePermissionDTO> getAllActivityRolePermission();
	
	@Query("Select rolePermission FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true AND role.id =:roleId AND activity.id =:activityId")
	public List<ActivityRolePermission> getPermissionRoleActivityList(@Param("roleId") Long roleId, @Param("activityId") Long activityId); 
	
	@Query("Select rolePermission FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where role.id =:roleId AND activity.id =:activityId And permission.id =:permissionId")
	public ActivityRolePermission getPermissionRoleActivity(@Param("roleId") Long roleId, @Param("activityId") Long activityId, @Param("permissionId") Long permissionId); 

	/*@Query("select New com.cloudzon.huddle.dto.SetUserPermissionDTO(user.firstName,user.lastName,user.userName,role.id,permission.id,activity.id) from UserRole AS userRole JOIN userRole.user AS user JOIN userRole.role AS role JOIN ActivityRolePermission as activityRolePermission JOIN activityRolePermission.permission AS permission  where user.userName =:userName AND user.password =:password AND user.isVerified = true AND activityRolePermission.activityId = activity.id AND activityRolePermission.permissionId = permission.id AND activityRolePermission.roleId = role.id AND activityRolePermission.active = true")
	public List<SetUserPermissionDTO> setUserPermission(@Param("userName") String userName, @Param("password") String password);*/
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.GroupPermissionDTO(permission.id,permission.permission) FROM ActivityRolePermission AS activityRolePermission JOIN activityRolePermission.role as role JOIN activityRolePermission.activity as activity JOIN activityRolePermission.permission AS permission where role.id =:roleId AND activity.id =:activityId AND activityRolePermission.active =true")
	public List<GroupPermissionDTO> getPermissionByRoleIdActivityId(@Param("roleId") Long roleId, @Param("activityId") Long activityId);
	
	/*@Query("Select NEW com.cloudzon.huddle.dto.RoleActivityPermissionDTO(role.id,role.roleName,activity.id,activity.activityLink,permission.id,permission.permission) FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true AND role.id =:roleId AND activity.id =:activityId")
	public List<RoleActivityPermissionDTO> getAllRoleActivityList(@Param("roleId") Long roleId, @Param("activityId") Long activityId); 
	*/
	@Query("Select DISTINCT NEW com.cloudzon.huddle.dto.ActivityDTO(activity.id,activity.activityLink) FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true AND role.id IN :roleId ")
	public List<ActivityDTO> getActivityPermissionList(@Param("roleId") List<Long> roleId);
	
	@Query("Select DISTINCT permission.permission FROM ActivityRolePermission as rolePermission JOIN rolePermission.role as role JOIN rolePermission.permission as permission JOIN rolePermission.activity as activity where rolePermission.active =true AND activity.id =:activityId AND role.id IN :roleId ")
	public List<String> getActivityPermissions(@Param("activityId") Long activityId, @Param("roleId") List<Long> roleId);
	
}
