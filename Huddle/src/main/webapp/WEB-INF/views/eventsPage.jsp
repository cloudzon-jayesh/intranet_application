<!doctype html>
<html class="no-js" lang="en">
<head>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Events</title>
   <link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <link rel="stylesheet" type="text/css" href="css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.dataTables.css" />
	<link type="text/css" rel="stylesheet" href="css/dataTables.responsive.css" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/smoothness/jquery-ui.min.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.css" />
    <style type="text/css">
    	 .errorText{color:red;}
		.input-left-main{width:48%; float:left;}
		.input-main{width:100%;}
		
		.input-main input{ border-radius:3px; padding:4px 10px; height:auto;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		.browser-select{border:1px solid #999; padding:7px;}
		.btn-main{padding:15px 80px; margin-top:15px;  }
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
				
		}
		
    </style>
</head>
<body>
<header>
  <nav class="top-bar" data-topbar role="navigation">
    <ul class="title-area">
      <li class="name">
        <h1><a href="huddle">CloudZon<span>.huddle</span><img src="_img/talk.png" alt="chat" height="16"width="16" hspace="3" style="padding-bottom:10px"></a></h1>
      </li>
      <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
      <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
    </ul>
    <section class="top-bar-section">
      <!-- Right Nav Section -->
      <ul class="right">
        <li><a href="huddle">Home</a></li>
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
    
	<div id="addEventModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
							Add Event <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-7  medium-12 small-12 columns input-block">
						<span id="error"></span>
						<form id="eventForm" enctype="multipart/form-data" onsubmit="return false">
							<div class="input-left-main">
							<input type="hidden" Id="hidId">
								<label for="eventName">Event Name </label>
								<div class="control-group eventName">
									<input type="text" size="40" name="eventName" id="eventName" >
									<span class="help-inline"></span>
								</div> 
								<label for="description">Description</label>
								<div class="description control-group">
									<textarea rows="3" cols="10" name="description" id="description"></textarea>
									<span class="help-inline"></span>
								</div>
								<label for="date">Date</label>
								<div class="date control-group">
									<input type="text" name="date" id="date" readonly="readonly" autocomplete="off">
									<span class="help-inline"></span>
								</div>
								<label for="time">Time</label>
								<div class="time control-group">
									<input type="text" name="time" id="time" autocomplete="off">
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="input-right-main">
								<span class="title">Images</span>
								<div class="control-group imageGroup" id="imageGroup">
									<div class="control-group fileinput">
										<label for="eventImage">Select Image</label>
										<input type="file" class="imgGroup" name="fileinput" accept="image/*"
										class="browser-select">
									</div>	
									<span class="help-inline"></span>
								</div>
								<a class="right" id="addImageButton" >Add More Images</a>
							</div>	
							<div class="input right main">
									
								</div>	
							<Button class='radius right btn-main' id="addEventButton">Add Event</Button>
						</form>
					</div>
				</div>
			</div>
		</div>
				
			
		<!-- <div id="uploadModel" class="reveal-modal" data-reveal
		aria-labelledby="secondModalTitle" aria-hidden="true" role="dialog">
		<div class="main-container" id="main-container">
		<div class="content-container">
			<div class="row outer-title">
				<div class="large-12 medium-12 small-12 columns text-main">
					<p>Event Images</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/template" id="main_template">
	
	<div class="row login-container"  align="center">
		<div class="large-7  medium-12 small-12 columns input-block" align="center">
				<form enctype="multipart/form-data" 
				 method="post" action="user/uploadProfile.json" >
				 <input type="hidden" name="hidId" id="hidId" />			
				 <div class="input-right-main" >
				 	
				</div>
					<button class=" radius" id="upload_button">Upload</button>
				</form>
			</div>
		</div>
	</div> -->
		<div id="editEventModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
		<script type="text/template" id="edit_template">
			<a class="close-reveal-modal" aria-label="Close">&#215;</a>
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
							Update Event <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-8 medium-12 small-12 columns">
							<div class="input-left-main">
						<label for="eventName">Event Name </label>
						<div class="control-group eventName">
							<input type="text" size="40"  name="eventName" id="editEventName" >
							<span class="help-inline"></span>
						</div> 
						<label for="description">Description</label>
						<div class="description control-group">
							<textarea rows="3" cols="10" name="description" id="editDescription"></textarea>
							<span class="help-inline"></span>
						</div>
						<label for="date">Date</label>
						<div class="date control-group">
							<input type="text" name="date" id="editDate" readonly="readonly" autocomplete="off">
							<span class="help-inline"></span>
						</div>
						<label for="time">Time</label>
						<div class="time control-group">
							<input type="text" name="time" id="editTime" autocomplete="off">
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
						<button class='right radius btn-main' id="editEventButton">Update Event</button></div>
						</div>
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

<script type="text/template" id="event_template">
<div class="content-container">
    <div class="row outer-title">
      <div class="large-12 medium-12 small-12 columns text-main">
        <p>Events</p>
      </div>
    </div>
    </div>
<div class="row login-container">
    <div class="large-7  medium-12 small-12 columns input-block">
      <button data-reveal-id="addEventModal" id="addNewEvent" class="radius btn-main">Add Events</button>
			<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="event_data">
			</table>
		</div>
      </div>
     </div>
  </div>
</div>


</script>

	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/web-fonts.js"></script>
	<script src="js/sticky-footer.js"></script>
	<script src="js/foundation.js"></script>
	<script src="js/foundation.topbar.js"></script>
	<script src="js/foundation.reveal.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/model/eventModel.js"></script>
	<script src="js/model/editEventModel.js"></script>
	<script src="js/view/eventView.js"></script>
	<script>
		var eventView = new eventView();
		eventView.render();
		var addEvent = new addEvent({
			el : $("#addEventModal")
		});
		addEvent.render();
	</script>
	<script>
    $(document).foundation();
    </script>
     <c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="req"></c:set>
	<c:if test="${userPermission != null  && sessionUser != null}">
				<c:if test="${userPermission.getUserName() eq sessionUser.getUsername()}">
					<c:forEach items="${userPermission.roleActivityPermissionDTOs}" var="permission">
						<c:if test="${fn:containsIgnoreCase(req,permission.activityLink)}">
						<%-- <c:set value="${permission.permissions}" var="per"></c:set>
							<c:if test="${fn:containsIgnoreCase(per,'R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addNewEvent").css("display", "none");
										$("#flag").val("R");
									});
									</script>
								</c:if>
							<c:if test="${fn:containsIgnoreCase(per,'W')}" >
								<script>
									$(document).ready(function() {
										console.log('w');
										$("#addNewEvent").css("display", "block");
										$("#flag").val($("#flag").val() + ",W");
									});
								</script>
							</c:if>
							<c:if test="${fn:containsIgnoreCase(per,'D')}" >
								<script>
									$(document).ready(function() {
										console.log('D');
										$("#flag").val($("#flag").val() + ",D");
									});
								</script>
							</c:if>		 --%>
							<c:forEach items="${permission.permissions}" var="per" varStatus="status">
								<c:if test="${per eq ('')}" >
								<script>
									$(document).ready(function() {
										console.log('null');
										$("#addNewEvent").css("display", "none");
										$(".edit_button").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addNewEvent").css("display", "none");
										$("#flag").val("R");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#addNewEvent").css("display", "block");
										$("#flag").val($("#flag").val() + ",W");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('D')}" >
									<script>
									$(document).ready(function() {
										console.log('D');
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
