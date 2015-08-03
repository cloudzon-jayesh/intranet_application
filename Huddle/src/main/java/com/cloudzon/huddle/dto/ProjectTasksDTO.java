package com.cloudzon.huddle.dto;

import java.util.Date;


public class ProjectTasksDTO 
{
	private Long id;
	private String tasks;
	private Date startDate;
	private Date endDate;
	private boolean complete;
	
	
	
	public ProjectTasksDTO(Long id, String tasks, Date startDate, Date endDate,
			boolean complete) {
		super();
		this.id = id;
		this.tasks = tasks;
		this.startDate = startDate;
		this.endDate = endDate;
		this.complete = complete;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTasks() {
		return tasks;
	}
	public void setTasks(String tasks) {
		this.tasks = tasks;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean getComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
		
}
