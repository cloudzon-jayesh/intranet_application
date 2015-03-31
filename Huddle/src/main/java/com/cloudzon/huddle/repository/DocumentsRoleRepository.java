package com.cloudzon.huddle.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cloudzon.huddle.model.DocumentRole;

public interface DocumentsRoleRepository extends BaseRepository<DocumentRole> 
{
	@Query("SELECT role.id FROM DocumentRole AS documentRole JOIN documentRole.documents AS document JOIN documentRole.role AS role where documentRole.active=true AND document.id =:documentId")
	public List<Long> getRolesByDocument(@Param("documentId")Long documentId);
	
	@Query("SELECT documentRole FROM DocumentRole AS documentRole JOIN documentRole.documents AS document JOIN documentRole.role AS role where document.id =:documentId")
	public List<DocumentRole> getDocumentRolesById(@Param("documentId") Long documentId);
	
	@Query("SELECT documentRole FROM DocumentRole AS documentRole JOIN documentRole.documents AS document JOIN documentRole.role AS role where document.id =:documentId AND role.id =:roleId")
	public DocumentRole  getDocumnetByIdRole(@Param("documentId") Long projectId,@Param("roleId")Long roleId);
}
