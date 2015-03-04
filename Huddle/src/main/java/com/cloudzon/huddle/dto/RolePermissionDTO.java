package com.cloudzon.huddle.dto;

import java.util.List;

public class RolePermissionDTO 
{
	Long id;
	List<Long> permissionId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Long> getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(List<Long> permissionId) {
		this.permissionId = permissionId;
	}
	
	
}
