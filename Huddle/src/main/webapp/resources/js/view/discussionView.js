
var discussionView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#discussion_template").html(), {});
		this.$el.html(template);
		this.getDiscussionData();
		
		
	},
	getDiscussionData : function()
	{
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
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th>Action</th>");
				var th1 = $("<th>No</th>");
				var th2 = $("<th>Discussion Name</th>");
				var th3 = $("<th>View</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				thead.append(trh);
				$("#discussion_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+ data[i].discussionTopic +"</td>");
					var td3 = $("<td></td>");
					var comment = $('<a class="comment_button" attr-name="'+ data[i].id + '">View</a>');
					var button =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button);
					td3.append(comment);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tBody.append(tr);
					$("#discussion_data").append(tBody);
					if($("#flag").val().indexOf("R") >= 0)
					{
						th0.hide();
						td0.hide();
						button.hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						th0.hide();
						td0.hide();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						th0.show();
						td0.show();
						button.show();
					}
				}
				
				$('.comment_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					var commentDiscussionView1 = new commentDiscussionView();
					commentDiscussionView1.render();
					$('#commentModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$("#hidId").val(name);
					$.ajax({
						url : 'user/deleteDiscussion.json',
						type : 'POST',
						data : JSON.stringify({	"id" : name}),
						headers : 
							{
								'Accept' : 'application/json',
								'Content-Type' : 'application/json; charset=UTF-8'
							},
						success :function(data)
						{
							console.log("Success");
							discussionView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#discussion_data').dataTable({
					responsive: true,
					 "searching": false,
					  	"order": [[ 1, "desc" ]],
					    "iDisplayLength": 5,
					    "columnDefs": [ { orderable: false, targets: [0] }],
					    "bAutoWidth": false,
					    "bLengthChange": false
				});
			}
		});
			
	}
});

var addDiscussion =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addDiscussionButton" : "addDiscussion",
	},
	render : function() {
		this.getUserRole();
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
						+'<input name="group" type="checkbox" value="'+data[i].id+'" class="group">'
						+'<span>&nbsp;'+data[i].roleName+'</span>'
					+'</div>'
					$("#groupName").append(chk);
				}
				
			}
		});
	},
	addDiscussion : function(e){
		this.hideErrors();
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
		this.discussionModel = new discussionModel({
			"userName": $("#hidUser").val(),
			"discussionTopic" :  $("#discussionTopic").val(),
			"rolesId":val
		});
		if (!this.discussionModel.isValid()) {
			this.showErrors(this.discussionModel.validationError);
		} 
		else
		{
			$.ajax({
				type : "POST",
				url : "user/addDiscussion.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.discussionModel),
				success: function (response) {
					console.log("Add Success");
					$('#addDiscussionModal').foundation('reveal', 'close');
					window.location = "setDiscussion";
				},
				error : function(e) {
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

var commentDiscussionView = Backbone.View.extend({

	el : $("#commentModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#comment_template").html(), {});
		this.$el.html(template);
		this.getDiscussionFromId();
	},
	getDiscussionFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/getDiscuusion.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
				{
					"id" : id
				}),
			success: function (data) {
				var discussion = $('<div class="first-comment clearfix">'+
						'<div class="container clearfix">'+
							'<img src="images/profilePicture/'+data.profilePic+'" alt="Profile Pic">'+
							'<div class="profile-one">'+
							'<h2 class="profile-name-one">'+data.userName+'</h2>'+
							'<p class="profile-comment-one">'+data.disscussionTopic+'</p>'+
							'</div>'+
						'</div>'+
					'</div>'+
						'<div class="clearfix"></div>');
				$("#commentDiv").append(discussion);
				var comments = data.commentDTO;
				if(comments != null)
				{
					var len = comments.length;
					for(var i =0; i<len; i++)
					{
						var comment = $('<div class="second-comment clearfix">'+
								'<div class="container clearfix">'+
								'<div class="clearfix"></div>'+
								'<img src="images/profilePicture/'+comments[i].profilePic+'" alt="Profile Pic">'+
								'<div class="profile-two">'+
									'<h2 class="profile-name-two">'+comments[i].userName+'</h2>'+
									'<p class="profile-comment-two">'+comments[i].comment+'</p>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="clearfix"></div>');
						$("#commentDiv").append(comment);
					}
				}
				var addCommentBox = $('<div class="third-comment clearfix">'+
						'<div class="container clearfix">'+
							'<img src="images/profilePicture/profile-pic-img.png" alt="Profile Pic">'+
							'<div class="profile-third">'+
							'<textarea name="comment" id ="comment" placeholder="Add Comment here..." class="profile-comment-third"></textarea>'+
							'<a href="#" class="add_field">Add Comment</a>'+
							'</div>'+
						'</div>'+
					'</div>'+
						'<div class="clearfix"></div>');
				$("#commentDiv").append(addCommentBox);
				$("#commentDiv").on("click",".add_field", function(e){ 
			        e.preventDefault(); 
			        if( $.trim($("#comment").val()).length > 0 && $("#comment").val() != null)
			        {
			        	$.ajax({
							url : 'user/addComment.json',
							type : "POST",
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json; charset=UTF-8'
							},
							data : JSON.stringify(
								{
									"userName": $("#hidUser").val(),
									"id" : $("#hidId").val(),
									"comment" : $("#comment").val()
								}),
							success: function (response) {
								console.log("done");
								var commentDiscussionView2 = new commentDiscussionView();
								commentDiscussionView2.render();
								$('#commentModal').foundation('reveal', 'open');
							},
							error : function(response)
							{
								console.log("error come");
							}
			        	});
			        }
			        else
			        {
			        	$("#comment").css("border-color","red");
			        }
			    });
				/*$('#comment').bind('keypress', function(e) {
					if (e.keyCode == 13 && e.shiftKey)
				    {
						$('#comment').append("\n");
				        e.preventDefault();
				    }
					else if(e.keyCode==13){
						if($("#comment").val() != '')
				        {
					    	$.ajax({
								url : 'user/addComment.json',
								type : "POST",
								headers : {
									'Accept' : 'application/json',
									'Content-Type' : 'application/json; charset=UTF-8'
								},
								data : JSON.stringify(
									{
										"userName": $("#hidUser").val(),
										"id" : $("#hidId").val(),
										"comment" : $("#comment").val()
									}),
								success: function (response) {
									console.log("done");
									var commentDiscussionView2 = new commentDiscussionView();
									commentDiscussionView2.render();
									$('#commentModal').foundation('reveal', 'open');
								},
								error : function(response)
								{
									console.log("error come");
								}
				        	});
				        }
				        else
				        {
				        	$("#comment").css("border-color","red");
				        }
					   }
					});*/
				},
		error : function(response)
		{
			console.log("error come");
		}
		});
	}
});