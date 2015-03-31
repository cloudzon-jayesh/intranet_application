package com.cloudzon.huddle.dto;

import java.util.List;



public class MeetingDTO 
{
	private Long id;
	private String meetingName;
	private String description;
	private String dateAndTime;
	private List<Long> rolesId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
	
	
}
