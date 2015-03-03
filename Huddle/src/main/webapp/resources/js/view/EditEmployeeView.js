var EditEmployeeView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
		// alert("call view");
		/*this.template = _.template($('#main_template').html());
		this.render();*/
	},
	render : function() {
		/*var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		this.$el.html(this.template());*/
		var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		this.getUserRole();
	},
	events : {
		"click #update_button" : "reset"
	},
	getUserRole : function()
	{
		$.ajax({
			url : 'rest/user/getUserRole.json',
			type : 'GET',
			success : function(data)
			{
				var len = data.length;
				var chk = "";
				for (var i = 0; i < len; i++) 
				{
					chk ='<div class="input-main">'
						+'<input name="group" type="checkbox" value="'+data[i].id+'" class="group">'
						+'<span>'+data[i].roleName+'</span>'
					+'</div>'
					$("#groupName").append(chk);
				}
				
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
			"userName" : $("#email").val(),
			"firstName" : $("#firstName").val(),
			"lastName" : $("#lastName").val(),
			"email" : $("#email").val(),
			"mobileNumber" : $("#mobileNumber").val(),
			"password" : $("#password").val(),
			"retypePassword" : $("#retypePassword").val(),
			"dob" : $("#b_date").val(),
			"joiningDate" : $("#j_date").val(),
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