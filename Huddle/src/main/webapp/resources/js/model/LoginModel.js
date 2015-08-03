var LoginModel = Backbone.Model.extend({
	/*
	 * defaults : { userName : '', password : '' },
	 */
	/*
	 * validate : function(attributes) { if (attributes.age < 0 &&
	 * attributes.name != "jayesh") { return "You can't be negative years old"; } },
	 * initialize : function() { alert("call model");
	 * 
	 * this.on("change:name", function(model) { var name = model.get("name"); //
	 * 'Stewie Griffin' alert("Changed my name to " + name); });
	 * 
	 * 
	 * this.bind("error", function(model, error) { alert(error); }); }
	 */

	defaults : {
		"userName" : '',
		"password" : ''
	},

	validate : function(attrs) {
		//alert("validate click");
		
		var errors = [];
		var emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
		if (!attrs.userName) {
			errors.push({
				name : 'lUserName',
				message : '* Please enter Email.'
			});
		}
		else if (!emailPattern.test(attrs.userName)) {
			errors.push({
				name : 'lUserName',
				message : '* Please enter valid Email Address.'
			});
		}
		if (!attrs.password) {
			errors.push({
				name : 'lPassword',
				message : '* Please enter Password.'
			});
		}
		

		return errors.length > 0 ? errors : false;

	}
});