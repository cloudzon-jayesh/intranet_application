var PermissionView = Backbone.View
		.extend({
			
			el : $("#main-container"),
			initialize : function() {
				var r_perm="";
				var r_val;
				var w_perm="";
				var w_val;
				var d_perm="";
				var d_val;
			},
			render : function() {
				var template = _.template($("#group_template").html(), {});
				this.$el.html(template);
				this.getGroupPermission();
				this.getGroupData();
			},
			events : {
				"click #add_button" : "reset"
			},
			getGroupPermission : function()
			{
				
						$.ajax({
							url : 'rest/user/getGroupPermission.json',
							type : 'GET',
							success : function(data)
							{
								var len = data.length;
								for (var i = 0; i < len; i++) 
								{
									if(data[i].permission === "R")
									{
										r_val = data[i].id;
										r_perm = data[i].permission;
									}
									else if(data[i].permission === "W")
									{
										w_val = data[i].id;
										w_perm = data[i].permission;
									}
									else if(data[i].permission === "D")
									{
										d_val = data[i].id;
										d_perm = data[i].permission;
									}
								}
							}
						});
			},
			getGroupData : function()
			{
				$.ajax({
					url : 'rest/user/getUserRole.json',
					type : 'GET',
					success : function(data)
					{
						var len = data.length;
						var chk = "";
						var thead = $("<thead></thead>");
						var trh = $("<tr></tr>");
						var th0 = $("<th>No</th>");
						var th1 = $("<th>Group Name</th>");
						var th2 = $("<th>Permission</th>");
						var th3 = $("<th>Add</th>");
						trh.append(th0);
						trh.append(th1);
						trh.append(th2);
						trh.append(th3);
						thead.append(trh);
						$("#group_data").append(thead);
						var tBody = $("<tbody></tbody>");
						for (var i = 0; i < len; i++) 
						{
							var tr = $("<tr></tr>");
							var td0 = $("<td>" + data[i].id	+ "</td>");
							var td1 = $("<td>" + data[i].roleName + "</td>");
							var td2 = $("<td>" +
									"<input type='checkbox' value="+r_val+">&nbsp;"+r_perm+
									"&nbsp;<input type='checkbox' value="+w_val+">&nbsp;"+w_perm+
									"&nbsp;<input type='checkbox' value="+d_val+">&nbsp;"+d_perm
									+"</td>");
							var td3 = $("<td></td>");
							var button = $("<input type='button' value='Set Permission' class='add-btn' attr-name='"+ data[i].id + "'>");
							td3.append(button);
							tr.append(td0);
							tr.append(td1);
							tr.append(td2);
							tr.append(td3);
							tBody.append(tr);
							
							$("#group_data").append(tBody);
						}
						$('.add-btn').click(function(i) {
							var name = $(this).attr("attr-name");
							var val = [];
							 $(':checkbox:checked').each(function(i){
						          val[i] = $(this).val();
						        });
							alert(val);
							console.log(name);
							$.ajax({
								url : 'rest/user/addGroupPermission.json',
								type : 'POST',
								data : JSON.stringify(
									{	
										"id" : name,
										"permissionId" : val
									}),
									headers : 
									{
										'Accept' : 'application/json',
										'Content-Type' : 'application/json; charset=UTF-8'
									},
									success :function(data)
									{
										console.log("Success");
									},
									error : function(data) {
										console.log("Success");
									}
							});
						});
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
					},
					error : function(data) {
						console.log("Success");
					}
				});
			}
});