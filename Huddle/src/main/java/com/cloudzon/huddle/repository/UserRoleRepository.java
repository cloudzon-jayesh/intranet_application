package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;

public interface UserRoleRepository extends BaseRepository<UserRole> {

	@Query(value = "SELECT role.roleName FROM UserRole AS userRole JOIN userRole.user AS user JOIN userRole.role AS role WHERE (user.userName=:userNameOrEmail OR user.email=:userNameOrEmail) AND userRole.active=true AND role.active=true")
	public List<String> getUserRolesByUserNameOrEmail(
			@Param("userNameOrEmail") String userNameOrEmail);
	}
