var editEventModel = Backbone.Model.extend(
{
	
	defaults : {
		"eventName" : '',
		"description" : '',
		"date":'',
		"time":'',
	},

	validate : function(attrs) {
		var timeFormat = /^([0-9]{2})\:([0-9]{2})$/;
		var errors = [];
		if (!attrs.eventName) {
			errors.push({
				name : 'eventName',
				message : '* Please enter Event Name.'
			});
		}
		if (!attrs.description) {
			errors.push({
				name : 'description',
				message : '* Please enter Description.'
			});
		}
		if (!attrs.date) {
			errors.push({
				name : 'date',
				message : '* Please enter date.'
			});
		}	
		if (!attrs.time) {
			errors.push({
				name : 'time',
				message : '* Please enter time.'
			});
		}	
		/*if (!timeFormat.match(attrs.time)) {
			errors.push({
				name : 'time',
				message : '* Please enter valid time.'
			});
		}*/
		 return errors.length > 0 ? errors : false;
	
	}
});