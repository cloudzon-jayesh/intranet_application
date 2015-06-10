var groupModel = Backbone.Model.extend(
{
	
	defaults : {
		"roleName" : '',
	},

	validate : function(attrs) {
	//alert("validate click");
	
		var errors = [];
		if (!attrs.roleName) {
			errors.push({
				name : 'roleName',
				message : '* Please enter Group Name.'
			});
		}
		return errors.length > 0 ? errors : false;
	
	}
});