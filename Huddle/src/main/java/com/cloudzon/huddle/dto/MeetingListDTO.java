package com.cloudzon.huddle.dto;

import java.util.Date;

public class MeetingListDTO 
{
	private Long id;
	private String meetingName;
	private String description;
	private Date dateAndTime;

	
	public MeetingListDTO(Long id, String meetingName, String description,
			Date dateAndTime) {
		super();
		this.id = id;
		this.meetingName = meetingName;
		this.description = description;
		this.dateAndTime = dateAndTime;
	}

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

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
}
