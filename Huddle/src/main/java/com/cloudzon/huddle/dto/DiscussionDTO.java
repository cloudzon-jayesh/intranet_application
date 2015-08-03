package com.cloudzon.huddle.dto;

import java.util.List;



public class DiscussionDTO 
{
	private Long id;
	private String discussionTopic;
	private String userName;
	private List<Long> rolesId;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Long> getRolesId() {
		return rolesId;
	}
	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}
}
