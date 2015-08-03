package com.cloudzon.huddle.dto;

import java.util.List;

public class RoleActivityPermissionDTO 
{
	private String activityLink;
	private List<String> permissions;
	
	public String getActivityLink() {
		return activityLink;
	}
	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	
}
