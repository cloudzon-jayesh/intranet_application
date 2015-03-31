package com.cloudzon.huddle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_event_images")
@DynamicUpdate
public class EventImages extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="images", length= 1000 )
	private String images;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Events.class)
	@JoinColumn(name = "event_id", nullable = false)
	private Events eventId;

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Events getEventId() {
		return eventId;
	}

	public void setEventId(Events eventId) {
		this.eventId = eventId;
	}

}
