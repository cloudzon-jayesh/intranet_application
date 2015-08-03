package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.DiscussionListDTO;
import com.cloudzon.huddle.model.Discussion;


public interface DiscussionRepository extends BaseRepository<Discussion>
{
	@Query("SELECT DISTINCT NEW com.cloudzon.huddle.dto.DiscussionListDTO(discussion.id,discussion.discussionTopic) FROM DiscussionRole AS discussionRole JOIN discussionRole.discussion AS discussion where discussionRole.active=true AND discussion.active=true")
	public List<DiscussionListDTO> getAllDiscussionByRole();
	
	@Query("SELECT DISTINCT NEW com.cloudzon.huddle.dto.DiscussionListDTO(discussion.id,discussion.discussionTopic) FROM DiscussionRole AS discussionRole JOIN discussionRole.discussion AS discussion JOIN discussionRole.role AS role where discussionRole.active=true AND discussion.active=true AND role.id IN :roleId")
	public List<DiscussionListDTO> getDiscussionByRole(@Param("roleId")List<Long> roleId);
	
	@Query("SELECT discussion FROM Discussion As discussion where discussion.active = true AND discussion.id =:id")
	public Discussion getDiscussionById(@Param("id")Long id);
	
}
