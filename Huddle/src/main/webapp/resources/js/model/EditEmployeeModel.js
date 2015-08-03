var EditEmployeeModel = Backbone.Model.extend({
	url : 'user/editEmployee.json',
	defaults : {
		
		'userName' : '',
		'firstName' : '',
		'lastName' : '',
		'mobileNumber' : '',
		'email' : '',
		'password' : '',
		'retypePassword' : '',
		'dob' : '',
		'joiningDate' : '',
		'profilePic' : '',
		'redirectURL' : '',
		'groupName' : ''
		/*'isAgree' : ''*/
	},

	validate : function(attrs) {
		//alert("validate click");
		
		var errors = [];
		if (!attrs.firstName) {
			errors.push({
				name : 'firstName',
				message : '* Please fill First Name field.'
			});
		}
		if (!attrs.lastName) {
			errors.push({
				name : 'lastName',
				message : '* Please fill Last Name field.'
			});
		}
		if (!attrs.email) {
			errors.push({
				name : 'email',
				message : '* Please fill Email field.'
			});
		}
		if (!attrs.mobileNumber) {
			errors.push({
				name : 'mobileNumber',
				message : '* Please fill Contact No field.'
			});
		}
		if (!attrs.password) {
			errors.push({
				name : 'password',
				message : '* Please fill Password field.'
			});
		}
		if (attrs.password.length  < 8) {
			errors.push({
				name : 'password',
				message : '* Please enter 8 character Password.'
			});
		}
		if (!attrs.retypePassword) {
			errors.push({
				name : 'retypePassword',
				message : '* Please fill Retype Password field.'
			});
		}
		if (attrs.password != attrs.retypePassword) {
			errors.push({
				name : 'retypePassword',
				message : '* Please match Password and Retype Password field.'
			});
		}
		if (!attrs.dob) {
			errors.push({
				name : 'dob',
				message : '* Please select Date of Birth field.'
			});
		}
		if (!attrs.joiningDate) {
			errors.push({
				name : '* joiningDate',
				message : 'Please select Joining Date field.'
			});
		}
		/*if (!attrs.profilePic) {
			errors.push({
				name : 'profilePic',
				message : 'Please fill profilePic field.'
			});
		}*/
		
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