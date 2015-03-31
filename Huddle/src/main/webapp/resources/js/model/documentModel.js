var documentModel = Backbone.Model.extend(
{
	
	defaults : {
		"documentName" : '',
		"description" : '',
		"documentPath": '',
		"groupName" : ''
	},

	validate : function(attrs) {
		var errors = [];
		if (!attrs.documentName) {
			errors.push({
				name : 'documentName',
				message : '* Please enter Document Name.'
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