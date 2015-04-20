
var eventView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#event_template").html(), {});
		this.$el.html(template);
		this.getEventData();
	},
	getEventData : function()
	{
		$.ajax({
			url : 'user/getAllEvents.json',
			type : 'GET',
			success : function(data)
			{
				console.log("per : "+$("#flag").val())
				var len = data.length;
				var thead = $("<thead></thead>");
				var trh = $("<tr></tr>");
				var th0 = $("<th class='col1'>Action</th>");
				var th1 = $("<th>No</th>");
				var th2 = $("<th>Event Name</th>");
				var th3 = $("<th>Date</th>");
				var th4 = $("<th>Time</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				trh.append(th4);
				thead.append(trh);
				$("#event_data").append(thead);
				var tBody = $("<tbody></tbody>");
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
					
					var tr = $("<tr></tr>");
					var td0 = $("<td class='col1'></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+ data[i].eventName +"</td>");
					var td3 = $("<td>"+ eventDate +"</td>");
					var td4 = $("<td>"+ eventTime +"</td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
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
					$("#event_data").append(tBody);
				}
				
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					var editEventView1 = new editEventView({
						el : $("#editEventModal"),
					});
					editEventView1.render();
					$('#editEventModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteEvents.json',
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
							eventView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#event_data').dataTable({
					responsive: true,
					 "searching": false,
					  	"order": [[ 1, "desc" ]],
					    "iDisplayLength": 5,
					    "columnDefs": [ { orderable: false, targets: [0] }],
					    "bAutoWidth": false,
					    "bLengthChange": false
				});
		        /*var bVis = table.fnSettings().aoColumns[0].bVisible;
		        table.fnSetColumnVis(0, bVis ? false : true);*/
			}
		});
			
	}
});

