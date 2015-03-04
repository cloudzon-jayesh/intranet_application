package com.cloudzon.huddle.dto;

public class GroupPermissionDTO 
{
	private Long id;
	private String permission;
	public GroupPermissionDTO(Long id, String permission) {
		super();
		this.id = id;
		this.permission = permission;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
