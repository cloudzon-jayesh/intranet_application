package com.cloudzon.huddle.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_meetings")
@DynamicUpdate
public class Meetings extends BaseEntity 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="meeting_name", length = 50)
	private String meetingName;
	
	@Column(name="description", length = 250)
	private String description;
	
	@Column(name="date_time")
	private Date dateAndTime;
	
	@Column(name="active")
	private boolean active;

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}	

}
