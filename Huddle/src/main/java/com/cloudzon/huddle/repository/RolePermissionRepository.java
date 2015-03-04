package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.model.RolePermission;

public interface RolePermissionRepository extends
		BaseRepository<RolePermission> {

	@Query(value = "SELECT role.roleName FROM RolePermission AS rolePermission JOIN rolePermission.role AS role JOIN rolePermission.permission AS permission WHERE permission.id=:permissionId AND role.active=true AND permission.active=true AND rolePermission.active=true")
	public List<String> getPermissionRoleName(
			@Param("permissionId") Long permissionId);

	@Query(value = "SELECT role.roleName FROM RolePermission AS rolePermission JOIN rolePermission.role AS role JOIN rolePermission.permission AS permission WHERE role.active=true AND permission.active=true AND rolePermission.active=true AND permission.method=:method AND permission.url=:url")
	public List<String> getRoles(@Param("url") String url,
			@Param("method") String method);
	@Query(value="SELECT permission.permission, role.role_name,rolePermission.id FROM Permission as permission, Role as role LEFT JOIN RolePermission ON role.id = rolePermission.roleId") 
	public List<String> getRolePermission();
}
