
var meetingView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#meeting_template").html(), {});
		this.$el.html(template);
		this.getMeetingData();
	},
	getMeetingData : function()
	{
		var userName = $("hidUser").val();
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
			success : function(data)
			{
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th>Action</th>");
				var th1 = $("<th>No</th>");
				var th2 = $("<th>Meeting Name</th>");
				var th3 = $("<th>Date And Time</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				thead.append(trh);
				$("#meeting_data").append(thead);
				var tBody = $("<tbody></tbody>");
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
					
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+ data[i].meetingName +"</td>");
					var td3 = $("<td>"+ meetingDate +"</td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tBody.append(tr);
					$("#meeting_data").append(tBody);
					if($("#flag").val().indexOf("R") >= 0)
					{
						th0.hide();
						td0.hide();
						button1.hide();
						button2.hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						th0.show();
						td0.show();
						button1.show();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						th0.show();
						td0.show();
						button2.show();
					}
				}
				
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					var editMeetingView1 = new editMeetingView({
						el : $("#editMeetingModal"),
					});
					editMeetingView1.render();
					$('#editMeetingModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteMeetings.json',
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
							meetingView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#meeting_data').dataTable({
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

var addMeeting =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addMeetingButton" : "addMeeting",
	},
	render : function() {
		$('#date').datetimepicker({
			formatTime:'H:i',
			formatDate:'d.m.Y',
			format : 'd/m/Y H:i',
			minDate : 0,
			step:10
		});
		/*$('#date').fdatetimepicker({
			format: 'dd/mm/yyyy hh:ii'
			});
*/		this.getUserRole();
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
	addMeeting : function(e){
		this.hideErrors();
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
		this.meetingModel = new meetingModel({
			"meetingName" :  $("#meetingName").val(),
			"description" : $("#description").val(),
			"dateAndTime": $("#date").val(),
			"rolesId":val
		});
		if (!this.meetingModel.isValid()) {
			this.showErrors(this.meetingModel.validationError);
		} 
		else
		{
			$.ajax({
				type : "POST",
				url : "user/addMeeting.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.meetingModel),
				success: function (response) {
					console.log("Add Success");
					$('#MeetingModal').foundation('reveal', 'close');
					window.location = "setMeeting";
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

var editMeetingView = Backbone.View.extend({

	el : $("#editMeetingModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getUserRole();
		this.getMeetingFromId();
		$('#editDate').datetimepicker({
			formatTime:'H:i',
			formatDate:'d.m.Y',
			format : 'd/m/Y H:i',
			minDate : 0,
			step:10
		});
		/*$('#editDate').fdatetimepicker({
			format: 'dd/mm/yyyy hh:ii'
			});*/
	},
	events : {
		"click #editMeetingButton" : "editMeeting"
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
						+'<input name="editGroup" type="checkbox" value="'+data[i].id+'" class="group">'
						+'<span>&nbsp;'+data[i].roleName+'</span>'
					+'</div>'
					$("#editGroupName").append(chk);
				}
				
			}
		});
	},
	getMeetingFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editMeetingList.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
				{
					"id" : id
				}),
			parse : function(response) {
				console.log(response.Object);
			},
			success: function (data) {
				var tDate = (data.dateAndTime).split(".");
				var mDate = new Date((tDate[0]).replace(/-/g,"/"));
				var curr_date = mDate.getDate();
				var curr_month = mDate.getMonth()+1;
				var curr_year = mDate.getFullYear();
				var hours = mDate.getHours();
				var minutes = mDate.getMinutes();
				var meetingDate = curr_date + "/" + curr_month+ "/" + curr_year +"  "+ hours + ":"+ minutes;
								
				$("#editMeetingName").val(data.meetingName),
				$("#editDescription").val(data.description),
				$("#editDate").val(meetingDate)
				var roles = data.rolesId;
					var getval;
					if (roles !== null) {
					for (var i = 0; i < roles.length; i++) {
						$("input:checkbox[name^='editGroup']").each(function() {
							getval = $(this).val();
							if (getval == roles[i]) {
								$(this).attr('checked', true);
								console.log("checked");
							} else {
								
							}
						});
					}
				}
			},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	editMeeting : function() {
		this.hideErrors();
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
		this.meetingModel = new meetingModel({
			"id":$("#hidId").val(),
			"meetingName" : $("#editMeetingName").val(),
			"description" : $("#editDescription").val(),
			"dateAndTime": $("#editDate").val(),
			"rolesId":val
		});
		
		if (!this.meetingModel.isValid()) {
			this.showErrors(this.meetingModel.validationError);
		} else {
			this.meetingModel.fetch({
				type : "POST",
				url : "user/editMeetings.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.meetingModel),
				success: function (response) {
					console.log("Update Success");
					$('#editMeetingModal').foundation('reveal', 'close');
					window.location = "setMeeting";
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
			console.log(error.name);
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