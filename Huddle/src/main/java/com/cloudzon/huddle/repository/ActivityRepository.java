package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.ActivityDTO;
import com.cloudzon.huddle.model.Activity;

public interface ActivityRepository extends BaseRepository<Activity>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.ActivityDTO(activity.id,activity.activityName,activity.activityLink) FROM Activity AS activity where activity.active =true")
	public List<ActivityDTO> getAllActivity();
	
	@Query("SELECT activity FROM Activity AS activity WHERE activity.id=:activityId AND activity.active=true")
	public Activity getActivityByActivityId(@Param("activityId") Long activityId);
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.ActivityDTO(activity.id,activity.activityName,activity.activityLink) FROM ActivityRolePermission AS activityRolePermission INNER JOIN activityRolePermission.role as role INNER JOIN activityRolePermission.activity as activity where role.id =:roleId AND activityRolePermission.active =true")
	public List<ActivityDTO> getActivityByRoleId(@Param("roleId") Long roleId);
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.ActivityDTO(activity.id,activity.activityName,activity.activityLink) FROM Activity AS activity WHERE activity.id=:activityId AND activity.active=true")
	public ActivityDTO getActivityById(@Param("activityId") Long activityId);
}
