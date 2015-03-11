var PermissionModel = Backbone.Model.extend({
	url : 'rest/user/addActivityPermission.json',
	dafaults : {
		"groupId" : "",
		"activityId" : "",
		"permissionIds" :""
	}
	
});