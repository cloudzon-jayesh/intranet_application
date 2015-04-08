package com.cloudzon.huddle.dto;

import java.util.Date;
import java.util.List;

public class EventsListDTO 
{
	private Long id;
	private String eventName;
	private String description;
	private Date date;
	private Date time;
	private List<EventImagesDTO> eventImagesDTOs;
	
	
	public EventsListDTO() {
		super();
	}
	public EventsListDTO(Long id, String eventName, String description,
			Date date, Date time) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.description = description;
		this.date = date;
		this.time = time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<EventImagesDTO> getEventImagesDTOs() {
		return eventImagesDTOs;
	}
	public void setEventImagesDTOs(List<EventImagesDTO> eventImagesDTOs) {
		this.eventImagesDTOs = eventImagesDTOs;
	}
}