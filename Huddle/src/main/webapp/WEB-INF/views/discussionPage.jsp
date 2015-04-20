<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Discussions</title>
   <link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <link rel="stylesheet" type="text/css" href="css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link type="text/css" rel="stylesheet" href="css/jquery.dataTables.css" />
	<link type="text/css" rel="stylesheet" href="css/dataTables.responsive.css" />
   	<link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.css" />
   	<link type="text/css" rel="stylesheet" href="css/jquery.datetimepicker.css">
   	<link type="text/css" rel="stylesheet" href="css/discussion.css">
    <style type="text/css">
    	.cf:before, .cf:after { content: ""; display: table; }
		.cf:after { clear: both; }
		.cf { zoom: 1; }
		.block{display:block; background-color:#fff; box-shadow: 0 0 16px #ccc; padding:1% 13px; max-width:89%; margin: 0 auto;}
		.btn-main{padding: 5px 12px;margin-top: 15px;float: right;margin-right: 32px; }
    	.pageTitle{margin:20px 70px; display:block; font-size:20px; color:#000;}
    	.errorText{color:red;}
		.input-left-main{width:48%; float:left;}
		.input-main{width:100%;}
		
		.input-main input{ border-radius:3px; padding:4px 10px; height:auto;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		.browser-select{border:1px solid #999; padding:7px;}
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
	<div id="addDiscussionModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
			<a href="" class="pageTitle">Add Discussion</a>
			<div class="block cf">
				<span id="error"></span>
					<div class="input-left-main">
						<input type="hidden" Id="hidId">
						<input type="hidden" id="hidUser" value='<c:out value="${sessionUser.getUsername() }"></c:out>'>
						<label for="discussionTopic">Discussion Topic </label>
						<div class="control-group discussionTopic">
							<textarea rows="2" cols="10" name="discussionTopic" id="discussionTopic" ></textarea>
							<span class="help-inline"></span>
						</div> 
						<div style="border:1px solid #ccc; padding:20px; margin-top:30px;">
							<span class="title">Group</span>
								<div class="control-group groupName" id="groupName">
									<span class="help-inline"></span>
								</div>
						</div>
						<Button class='radius right btn-main' id="addDiscussionButton">Add Discussion</Button>
					</div>
				</div>
			</div>

				
		<div id="commentModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog" >
		</div>
		<script type="text/template" id="comment_template">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Comment on Discussion</a>
		<div class="block cf">					
			<section id="commentDiv">
			</section>
		</div>	
		</script> 
		
		<!-- <div class="row login-container">
					<div class="large-7  medium-12 small-12 columns input-block">
						<span id="error"></span>
						<form id="discussionForm" onSubmit="return false;">
							<div class="input-left-main">
							<input type="hidden" Id="hidId">
								<label for="discussionTopic">Discussion Topic </label>
								<label id="userName"></label>
								<div class="control-group discussionTopic">
									<textarea rows="2" cols="10" name="discussionTopic" id="editDiscussionTopic" disabled="disabled"></textarea>
									<a id="addCommentButton"><img src= 'images/Add.png' style='width:25px; height:25px;'></a>
									<div id="commentDiv"></div>
								</div> 
							</div>
						</form>
					</div>
				</div>
			</div> -->
		
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

<script type="text/template" id="discussion_template">
	<a href="" class="pageTitle">Discussions</a>
	<div class="block cf">	
      <button data-reveal-id="addDiscussionModal" id="addDiscussionButton" class="radius btn-main">Add Discussions</button>
		<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="discussion_data">
		</table>
	</div>
</script>

	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/jquery.datetimepicker.js"></script>
	<script src="js/web-fonts.js"></script>
	<script src="js/sticky-footer.js"></script>
	<script src="js/foundation.js"></script>
	<script src="js/foundation.topbar.js"></script>
	<script src="js/foundation.reveal.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<script src="js/jquery.simple-dtpicker.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/model/discussionModel.js"></script>
	<script src="js/view/discussionView.js"></script>
	<script>
		var discussionView = new discussionView();
		discussionView.render();
		var addDiscussion = new addDiscussion({
			el : $("#addDiscussionModal")
		});
		addDiscussion.render();
	</script>
	<script>
	$(document).ready(function(){
    	$(document).foundation();
	});
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
										$("#addDiscussionButton").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addDiscussionButton").css("display", "none");
										$("#flag").val("R");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#addDiscussionButton").css("display", "block");
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
