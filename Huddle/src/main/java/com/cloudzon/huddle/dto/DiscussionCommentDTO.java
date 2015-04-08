package com.cloudzon.huddle.dto;

import java.util.List;

public class DiscussionCommentDTO 
{
	private Long id;
	private String disscussionTopic;
	private String userName;
	private String profilePic;
	private List<CommentDTO> commentDTO;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDisscussionTopic() {
		return disscussionTopic;
	}
	public void setDisscussionTopic(String disscussionTopic) {
		this.disscussionTopic = disscussionTopic;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public List<CommentDTO> getCommentDTO() {
		return commentDTO;
	}
	public void setCommentDTO(List<CommentDTO> commentDTO) {
		this.commentDTO = commentDTO;
	}
}
