package com.cloudzon.huddle.dto;

public class ActivityDTO 
{
	private Long id;
	private String activityName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public ActivityDTO(Long id, String activityName) {
		super();
		this.id = id;
		this.activityName = activityName;
	}
	
	

}
