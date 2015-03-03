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
			events:
				{
					"click #new_emp_btn" : "newEmpPage"
				},
				newEmpPage: function() {
					window.location = "signup";
				},
			getUserData : function() {
				$.ajax({
							url : 'rest/user/employeDetails.json',
							type : 'GET',
							parse : function(response) {
								console.log(response.Object);
							},
							success : function(data) {
								// console.log(data);
								var len = data.length;
								var txt;
								var thead = $("<thead></thead>");
								var trh = $("<tr></tr>");
								var th0 = $("<th>Edit</th>");
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
								trh.append(th6);
								thead.append(trh);
								$("#employee_data").append(thead);
								var tBody = $("<tbody></tbody>");
								for (var i = 0; i < len ; i++) {
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
									
									var tr = $("<tr></tr>");
									var td0 = $("<td></td>");
									var td1 = $("<td>" + data[i].id
											+ "</td>");
									var td2 = $("<td>" + data[i].firstName
											+ "</td>");
									var td3 = $("<td>" + data[i].lastName
											+ "</td>");
									var td4 = $("<td>" + data[i].email
											+ "</td>");
									var td5 = $("<td>" + myDob + "</td>");
									var td6 = $("<td>" + myJoiningDate
											+ "</td>");
									
									var button = $("<input type='button' value='Edit' class='edit_button'  attr-name='"+ data[i].email + "'>");
									td6.append(button);
									td0.append(button);
									tr.append(td0);
									tr.append(td1);
									tr.append(td2);
									tr.append(td3);
									tr.append(td4);
									tr.append(td5);
									tr.append(td6);
									tBody.append(tr);
								}
								
								$("#employee_data").append(tBody);
								$('.edit_button').click(function() {
									var name = $(this).attr("attr-name");
									console.log(name);
									window.location="edit_registration?id="+name;
								});
								$('#employee_data').dataTable({
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
								});
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
