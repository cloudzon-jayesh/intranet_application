package com.cloudzon.huddle.dto;

public class GetActivityRolePermissionDTO 
{
	private Long activityId;
	private Long permissionId;
	private Long roleId;
	public GetActivityRolePermissionDTO(Long activityId, Long permissionId,
			Long roleId) {
		super();
		this.activityId = activityId;
		this.permissionId = permissionId;
		this.roleId = roleId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	

}
