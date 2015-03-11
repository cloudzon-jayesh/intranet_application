var GroupView = Backbone.View.extend({

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
		var template = _.template($("#group_template").html(), {});
		this.$el.html(template);
		this.getGroupData();
	},
	events : {
		"click #add_button" : "reset"
	},
	getGroupData : function()
	{
		$.ajax({
			url : 'rest/user/getUserRole.json',
			type : 'GET',
			success : function(data)
			{
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th>No</th>");
				var th1 = $("<th>Group Name</th>");
				trh.append(th0);
				trh.append(th1);
				thead.append(trh);
				$("#group_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td>" + (i+1)	+ "</td>");
					var td1 = $("<td>"+data[i].roleName+"</td>");
					tr.append(td0);
					tr.append(td1);
					tBody.append(tr);
					$("#group_data").append(tBody);
				}
			}
		});
			
	},
	reset : function()
	{
		$.ajax({
			url : 'rest/user/addGroup.json',
			type : 'POST',
			data : JSON.stringify({	"roleName" : $("#groupName").val()}),
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
				console.log("Success");
			}
		});
	}
});