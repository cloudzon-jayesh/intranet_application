
var documentView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#document_template").html(), {});
		this.$el.html(template);
		this.getDocumentData();
	},
	getDocumentData : function()
	{
		var userName = $("hidUser").val();
		$.ajax({
			url : 'user/getAllDocuments.json',
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
				var th2 = $("<th>Document Name</th>");
				var th3 = $("<th>Document</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				thead.append(trh);
				$("#document_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+ data[i].documentName +"</td>");
					var td3 = $("<td><a target='_blank' href= 'documents/"+ data[i].documentPath +"'>"+data[i].documentPath+"</a></td>");
					//var td3 = $("<td>"+ data[i].documentPath +"</td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tBody.append(tr);
					$("#document_data").append(tBody);
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
					var editDocumentView1 = new editDocumentView({
						el : $("#editDocumentModal"),
					});
					editDocumentView1.render();
					$('#editDocumentModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteDocument.json',
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
							documentView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#document_data').dataTable({
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

var addDocument =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
	},
	events : {
		"click #addDocumentButton" : "addDocument",
	},
	render : function() {
		this.getUserRole();
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
	addDocument : function(e){
		this.hideErrors();
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
        var flag = false;
        var docext = $('#documentPath').val().split('.').pop().toLowerCase();
		if($("#documentPath").val() != '' )
		{
			flag = true;
			
			if($.inArray(docext, ['doc','docx','pdf','txt','rtf']) == -1) {
				console.log("document file typr error : "+docext);
		  		$(".documentPath").find('.help-inline').text("Document File Type must be doc or docx or pdf or txt or rtf").addClass('errorText');
			}
			else if($("#documentPath")[0].files[0].size > 20971520)
		  	{
				console.log("document file size error");
		  		$(".documentPath").find('.help-inline').text("Document File Size must be less than 20mb.").addClass('errorText');
		  	}
		  	else
			{ 
				flag = false;
				console.log("document file error: "+flag);
				$(".documentPath").find('.help-inline').text("").addClass('errorText');
			}
		}
        var data = new FormData();
		 data.append('documentName', $("#documentName").val());
		 data.append('description', $("#description").val());
		 data.append('documentPath', $("#documentPath")[0].files[0]);
		 data.append('group', val);
		this.documentModel = new documentModel({
			"documentName" :  $("#documentName").val(),
			"description" : $("#description").val(),
			"rolesId":val
		});
		if (!this.documentModel.isValid() || flag === true) {
			this.showErrors(this.documentModel.validationError);
		} 
		else
		{
			$.ajax({
				type : "POST",
				url : "user/addDocument.json",
				data : data,
				cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
				success: function (response) {
					console.log("Add Success");
					$('#DocumentModal').foundation('reveal', 'close');
					window.location = "setDocument";
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

var editDocumentView = Backbone.View.extend({

	el : $("#editDocumentModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getUserRole();
		this.getDocumentFromId();
	},
	events : {
		"click #editDocumentButton" : "editDocument",
		"click #changeDocument" : "changeDocument",
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
	changeDocument : function()
	{
		/*var projectInput = $('<div><input type="file" name="document" id="editDocument"' 
			+'accept=".txt,.doc,.docx,.rtf,.pdf" class="browser-select">'*/
			var remove = $('<a href="#" id="remove2">'
			+'<img src= "images/remove.png" style="width:20px; height:20px;"></a></div>');
		var addLink = $('<a class="radius right" id="changeDocument">Change Document</a>');
		$("#docDiv").html("");
		$("#docDiv").append(remove);
		$("#editDocumentPath").css("display","block");
		$("#docDiv").on("click","#remove2", function(e){ 
	        e.preventDefault();
	        $("#docDiv").html(addLink);
	        $("#editDocumentPath").css("display","none");
	        $("#remove2").remove();
	    });
	},
	getDocumentFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editDocumentList.json',
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
	
				$("#editDocumentName").val(data.documentName),
				$("#editDescription").val(data.description),
				$("#documentLink").val(data.documentPath)
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
	editDocument : function() {
		this.hideErrors();
		var flag = false;
		var docext = $('#editDocumentPath').val().split('.').pop().toLowerCase();
		if($("#editDocumentPath").val() != '' )
		{
			flag = true;
			
			if($.inArray(docext, ['doc','docx','pdf','txt','rtf']) == -1) {
				console.log("document file typr error : "+docext);
		  		$(".documentPath").find('.help-inline').text("Document File Type must be doc or docx or pdf or txt or rtf").addClass('errorText');
			}
			else if($("#editDocumentPath")[0].files[0].size > 20971520)
		  	{
				console.log("document file size error");
		  		$(".documentPath").find('.help-inline').text("Document File Size must be less than 20mb.").addClass('errorText');
		  	}
		  	else
			{ 
				flag = false;
				console.log("document file error: "+flag);
				$(".documentPath").find('.help-inline').text("").addClass('errorText');
			}
		}
		var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
        var data = new FormData();
        data.append("id",$("#hidId").val());
		data.append('documentName', $("#editDocumentName").val());
		data.append('description', $("#editDescription").val());
		data.append('documentPath', $("#editDocumentPath")[0].files[0]);
		data.append('group', val);
		this.documentModel = new documentModel({
			"id":$("#hidId").val(),
			"documentName" : $("#editDocumentName").val(),
			"description" : $("#editDescription").val(),
			"rolesId":val
		});
		
		if (!this.documentModel.isValid() || flag === true) {
			this.showErrors(this.documentModel.validationError);
		} else {
			$.ajax({
				type : "POST",
				url : "user/editDocument.json",
				data : data,
				cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
				success: function (response) {
					console.log("Update Success");
					$('#editDocumentModal').foundation('reveal', 'close');
					window.location = "setDocument";
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