package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.EventImagesDTO;
import com.cloudzon.huddle.model.EventImages;
import com.cloudzon.huddle.model.ProjectImages;

public interface EventImagesRepository extends BaseRepository<EventImages>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.EventImagesDTO(eventImages.id,eventImages.images,event.id) FROM EventImages AS eventImages JOIN eventImages.events AS event where eventImages.active = true AND event.active = true AND event.id =:id")
	public List<EventImagesDTO> getImagesofEvent(@Param("id")Long id);
	
	@Query("SELECT eventImages FROM EventImages AS eventImages JOIN eventImages.events AS event where event.active = true AND event.id =:eventId")
	public List<EventImages> getImagesByEventID(@Param("eventId")Long eventId);
	
	@Query("SELECT eventImages FROM EventImages AS eventImages JOIN eventImages.events AS event  where event.active = true AND eventImages.id =:id AND event.id =:eventId")
	public List<EventImages> getImagesByIdEventID(@Param("id")Long id,@Param("eventId")Long eventId);
}
