package com.cloudzon.huddle.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.EventsListDTO;
import com.cloudzon.huddle.model.Events;

public interface EventsRepository extends BaseRepository<Events>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.EventsListDTO(event.id,event.eventName,event.description,event.date,event.time)  From Events AS event where event.active = true")
	public List<EventsListDTO> getAllEvents(); 
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.EventsListDTO(event.id,event.eventName,event.description,event.date,event.time)  From Events AS event where event.active = true AND event.id =:eventId")
	public EventsListDTO getEventsById(@Param("eventId") Long eventId); 
	
	@Query("SELECT event From Events AS event where event.active = true AND event.id =:eventId")
	public Events getEventById(@Param("eventId") Long eventId);
}
