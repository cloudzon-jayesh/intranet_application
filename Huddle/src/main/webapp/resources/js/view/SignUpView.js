var SignUpView = Backbone.View.extend({

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
		"click #signUp_button" : "reset"
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
		// alert("button click");
		this.hideErrors();
		 var val = [];
	        $(':checkbox:checked').each(function(i){
	          val[i] = $(this).val();
	        });
		this.signUpModel = new SignUpModel({
			"userName" : $("#email").val(),
			"firstName" : $("#firstName").val(),
			"lastName" : $("#lastName").val(),
			"email" : $("#email").val(),
			"mobileNumber" : $("#mobileNumber").val(),
			"password" : $("#password").val(),
			"retypePassword" : $("#retypePassword").val(),
			"dob" :$("#b_date").val(),
			"joiningDate" : $("#j_date").val(),
			"redirectURL" : "http://localhost:8080/huddle/",
			"profilePic" : "profile-pic-img.png",
			"rolesId":val
		/* "terms" : $("#terms").val(), */
		});
		if (!this.signUpModel.isValid()) {
			this.showErrors(this.signUpModel.validationError);
		} else {
			this.signUpModel.save({}, {
				success: function(response) 
				{
					//var obj = JSON.stringify(response.message);
					var email = $("#email").val();
					//$("#error").css("display : block");
					//$("#error").html("<font color='#0055FF'>&quot;Register Suucessfully &quot;<font>");
					window.location = "imageUpload?id="+email;
				},
				error : function(signUpModel,response)
				{
					//alert(response.errors);
					//console.log('bjfbd');
					//var obj = $.parseJSON(response);
					//var obj1 = JSON.stringify(response);
					//console.log(obj1);
					//$("#error").css("display : block");
					$("#error").html("<font color='red'>&quot; Email Already Exists &quot;</font><br>");
				}

			});

			// $("#error").html("<font color='#0055FF'>&quot;Register
			// Suucessfully &quot;<font>");
		}
	},
	showErrors : function(errors) {
		_.each(errors, function(error) {
			var controlGroup = this.$('.' + error.name);
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