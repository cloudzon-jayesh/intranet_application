package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.CommentDTO;
import com.cloudzon.huddle.model.DiscussionComment;

public interface DiscussionCommentRepository extends BaseRepository<DiscussionComment>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.CommentDTO(disscussionComment.id,disscussionComment.comment,user.userName,user.profilePic) FROM DiscussionComment AS disscussionComment JOIN disscussionComment.discussion AS discussion JOIN disscussionComment.user AS user where disscussionComment.active = true AND discussion.active = true AND discussion.id =:id")
	public List<CommentDTO> getAllCommentsByDiscssionId(@Param("id")Long id);
}
