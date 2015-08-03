var EmployeeView = Backbone.View
		.extend({
			el : $("#main-container"),
			initialize : function() {
			},
			render : function() {
				var template = _.template($("#employee_template").html(), {});
				this.$el.html(template);
				this.getUserData();
			},
			getUserData : function() {
				$.ajax({
							url : 'user/employeDetails.json',
							type : 'GET',
							parse : function(response) {
								console.log(response.Object);
							},
							success : function(data) {
								console.log($("#flag").val());
								var len = data.length;
								var txt;
								/*var thead = $("<thead></thead>");
								var trh = $("<tr></tr>");
								var th0 = $("<th class='editButton'>Edit</th>");
								var th1 = $("<th>ID</th>");
								var th2 = $("<th>First Name</th>");
								var th3 = $("<th>Last Name</th>");
								var th4 = $("<th>Email</th>");
								var th5 = $("<th>Date of Birth</th>");
								var th6 = $("<th>Joining Date</th>>");
								
								trh.append(th0);
								trh.append(th1);
								trh.append(th2);
								trh.append(th3);
								trh.append(th4);
								trh.append(th5);
								trh.append(th6);*/
								//thead.append(trh);
								//$("#employee_data").append(thead);
								var tBody = $("<div></div>");
								for (var i = 0; i <len ; i++) {
									var m_names = new Array("Jan", "Feb", "Mar", 
											"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
											"Octr", "Nov", "Dec");
									var dob = new Date(data[i].dob);
									var curr_date = dob.getDate();
									var curr_month = dob.getMonth();
									var curr_year = dob.getFullYear();
									var myDob = curr_date + "-" + m_names[curr_month]+ "-" + curr_year;

									var joiningDate = new Date(data[i].joiningDate);
									var curr_date1 = joiningDate.getDate();
									var curr_month1 = joiningDate.getMonth();
									var curr_year1 = joiningDate.getFullYear();
									var myJoiningDate = curr_date1 + "-" + m_names[curr_month1]+ "-" + curr_year1;
									
									var imageDiv = $('<div class="imgBlock"></div>');
									var images = $('<img src="images/profilePicture/'+ data[i].profilePic+'" alt="Profile"  >');
									var name = $("<h6><b>Name : </b>" + data[i].firstName + " " + data[i].lastName +"</h6>");
									var email = $("<h6><b>Email : </b>" + data[i].email + "</h6>");
									var dob = $("<h6><b>Date of Birth : </b>" + myDob + "</h6>");
									/*var tr = $("<tr></tr>");
									var td0 = $("<td class='editButton' align=center></td>");
									var td1 = $("<td>" + (i+1)//data[i].id
											+ "</td>");
									var td2 = $("<td>" + data[i].firstName
											+ "</td>");
									var td3 = $("<td>" + data[i].lastName
											+ "</td>");
									var td4 = $("<td>" + data[i].email
											+ "</td>");
									var td5 = $("<td>" + myDob + "</td>");
									var td6 = $("<td>" + myJoiningDate
											+ "</td>");*/
									var button =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].email + "'><img src= 'images/edit.png' style='width:15px; height:15px;float:left;'></a>");
									//var button = $("<input type='button' style='background-image: url('images/editar.png');width:25px; height:20px;' class='edit_button'  attr-name='"+ data[i].email + "'>");
									/*td0.append(button);
									tr.append(td0);
									tr.append(td1);
									tr.append(td2);
									tr.append(td3);
									tr.append(td4);
									tr.append(td5);
									tr.append(td6);*/
									//tBody.append(tr);
									imageDiv.append(button);
									imageDiv.append(images);
									imageDiv.append(name);
									imageDiv.append(email);
									imageDiv.append(dob);
									tBody.append(imageDiv);
									if($("#flag").val().indexOf("R") >= 0)
									{
										//th0.hide();
										//td0.hide();
										button.hide();
									}
									if($("#flag").val().indexOf("W") >= 0)
									{
										//th0.show();
										//td0.show();
										button.show();
									}
									if($("#flag").val().indexOf("D") >= 0)
									{
									}
								}
								
								$("#employee_data").append(tBody);
								$('.edit_button').click(function() {
									var name = $(this).attr("attr-name");
									console.log(name);
									//window.location="edit_registration?id="+name;
									$("#hemail").val(name);
									var editEmployeeView = new EditEmployeeView({
										el : $("#thirdModal"),
									});
									
									$('#thirdModal').foundation('reveal', 'open');
								
									
									editEmployeeView.render();
								});
								/*var table = $('#employee_data').dataTable({
									responsive: true,
									 "searching": true,
									    //"ordering": false,
									  	"order": [[ 1, "desc" ]],
									    "iDisplayLength": 5,
									    //"bFilter": true,
									    columnDefs: [ { orderable: false, targets: [0] }],
									    "bAutoWidth": false,
									    "bLengthChange": false
									  //"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]]
								});*/
								/*$('#employee_data').paging({
									limit: 5,
									//number_of_items : 6,
									perpage : 5,
									rowDisplayStyle: 'block',
									activePage: 0,
									rows: []
									});*/
								
							}
							
						});
			}

		});
