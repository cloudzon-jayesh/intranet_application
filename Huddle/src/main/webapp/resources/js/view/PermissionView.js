var PermissionView = Backbone.View
		.extend({
			
			el : $("#main-container"),
			initialize : function() {
			},
			render : function() {
				var r_perm="";
				var r_val;
				var w_perm="";
				var w_val;
				var d_perm="";
				var d_val;
				var template = _.template($("#group_template").html(), {});
				this.$el.html(template);
				this.getGroupData();
				this.getAvtivityData();
			},
			events :
			{
				"click #roles" : "reset"
			},
			reset : function()
			{
				$("input:checkbox[name^='group']").each(function() {
					$(this).attr('checked',false);
				});
				$('#msgBox').html("");
			},
			getGroupData : function()
			{
				$.ajax({
					url : 'user/getUserRole.json',
					type : 'GET',
					success : function(data)
					{
						var len = data.length;
						var select = $("<select id='roles' style='width : 250px;'></selcet>");
						for (var i = 0; i < len; i++) 
						{
							select.append("<option value="+data[i].id+">" + data[i].roleName + "</option>");
						}
						$("#group_data").append(select);
					
					}
				});	
			
			},
			getAvtivityData : function()
			{
				$.ajax({
					url : 'user/getGroupPermission.json',
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
				
				$.ajax({
					url : 'user/getActivity.json',
					type : 'GET',
					success : function(data)
					{
						var thead = $("<thead></thead>");
						var trh = $("<tr style='border: 1px solid #ccc; text-align: center;'></tr>");
						var th0 = $("<th style='border: 1px solid #ccc; text-align: center;'>No</th>");
						var th1 = $("<th style='border: 1px solid #ccc; text-align: center;'> Screen Name</th>");
						var th2 = $("<th style='border: 1px solid #ccc; text-align: center;'>Permission</th>");
						trh.append(th0);
						trh.append(th1);
						trh.append(th2);
						thead.append(trh); 
						$("#event_data").append(thead);
						var len = data.length;
						var tBody = $("<tbody></tbody>");
						for (var i = 0; i < len; i++) 
						{
							var tr = $("<tr class='trGroup' style='border: 1px solid #ccc; text-align: center;'></tr>");
							var td0 = $("<td style='border: 1px solid #ccc; text-align: center;'>" + (i+1)	+ "</td>");
							var td1 = $("<td style='border: 1px solid #ccc; text-align: center;'>"+data[i].activityName+"</td>");
							var td2 = $("<td id='list' style='border: 1px solid #ccc; text-align: center;'>" +
									"<input type='checkbox'  name='group'  value="+r_val+" attr-name='"+ data[i].id + "'>&nbsp;"+r_perm+
									"&nbsp;<input type='checkbox' name='group'  value="+w_val+" attr-name='"+ data[i].id + "'>&nbsp;"+w_perm+
									"&nbsp;<input type='checkbox' name='group'  value="+d_val+" attr-name='"+ data[i].id + "'>&nbsp;"+d_perm
									+"</td>");
							tr.append(td0);
							tr.append(td1);
							tr.append(td2);
							tBody.append(tr);
							$("#event_data").append(tBody);
						}
						
						$('#roles').change(function(){
							$.ajax({
								url : 'user/getPermissionRoleActivity.json',
								type: 'GET',
								success : function(data)
								{
									var l = data.length;
									var values;
									for(var i=0;i<l;i++)
									{
										group = $("#roles option:selected").val();
											$('.trGroup').each(function()
											{
												values = data[i].permissionId;
												var getval;
												var name;
												$("input:checkbox[name^='group']").each(function() 
												{
													getval = $(this).val();
													name = $(this).attr("attr-name");
													if (getval == values && group == data[i].roleId  && name == data[i].activityId) 
													{
														$(this).prop('checked',true);
														//console.log("per : " + getval +"    , grp : " + group +"act  :  "+name);
														//($('#list [value="'+values+'"]').prop('checked',true));
													}
												});
											});
										}
								},
								error : function()
								{
									console.log('error');
								}
								
							});
						});
						
						var permissionModel = new PermissionModel();
						$('#addButton').click(function() 
						{
							group = $("#roles option:selected").val();
							$('.trGroup').each(function(i)
							{
								var val = [];
								var name = "";
								$(this).find(':checkbox:checked').each(function(j)
								{
									name = $(this).attr("attr-name");
									val[j] = $(this).val();
								});
								this.permissionModel = new PermissionModel
								({
									  "groupId" : group, 
					        		  "activityId" : name,
					        		  "permissionIds" : val
								});
								this.permissionModel.save({}, 
								{
									success: function(response) 
									{
										//$('#try').css('background-color:#E0ECF1; color:#fff;')
										$('#msgBox').html("&nbsp; &quot; Permission is set &quot;");
										console.log("1 record inserted");
									},
									error : function(data)
									{
										console.log("error");
									}
								});
							});
						});
					}
				});	
			}								
});