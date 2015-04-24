
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
				for(var i=0;i<len;i++)
				{
					var userName = (data[i].userName).split('@');
					var blocks = $('<div class="block img">'+
						   '<h2>'+data[i].projectName+'</h2>'+
						   '<h3>'+data[i].url+'</h3>'+
						   '<div class="name-post cf">'+
						   		'<img src="img/H.png">'+
								   '<div class="overlay">'+
								   '<div class="first-part">'+
								   '<a href="#" title="Overview">'+
								   	'<div class="overview-part cf" attr-name='+data[i].id+'>'+
										'<img class="image" src="img/overview.png" alt="overview">'+
										'<h4>Overview</h4>'+
									'</div>'+
									'</a>'+
									'<a href="#" title="Tasks">'+
									'<div class="discussion-part cf newTask" attr-name='+data[i].id+'>'+
										'<img class="image" src="img/discussion.png" alt="tasks">'+
										'<h4>Tasks</h4>'+
									'</div>'+
									'<a>'+
									'</div>'+
									'<div class="cf"></div>'+
									'<div class="second-part">'+
									'<a  href="#"  title="Images">'+
									'<div class="files-part cf newImage" attr-name='+data[i].id+'>'+
										'<img class="image" src="img/files.png" alt="overview">'+
										'<h4>Images</h4>'+
									'</div>'+
									'</a>'+
									'<a title="Documents">'+
									'<div class="calendar-part cf newDocument"  attr-name='+data[i].id+'>'+
										'<img class="image" src="img/calendar.png" alt="overview">'+
										'<h4>Documents</h4>'+
									'</div>'+
									'</a>'+
								    '</div>'+
									'</div>'+
								'<span class="desc">'+
									'<div class="name">'+ userName[0] +'</div>'+
									'<div class="post">Manager</div>'+
								'</span>'+	  
						   '</div>'+
						   '<a href="#" class="more-btn"><span>'+ userName[0] +'</span>'+
						   '<span>7 Days</span>'+
						   '</a>'+
						   '</div>');
						$("#effect-1").append(blocks);
				}
				/*<span><a href="#" class="edit_button" title="edit" attr-name="'+ data[i].id + '">Edit</a>'+
				   '<a href="#" class="delete_button right" title="delete" attr-name="'+ data[i].id + '">Delete</a><span>'+*/
				$('.newTask').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					//$("#projectId").val(name);
					var tasksProjectView1 = new tasksProjectView({
						el : $("#tasksModal"),
					});
					tasksProjectView1.render();
					$('#tasksModal').foundation('reveal', 'open');
				});
				$('.newDocument').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					//$("#projectId").val(name);
					var documentsProjectView1 = new documentsProjectView({
						el : $("#documentsModal"),
					});
					documentsProjectView1.render();
					$('#documentsModal').foundation('reveal', 'open');
				});
				$('.newImage').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					//$("#projectId").val(name);
					var imagesProjectView1 = new imagesProjectView({
						el : $("#imagesModal"),
					});
					imagesProjectView1.render();
					$('#imagesModal').foundation('reveal', 'open');
				});
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
					var result = confirm("Are you sure you want to Delete?");
					if (result) {
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
					}
				});
				$('.overview-part').click(function() {
					var name = $(this).attr("attr-name");
					console.log(name);
					$("#hidId").val(name);
					//$("#projectId").val(name);
					var statusProjectView1 = new statusProjectView({
						el : $("#statusModal"),
					});
					statusProjectView1.render();
					$('#statusModal').foundation('reveal', 'open');
				});
				if (Modernizr.touch) {
		            // show the close overlay button
		            $(".close-overlay").removeClass("hidden");
		            // handle the adding of hover class when clicked
		            $(".img").click(function(e){
		                if (!$(this).hasClass("hover")) {
		                    $(this).addClass("hover");
		                }
		            });
		            // handle the closing of the overlay
		            $(".close-overlay").click(function(e){
		                e.preventDefault();
		                e.stopPropagation();
		                if ($(this).closest(".img").hasClass("hover")) {
		                    $(this).closest(".img").removeClass("hover");
		                }
		            });
		        } else {
		            // handle the mouseenter functionality
		            $(".img").mouseenter(function(){
		                $(this).addClass("hover");
		            })
		            // handle the mouseleave functionality
		            .mouseleave(function(){
		                $(this).removeClass("hover");
		            });
		        }
				/*var len = data.length;
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
					}*/
				}
				/*$('.edit_button').click(function() {
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
				});*/
				/*var table = $('#project_data').dataTable({
					responsive: true,
					 "searching": false,
					  	"order": [[ 1, "desc" ]],
					    "iDisplayLength": 5,
					    "columnDefs": [ { orderable: false, targets: [0] }],
					    "bAutoWidth": false,
					    "bLengthChange": false
				});*/
			
		});
	}
});

