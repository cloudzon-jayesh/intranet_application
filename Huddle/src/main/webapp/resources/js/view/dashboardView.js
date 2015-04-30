var dashboardView = Backbone.View.extend({
	el : $("#main-container"),
	initialize : function() {
	},
	render : function(){
		var self = this;
		 setInterval(function (){
			 self.getMeetingsData();
		 }, 10000);
		 this.getMeetingsData();
		 this.getEventsData();
		 this.getProjectsData();
		 this.getDocumentsData();
		 this.getDiscussionsData();
	},
	events:{
		"click #logOutBtn" : "reset"
	},
	reset : function()
	{
		//alert('h');
		Location.href = "login";
	},
	getMeetingsData : function(){
		$.ajax({
			url : 'user/getAllMeetings.json',
			type : 'POST',
			async: false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify({
				"userName": $("#hidUser").val()
			}),
				success: function (data) {
					$("#meetingDIV").html("");
					$("#notification").html("");
					var len = data.length;
					var todaysDate = new Date();
					var count = 0;
					var c = 0;
					if(len === 0)
					{
						$("#meetingDIV").remove();
					}
					else
					{
						//var div = $('<div class="block"></div>');
						var meetingDiv = $('<div class="scroll">'+
								   '<h2>Meetings</h2>'+
								   '</div>');
						var notificationDiv = $('<li class="noti"></li>');
						//div.append(meetingDiv);
						for (var i = (len-1); i >= 0; i--) 
						{
							var m_names = new Array("Jan", "Feb", "Mar", 
									"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
									"Octr", "Nov", "Dec");
							var mDate = new Date(data[i].dateAndTime);
							var curr_date = mDate.getDate();
							var curr_month = mDate.getMonth();
							var curr_year = mDate.getFullYear();
							var hours = mDate.getHours();
							var minutes = mDate.getMinutes();
							var meetingDate = curr_date + "-" + m_names[curr_month]+ "-" + curr_year +"  "+ hours + ":"+ minutes;
							var meetings = $('<h3>'+data[i].meetingName+'</h3>'+
									   '<p>'+meetingDate+'</p>');

							if(todaysDate <= mDate)
							{
								count++;
								
								var li = $('<a href="#" class="meetingText" attr-name="'+ data[i].id + '">'+data[i].meetingName+'</a>')
								$(".noti").addClass('errorText');
								notificationDiv.append(li);
								if (count < 4) { meetingDiv.append(meetings); }
							}
							
						}
						if(count == 0)
						{
							$("#meetingDIV").remove();
							$("#noti").hide();
						}
						else
						{
							$(".close-noti").text(count);
						}
						var aLink = $('<a href="setMeeting" class="more-btn">More</a>');
						
						$("#meetingDIV").append(meetingDiv);
						$("#meetingDIV").append(aLink );
						$("#notification").append(notificationDiv);
						//$(".dashboard-block").append(div);
						$("#noti").click(function(){
							$(".close-noti").removeClass("blink_me");
							
						});
						$(".meetingText").click(function(e){
							e.preventDefault();
							var no = $(this).attr("attr-name");
							var meetingName;
							var meetingDate;
							for (var i = (len-1); i >= 0; i--) 
							{
								var mDate = new Date(data[i].dateAndTime);
								var curr_date = mDate.getDate();
								var curr_month = mDate.getMonth();
								var curr_year = mDate.getFullYear();
								var hours = mDate.getHours();
								var minutes = mDate.getMinutes();
								
								if(data[i].id == no)
								{
									console.log(no);
									meetingName  = data[i].meetingName;
									meetingDate = curr_date + "-" + m_names[curr_month]+ "-" + curr_year +"  "+ hours + ":"+ minutes;
								}
							}
							var meetText = $("<h3>"+ meetingName +" is at </h3><h4> "+meetingDate+"</h4>")
							$("#meetingText").html(meetText);
							$('#meetingModel').foundation('reveal', 'open');
						});
					}
				}
		});
	},
	getEventsData : function(){
		$.ajax({
			url : 'user/getAllEvents.json',
			type : 'GET',
			async: false, 
			success : function(data)
			{
				var len = data.length;
				var todaysDate = new Date();
				var count = 0;
				if(len === 0)
				{
					$("#eventDIV").remove();
				}
				else
				{
					var eventsDiv = $('<div class="scroll">'+
							   '<h2>Events</h2>'+
							   '</div>');
					for (var i = (len-1); i >= 0; i--) 
					{
						var m_names = new Array("Jan", "Feb", "Mar", 
								"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
								"Octr", "Nov", "Dec");
						var eDate = new Date(data[i].date);
						var curr_date = eDate.getDate();
						var curr_month = eDate.getMonth();
						var curr_year = eDate.getFullYear();
						var eventDate = curr_date + "-" + m_names[curr_month]+ "-" + curr_year;
						
						var eTime = new Date(data[i].time);
						var minutes = eTime.getMinutes();
						var hours = eTime.getHours();
						var eventTime = hours+":"+minutes;	
						
						var events = $('<h3>'+data[i].eventName+'</h3>'+
						   '<p>'+eventDate+' '+eventTime+'</p>');
						if(todaysDate <= eDate)
						{
							eventsDiv.append(events);
							count++;
						}
						if (i === (len-3)) { break; }
					}
					if(count == 0)
					{
						$("#meetingDIV").remove();
					}
						
					var aLink = $('<a href="setEvent" class="more-btn">More</a>');
					$("#eventDIV").append(eventsDiv);
					$("#eventDIV").append(aLink );
				}
		}
		});
	},
	getProjectsData : function(){
		$.ajax({
			url : 'user/getAllProjects.json',
			type : 'POST',
			async: false,
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
				if(len === 0)
				{
					$("#projectDIV").remove();
				}
				else
				{
					var projectDiv = $('<div class="scroll">'+
							   '<h2>Projects</h2>'+
							   '</div>');
					for (var i = (len-1); i >= 0; i--) 
					{
						var projects = $('<h3>'+data[i].projectName+'</h3>'+
						   '<p>last modified at sep 2, 2015</p>');
						projectDiv.append(projects);
						if (i === (len-3)) { break; }
					}
					var aLink = $('<a href="setProject" class="more-btn">More</a>');
					$("#projectDIV").append(projectDiv);
					$("#projectDIV").append(aLink );
				}
			}
		});
	},
	getDocumentsData : function(){
		$.ajax({
			url : 'user/getAllDocuments.json',
			type : 'POST',
			async: false,
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
				if(len === 0)
				{
					$("#documentDIV").remove();
				}
				else
				{
					var documentDiv = $('<div class="scroll">'+
							   '<h2>Documents</h2>'+
							   '</div>');
					for (var i = (len-1); i >= 0; i--) 
					{
						var documents = $('<h3>'+data[i].documentName+'</h3>'+
						   '<p>last modified at sep 2, 2015</p>');
						documentDiv.append(documents);
						if (i === (len-3)) { break; }
					}
					var aLink = $('<a href="setDocument" class="more-btn">More</a>');
					$("#documentDIV").append(documentDiv);
					$("#documentDIV").append(aLink );
				}
			}
		});
	},
	getDiscussionsData : function(){
		$.ajax({
			url : 'user/getAllDiscussion.json',
			type : 'POST',
			async: false,
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
				if(len === 0)
				{
					$("#discussionDIV").remove();
				}
				else
				{
					var discussionDiv = $('<div class="scroll">'+
							   '<h2>Discussions</h2>'+
							   '</div>');
					var showChar = 50;
					var ellipsestext = "....";
					for (var i = (len-1); i >= 0; i--) 
					{
						var content = data[i].discussionTopic;
						 if(content.length > showChar) {
							 var c = content.substr(0, showChar);
					         var discussions = '<h3 title="'+data[i].discussionTopic+'">'+c + '<span>' + ellipsestext+ ' </span></h3><p></p>';
						 }
						/*var discussions = $('<h3>'+data[i].discussionTopic+'</h3>'+
						   '<p>last modified at sep 2, 2015</p>');
						discussionDiv.append(discussions);*/
						 discussionDiv.append(discussions);
						if (i === (len-3)) { break; }
					}
					var aLink = $('<a href="setDiscussion" class="more-btn">More</a>');
					$("#discussionDIV").append(discussionDiv);
					$("#discussionDIV").append(aLink );
				}
		}
	});
		
	}
});