package com.cloudzon.huddle.dto;

public class DiscussionListDTO 
{
	private Long id;
	private String discussionTopic;
	
	
	public DiscussionListDTO(Long id, String discussionTopic) {
		super();
		this.id = id;
		this.discussionTopic = discussionTopic;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDiscussionTopic() {
		return discussionTopic;
	}
	public void setDiscussionTopic(String discussionTopic) {
		this.discussionTopic = discussionTopic;
	}
	
	
}
