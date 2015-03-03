var EditEmployeeList = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		this.getEmployeeFromEmail();
	},
		
	getEmployeeFromEmail : function()
	{
		this.hideErrors();
		var url = (document.URL).split('=');
		var  email= url[1];

		$.ajax({
			url : 'rest/user/editEmployeList.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
				{
					"email" : email
				}),
			parse : function(response) {
				console.log(response.Object);
			},
			success: function (data) {
				var len = data.length;
				//for (var i = 0; i < len; i++) {
							
					var dob = new Date(data.dob);
					var curr_date = dob.getDate();
					var curr_month = dob.getMonth();
					var curr_year = dob.getFullYear();
					var myDob = curr_date + "/" + curr_month+ "/" + curr_year;

					var joiningDate = new Date(data.joiningDate);
					var curr_date1 = joiningDate.getDate();
					var curr_month1 = joiningDate.getMonth();
					var curr_year1 = joiningDate.getFullYear();
					var myJoiningDate = curr_date1 + "/" + curr_month1+ "/" + curr_year1;
					console.log(data.email);
					$('#firstName').val(data.firstName);
					$('#lastName').val(data.lastName);
					$('#mobileNumber').val(data.mobileNumber);
					$('#email').val(data.email);
					$('#password').val(data.password);
					$('#retypePassword').val(data.password);
					$('#b_date').val(myDob);
					$('#j_date').val(myJoiningDate);
					var roles = data.roles;
					//alert(roles.id);
					var getval;
					if (roles !== null) {
					for (var i = 0; i < roles.length; i++) {
						$("input:checkbox[name^='group']").each(function() {
							getval = $(this).val();
							if (getval == roles[i].id) {
								console.log(getval);
								$(this).attr('checked', true);
							} else {
								
							}
						});
					}
				}
					// $('#fileinput').val(data[i].profilePic);
				// }
			},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	showErrors : function(errors) {
		_.each(errors, function(error) {
			var controlGroup = this.$('.' + error.name);
			console.log("controlGroup"+controlGroup);
			controlGroup.addClass('error');
			controlGroup.find('.help-inline').text(error.message);
			
		}, this);
	},
	hideErrors : function() {
		this.$('.control-group').removeClass('error');
		this.$('.help-inline').text('');
	}
});