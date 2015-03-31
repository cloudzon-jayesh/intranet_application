var projectEditModel = Backbone.Model.extend(
{
	
	defaults : {
		"projectName" : '',
		"description" : '',
		"url":'',
		"document":'',
		"video":'',
		"groupName":''
	},

	validate : function(attrs) {
		var errors = [];
		if (!attrs.projectName) {
			errors.push({
				name : 'projectName',
				message : '* Please enter Project Name.'
			});
		}
		if (!attrs.description) {
			errors.push({
				name : 'description',
				message : '* Please enter Description.'
			});
		}
		if($(".group:checkbox:checked").length > 0){
			 
		   }else 
		   {
			   errors.push({
					name : 'groupName',
					message : '* Please select Minimum 1 Group.'
			   });
		   }
		 return errors.length > 0 ? errors : false;
	
	}
});