package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.MeetingListDTO;
import com.cloudzon.huddle.model.MeetingRole;
import com.cloudzon.huddle.model.Meetings;
import com.cloudzon.huddle.model.User;
import com.cloudzon.huddle.model.UserRole;

public interface MeetingRoleRepository extends BaseRepository<MeetingRole> 
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.MeetingListDTO(meeting.id,meeting.meetingName,meeting.description,meeting.dateAndTime) FROM MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting JOIN meetingRole.role AS role where meetingRole.active=true AND meeting.active=true AND role.id =:roleId")
	public List<MeetingListDTO> getMeetingsByRole(@Param("roleId")Long roleId);
	
	@Query("SELECT role.id FROM MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting JOIN meetingRole.role AS role where meetingRole.active=true AND meeting.id =:meetingId")
	public List<Long> getRolesByMeeting(@Param("meetingId")Long meetingId);
	
	@Query("SELECT meetingRole FROM MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting  WHERE meeting.id =:meetingId")
	public List<MeetingRole> getMeetingRolesByMeeting(@Param("meetingId") Long meetingId);
	
	@Query("select meetingRole from MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting JOIN meetingRole.role AS role where meeting.id =:meetingId AND role.id =:roleId")
	public MeetingRole getRolesByIds(@Param("meetingId") Long meetingId,
			@Param("roleId") Long roleId);
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.MeetingListDTO(meeting.id,meeting.meetingName,meeting.description,meeting.dateAndTime) FROM MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting JOIN meetingRole.role AS role where meetingRole.active=true AND meeting.active=true AND role.id IN :roleIds")
	public List<MeetingListDTO> getMeetingsByRoles(@Param("roleIds")List<Long> roleIds);
	
	@Query("SELECT DISTINCT NEW com.cloudzon.huddle.dto.MeetingListDTO(meeting.id,meeting.meetingName,meeting.description,meeting.dateAndTime) FROM MeetingRole AS meetingRole JOIN meetingRole.meetings AS meeting where meeting.active=true")
	public List<MeetingListDTO> getAllMeetingsByRoles();
}
