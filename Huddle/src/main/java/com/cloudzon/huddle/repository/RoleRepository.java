package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.dto.UserRoleDTO;
import com.cloudzon.huddle.model.Role;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;

public interface RoleRepository extends BaseRepository<Role> {

	@Query(value = "SELECT role FROM Role AS role WHERE role.isDefault=true AND role.active=true")
	public List<Role> getDefaultRoles();

	@Query(value = "SELECT NEW com.cloudzon.huddle.dto.UserRoleDTO(role.id, role.roleName) FROM Role AS role WHERE role.active=true")
	public List<UserRoleDTO> getAllUserRole();

	@Query("SELECT role FROM Role AS role WHERE role.id=:roleId AND role.active=true")
	public Role getRoleByRoleId(@Param("roleId") Long roleId);

	@Query("select NEW com.cloudzon.huddle.dto.RoleDTO(role.id,role.roleName)  from UserRole AS userRole JOIN userRole.user user JOIN userRole.role as role where user.id =:id AND userRole.active=true")
	public List<RoleDTO> getRoleByUserId(@Param("id") Long id);

	@Query("select userRole from UserRole AS userRole JOIN userRole.user user JOIN userRole.role as role where user.id =:id AND role.id =:roleId")
	public UserRole getRoleIdUserId(@Param("id") Long id,
			@Param("roleId") Long roleId);

	@Query("SELECT userRole FROM UserRole AS userRole WHERE userRole.user =:user")
	public List<UserRole> getUserRolesByUserId(@Param("user") User user);
	
	@Query(value = "SELECT permission.id FROM RolePermission AS rolePermission INNER JOIN  rolePermission.role AS role INNER JOIN rolePermission.permission AS permission  WHERE role.id=:roleId")
	public List<Long> getAllUserRolePermission(@Param("roleId") Long roleId);
}
