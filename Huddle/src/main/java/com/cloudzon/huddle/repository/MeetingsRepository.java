package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.MeetingListDTO;
import com.cloudzon.huddle.dto.RoleDTO;
import com.cloudzon.huddle.model.Meetings;

public interface MeetingsRepository extends BaseRepository<Meetings>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.MeetingListDTO(meeting.id, meeting.meetingName,meeting.description, meeting.dateAndTime) FROM Meetings As meeting where meeting.active = true")
	public List<MeetingListDTO> getAllMeetings();
	
	@Query("SELECT meeting FROM Meetings As meeting where meeting.active = true AND meeting.id =:id")
	public Meetings getMeetingsById(@Param("id")Long id);
	
}
