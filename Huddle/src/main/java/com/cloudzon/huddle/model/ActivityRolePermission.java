package com.cloudzon.huddle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_activity_role_permission")
@DynamicUpdate
public class ActivityRolePermission extends BaseEntity 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Activity.class)
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Permission.class)
	@JoinColumn(name = "permisson_id", nullable = false)
	private Permission permission;
	
	@Column(name="active")
	private boolean active;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
