var taskModel = Backbone.Model.extend(
{
	
	defaults : {
		"tasks" : '',
		"startDate" : '',
		"endDate" :''
	},

	validate : function(attrs) {
	//alert("validate click");
	
		var errors = [];
		if (!attrs.tasks) {
			errors.push({
				name : 'taskName',
				message : '* Please enter Task.'
			});
		}
		if (!attrs.startDate) {
			errors.push({
				name : 'startDate',
				message : '* Please Select Start Date.'
			});
		}
		if (!attrs.endDate) {
			errors.push({
				name : 'endDate',
				message : '* Please Select End Date.'
			});
		}
		
	
		return errors.length > 0 ? errors : false;
	
	}
});