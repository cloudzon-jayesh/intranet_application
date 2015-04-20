var GroupView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {},
	render : function() {
		var template = _.template($("#group_template").html(), {});
		this.$el.html(template);
		this.getGroupData();
	},
	getGroupData : function()
	{
		$.ajax({
			url : 'user/getUserRole.json',
			type : 'GET',
			success : function(data)
			{
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th>Edit</th>");
				var th1 = $("<th>No</th>");
				var th2 = $("<th>Group Name</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				thead.append(trh);
				$("#group_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>");
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+data[i].roleName+"</td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tBody.append(tr);
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
				$("#group_data").append(tBody);
				
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					var editGroupView1 = new editGroupView({
						el : $("#editGroupModal"),
					});
					editGroupView1.render();
					$('#editGroupModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteGroup.json',
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
							var groupView = new GroupView();
							groupView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#group_data').dataTable({
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

var addGroup =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addGroupButton" : "addGroup",
	},
	render : function() {},
	addGroup : function()
	{	
		this.hideErrors();
		this.groupModel = new groupModel({
			"roleName" : $("#roleName").val()
		});
		
		if (!this.groupModel.isValid()) {
			this.showErrors(this.groupModel.validationError);
		} else {
			this.groupModel.fetch({
				type : "POST",
				url : "user/addGroup.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.groupModel),
				success: function (response) {
					console.log("Success");
					window.location = "setGroup";
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

var editGroupView = Backbone.View.extend({

	el : $("#editGroupModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getGroupFromId();
	},
	events : {
		"click #editGroupButton" : "editGroup"
	},
	getGroupFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editGroupList.json',
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
				$("#editRoleName").val(data.roleName)
			},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	editGroup : function() {
		this.hideErrors();
		this.groupModel = new groupModel({
			"id":$("#hidId").val(),
			"roleName" : $("#editRoleName").val()
		});
		
		if (!this.groupModel.isValid()) {
			this.showErrors(this.groupModel.validationError);
		} else {
			this.groupModel.fetch({
				type : "POST",
				url : "user/addGroup.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.groupModel),
				success: function (response) {
					console.log("Update Success");
					$('#editGroupModal').foundation('reveal', 'close');
					window.location = "setGroup";
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