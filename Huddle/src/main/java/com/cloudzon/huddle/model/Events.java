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
@Table(name = "huddle_events")
@DynamicUpdate
public class Events extends BaseEntity 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="event_name", length = 50)
	private String eventName;
	
	@Column(name="description", length= 1000)
	private String description;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="time")
	private Date time;
	
	@Column(name="active")
	private boolean active;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
