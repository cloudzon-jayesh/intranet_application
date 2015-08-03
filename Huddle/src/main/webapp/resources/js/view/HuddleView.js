var HuddleView = Backbone.View.extend({
	
	initialize : function() {
	},
	render : function(){
		$.ajax({
			url : 'user/getAllMeetings.json',
			type : 'POST',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify({
				"userName": $("#hidUser").val()
			}),
				success: function (data) {
					if(data.length === 0)
					{
						$("#meetingDiv").css("display","none");
					}
					else
					{
						$("#meetingDiv").css("display","block");
					}
				}
		});
		
		$.ajax({
			url : 'user/getAllProjects.json',
			type : 'POST',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify({
				"userName": $("#hidUser").val()
			}),
			success : function(data)
			{
				if(data.length === 0)
				{
					$("#projectDiv").css("display","none");
				}
				else
				{
					$("#projectDiv").css("display","block");
				}
			}
		});
		
		$.ajax({
			url : 'user/getAllDocuments.json',
			type : 'POST',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify({
				"userName": $("#hidUser").val()
			}),
			success : function(data)
			{
				if(data.length === 0)
				{
					$("#documentDiv").css("display","none");
				}
				else
				{
					$("#documentDiv").css("display","block");
				}
			}
		});
		
		$.ajax({
			url : 'user/getAllDiscussion.json',
			type : 'POST',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify({
				"userName": $("#hidUser").val()
			}),
			success : function(data)
			{
				if(data.length === 0)
				{
					$("#discussionDiv").css("display","none");
				}
				else
				{
					$("#discussionDiv").css("display","block");
				}
		}
	});
	},
	events:{
		"click #logOutBtn" : "reset"
	},
	reset : function()
	{
		//alert('h');
		Location.href = "login";
	}
});