package com.cloudzon.huddle.dto;

public class EventImagesDTO {
	
	private Long id;
	private String images;
	private Long eventId;
	public EventImagesDTO(Long id, String images, Long eventId) {
		super();
		this.id = id;
		this.images = images;
		this.eventId = eventId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	
	
}
