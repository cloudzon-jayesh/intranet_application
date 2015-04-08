
var projectView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#project_template").html(), {});
		this.$el.html(template);
		this.getProjectData();
	},
	getProjectData : function()
	{
		$.ajax({
			url : 'user/getAllProjects.json',
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
				var th2 = $("<th>Project Name</th>");
				var th3 = $("<th>Description</th>");
				var th4 = $("<th>URL</th>");
				var th5 = $("<th>Project</th>");
				var th6 = $("<th>Project Document</th>");
				var th7 = $("<th>Project Video</th>");
				trh.append(th0);
				trh.append(th1);
				trh.append(th2);
				trh.append(th3);
				trh.append(th4);
				trh.append(th5);
				trh.append(th6);
				trh.append(th7);
				thead.append(trh);
				$("#project_data").append(thead);
				var tBody = $("<tbody></tbody>");
				for (var i = (len-1); i >= 0; i--) 
				{
					var tr = $("<tr></tr>");
					var td0 = $("<td></td>")
					var td1 = $("<td>" + (i+1)	+ "</td>");
					var td2 = $("<td>"+ data[i].projectName +"</td>");
					var td3 = $("<td>"+ data[i].description +"</td>");
					var td4 = $("<td>"+ data[i].url +"</td>");
					var td5 = $("<td><a target='_blank' href= 'projects/project/"+ data[i].projectPath +"'>"+data[i].projectPath+"</a></td>");
					var td6 = $("<td><a target='_blank' href= 'projects/document/"+ data[i].document +"'>"+data[i].document+"</a></td>");
					var td7 = $("<td><a target='_blank' href= 'projects/videos/"+ data[i].video +"'>"+data[i].video+"</a></td>");
					var button1 =$("<a href=# class='edit_button' title='edit' attr-name='"+ data[i].id + "'><img src= 'images/edit.png' style='width:25px; height:25px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:25px; height:25px;'></a>");
					td0.append(button1);
					td0.append(button2);
					tr.append(td0);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
					tr.append(td5);
					tr.append(td6);
					tr.append(td7);
					tBody.append(tr);
					$("#project_data").append(tBody);
					if($("#flag").val().indexOf("R") >= 0)
					{
						console.log("read");
						th0.hide();
						td0.hide();
						button1.hide();
						button2.hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						console.log("wr");
						th0.show();
						td0.show();
						button1.show();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						console.log("del");
						th0.show();
						td0.show();
						button2.show();
					}
				}
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					$("#projectId").val(name);
					var editProjectView1 = new editProjectView({
						el : $("#editProjectModal"),
					});
					editProjectView1.render();
					$('#editProjectModal').foundation('reveal', 'open');
				});
				$('.delete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/deleteProject.json',
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
							projectView.render();
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				var table = $('#project_data').dataTable({
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

var addProject =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
		
	},
	events : {
		"click #addProjectButton" : "addProject",
		"click #addImageButton" : "addImage",
	},
	render : function() {
		this.getUserRole();
	},
	addImage : function(){
		
		var addItems = $(
				'<div class="control-group images">'
				+'<label for="projectImage">Select Image</label>'
					+'<input type="file" class="imgGroup" name="images" accept="image/*"'
					+'class="browser-select"><a href="#" class="remove_field errorText"><img src= "images/remove.png" style="width:20px; height:20px;"></a></div>'
				+'</div>	');
				$("#imageGroup").append(addItems);
				$("#imageGroup").on("click",".remove_field", function(e){ 
			        e.preventDefault(); 
			        $(this).parent('div').remove(); 
			    });
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
	addProject : function(e){
		this.hideErrors();
		var flag1 = false;
		var flag2 = false;
		var flag3 = false;
		var urlext = $('#projectPath').val().split('.').pop().toLowerCase();
		var docext = $('#document').val().split('.').pop().toLowerCase();
		if($("#projectPath").val() != '')
		{
				flag1 = true;
			  	console.log("project file error : "+flag1);
			  	if($.inArray(urlext, ['rar','zip','war']) == -1) {
			  		$(".projectPath").find('.help-inline').text("Project File Type must be rar or war or zip").addClass('errorText');
				}
			  	else if($("#projectPath")[0].files[0].size > 20971520)
			  	{
			  		$(".projectPath").find('.help-inline').text("Project File Size must be less than 20mb.").addClass('errorText');
			  	}
			  	else
				{ 
					console.log("eeeee  :: "+$("#projectPath").val());
					flag1 = false;
					$(".projectPath").find('.help-inline').text("").addClass('errorText');
				}
		}
		
		
		if($("#document").val() != '' )
		{
			flag2 = true;
			
			if($.inArray(docext, ['doc','docx','pdf','txt','rtf']) == -1) {
				console.log("document file typr error : "+urlext);
		  		$(".document").find('.help-inline').text("Document File Type must be doc or docx or pdf or txt or rtf").addClass('errorText');
			}
			else if($("#document")[0].files[0].size > 10485760)
		  	{
				console.log("document file size error");
		  		$(".document").find('.help-inline').text("Document File Size must be less than 10mb.").addClass('errorText');
		  	}
		  	else
			{ 
				flag2 = false;
				console.log("document file error: "+flag2);
				$(".document").find('.help-inline').text("").addClass('errorText');
			}
		}
		
		if($("#video").val() != '' && $("#video")[0].files[0].size > 20971520)
		{
			flag3 = true;
			console.log("video file error");
			$(".video").find('.help-inline').text("Video File Size must be less than 20mb.").addClass('errorText');
		}
		else
		{ 
			flag3 = false;
			$(".video").find('.help-inline').text("").addClass('errorText');
		}
		this.projectModel = new projectModel({
			"projectName" :  $("#projectName").val(),
			"description" : $("#description").val(),
			"url" : $("#url").val()
		});
		if (!this.projectModel.isValid() || flag1 === true  || flag2 === true  || flag3 === true)   {
			this.showErrors(this.projectModel.validationError);
			e.preventDefault();
			/*$("#projectForm").submit(function(){
				return false;
			});*/
		} else {
			//$("#projectForm").submit();
			console.log("Submiited");
			 var project = $('#projectPath')[0].files[0];
			 var document = $("#document")[0].files[0];
			 var video = $("#video")[0].files[0];
			 var val = [];
		        $(':checkbox:checked').each(function(i){
		          val[i] = $(this).val();
		        });
			 var data = new FormData();
			 data.append('projectName', $("#projectName").val());
			 data.append('description', $("#description").val());
			 data.append('url', $("#url").val());
			 data.append('projectPath', project);
			 data.append('document', document);
			 data.append('video', video);
			 data.append('group', val);
			 $.each($("input[name^=images]"), function(i, obj) {
			        $.each(obj.files,function(j,file){
			            data.append('images',file);
			            console.log(file);
			        })
			});
		   
		 	 $.ajax({
				    url: 'user/addProject.json',
				    data:data,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    mimeType: "multipart/form-data",
				    //data :data,//this.projectModel,//JSON.stringify(this.projectModel),
				    success: function(data){
				    	window.location = "setProject";
				    	console.log("success");
				    },
				    error: function(data){
				    	console.log("error");
				    }
				  }); 
			
		}
	},
	showErrors : function(errors) {
		_.each(errors, function(error) {
			var controlGroup = this.$('.' + error.name);
			//controlGroup.addClass('error');
			//console.log(error);
			controlGroup.find('.help-inline').text(error.message);
			controlGroup.find('.help-inline').addClass('errorText');
		}, this);
	},
	hideErrors : function() {
		this.$('.control-group').removeClass('error');
		this.$('.help-inline').text('');
	}
});

var editProjectView = Backbone.View.extend({

	el : $("#editProjectModal"),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#edit_template").html(), {});
		this.$el.html(template);
		this.getUserRole();
		this.getProjectFromId();
		$("#projectId").val($("#hidId").val());
	},
	events : {
		"click #editProjectButton" : "editProject",
		"click #changeProject" : "changeProject",
		"click #changeDocument" : "changeDocument",
		"click #changeVideo" : "changeVideo",
		"click #editAddImageButton" : "addImage",
	},
	changeProject : function()
	{
		/*var projectInput = $('<div><input type="file" name="url" id="editUrl"' 
		+'accept=".war,.rar,.zip" class="browser-select">');*/
			var remove = $('<a href="#" id="remove1">'
			+'<img src= "images/remove.png" style="width:20px; height:20px;"></a></div>');
		var addLink = $('<a class="radius right" id="changeProject">Change Project</a>');
		$("#prjDiv").html("");
		$("#prjDiv").append(remove);
		$("#editProjectPath").css("display","block");
		$("#prjDiv").on("click","#remove1", function(e){ 
	        e.preventDefault();
	        $("#prjDiv").html(addLink);
	        $("#editProjectPath").css("display","none");
	        $("#remove1").remove();
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
		$("#editDocument").css("display","block");
		$("#docDiv").on("click","#remove2", function(e){ 
	        e.preventDefault();
	        $("#docDiv").html(addLink);
	        $("#editDocument").css("display","none");
	        $("#remove2").remove();
	    });
	},
	changeVideo : function()
	{
		/*var projectInput = $('<div><input type="file" name="video" id="editVideo"' 
			+'accept="video/*" class="browser-select">'*/
			var remove = $('<a href="#"  id="remove3">'
			+'<img src= "images/remove.png" style="width:20px; height:20px;"></a></div>');
		var addLink = $('<a class="radius right" id="changeVideo">Change Video</a>');
		$("#videoDiv").html("");
		$("#videoDiv").append(remove);
		$("#editVideo").css("display","block");
		$("#videoDiv").on("click","#remove3", function(e){ 
	        e.preventDefault();
	        $("#videoDiv").html(addLink);
	        $("#editVideo").css("display","none");
	        $("#remove3").remove();
	    });
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
						+'<input name="groupName" type="checkbox" value="'+data[i].id+'" class="group">'
						+'<span>&nbsp;'+data[i].roleName+'</span>'
					+'</div>'
					$("#editGroupName").append(chk);
				}
				
			}
		});
	},
	getProjectFromId : function()
	{
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/editProjectList.json',
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
				$("#editProjectName").val(data.projectName);
				$("#editDescription").val(data.description);
				$("#editUrl").val(data.url);
				$("#projectLink").val(data.projectPath);
				$("#documentLink").val(data.document);
				$("#videoLink").val(data.video);
				var images = data.projectImagesDTO;
				var imgaes = "";
				for(var i=0;i<images.length;i++)
				{
					chk ='<div class="input-main imgDiv">'
						+'<input name="editImagesChecked" checked="checked" type="checkbox" value="'+images[i].id+'" class="imgGroup" disabled="disabled">'
						+'<span>&nbsp;<img src="projects/images/'+images[i].images+'" alt='+images[i].images+'  style="height:25%; width:25%;margin:5px;"></span>'
					+'<a href="#" class="remove_image"><img src= "images/remove.png" style="width:20px; height:20px;"></a></div>';
					$("#editImageGroup").append(chk);
				}
				$(".imgDiv").on("click",".remove_image", function(e){ 
			        e.preventDefault(); 
			        $(this).parent('div').remove(); 
			    });
				var roles = data.rolesId;
					var getval;
					if (roles !== null) {
					for (var i = 0; i < roles.length; i++) {
						$("input:checkbox[name^='groupName']").each(function() {
							getval = $(this).val();
							if (getval == roles[i]) {
								$(this).attr('checked', true);
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
	editProject : function(e) {
		this.hideErrors();
		var flag1 = false;
		var flag2 = false;
		var flag3 = false;
		var urlext = $('#editProjectPath').val().split('.').pop().toLowerCase();
		var docext = $('#editDocument').val().split('.').pop().toLowerCase();
		if($("#editProjectPath").val() != '')
		{
				flag1 = true;
			  	console.log("project file error : "+flag1);
			  	if($.inArray(urlext, ['rar','zip','war']) == -1) {
			  		$(".projectPath").find('.help-inline').text("Project File Type must be rar or war or zip").addClass('errorText');
				}
			  	else if($("#editProjectPath")[0].files[0].size > 20971520)
			  	{
			  		$(".projectPath").find('.help-inline').text("Project File Size must be less than 20mb.").addClass('errorText');
			  	}
			  	else
				{ 
					console.log("eeeee  :: "+$("#editProjectPath").val());
					flag1 = false;
					$(".projectPath").find('.help-inline').text("").addClass('errorText');
				}
		}
		/*else
		{
			flag1 = true;
			$(".projectPath").find('.help-inline').text("* Please Select Project.").addClass('errorText');
		}*/
		
		
		if($("#editDocument").val() != '' )
		{
			flag2 = true;
			
			if($.inArray(docext, ['doc','docx','pdf','txt','rtf']) == -1) {
				console.log("document file typr error : "+docext);
		  		$(".document").find('.help-inline').text("Document File Type must be doc or docx or pdf or txt or rtf").addClass('errorText');
			}
			else if($("#editDocument")[0].files[0].size > 10485760)
		  	{
				console.log("document file size error");
		  		$(".document").find('.help-inline').text("Document File Size must be less than 10mb.").addClass('errorText');
		  	}
		  	else
			{ 
				flag2 = false;
				console.log("document file error: "+flag2);
				$(".document").find('.help-inline').text("").addClass('errorText');
			}
		}
		/*else
		{
			flag2 = true;
			$(".document").find('.help-inline').text("* Please Select Document.").addClass('errorText');
		}*/
		
		if($("#editVideo").val() != '')
		{
			flag3 = true;
			if($("#editVideo")[0].files[0].size > 20971520)
			{
				console.log("video file error");
				$(".video").find('.help-inline').text("Video File Size must be less than 20mb.").addClass('errorText');
			}
			else
			{
				flag3 = false;
				$(".video").find('.help-inline').text("").addClass('errorText');
			}
			
		}
		/*else
		{ 
			flag3 = true;
			$(".video").find('.help-inline').text("* Please Select Video.").addClass('errorText');
		}*/
		this.projectEditModel = new projectEditModel({
			"projectName" :  $("#editProjectName").val(),
			"description" : $("#editDescription").val(),
			"url" : $("#editUrl").val()
		});
		if (!this.projectEditModel.isValid() || flag1 === true  || flag2 === true  || flag3 === true)   {
			this.showErrors(this.projectEditModel.validationError);
			e.preventDefault();
		} else {
			
			 var project = $('#editProjectPath')[0].files[0];
			 var document = $("#editDocument")[0].files[0];
			 var video = $("#editVideo")[0].files[0];
			 var val = [];
		        $('.group:checkbox:checked').each(function(i){
		          val[i] = $(this).val();
		        });
		     var imgVal = [];
		        $('.imgGroup:checkbox:checked').each(function(i){
		        	imgVal[i] = $(this).val();
		        });
			 var data = new FormData();
			 if($('#editProjectPath').val() != '')
			 {
				 data.append('projectPath', project);
			 }
			 else
			 {
				 data.append('projectPath', null);
			 }
			 if($("#editDocument").val() != '')
			 {
				 data.append('document', document);
			 }
			 else
			 {
				 data.append('document', null);
			 }
			 if($("#editVideo").val() != '')
			 {
				 data.append('video', video);
			 }
			 else
			 {
				 data.append('video', null);
			 }
			 data.append('projectId', $("#projectId").val());
			 data.append('projectName', $("#editProjectName").val());
			 data.append('description', $("#editDescription").val());
			 data.append('url', $("#editUrl").val());
			 data.append('editImagesChecked', imgVal);
			 data.append('editGroupName', val);
			 $.each($("input[name^=images]"), function(i, obj) {
			        $.each(obj.files,function(j,file){
			            data.append('images',file);
			            console.log(file);
			        })
			});
		   
		 	 $.ajax({
				    url: 'user/editProject.json',
				    data:data,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    mimeType: "multipart/form-data",
				    success: function(data){
				    	window.location = "setProject";
				    	console.log("success");
				    },
				    error: function(data){
				    	console.log("error");
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