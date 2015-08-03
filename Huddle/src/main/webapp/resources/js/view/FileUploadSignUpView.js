var FileUploadSignUpView =  Backbone.View.extend({
	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#main_template").html(), {});
		this.$el.html(template);
		
	},
	events : {
		"click #upload_button" : "profileUpload"
	},	
	profileUpload : function()
	{
		var data = new FormData();	
		data.append('fileInput',jQuery('#fileinput')[0].files);
		data.append('hemail',jQuery('#hemail')[0]);
		$.ajax({
			url : 'user/uploadProfile.json',
			type : "POST",
			data : data,
			dataType : "multipart/form-data",
			success : function(data)
			{
				window.location = "employee";
			},
			error : function(data)
			{
				alert("error");
			}
		});
	}
	
});