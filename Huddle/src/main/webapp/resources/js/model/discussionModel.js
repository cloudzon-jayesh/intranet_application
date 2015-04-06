var discussionModel = Backbone.Model.extend(
{
	
	defaults : {
		"discussionTopic" : '',
		"groupName" : ''
	},

	validate : function(attrs) {
		var errors = [];
		if (!attrs.discussionTopic) {
			errors.push({
				name : 'discussionTopic',
				message : '* Please enter Discussion Topic.'
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