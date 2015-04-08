package com.cloudzon.huddle.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class EventsDTO 
{
	private Long id;
	private String eventName;
	private String description;
	private String date;
	private String time;
	private List<MultipartFile> images;
	private List<Long> imageIds;
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<MultipartFile> getImages() {
		return images;
	}
	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public List<Long> getImageIds() {
		return imageIds;
	}
	public void setImageIds(List<Long> imageIds) {
		this.imageIds = imageIds;
	}
	
}
