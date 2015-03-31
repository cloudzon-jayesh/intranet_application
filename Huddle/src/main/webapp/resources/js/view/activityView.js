
var activityView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
		var r_perm = "";
		var r_val;
		var w_perm = "";
		var w_val;
		var d_perm = "";
		var d_val;
		// var idGroup = new Array();
	},
	render : function() {
		var template = _.template($("#activity_template").html(), {});
		this.$el.html(template);
		this.getActivityData();
	},
	getActivityData : function()
	{
		$.ajax({
			url : 'user/getActivity.json',
			type : 'GET',
			success : function(data)
			{
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th>Action</th>");
				var th1 = $("<th>No</th>");
				var th2 = $("<th>Activity Name</th>");
				var th3 = $("<th>Activity Link</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				thead.append(trh);
				$("#activity_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+data[i].activityName+"</td>");
					var td3 = $("<td>"+data[i].activityLink+"</td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tBody.append(tr);
					
				}
				$("#activity_data").append(tBody);
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					var editActivityView1 = new editActivityView({
						el : $("#editActivityModal"),
					});
					editActivityView1.render();
					$('#editActivityModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteActivity.json',
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
							activityView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#activity_data').dataTable({
					responsive: true,
					 "searching": false,
					  	"order": [[ 0, "desc" ]],
					    "iDisplayLength": 5,
					    "columnDefs": [ { orderable: false, targets: [0] }],
					    "bAutoWidth": false,
					    "bLengthChange": false
				});
			}
		});
			
	}
});

var addActivity =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addActivityButton" : "addActivity",
	},
	render : function() {},
	addActivity : function()
	{	
		this.hideErrors();
		this.activityModel = new activityModel({
			"activityName" : $("#activityName").val(),
			"activityLink" : $("#activityLink").val()
		});
		
		if (!this.activityModel.isValid()) {
			this.showErrors(this.activityModel.validationError);
		} else {
			this.activityModel.fetch({
				type : "POST",
				url : "user/addActivity.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.activityModel),
				success: function (response) {
					console.log("Success");
					window.location = "setActivity";
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

var editActivityView = Backbone.View.extend({

	el : $("#editActivityModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getActivityFromId();
	},
	events : {
		"click #editActivityButton" : "editAvtivity"
	},
	getActivityFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editActivityList.json',
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
				var len = data.length;
					$("#editActivityName").val(data.activityName),
					$("#editActivityLink").val(data.activityLink)
			},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	editAvtivity : function() {
		this.hideErrors();
		this.activityModel = new activityModel({
			"id":$("#hidId").val(),
			"activityName" : $("#editActivityName").val(),
			"activityLink" : $("#editActivityLink").val()
		});
		
		if (!this.activityModel.isValid()) {
			this.showErrors(this.activityModel.validationError);
		} else {
			this.activityModel.fetch({
				type : "POST",
				url : "user/addActivity.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.activityModel),
				success: function (response) {
					console.log("Update Success");
					$('#editActivityModal').foundation('reveal', 'close');
					window.location = "setActivity";
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