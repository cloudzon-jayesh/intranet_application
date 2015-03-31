var PermissionModel = Backbone.Model.extend({
	url : 'user/addActivityPermission.json',
	dafaults : {
		"groupId" : "",
		"activityId" : "",
		"permissionIds" :""
	}
});