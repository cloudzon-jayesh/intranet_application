package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.GroupPermissionDTO;
import com.cloudzon.huddle.model.Permission;
import com.cloudzon.huddle.model.Role;

public interface PermissionRepository extends BaseRepository<Permission> {

	@Query(value = "SELECT permission FROM Permission AS permission")
	public List<Permission> getAllPermission();
	
	@Query(value = "SELECT NEW com.cloudzon.huddle.dto.GroupPermissionDTO(permission.id,permission.permission) FROM Permission AS permission INNER JOIN RolePermission AS rolePermission where permission.id =:rolePermission.id")
	public List<GroupPermissionDTO> getAllPermissionName();

	@Query("SELECT permission FROM Permission AS permission WHERE permission.id=:permissionId")
	public Permission getPermissionById(@Param("permissionId") Long permissionId);
}