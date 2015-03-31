var activityModel = Backbone.Model.extend(
{
	
	defaults : {
		"activityName" : '',
		"activityLink" : ''
	},

	validate : function(attrs) {
	//alert("validate click");
	
		var errors = [];
		if (!attrs.activityName) {
			errors.push({
				name : 'activityName',
				message : '* Please enter Activity Name.'
			});
		}
		if (!attrs.activityLink) {
			errors.push({
				name : 'activityLink',
				message : '* Please enter Activity Link.'
			});
		}
		
	
		return errors.length > 0 ? errors : false;
	
	}
});