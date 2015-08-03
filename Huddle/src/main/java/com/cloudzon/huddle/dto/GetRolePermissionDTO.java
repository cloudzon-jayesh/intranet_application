package com.cloudzon.huddle.dto;

import java.util.List;

public class GetRolePermissionDTO 
{
	private Long roleID;
	private Long permissionId;
	public GetRolePermissionDTO(Long roleID, Long permissionId) {
		super();
		this.roleID = roleID;
		this.permissionId = permissionId;
	}
	public Long getRoleID() {
		return roleID;
	}
	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
	
	
	

}
