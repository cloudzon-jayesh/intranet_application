var EditSignUpModel = Backbone.Model.extend({
	url : 'user/editEmployeList.json',
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
	idAttribute : "email",
	validate : function(attrs) {
		//alert("validate click");
		
		var errors = [];
		var emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
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
		else if (!emailPattern.test(attrs.email)) {
			errors.push({
				name : 'email',
				message : '* Please enter valid Email Address.'
			});
		}
		if (!attrs.mobileNumber) {
			errors.push({
				name : 'mobileNumber',
				message : '* Please fill Contact No field.'
			});
		}
		else if (isNaN(attrs.mobileNumber)) {
			errors.push({
				name : 'mobileNumber',
				message : '* Please fill only Numbers'
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
				message : '* Please match Password and Retype password field.'
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
				name : 'joiningDate',
				message : '* Please select Joining Date field.'
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
		/*if (!attrs.profilePic) {
			errors.push({
				name : 'profilePic',
				message : 'Please fill profilePic field.'
			});
		}*/
		return errors.length > 0 ? errors : false;

	}
});