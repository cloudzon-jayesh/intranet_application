var EditEmployeeView = Backbone.View.extend({

	el : $("#thirdModal"),
	initialize : function() {
		// alert("call view");
		/*this.template = _.template($('#main_template').html());
		this.render();*/
	},
	render : function() {
		/*var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		this.$el.html(this.template());*/
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getUserRole();
		this.getEmployeeFromEmail();
	$("#editB_date").datepicker(
		{
			dateFormat: 'dd/mm/yy',
			maxDate : 0,
			changeMonth: true,
            changeYear: true,
            yearRange: "-100:-18",
			onSelect : function(dateText, datePicker) 
			{
				console.log('onSelect', dateText);
				$(this).selectedDate = dateText;
			}
		});
	$("#editJ_date").datepicker(
		{
			dateFormat: 'dd/mm/yy',
			maxDate : 0,
			changeMonth: true,
			changeYear: true,
			yearRange: "-50:+-10",
			onSelect : function(dateText, datePicker) 
			{
				console.log('onSelect', dateText);
				$(this).selectedDate = dateText;
			}
	});
		$("#editMobileNumber").keydown(function(event) 
			{
			// Allow only backspace and delete
			if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 ||(event.keyCode > 95 && event.keyCode < 106)) 
			{
				// let it happen, don't do anything
			}
			else 
			{
				// Ensure that it is a number and stop the keypress
				if (event.keyCode < 48 || event.keyCode > 57 ) 
				{
					event.preventDefault();	
				}	
			}
		});
	},
	events : {
		"click #update_button" : "reset"
	},
	getUserRole : function()
	{
		$.ajax({
			url : 'user/getUserRole.json',
			type : 'GET',
			success : function(data)
			{
				var len = data.length;
				var chk = "";
				for (var i = 0; i < len; i++) 
				{
					chk ='<div class="input-main">'
						+'<input name="editGroupName" type="checkbox" value="'+data[i].id+'" class="group">'
						+'<span>&nbsp;'+data[i].roleName+'</span>'
					+'</div>'
					$("#editGroupName").append(chk);
				}
				
			}
		});
	},
	getEmployeeFromEmail : function()
	{
		this.hideErrors();
		//var url = (document.URL).split('=');
		//var  email= url[1];
		var email = $("#hemail").val();
		console.log("email ...."+email);
		$.ajax({
			url : 'user/editEmployeList.json',
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
					$('#editFirstName').val(data.firstName);
					$('#editLastName').val(data.lastName);
					$('#editMobileNumber').val(data.mobileNumber);
					$('#editEmail').val(data.email);
					$('#editPassword').val(data.password);
					$('#editRetypePassword').val(data.password);
					$('#editB_date').val(myDob);
					$('#editJ_date').val(myJoiningDate);
					var roles = data.roles;
					//alert(roles.id);
					var getval;
					if (roles !== null) {
					for (var i = 0; i < roles.length; i++) {
						$("input:checkbox[name^='editGroup']").each(function() {
							getval = $(this).val();
							if (getval == roles[i].id) {
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
	
	reset : function() {
		 //alert("button click");
		this.hideErrors();
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
		this.editEmployeeModel = new EditEmployeeModel({
			"userName" : $("#editEmail").val(),
			"firstName" : $("#editFirstName").val(),
			"lastName" : $("#editLastName").val(),
			"email" : $("#editEmail").val(),
			"mobileNumber" : $("#editMobileNumber").val(),
			"password" : $("#editPassword").val(),
			"retypePassword" : $("#editRetypePassword").val(),
			"dob" : $("#editB_date").val(),
			"joiningDate" : $("#editJ_date").val(),
			"redirectURL" : "http://localhost:8080/huddle/",
			//"profilePic" : "profilePicture/profile-pic-img.png"
			"rolesId":val
		/* "terms" : $("#terms").val(), */
		});
		if (!this.editEmployeeModel.isValid()) {
			this.showErrors(this.editEmployeeModel.validationError);
		} else {
			this.editEmployeeModel.save({}, {
				success: function(response) 
				{
					//var obj = JSON.stringify(response.message);
					//$("#error").html("<font color='#0055FF'>&quot;Update Suucessfully<br>" + obj +" &quot;<font>");
					console.log("update successfully");
					
					$('#thirdModal').foundation('reveal', 'close');
					window.location="employee";
				},
				
				
				error : function(editEmployeeModel,response)
				{
					//alert(response.errors);
					//console.log('bjfbd');
					//var obj = $.parseJSON(response);
					//var obj1 = JSON.stringify(response);
					//console.log(obj1);
					//$("#error").css("display : block");
					$("#error").html("<font color='red'>&quot; Error &quot;</font><br>");
				}

			});

			// $("#error").html("<font color='#0055FF'>&quot;Register
			// Suucessfully &quot;<font>");
		}
	},
	showErrors : function(errors) {
		_.each(errors, function(error) {
			var controlGroup = this.$('.' + error.name);
			console.log("controlGroup"+controlGroup);
			//controlGroup.addClass('error');
			controlGroup.find('.help-inline').text(error.message);
			controlGroup.find('.help-inline').addClass('errorText');
		}, this);
	},
	hideErrors : function() {
		this.$('.control-group').removeClass('error');
		this.$('.help-inline').text('');
	}

});