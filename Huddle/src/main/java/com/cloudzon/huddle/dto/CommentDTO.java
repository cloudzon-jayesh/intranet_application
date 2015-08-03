package com.cloudzon.huddle.dto;

public class CommentDTO
{
	private Long id;
	private String comment;
	private String userName;
	private String profilePic;
	
	public CommentDTO() {
		super();
	}
	
	public CommentDTO(Long id, String comment, String userName,
			String profilePic) {
		super();
		this.id = id;
		this.comment = comment;
		this.userName = userName;
		this.profilePic = profilePic;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	
}
