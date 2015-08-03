package com.cloudzon.huddle.dto;

public class ActivityDTO 
{
	private Long id;
	private String activityName;
	private String activityLink;
	
	
	public ActivityDTO(Long id, String activityName, String activityLink) {
		super();
		this.id = id;
		this.activityName = activityName;
		this.activityLink = activityLink;
	}


	public ActivityDTO(Long id, String activityLink) {
		super();
		this.id = id;
		this.activityLink = activityLink;
	}


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


	public String getActivityLink() {
		return activityLink;
	}


	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}
}
