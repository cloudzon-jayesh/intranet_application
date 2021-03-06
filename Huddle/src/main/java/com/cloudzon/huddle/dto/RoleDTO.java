package com.cloudzon.huddle.dto;


public class RoleDTO {
	private Long id;
	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RoleDTO(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

}