var addEvent =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addEventButton" : "addEvent",
		"click #addImageButton" : "addImage",
	},
	render : function() {
		$("#date").datepicker(
				{
					dateFormat: 'dd/mm/yy',
					minDate : 0,
					changeMonth: true,
		            changeYear: true,
		            yearRange: "+0:+10",
					onSelect : function(dateText, datePicker) 
					{
						console.log('onSelect', dateText);
						$(this).selectedDate = dateText;
					}
				});
		//$('#time').timeEntry({show24Hours: true});
		
		$('#time').blur(function() {
			var timeFormat = /^([0-9]{2})\:([0-9]{2})$/;
	        if (timeFormat.test(this.value)){
	        	$(this).siblings(".help-inline").html("");
	        }
	        else {
	        	$(this).siblings(".help-inline").html("Time Format should be like 24:60");
	        	$(this).siblings(".help-inline").addClass("errorText");
	        }
	    });
	},
	addImage : function(){
		
		var addItems = $(
				'<div class="control-group fileinput">'
				+'<label for="eventImage">Select Image</label>'
					+'<input type="file" class="imgGroup" name="fileinput" accept="image/*"'
					+'class="browser-select"><a href="#" class="remove_field errorText"><img src= "images/remove.png" style="width:20px; height:20px;"></a></div>'
				+'</div>	');
				$("#imageGroup").append(addItems);
				$("#imageGroup").on("click",".remove_field", function(e){ 
			        e.preventDefault(); 
			        $(this).parent('div').remove(); 
			    });
	},
	addEvent : function(e){
		this.hideErrors();
		this.eventModel = new eventModel({
			"eventName" :  $("#eventName").val(),
			"description" : $("#description").val(),
			"date": $("#date").val(),
			"time": $("#time").val(),
		});
		var data = new FormData();
        data.append('id', $("#hidId").val());
        data.append('eventName', $("#eventName").val());
        data.append('description', $("#description").val());
        data.append('date', $("#date").val());
        data.append('time', $("#time").val());
        $.each($("input[name^=fileinput]"), function(i, obj) {
	        $.each(obj.files,function(j,file){
	            data.append('fileinput',file);
	            console.log(file);
	        })
        });
		if (!this.eventModel.isValid()) {
			this.showErrors(this.eventModel.validationError);
			e.preventDefault();
		} else {
			$.ajax({
				data:data,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
				url : "user/addEvent.json",
				
				success: function (response) {
					console.log("Insert Success");
					$('#addEventModal').foundation('reveal', 'close');
					window.location = "setEvent";
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

var editEventView = Backbone.View.extend({

	el : $("#editEventModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getEventFromId();
		$("#editDate").datepicker(
				{
					dateFormat: 'dd/mm/yy',
					minDate : 0,
					changeMonth: true,
		            changeYear: true,
		            yearRange: "+0:+10",
					onSelect : function(dateText, datePicker) 
					{
						console.log('onSelect', dateText);
						$(this).selectedDate = dateText;
					}
				});
		$('#editTime').blur(function() {
			var timeFormat = /^([0-9]{2})\:([0-9]{2})$/;
	        if (timeFormat.test(this.value)){
	        	$(this).siblings(".help-inline").html("");
	        }
	        else {
	        	$(this).siblings(".help-inline").html("Time Format should be like 24:60");
	        	$(this).siblings(".help-inline").addClass("errorText");
	        }
	    });
	},
	events : {
		"click #editEventButton" : "editEvent",
		"click #editAddImageButton" : "addImage",
	},
	addImage : function(){
			
			var addItems = $(
					'<div class="control-group images">'
					+'<label for="projectImage">Select Image</label>'
						+'<input type="file" class="imgGroup" name="images" accept="image/*"'
						+'class="browser-select"><a href="#" class="remove_field errorText"><img src= "images/remove.png" style="width:20px; height:20px;"></a></div>'
					+'</div>	');
					$("#editImageGroup").append(addItems);
					$("#editImageGroup").on("click",".remove_field", function(e){ 
				        e.preventDefault(); 
				        $(this).parent('div').remove(); 
				    });
		},
	getEventFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editEventList.json',
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
				var eDate = new Date(data.date);
				var curr_date = eDate.getDate();
				var curr_month = eDate.getMonth()+1;
				var curr_year = eDate.getFullYear();
				console.log(eDate);
				console.log(curr_month);
				var eventDate = curr_date + "/" + curr_month + "/" + curr_year;
				
				var eTime = new Date(data.time);
				var minutes = eTime.getMinutes();
				var hours = eTime.getHours();
				var eventTime = hours+":"+minutes;
				
				$("#editEventName").val(data.eventName),
				$("#editDescription").val(data.description),
				$("#editDate").val(eventDate),
				$("#editTime").val(eventTime)
				
				var images = data.eventImagesDTOs;
				if(images !=null)
				{
					for(var i=0;i<images.length;i++)
					{
						chk ='<div class="input-main imgDiv">'
							+'<input name="editImagesChecked" checked="checked" type="checkbox" value="'+images[i].id+'" class="imgGroup" disabled="disabled">'
							+'<span>&nbsp;<img src="images/events/'+images[i].images+'" alt='+images[i].images+'  style="height:25%; width:25%;margin:5px;"></span>'
						+'<a href="#" class="remove_image"><img src= "images/remove.png" style="width:20px; height:20px;"></a></div>';
						$("#editImageGroup").append(chk);
					}
					$(".imgDiv").on("click",".remove_image", function(e){ 
				        e.preventDefault(); 
				        $(this).parent('div').remove(); 
				    });
				}
				
			},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	editEvent : function() {
		this.hideErrors();
		this.editEventModel = new editEventModel({
			"id":$("#hidId").val(),
			"eventName" :  $("#editEventName").val(),
			"description" : $("#editDescription").val(),
			"date": $("#editDate").val(),
			"time": $("#editTime").val(),
		});
		var imgVal = [];
        $('.imgGroup:checkbox:checked').each(function(i){
        	imgVal[i] = $(this).val();
        });
        var data = new FormData();
        data.append('id', $("#hidId").val());
        data.append('eventName', $("#editEventName").val());
        data.append('description', $("#editDescription").val());
        data.append('date', $("#editDate").val());
        data.append('time', $("#editTime").val());
        data.append('editImagesChecked', imgVal);
		$.each($("input[name^=images]"), function(i, obj) {
		        $.each(obj.files,function(j,file){
		            data.append('images',file);
		            console.log(file);
		        })
		});
		if (!this.editEventModel.isValid()) {
			this.showErrors(this.editEventModel.validationError);
		} else {
			$.ajax({
				data:data,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
				url : "user/editEvent.json",
				
				success: function (response) {
					console.log("Update Success");
					$('#editEventModal').foundation('reveal', 'close');
					window.location = "setEvent";
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