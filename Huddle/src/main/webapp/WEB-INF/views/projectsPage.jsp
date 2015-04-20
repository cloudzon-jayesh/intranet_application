<!doctype html>
<html class="no-js" lang="en">
<head>
  <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>CloudZon.huddle | Projects</title>
<link rel="icon" type="image/x-icon" href="img/huddle.ico" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Mono'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/foundation.min.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/hover.css"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<style>
	.cf:before, .cf:after { content: ""; display: table; }
	.cf:after { clear: both; }
	.cf { zoom: 1; }
	.clear { clear: both; }
	.clearfix:after { content: "."; display: block; height: 0; clear: both; visibility: hidden; }
	.clearfix { display: inline-block; }
	.dashboard-block{display:block; background-color:#fff; box-shadow: 0 0 16px #ccc; padding:1% 13px; max-width:89%; margin: 0 auto;}
	.dashboard-block-main{width:100%; display:block; margin-bottom:25px; }
	.block{width:200px;  background-color:#fff; border:2px solid #018bb9;  margin-left:15px; float:left; margin-bottom:28px !important;}
	.block h2{font-size:24px; font-family:Arial, Helvetica, sans-serif; color:#018bb9; display:block; text-align:center; margin-bottom:5px; }
	.block h3{font-size:16px; font-family:Arial, Helvetica, sans-serif; color:#828282; display:block; text-align:center; margin-bottom:45px; font-weight:normal; margin-top:0;}
	.block .more-btn{width:100%; font-size:20px; color:#fff; background-color:#018bb9; text-align:center; padding:8px 0px; display:block; text-decoration:none; min-height:57px;}

	.block .more-btn:hover{background-color:#000; color:#fff;}
	.block .more-btn span{color:#fff; font-size:16px; display:inline-block; margin: 0 27px; font-family:Arial, Helvetica, sans-serif;}
	.block .name-post{ margin:0 0 10px 10px;}
	.block .name-post img{ display:block;padding-right:5px; float: left;}
	.block .name-post .desc{ float:left;}
	.block .name-post .desc .name{ font-size:14px; color:#0287b3; font-family:Arial, Helvetica, sans-serif; margin-top:7px; margin-bottom:10px;}
	.block .name-post .desc .post{font-family:Arial, Helvetica, sans-serif; color:#828282; font-size:13px;}
	.overlay{ width:100%;}
	.overlay .first-part{}
	.overlay .first-part a .overview-part{ text-decoration:none;}
	.overlay .first-part a:hover .overview-part{  background: rgba(1, 52, 69, 0.8);}
	.overlay .first-part .overview-part{ float:left; text-align:center; padding: 20px 16px 29px 16px; border-right:1px solid #fff;}
	.overlay .first-part .overview-part .image{ display:inline-block; margin-left:5px; margin-bottom:10px;}
	.overlay .first-part .overview-part h4{ font-size:14px; color:#fff;font-family:Arial, Helvetica, sans-serif; font-weight:normal; margin:0px; padding:0;}
	.overlay .first-part a .discussion-part{ text-decoration:none;}
	.overlay .first-part a:hover .discussion-part{  background: rgba(1, 52, 69, 0.8);}
	.overlay .first-part .discussion-part{ float:right; text-align:center; padding:20px 21px;}
	.overlay .first-part .discussion-part .image{ display:inline-block; margin-left:5px;margin-bottom:10px;}
	.overlay .first-part .discussion-part h4{ display:block;font-size:14px; color:#fff;font-family:Arial, Helvetica, sans-serif; font-weight:normal;margin:0px; padding:0;}
	.overlay .second-part{ border-top:1px solid #fff; }
	.overlay .second-part a .files-part{ text-decoration:none;}
	.overlay .second-part a:hover .files-part{  background: rgba(1, 52, 69, 0.8);}
	.overlay .second-part .files-part{ float:left; text-align:center; padding: 20px 22px 22px 23px; border-right:1px solid #fff;}
	.overlay .second-part .files-part .image{display:inline-block;margin-left:5px;margin-bottom:10px;}
	.overlay .second-part .files-part h4{font-size:14px; color:#fff;font-family:Arial, Helvetica, sans-serif; font-weight:normal;margin:0px; padding:0;}
	.overlay .second-part a .calendar-part{ text-decoration:none;}
	.overlay .second-part a:hover .calendar-part{  background: rgba(1, 52, 69, 0.8); display:block;}
	.overlay .second-part .calendar-part{ float:right; text-align:center; padding:20px 14px 25px;}
	.overlay .second-part .calendar-part .image{display:inline-block; margin-left:5px;margin-bottom:10px;}
	.overlay .second-part .calendar-part h4{display:block;  font-size:14px; color:#fff;font-family:Arial, Helvetica, sans-serif; font-weight:normal; margin:0; padding:0;}		
	.pageTitle{margin:20px 70px; display:block; font-size:20px; color:#000;}
	 .errorText{color:red;}
		.input-left-main{width:48%; float:left;}
		.input-main{width:100%;}
		
		.input-main input{ border-radius:3px; padding:4px 10px; height:auto;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		.browser-select{border:1px solid #999; padding:7px;}
		.btn-main{padding: 5px 12px;margin-top: 15px;float: right;margin-right: 32px; }
		.text-main{width:100%; float:left; text-align:center;}
		.text-main p{width:100%; display:inline-block; border-bottom:1px solid #ccc; padding-bottom:50px;}
		.select-box{width:100%; border-radius:3px;}
		 
		 .input-right-main{width:48%; float:right; border:1px solid #ccc; padding:20px; margin-top:30px;}
		 .input-right-main .title{font-size:36px; font-weight:bold; font-family:Arial, Helvetica, sans-serif; margin-bottom:20px; display:block; }
		 .input-right-main span{font-size:15px; font-family:Arial, Helvetica, sans-serif;  }
		.input-right-main .checkbox{ margin-right:15px; width:10px;}
		@media (max-width:640px) {
		.input-main{width:100%; float:none; display:block;}
		.input-main.last{width:100%; display:block; float:none;}
		.input-main input{width:100%; border-radius:3px;}
		.content-container{margin:0;}
		.text-main p{padding: 20px 0 26px 0;}
		.top-bar{padding:0px;}
		.btn-main{padding:10px 0; width:100%; float:left; margin-top:5px;}
		footer{padding:30px 0 0}
		.career-container li a div{width:100% !important;}
		.input-left-main{width:100%; float:right;}
		.input-right-main{width:100%; float:left; border:1px solid #ccc; padding:20px 0 0 2%; margin-top:10px; margin-bottom:10px;}
	
	</style>
	
	
  </head>
<body>
<header>
  <nav class="top-bar" data-topbar role="navigation">
    <ul class="title-area">
      <li class="name">
        <h1><a href="dashboard">CloudZon<span>.huddle</span><img src="_img/talk.png" alt="chat" height="16"width="16" hspace="3" style="padding-bottom:10px"></a></h1>
      </li>
      <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
      <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
    </ul>
    <section class="top-bar-section">
      <!-- Right Nav Section -->
      <ul class="right">
        <li><a href="dashboard">Home</a></li>
        <li><a href="company">Company</a></li>
        <li><a href="careers">Careers</a></li>
        <li class="has-dropdown"><a href="#">Hi, <c:out value="${sessionUser.getUsername() }"></c:out></a>
        <ul class="dropdown">
        	<li><a id="logOutBtn" href="user/logout.json">Logout</a></li>            
        </ul>
        </li>
      </ul>
      <!-- Left Nav Section -->
    </section>
  </nav>
</header>
<div class="main-container" id="main-container">
    </div>
     <input type="hidden" Id="flag">
	<div id="addProjectModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Add Project</a> 
			<div class="dashboard-block cf">
						<span id="error"></span>
						<form id="projectForm" enctype="multipart/form-data" onsubmit="return false">
							<div class="input-left-main">
							<input type="hidden" Id="hidId">
								<label for="projectName">Project Name </label>
								<div class="control-group projectName">
									<input type="text" size="40" name="projectName" id="projectName" >
									<span class="help-inline"></span>
								</div> 
								<label for="description">Description</label>
								<div class="description control-group">
									<textarea rows="3" cols="10" name="description" id="description"></textarea>
									<span class="help-inline"></span>
								</div>
								<label for="url">URL</label>
								<div class="url control-group">
									<input type="text" size="50" name="url" id="url">
									<span class="help-inline"></span>
								</div>
								<label for="projectPath">Select Project</label>
								<div class="projectPath control-group" ID="projectDiv" >
									<input type="file" name="projectPath" id="projectPath" accept=".zip,.rar,.war" class="browser-select">
									<span class="help-inline"></span>
								</div>
								<label for="document">Select Document</label>
								<div class="document control-group">
									<input type="file" name="document" id="document" accept=".txt,.doc,.docx,.rtf,.pdf" class="browser-select">
									<span class="help-inline"></span>
								</div>
								<label for="video">Select Video</label>
								<div class="projectVideo control-group">
									<input type="file" name="projectVideo" id="video" accept="video/*" class="browser-select">
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="input-right-main">
								<span class="title">Images</span>
								<div class="control-group imageGroup" id="imageGroup">
									<div class="control-group images">
										<label for="projectImage">Select Image</label>
										<input type="file" class="imgGroup" name="images" accept="image/*"
										class="browser-select">
									</div>	
									<span class="help-inline"></span>
								</div>
								<a class="radius right" id="addImageButton" >Add More Images</a>
							</div>	
							<div class="input-right-main">
								<span class="title">Group</span>
								<div class="control-group groupName" id="groupName">
									<span class="help-inline"></span>
								</div>
							</div>	
							<Button  class='radius right btn-main' id="addProjectButton">Add Project</Button>
						</form>
					</div>
				</div>
				
		<div id="editProjectModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
		<script type="text/template" id="edit_template">
			<a class="close-reveal-modal" aria-label="Close">&#215;</a>
			<a href="" class="pageTitle">Update Project</a> 
				<div class="dashboard-block cf"> 
					<form id="editProjectForm" onSubmit="return false;" enctype="multipart/form-data" action="user/editProject.json" method="post">
							<input type="hidden" Id="projectId" name="projectId">
							<div class="input-left-main">
								<label for="projectName">Project Name </label>
								<div class="control-group projectName">
									<input type="text" size="40" name="projectName" id="editProjectName" >
									<span class="help-inline"></span>
								</div> 
								<label for="description">Description</label>
								<div class="description control-group">
									<textarea rows="3" cols="10" name="description" id="editDescription"></textarea>
									<span class="help-inline"></span>
								</div>
								<label for="url">URL</label>
								<div class="url control-group">
									<input type="text" size="50" name="url" id="editUrl">
									<span class="help-inline"></span>
								</div>
								<label for="projectPath">Select Project</label>
								<div class="projectPath control-group"  >
									<input type="text" id="projectLink" readonly="readonly"/>
									<input type="file" name="projectPath" id="editProjectPath" accept=".war,.rar,.zip"  style="display:none;" class="browser-select">
									<div id="prjDiv">
											<a class="radius right" id="changeProject">Change Project</a>
										</div>
									<span class="help-inline"></span>
								</div>
								<label for="document">Select Document</label>
								<div class="document control-group">
									<input type="text" id="documentLink" readonly="readonly"/>
									<input type="file" name="document" id="editDocument" accept=".txt,.doc,.docx,.rtf,.pdf" class="browser-select" style="display:none;">
										<div id="docDiv">
											<a class="radius right" id="changeDocument">Change Document</a>
										</div>
										<span class="help-inline"></span>
								</div>
								<label for="video">Select Video</label>
								<div class="video control-group">
									<input type="text" id="videoLink" readonly="readonly"/>
									<input type="file" name="video" id="editVideo" accept="video/*" class="browser-select" style="display:none;">
										<div id="videoDiv">
											<a class="radius right" id="changeVideo">Change Video</a>
										</div>
									<span class="help-inline"></span>
								</div>								
								
							</div>
							<div class="input-right-main">
								<span class="title">Images</span>
								<div class="control-group imageGroup" id="editImageGroup">
									<div class="control-group fileinput">
										<label for="projectImage">Project Images</label>
										<input type="file" class="imgGroup" name="images" accept="image/*"
										class="browser-select" style="display:none;">
										<input name="editImagesChecked" checked="checked" type="checkbox" value="0" class="imgGroup" style="display:none;">
									</div>	
									<span class="help-inline"></span>
								</div>
								<a class="radius right" id="editAddImageButton">Add More Images</a>
							</div>	
							<div class="input-right-main">
								<span class="title">Group</span>
								<div class="control-group groupName" id="editGroupName">
									<span class="help-inline"></span>
								</div>
							</div>	
							<Button class='radius right btn-main' id="editProjectButton">Update Project</Button>
						</form>
					</div>
			
			
		</script>
	<footer>
  <div class="row" id="top-footer">
    <div class="large-12 medium-12 small-12 columns">
      <div><a href="#"><img src="_img/g.png" alt="google+"></a></div>
      <div><a href="#"><img src="_img/tweet.png" alt="twitter"></a></div>
      <div><a href="#"><img src="_img/face.png" alt="facebook"></a></div>
    </div>
  </div>
  <div class="row" id="bottom-footer">
    <div class="large-12 medium-12 small-12 columns">
      <div>©2015 CloudZon. All rights reserved.</div>
    </div>
  </div>
</footer>

		<!-- <div class="content-container">
    <div class="row outer-title">
      <div class="large-12 medium-12 small-12 columns text-main">
        <p>Projects</p>
      </div>
    </div>
    </div>
    
<div class="row login-container">
    <div class="large-7  medium-12 small-12 columns input-block"> -->
<script type="text/template" id="project_template">
<a href="" class="pageTitle">Project Details</a> 
<div class="dashboard-block"> 
		<input type="hidden" id="hidUser" value='<c:out value="${sessionUser.getUsername() }"></c:out>'>
      	<button data-reveal-id="addProjectModal" id="addNewProject" class="radius btn-main">Add Projects</button>
	<!--	<img data-reveal-id="addProjectModal" id="addNewProject" class="image right" src="img/Add.png" title="Add Project" alt="Add Project" style="height:30px;width:30px;">-->
   		<div id="effect-1" class="effects clearfix">
		</div>
	</div
    
  


</script>
<div id="tasksModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog" >
</div>
		<script type="text/template" id="tasks_template">	 
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Project Tasks</a> 
		<div class="dashboard-block"> 
			<button id="editProjectBtn" class="radius btn-main">Edit Project</button>
			<button id="deleteProjectBtn" class="radius right btn-main">Delete Project</button>			
			<table id="task_data" style="width:100%;border : none;">
			</table>
		</div>			
</script> 
		
		<div id="statusModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog" >
		</div>
		<script type="text/template" id="status_template">	 
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Project Status</a> 
		<div class="dashboard-block"> 
			<div id="statusDiv">
			</div>
		</div>
		</script> 
		
	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<!-- <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script> -->
	<script src="js/web-fonts.js"></script>
	<script src="js/sticky-footer.js"></script>
	<script src="js/foundation.js"></script>
	<script src="js/foundation.reveal.js"></script>
	<script src="js/foundation.topbar.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<script type="text/javascript" src="js/modernizr.js"></script>
	<script src="js/jqmeter.min.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/model/projectModel.js"></script>
	<script src="js/model/projectEditModel.js"></script>
	<script src="js/view/projectView.js"></script>
	<script src="js/model/taskModel.js"></script>
	
	<script>
		var projectView = new projectView();
		projectView.render();
		var addProject = new addProject({
			el : $("#addProjectModal")
		});
		addProject.render();
	</script>
	<script>
    $(document).foundation();
    </script>
    <c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="req"></c:set>
	<c:if test="${userPermission != null  && sessionUser != null}">
				<c:if test="${userPermission.getUserName() eq sessionUser.getUsername()}">
					<c:forEach items="${userPermission.roleActivityPermissionDTOs}" var="permission">
						<c:if test="${fn:containsIgnoreCase(req,permission.activityLink)}">
							<c:forEach items="${permission.permissions}" var="per" varStatus="status">
								<c:if test="${per eq ('')}" >
								<script>
									$(document).ready(function() {
										console.log('null');
										$("#addNewProject").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addNewProject").css("display", "none");
										$("#flag").val("R");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#addNewProject").css("display", "block");
										$("#flag").val($("#flag").val() + ",W");
									});
									</script>
								</c:if>	
								<c:if test="${per eq ('D')}" >
									<script>
									$(document).ready(function() {
										console.log('d');
										$("#flag").val($("#flag").val() + ",D");
									});
									</script>
								</c:if>	
							</c:forEach>
						</c:if>
					</c:forEach>
				</c:if>
				</c:if>
</body>
