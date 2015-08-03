package com.cloudzon.huddle.dto;

public class ProjectStatusDTO 
{
	private Long id;
	private String projectName;
	private String projectPath;
	private String video;
	private int allTask;
	private int completedTask;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public int getAllTask() {
		return allTask;
	}
	public void setAllTask(int allTask) {
		this.allTask = allTask;
	}
	public int getCompletedTask() {
		return completedTask;
	}
	public void setCompletedTask(int completedTask) {
		this.completedTask = completedTask;
	}	
}
