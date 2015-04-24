package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.ProjectImagesDTO;
import com.cloudzon.huddle.model.ProjectDocuments;
import com.cloudzon.huddle.model.ProjectImages;

public interface ProjectImagesRepository extends BaseRepository<ProjectImages>
{
	@Query("SELECT NEW com.cloudzon.huddle.dto.ProjectImagesDTO(projectImages.id,projectImages.images,project.id) FROM ProjectImages AS projectImages JOIN projectImages.projects AS project where projectImages.active = true AND project.active = true AND project.id =:id")
	public List<ProjectImagesDTO> getImagesofProject(@Param("id")Long id);
	
	@Query("SELECT projectImage FROM ProjectImages AS projectImage JOIN projectImage.projects AS project where project.active = true AND projectImage.id =:id AND project.id =:projectId")
	public List<ProjectImages> getImagesByIdProjectID(@Param("id")Long id,@Param("projectId")Long projectId);
	
	@Query("SELECT projectImage FROM ProjectImages AS projectImage JOIN projectImage.projects AS project where project.active = true AND project.id =:projectId")
	public List<ProjectImages> getImagesByProjectID(@Param("projectId")Long projectId);
	
	@Query("SELECT projectImages FROM ProjectImages AS projectImages JOIN projectImages.projects AS project where projectImages.id =:id")
	public ProjectImages getAllImagesById(@Param("id")Long id);
}
