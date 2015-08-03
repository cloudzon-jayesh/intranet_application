var meetingModel = Backbone.Model.extend(
{
	
	defaults : {
		"meetingName" : '',
		"description" : '',
		"dateAndTime": '',
		"groupName" : ''
	},

	validate : function(attrs) {
		var errors = [];
		if (!attrs.meetingName) {
			errors.push({
				name : 'meetingName',
				message : '* Please enter Meeting Name.'
			});
		}
		if (!attrs.description) {
			errors.push({
				name : 'description',
				message : '* Please enter Description.'
			});
		}
		if (!attrs.dateAndTime) {
			errors.push({
				name : 'date',
				message : '* Please enter date.'
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