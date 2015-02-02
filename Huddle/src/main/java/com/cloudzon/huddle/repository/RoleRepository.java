package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cloudzon.huddle.model.Role;

public interface RoleRepository extends BaseRepository<Role> {

	@Query(value = "SELECT role FROM Role AS role WHERE role.isDefault=true AND role.active=true")
	public List<Role> getDefaultRoles();
}
