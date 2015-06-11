package com.cloudzon.huddle.dto;

import java.util.List;

public class SetUserPermissionDTO 
{
	private String firstName;
	private String lastName;
	private String userName;
	private String profilePic;
	private List<Long> roleIds;
	private List<RoleActivityPermissionDTO> roleActivityPermissionDTOs;	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public List<RoleActivityPermissionDTO> getRoleActivityPermissionDTOs() {
		return roleActivityPermissionDTOs;
	}
	public void setRoleActivityPermissionDTOs(
			List<RoleActivityPermissionDTO> roleActivityPermissionDTOs) {
		this.roleActivityPermissionDTOs = roleActivityPermissionDTOs;
	}
	
}
