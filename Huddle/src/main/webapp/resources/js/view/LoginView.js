var LoginView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
		//alert("call view");
		this.template = _.template($('#main_template').html());
		this.render();
	},
	render : function() {
		var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		this.$el.html(this.template());
	},
	events : {
		"click #login_button" : "reset"
	},
	reset : function() {
		//alert("button click");
		this.hideErrors();
		this.loginModel = new LoginModel({
			"userName" : $("#lUserName").val(),
			"password" : $("#lPassword").val()
		});
		
		if (!this.loginModel.isValid()) {
			this.showErrors(this.loginModel.validationError);
		} else {
			this.loginModel.fetch({
				type : "POST",
				url : "user/login.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.loginModel),
				success: function (response) {
				     //console.log("Found the User: " + response.get("access_token"));
				     window.location = "test";
				},
				error : function(loginModel,e) {
					var response = $.parseJSON(e.responseText);
					var obj = JSON.stringify(response.errorMessage);
					$("#error").html("<font color='red'>" + obj+ "</font>");
				}
			});
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