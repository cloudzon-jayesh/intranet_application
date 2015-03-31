package com.cloudzon.huddle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudzon.huddle.dto.DocumentListDTO;
import com.cloudzon.huddle.model.Documents;

public interface DocumentsRepository extends BaseRepository<Documents>
{
	
	@Query("SELECT DISTINCT NEW com.cloudzon.huddle.dto.DocumentListDTO(document.id,document.documentName,document.description,document.documentPath) FROM DocumentRole AS documentRole JOIN documentRole.documents AS document where documentRole.active=true AND document.active=true")
	public List<DocumentListDTO> getAllDocumentsByRole();
	
	@Query("SELECT NEW com.cloudzon.huddle.dto.DocumentListDTO(document.id,document.documentName,document.description,document.documentPath) FROM DocumentRole AS documentRole JOIN documentRole.documents AS document JOIN documentRole.role AS role where documentRole.active=true AND document.active=true AND role.id IN :roleId")
	public List<DocumentListDTO> getDocumentByRole(@Param("roleId")List<Long> roleId);
	
	@Query("SELECT document FROM Documents As document where document.active = true AND document.id =:id")
	public Documents getDocumentsById(@Param("id")Long id);
}
