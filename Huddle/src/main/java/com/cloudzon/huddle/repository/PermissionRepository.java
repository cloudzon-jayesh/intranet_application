package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cloudzon.huddle.model.Permission;

public interface PermissionRepository extends BaseRepository<Permission> {

	@Query(value = "SELECT permission FROM Permission AS permission")
	public List<Permission> getAllPermission();

}