var addProject =  Backbone.View.extend({
	el :$(""),
	initialize : function() {
		
	},
	events : {
		"click #addProjectButton" : "addProject",
		//"click #addImageButton" : "addImage",
	},
	render : function() {
		this.getUserRole();
	},
	/*addImage : function(){
		
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
	},*/
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
		//var docext = $('#document').val().split('.').pop().toLowerCase();
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
		
		
		/*if($("#document").val() != '' )
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
		}*/
		
		if($("#video").val() != '' && $("#video")[0].files[0].size > 20971520)
		{
			flag3 = true;
			console.log("video file error");
			$(".projectVideo").find('.help-inline').text("Video File Size must be less than 20mb.").addClass('errorText');
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
		if (!this.projectModel.isValid() || flag1 == true  || flag2 == true  || flag3 == true)   {
			this.showErrors(this.projectModel.validationError);
			e.preventDefault();
			$("#projectForm").submit(function(){
				return false;
			});
		} else {
			//$("#projectForm").submit();
			console.log("Submiited");
			 var project = $('#projectPath')[0].files[0];
			 //var document = $("#document")[0].files[0];
			 var video = $("#video")[0].files[0];
			 var val = [];
		        $(':checkbox:checked').each(function(i){
		          val[i] = $(this).val();
		        });
			 var data = new FormData();
			 if($('#projectPath').val() != '')
			 {
				 data.append('projectPath', project);
			 }
			 if($('#video').val() != '')
			 {
				 data.append('video', video);
			 }
			 data.append('projectName', $("#projectName").val());
			 data.append('description', $("#description").val());
			 data.append('url', $("#url").val());
			 
			// data.append('document', document);
			 
			 data.append('group', val);
			 data.append("userName",$("#hidUser").val());
			/* $.each($("input[name^=images]"), function(i, obj) {
			        $.each(obj.files,function(j,file){
			            data.append('images',file);
			            console.log(file);
			        })
			});*/
		   
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
			controlGroup.addClass('error');
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
		/*var self = this;
		$("#editAddImageButton").click(function(){
			self.addImage();
		});
		$('.fancybox').fancybox();*/
	},
	events : {
		"click #editProjectButton" : "editProject",
		"click #changeProject" : "changeProject",
		//"click #changeDocument" : "changeDocument",
		"click #changeVideo" : "changeVideo",
		//"click #editAddImageButton" : "addImage",
	},
	changeProject : function()
	{
		var projectInput = $('<div><input type="file" name="url" id="editUrl"' 
		+'accept=".war,.rar,.zip" class="browser-select">');
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
	/*changeDocument : function()
	{
		var projectInput = $('<div><input type="file" name="document" id="editDocument"' 
			+'accept=".txt,.doc,.docx,.rtf,.pdf" class="browser-select">');
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
	},*/
	changeVideo : function()
	{
		var projectInput = $('<div><input type="file" name="video" id="editVideo"' 
			+'accept="video/*" class="browser-select">');
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
	/*addImage : function(){
		
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
	},*/
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
				//$("#documentLink").val(data.document);
				$("#videoLink").val(data.video);
				/*var images = data.projectImagesDTO;
				var imgaes = "";
				for(var i=0;i<images.length;i++)
				{
					chk ='<div class="input-main imgDiv">'
						+'<span id="close" class="remove_image" title="remove"><big>X</big></span>'
						+'<input name="editImagesChecked" checked="checked" type="checkbox" value="'+images[i].id+'" class="imgGroup" disabled="disabled">'
						+'<span>&nbsp;<a class="fancybox" href="projects/images/'+images[i].images+'" data-fancybox-group="gallery" title=""><img src="projects/images/'+images[i].images+'" alt='+images[i].images+'  style="height:25%; width:25%;margin:5px;"></a></span>'
					+'</div>';
					$("#editImageGroup").append(chk);
				}
				$(".imgDiv").on("click",".remove_image", function(e){ 
			        e.preventDefault(); 
			        $(this).parent('div').remove(); 
			    });*/
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
		//var docext = $('#editDocument').val().split('.').pop().toLowerCase();
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
		
		
		/*if($("#editDocument").val() != '' )
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
		}*/
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
			 //var document = $("#editDocument")[0].files[0];
			 var video = $("#editVideo")[0].files[0];
			 var val = [];
		        $('.group:checkbox:checked').each(function(i){
		          val[i] = $(this).val();
		        });
		    /* var imgVal = [];
		        $('.imgGroup:checkbox:checked').each(function(i){
		        	imgVal[i] = $(this).val();
		        });*/
			 var data = new FormData();
			 if($('#editProjectPath').val() != '')
			 {
				 data.append('projectPath', project);
			 }
			 else
			 {
				 data.append('projectPath', null);
			 }
			/* if($("#editDocument").val() != '')
			 {
				 data.append('document', document);
			 }
			 else
			 {
				 data.append('document', null);
			 }*/
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
			 //data.append('editImagesChecked', imgVal);
			 data.append('editGroupName', val);
			 /*$.each($("input[name^=images]"), function(i, obj) {
			        $.each(obj.files,function(j,file){
			            data.append('images',file);
			            console.log(file);
			        })
			});*/
		   
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
			controlGroup.addClass('error');
			controlGroup.find('.help-inline').text(error.message);
			controlGroup.find('.help-inline').addClass('errorText');
		}, this);
	},
	hideErrors : function() {
		this.$('.control-group').removeClass('error');
		this.$('.help-inline').text('');
	}
});
var documentsProjectView = Backbone.View.extend({

	el : $(""),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#documents_template").html(), {});
		this.$el.html(template);
		this.getDocumentsFromId();
	},
	getDocumentsFromId : function()
	{
		var view = this;
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/getDocuments.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
				{
					"id" : id
				}),
		success: function (data) {
			var len = data.length;
			var thead = $("<thead></thead>");
			var trh = $("<tr></tr>");
			var th1 = $("<th>Action</th>");
			var th2 = $("<th>Document Name</th>");
			var th3 = $("<th>Download</th>");
			trh.append(th1);
			trh.append(th2);
			trh.append(th3);
			thead.append(trh);
			$("#document_data").html(thead);
			var tBody = $("<tbody></tbody>");
			if(len > 0)
			{
				for (var i = (len-1); i >= 0; i--) 
				{ 
					var tr = $("<tr></tr>");
					var td1 = $("<td></td>");
					var td2 = $("<td>"+ data[i].documentName+"</td>");
					var td3 = $('<td><a target="_blank" href= projects/document/'+ data[i].documents +' title="Document">Download</a></td>');
					var button =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:20px; height:20px;'></a>");
					
					td1.append(button);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tBody.append(tr);
					
					$("#document_data").append(tBody);
					if($("#flag").val().indexOf("R") >= 0)
					{
						console.log($("#flag").val());
						th1.hide();
						td1.hide();
						button.hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						console.log($("#flag").val());
						th1.show();
						td1.show();
						button.show();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						console.log($("#flag").val());
						th1.show();
						td1.show();
						button.show();
					}
				}
			}
			else
			{
				var tr = $("<tr align='center'></tr>");
				var tdd = $("<td colspan='3'><b>No Documents</b></td>");
				tr.append(tdd);
				tBody.append(tr);
				$("#document_data").append(tBody);
			}
					
				
				var tr1 = $("<tr></tr>");
				var tdb = $('<td></td>');
				
	
				var td6 = $('<td></td>');
				var div1 = $('<div class="control-group documentName"></div>');
				var inputDoc = $('<input type="text" size="40" name="documentName" id="documentName" placeholder="Document Name">');
				var span1 = $('<span class="help-inline"></span>');
				div1.append(inputDoc);
				div1.append(span1);
				td6.append(div1);
				
				var td7 = $('<td></td>');
				var div2 = $('<div class="control-group documents"></div>');
				var inputStart = $('<input type="file" name="documents" id="documents" accept=".txt,.doc,.docx,.rtf,.pdf" class="browser-select">');
				var span2 = $('<span class="help-inline"></span>');
				div2.append(inputStart);
				div2.append(span2);
				td7.append(div2);
				
				var td8 = $('<td></td>');
				var btn = $('<Button class="radius btn-main" id="addDocumentButton">Add Documents</Button>');
				
				btn.click(function()
				{
					view.addNewDocument();
				});
				td8.append(btn);
				
				var plus = $('<img class="image" src="img/Add.png" title="Add Task" alt="Add" style="height:30px;width:30px;">');
				tdb.append(plus);
				tr1.append(tdb);
				tr1.append(td6);
				tr1.append(td7);
				tr1.append(td8);
				
				if($("#flag").val().indexOf("R") >= 0)
				{
					
				}
				if($("#flag").val().indexOf("W") >= 0)
				{
					tBody.append(tr1);
				}
				$("#document_data").append(tBody);
				
				$('.delete_button').click(function() {
					var result = confirm("Are you sure you want to Delete?");
					if (result) {
						var name = $(this).attr("attr-name");
						$.ajax({
							url : 'user/deleteProjectDocument.json',
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
								var documentsProjectView1 = new documentsProjectView({
						    		el : $("#documentsModal"),
						    	});
						    	documentsProjectView1.render();
							},
							error : function(data) {
								console.log("Error");
							}
						});
					}
				});
				$('.edit_button').click(function() {
					var name = $(this).attr("attr-name");
				});
		},
		error : function(response)
		{
			console.log("error come");
		}	
	});
	},
	addNewDocument : function()
	{
		this.hideErrors();
		var document = $("#documents")[0].files[0];
		 var data = new FormData();
		 data.append("documentName", $("#documentName").val());
		 data.append("projectId", $("#hidId").val());
		 data.append("documents", document);
		
		 this.projectDocumentModel = new projectDocumentModel({
				"documentName" :  $("#documentName").val(),
			});
		if (!this.projectDocumentModel.isValid()) {
			this.showErrors(this.projectDocumentModel.validationError);
		} 
		else
		{
			 $.ajax({
				    url: 'user/addProjectDocuments.json',
				    data:data,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    mimeType: "multipart/form-data",
				    success: function (response) {
				    	console.log("Add Success");
				    	var documentsProjectView1 = new documentsProjectView({
				    		el : $("#documentsModal"),
				    	});
				    	documentsProjectView1.render();
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

var tasksProjectView = Backbone.View.extend({

	el : $(""),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#tasks_template").html(), {});
		this.$el.html(template);
		this.getTasksFromId();
		var view = this;
		$("#editProjectBtn").click(function(){
			view.editProject();
		});
		$("#deleteProjectBtn").click(function(){
			view.deleteProject();
		});
	},
	editProject : function()
	{
		var editProjectView1 = new editProjectView({
			el : $("#editProjectModal"),
		});
		editProjectView1.render();
		$('#editProjectModal').foundation('reveal', 'open');
	},
	deleteProject : function()
	{
		var result = confirm("Are you sure you want to Delete?");
		if (result) {
			$.ajax({
				url : 'user/deleteProject.json',
				type : 'POST',
				data : JSON.stringify({	"id" : $("#hidId").val()}),
				headers : 
					{
						'Accept' : 'application/json',
						'Content-Type' : 'application/json; charset=UTF-8'
					},
				success :function(data)
				{
					console.log("Success");
					$('#tasksModal').foundation('reveal', 'close');
					projectView.render();
				},
				error : function(data) {
					console.log("Error");
				}
			});
		}
	},
	getTasksFromId : function()
	{
		var view = this;
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/getTasks.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
				{
					"id" : id
				}),
		success: function (data) {
				
			var len = data.length;
			var thead = $("<thead></thead>");
			var trh = $("<tr></tr>");
			var th1 = $("<th>Action</th>");
			var th2 = $("<th>Tasks</th>");
			var th3 = $("<th>Start Date</th>");
			var th4 = $("<th>End Date</th>");
			var th5 = $("<th>Status</th>");
			trh.append(th1);
			trh.append(th2);
			trh.append(th3);
			trh.append(th4);
			trh.append(th5);
			thead.append(trh);
			$("#task_data").html(thead);
			var tBody = $("<tbody></tbody>");
			var todaysDate = new Date();
			if(len > 0)
			{
				for (var i = (len-1); i >= 0; i--) 
				{
					var m_names = new Array("Jan", "Feb", "Mar", 
							"Apr", "May", "Jun", "Jul", "Aug", "Sep", 
							"Octr", "Nov", "Dec");
					var sDate = new Date(data[i].startDate);
					var date1 = sDate.getDate();
					var month1 = sDate.getMonth();
					var year1 = sDate.getFullYear();
					var startDate = date1 + "-" + m_names[month1]+ "-" + year1;
					
					var eDate = new Date(data[i].endDate);
					var date2 = eDate.getDate();
					var month2 = eDate.getMonth();
					var year2 = eDate.getFullYear();
					var endDate = date2 + "-" + m_names[month2]+ "-" + year2;
					
					var tr = $("<tr></tr>");
					var td1 = $("<td></td>");
					var td2 = $("<td>"+ data[i].tasks +"</td>");
					var td3 = $("<td>"+ startDate +"</td>");
					var td4 = $("<td>"+ endDate +"</td>");
					
					var button1 = $("<a href=# class='complete_button' title='Complete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'img/project.png' style='width:15px; height:15px;'></a>");
					var button2 =$("<a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:20px; height:20px;'></a>");
					console.log(data[i].complete);
					
					if(todaysDate >= eDate)
					{
						if(data[i].complete == true)
						{
							console.log("true");
							var td5 = $("<td><font color='blue'>DONE</font></td>");
						}
						else
						{
							var td5 = $("<td><font color='red'><b>PRIOR</b></font></td>");
							console.log("doneee");
						}
					}
					else
					{
						if(data[i].complete == true)
						{
							console.log("true");
							var td5 = $("<td><font color='blue'><b>DONE</b></font></td>");
						}
						else
						{
							var td5 = $("<td><font color='green'><b>IN WORKING</b></font></td>");
							console.log("work");
						}
					}
					td1.append(button1);
					td1.append(button2);
					tr.append(td1);
					tr.append(td2);
					tr.append(td3);
					tr.append(td4);
					tr.append(td5);
					tBody.append(tr);
					
					$("#task_data").append(tBody);
					if($("#flag").val().indexOf("R") >= 0)
					{
						console.log($("#flag").val());
						th1.hide();
						td1.hide();
						button1.hide();
						button2.hide();
						$("#editProjectBtn").hide();
						$("#deleteProjectBtn").hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						console.log($("#flag").val());
						th1.show();
						td1.show();
						button1.show();
						$("#editProjectBtn").show();
						$("#deleteProjectBtn").show();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						console.log($("#flag").val());
						th1.show();
						td1.show();
						button2.show();
						$("#editProjectBtn").show();
						$("#deleteProjectBtn").show();
					}
				}
			}
			else
			{
				var tr = $("<tr align='center'></tr>");
				var tdd = $("<td colspan='4'><b>No Tasks</b></td>");
				tr.append(tdd);
				tBody.append(tr);
				$("#task_data").append(tBody);
			}
				var tr1 = $("<tr></tr>");
				var tdb = $('<td></td>');
				
	
				var td6 = $('<td></td>');
				var div1 = $('<div class="control-group taskName"></div>');
				var inputTask = $('<input type="text" size="40" name="taskName" id="taskName" placeholder="Task">');
				var span1 = $('<span class="help-inline"></span>');
				div1.append(inputTask);
				div1.append(span1);
				td6.append(div1);
				
				var td7 = $('<td></td>');
				var div2 = $('<div class="control-group startDate"></div>');
				var inputStart = $('<input type="text" name="startDate" id="startDate" placeholder="Start Date">');
				var span2 = $('<span class="help-inline"></span>');
				div2.append(inputStart);
				div2.append(span2);
				td7.append(div2);
				
				var td8 = $('<td></td>');
				var div3 = $('<div class="control-group endDate"></div>');
				var inputEnd = $('<input type="text" name="endDate" id="endDate" placeholder="End Date">');
				var span3 = $('<span class="help-inline"></span>');
				div3.append(inputEnd);
				div3.append(span3);
				td8.append(div3);
				
				var td9 = $('<td></td>');
				var btn = $('<Button class="radius btn-main" id="addTaskButton">Add Tasks</Button>');
				
				btn.click(function()
				{
					view.addNewTask();
				});
				td9.append(btn);
				
				var plus = $('<img class="image" src="img/Add.png" title="Add Task" alt="Add" style="height:30px;width:30px;">');
				tdb.append(plus);
				tr1.append(tdb);
				tr1.append(td6);
				tr1.append(td7);
				tr1.append(td8);
				tr1.append(td9);
				
				if($("#flag").val().indexOf("R") >= 0)
				{
					
				}
				if($("#flag").val().indexOf("W") >= 0)
				{
					tBody.append(tr1);
				}
				$("#task_data").append(tBody);
				inputStart.datepicker(
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
			           // inputEnd.datepicker("option", "minDate", dateText);
			            var date = $(this).datepicker('getDate');
		                if (date) 
		                {
		                	date.setDate(date.getDate() + 1);
		                }
		                inputEnd.datepicker('option', 'minDate', date);
					}
				});
				
				inputEnd.datepicker(
				{
					dateFormat: 'dd/mm/yy',
					minDate : 1,
					changeMonth: true,
		            changeYear: true,
		            yearRange: "+0:+10",
					onSelect : function(dateText, datePicker) 
					{
						console.log('onSelect', dateText);
						$(this).selectedDate = dateText;
					}
				});
				$('.complete_button').click(function() {
					var name = $(this).attr("attr-name");
					$.ajax({
						url : 'user/setCompleteTask.json',
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
							var tasksProjectView1 = new tasksProjectView();
							tasksProjectView1.getTasksFromId();
							//$('#tasksModal').foundation('reveal', 'open');
						},
						error : function(data) {
							console.log("Error");
						}
					});
				});
				$('.delete_button').click(function() {
					var result = confirm("Are you sure you want to Delete?");
					if (result) {
						var name = $(this).attr("attr-name");
						$.ajax({
							url : 'user/deleteTask.json',
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
								var tasksProjectView1 = new tasksProjectView({
									el : $("#tasksModal"),
								});
								tasksProjectView1.render();
								$('#tasksModal').foundation('reveal', 'open');
							},
							error : function(data) {
								console.log("Error");
							}
						});
					}
				});
		},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	addNewTask : function()
	{
		this.hideErrors();
		this.taskModel = new taskModel({
			"tasks" : $("#taskName").val(),
			"startDate" : $("#startDate").val(),
			"endDate" :$("#endDate").val(),
			"projectId" : $("#hidId").val()
		});
		
		if (!this.taskModel.isValid()) {
			this.showErrors(this.taskModel.validationError);
		} 
		else
		{
			$.ajax({
				type : "POST",
				url : "user/addTasks.json",
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json; charset=UTF-8'
				},
				data : JSON.stringify(this.taskModel),
				success: function (response) {
					console.log("Add Success");
					var tasksProjectView1 = new tasksProjectView({
						el : $("#tasksModal"),
					});
					tasksProjectView1.render();
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

var imagesProjectView = Backbone.View.extend({

	el : $(""),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#images_template").html(), {});
		this.$el.html(template);
		this.getImagesFromId();
		$('.fancybox').fancybox();
	},
	/*events 
	{
		"click addImageButton" : "addNewProjectImage"
	},*/
	getImagesFromId : function()
	{
		var view = this;
		var id = $("#hidId").val();
		$.ajax({
			url : 'user/getProjectImages.json',
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
			{
					"id" : id
			}),
			success: function (data) {
				
			var len = data.length;
			var tBody = $("<tbody></tbody>");
			var addImg = $('<div class="control-group imageGroup" id="imageGroup">'
					+'<div class="control-group images">'
					+'<label for="projectImage">Select Image</label>'
					+'<input type="file" multiple="multiple" class="imgGroup" id="imgGroup" name="images" accept="image/*"'
					+'class="browser-select">'
					+'</div>'	
					+'<span class="help-inline"></span>'
					+'</div>');
			
			var btn = $('<Button class="radius btn-main" id="addImageButton">Add Images</Button>');
			var tr1 = $("<tr></tr>");
			var td1 = $("<td></td>");
			var td2 = $("<td></td>");
			td1.append(addImg);
			td2.append(btn);
			tr1.append(td1);
			tr1.append(td2);
			btn.click(function()
			{
				if($("#imgGroup").val() != "")
				{
					view.addNewProjectImage();
				}
				else
				{
					$(".imageGroup").find('.help-inline').text("Please Select Image.").addClass('errorText');
				}
				
			});
			if($("#flag").val().indexOf("R") >= 0)
			{
				
			}
			if($("#flag").val().indexOf("W") >= 0)
			{
				tBody.append(tr1);
			}
			
			$("#projectImagesDiv").append(tBody);
			if(len > 0)
			{
				for (var i = (len-1); i >= 0; i--) 
				{ 
					var imageDiv = $('<div class="imgBlock"></div>')
					var images = $('<a class="fancybox" href="projects/images/'+ data[i].images+'" data-fancybox-group="gallery" title=""><img src="projects/images/'+ data[i].images+'" alt="Image Not Displayed"  ></a>');
					var span = $("<span class='right' style='padding:2px;'><a href=# class='delete_button' title='delete' attr-name='"+ data[i].id + "'>&nbsp;<img src= 'images/delete.png' style='width:12px; height:12px;'></a></span>");
					imageDiv.append(span);
					imageDiv.append(images);
					$("#projectImagesDiv").append(imageDiv);
					if($("#flag").val().indexOf("R") >= 0)
					{
						console.log($("#flag").val());
						span.hide();
					}
					if($("#flag").val().indexOf("W") >= 0)
					{
						console.log($("#flag").val());
						span.show();
					}
					if($("#flag").val().indexOf("D") >= 0)
					{
						console.log($("#flag").val());
						span.show();
					}
				}
			}
			else
			{
				var tr = $("<tr align='center'></tr>");
				var tdd = $("<td><b>No Images</b></td>");
				tr.append(tdd);
				tBody.append(tr);
				$("#projectImagesDiv").append(tBody);
			}
				
				
				
				$('.delete_button').click(function() {
					var result = confirm("Are you sure you want to Delete?");
					if (result) {
						var name = $(this).attr("attr-name");
						$.ajax({
							url : 'user/deleteProjectImage.json',
							type : 'POST',
							data : JSON.stringify({	"projectId" : name}),
							headers : 
							{
								'Accept' : 'application/json',
								'Content-Type' : 'application/json; charset=UTF-8'
							},
							success :function(data)
							{
								console.log("Success");
								var imagesProjectView1 = new imagesProjectView({
									el : $("#imagesModal"),
								});
								imagesProjectView1.render();
								$('#imagesModal').foundation('reveal', 'open');
							},
							error : function(data) {
								console.log("Error");
							}
						});
					}
					
				});
		},
		error : function(response)
		{
			console.log("error come");
		}
		});
	},
	addNewProjectImage : function()
	{		
			 var data = new FormData();
			 data.append('projectId', $("#hidId").val());
			 $.each($("input[name^=images]"), function(i, obj) {
			        $.each(obj.files,function(j,file){
			            data.append('images',file);
			            console.log(file);
			        })
			});
		   
		 	 $.ajax({
				    url: 'user/addProjectImages.json',
				    data:data,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    mimeType: "multipart/form-data",
				    
				    success: function(){
				    	var imagesProjectView2 = new imagesProjectView({
							el : $("#imagesModal"),
						});
						imagesProjectView2.render();
						$('#imagesModal').foundation('reveal', 'open');
				    	console.log("success");
				    },
				    error: function(data){
				    	console.log("error");
				    }
		 	 	}); 
	
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

var statusProjectView = Backbone.View.extend({

	el : $(""),
	initialize : function() {
	},
	render : function() {
		var template = _.template($("#status_template").html(), {});
		this.$el.html(template);
		var id = $("#hidId").val();
		
		$.ajax({
			url : "user/getStatus.json",
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			data : JSON.stringify(
			{
				"id" : id
			}),
			success: function (data) {
				
				var project = $("<h4><b>Project Name</b> :  "+ data.projectName+" </h4>");
				$("#statusDiv").append(project);
				var progress = $('<div id="progressbar"></div>');
				$("#statusDiv").append(progress);
				
				$('#progressbar').jQMeter(
				{
					goal: ''+ data.allTask,
					raised: ''+ data.completedTask, 
					width:'350px',
					height:'25px',
					bgColor: "#000",
					barColor: "#018bb9"
				});
				var taskStatus = $("<h5>Completed Task : "+data.completedTask+   "  out of   "+data.allTask+"</h5>")
				var link = $("<a target='_blank' href= 'projects/project/"+ data.projectPath +"'>Download</a>");
				var table = $("<table cellspacing='0' style='width: 100%; border : none;'> </table>")
				var tBody = $("<tbody></tbody>");
				var tr1 = $("<tr  style='Text-align:center;'></tr>");
				var td1 = $("<td style='border:2px solid #018bb9;'></td>");
				var td2 = $("<td style='border:2px solid #018bb9; '></td>");
				td1.append(project);
				td2.append(progress);
				td2.append(taskStatus);
				if(data.projectPath != null)
				{
					td1.append(link);
				}
				
				tr1.append(td1);
				tr1.append(td2);
				tBody.append(tr1);
				
				/*var video = $('<video id="MY_VIDEO_1" class="video-js vjs-default-skin" controls'
						+'preload="auto" width="640" height="264" poster="MY_VIDEO_POSTER.jpg"'
						+'data-setup="{}">'
						+'<source src="projects/videos/'+ data.video +'" type="video/mp4">'
						+'<p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>'
						+'</video>');*/
				var video = $('<div style="max-width:640px; height:480px; margin:0 auto;"><video id="vdo" height="480" width="640">'
						+'<source src="projects/videos/'+ data.video +'" >'
						+'</video> </div>');
				var tr2 = $("<tr ></tr>");
				var td4 = $("<td colspan='2'></td>");
				td4.append(video);
				tr2.append(td4);
				tBody.append(tr2);
				table.append(tBody);
				$("#statusDiv").append(table);
				$('#vdo').videoPlayer({
					'playerWidth' : 0.95,
					'videoClass' : 'video'	
				});
			},
			error : function(data)  {
				console.log("error");
			}
		});
	}
});
/*
var projectView = Backbone.View.extend({

	el : $("#main-container"),
	initialize : function() {
	},
	render : function() {
		//var template = _.template($("#project_template").html(), {});
		//this.$el.html(template);
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
				for(var i=0;i<len;i++)
				{
					var blocks = $('<div class="block img">'+
					   '<h2>'+data[i].projectName+'</h2>'+
					   '<h3>'+data[i].url+'</h3>'+
					   '<div class="name-post cf">'+
					   		'<img src="img/H.png">'+
							   '<div class="overlay">'+
							   '<div class="first-part">'+
							   '<a href="#" title="Overview">'+
							   	'<div class="overview-part cf">'+
									'<img class="image" src="img/overview.png" alt="overview">'+
									'<h4>Overview</h4>'+
								'</div>'+
								'</a>'+
								'<a href="#" title="Overview">'+
								'<div class="discussion-part cf">'+
									'<img class="image" src="img/discussion.png" alt="overview">'+
									'<h4>Discussion</h4>'+
								'</div>'+
								'<a>'+
								'</div>'+
								'<div class="cf"></div>'+
								'<div class="second-part">'+
								'<a target="_blank" href= projects/project/'+ data[i].projectPath +' title="Project">'+
								'<div class="files-part cf">'+
									'<img class="image" src="img/files.png" alt="overview">'+
									'<h4>Projects</h4>'+
								'</div>'+
								'</a>'+
								'<a target="_blank" href= projects/document/'+ data[i].document +' title="Documents">'+
								'<div class="calendar-part cf">'+
									'<img class="image" src="img/calendar.png" alt="overview">'+
									'<h4>Documents</h4>'+
								'</div>'+
								'</a>'+
							    '</div>'+
								'</div>'+
							'<span class="desc">'+
								'<div class="name">Hitesh Patel</div>'+
								'<div class="post">Manager</div>'+
							'</span>'+	  
					   '</div>'+
					   '<a href="#" class="more-btn"><span>Hitesh Patel</span>'+
					   '<span>7 days</span>'+
					   '</a>'+
					   '</div>');
					$("#effect-1").append(blocks);
				}
				if (Modernizr.touch) {
		            // show the close overlay button
		            $(".close-overlay").removeClass("hidden");
		            // handle the adding of hover class when clicked
		            $(".img").click(function(e){
		                if (!$(this).hasClass("hover")) {
		                    $(this).addClass("hover");
		                }
		            });
		            // handle the closing of the overlay
		            $(".close-overlay").click(function(e){
		                e.preventDefault();
		                e.stopPropagation();
		                if ($(this).closest(".img").hasClass("hover")) {
		                    $(this).closest(".img").removeClass("hover");
		                }
		            });
		        } else {
		            // handle the mouseenter functionality
		            $(".img").mouseenter(function(){
		                $(this).addClass("hover");
		            })
		            // handle the mouseleave functionality
		            .mouseleave(function(){
		                $(this).removeClass("hover");
		            });
		        }
			}
		});
	}
});*/
