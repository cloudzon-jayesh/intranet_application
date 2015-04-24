var projectEditModel = Backbone.Model.extend(
{
	
	defaults : {
		"projectName" : '',
		"description" : '',
		"url" :'',
		"groupName":''
	},

	validate : function(attrs) {
		var errors = [];
		var urlVal =  /^(([\w]+:)?\/\/)?(([\d\w]|%[a-fA-f\d]{2,2})+(:([\d\w]|%[a-fA-f\d]{2,2})+)?@)?([\d\w][-\d\w]{0,253}[\d\w]\.)+[\w]{2,4}(:[\d]+)?(\/([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)*(\?(&?([-+_~.\d\w]|%[a-fA-f\d]{2,2})=?)*)?(#([-+_~.\d\w]|%[a-fA-f\d]{2,2})*)?$/;
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
		if (!attrs.url) {
			errors.push({
				name : 'url',
				message : '* Please enter URL.'
			});
		}
		else if (!urlVal.test(attrs.url)) {
			errors.push({
				name : 'url',
				message : '* Please enter valid URL.'
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