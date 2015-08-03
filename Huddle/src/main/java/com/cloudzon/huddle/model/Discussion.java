package com.cloudzon.huddle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "huddle_discussion")
@DynamicUpdate
public class Discussion  extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="discussion_topic", length = 250)
	private String discussionTopic;

	@Column(name = "active", nullable = false)
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public String getDiscussionTopic() {
		return discussionTopic;
	}

	public void setDiscussionTopic(String discussionTopic) {
		this.discussionTopic = discussionTopic;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
