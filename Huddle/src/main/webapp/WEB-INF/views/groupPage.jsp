<!doctype html>
<html class="no-js" lang="en">
<head>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Group</title>
<link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/foundation.min.css" />
<link rel="stylesheet" type="text/css" href="css/slick.css"/>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery.dataTables.css" />
<link type="text/css" rel="stylesheet" href="css/dataTables.responsive.css" />
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/smoothness/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.css" />
<style type="text/css">
		.cf:before, .cf:after { content: ""; display: table; }
		.cf:after { clear: both; }
		.cf { zoom: 1; }
		.block{display:block; background-color:#fff; box-shadow: 0 0 16px #ccc; padding:1% 13px; max-width:89%; margin: 0 auto;}
		.btn-main{padding: 5px 12px;margin-top: 15px;float: right;margin-right: 32px; }
		.pageTitle{margin:20px 70px; display:block; font-size:20px; color:#000;}
		.errorText{color:red;}
    	.error{border:1px solid red;}
		.input-left-main{width:100%; float:left;}
		.input-main{width:100%; float:left; margin-bottom:20px;}
		
		.input-main-input{ border-radius:3px !important; padding:4px 10px !important; height:auto; width:80% !important; float:left;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		
		.add-btn{width:auto; padding:5px 25px; background-color:#000; color:#fff; border-radius:5px; border:none}
		@media (max-width:640px) {
		
		.content-container{margin:0;}
		
		.btn-main{padding:10px 0; width:100%; float:left; margin-top:5px;}
		footer{padding:30px 0 0}
		.career-container li a div{width:100% !important;}
		.input-left-main{width:100%; float:right;}
		.input-right-main{width:100%; float:left; border:1px solid #ccc; padding:20px 0 0 2%; margin-top:10px; margin-bottom:10px;}
		.input-main-input{ border-radius:3px !important; padding:4px 1% !important; height:auto; width:98% !important; float:left;}
		.reveal-modal { top: 100px !important; position: fixed;}
		.reveal-modal-bg {position: fixed;}
		}
		
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
            <c:if test="${ sessionUser !=null}">
            <li class="has-dropdown"><a href="#">Hi, <c:out value="${sessionUser.getUsername() }"></c:out></a>
            	<ul class="dropdown">
				<c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="req"></c:set>
				<c:if test="${userPermission != null  && sessionUser != null}">
				<c:if test="${userPermission.getUserName() eq sessionUser.getUsername()}">
					<c:forEach items="${userPermission.roleActivityPermissionDTOs}" var="permission">
						<%-- <c:if test="${fn:containsIgnoreCase(req,permission.activityLink)}">--%>
							<c:forEach items="${permission.permissions}" var="per" varStatus="status">
								<%-- <c:if test="${per eq ('R')}" >
								</c:if>
								<c:if test="${per eq ('W')}" >
									<li><a href="setGroup">Add Group</a></li>
									<li><a href="setActivity">Add Activity</a></li>
									<li><a href="setPermission">Add Permission</a></li>
									<li><a href="employee">Employess</a></li>
									<li><a href="setEvent">Add Event</a></li>
									<li><a href="setMeeting">Add Meeting</a></li>
									<li><a href="setProject">Add Project</a></li>
									<li><a href="setDocument">Add Document</a></li>
									<li><a href="setDiscussion">Add Discussion</a></li>
								</c:if>	 --%>
							
					<%-- 	</c:if>  --%>
						<%-- <c:set value="${permission.permissions}" var="per"></c:set> --%>
						<c:if test="${permission.activityLink eq ('setGroup') && per eq ('W')}">
							<li><a href="setGroup">Add Group</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setActivity') && per eq ('W')}">
							<li><a href="setActivity">Add Activity</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setPermission') && per eq ('W')}">
							<li><a href="setPermission">Add Permission</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('employee') && per eq ('W')}">
							<li><a href="employee">Employess</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setEvent') && per eq ('W')}">
							<li><a href="setEvent">Add Event</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setMeeting') && per eq ('W')}">
							<li><a href="setMeeting">Add Meeting</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setProject') && per eq ('W')}">
							<li><a href="setProject">Add Project</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setDocument') && per eq ('W')}">
							<li><a href="setDocument">Add Document</a></li>
						</c:if>
						<c:if test="${permission.activityLink eq ('setDiscussion') && per eq ('W')}">
							<li><a href="setDiscussion">Add Discussion</a></li>
						</c:if>
					</c:forEach>
					</c:forEach>
				</c:if>
				</c:if>
				<li><a id="logOutBtn" href="user/logout.json">Logout</a></li>            
            	</ul>
            </li>
            </c:if> 	
           </ul>
      <!-- Left Nav Section -->
    </section>
  </nav>
</header>
<div class="main-container" id="main-container">
  
    </div>
    <input type="hidden" Id="flag">
	<div id="addGroupModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Add Group</a>
		<div class="block cf">
			<div class="input-left-main">
					<input type="hidden" id="hidId">
					<div class="input-main">
						<label for="name">Group Name</label>
						<div class="control-group roleName">
							<input type="text" id="roleName" maxlength="40" name="roleName">
							<span class="help-inline"></span>
						</div>
					</div>			
					<button class='right radius btn-main' id="addGroupButton">Add Group</button>
			</div>
		</div>
	</div>
		<div id="editGroupModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
		<script type="text/template" id="edit_template">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
			<a href="" class="pageTitle">Update Group</a>
				<div class="block cf">
					<div class="input-left-main">
						<div class="input-main">
							<label for="name">Group Name</label>
							<div class="control-group roleName">
								<input type="text" id="editRoleName" maxlength="40" name="roleName">
								<span class="help-inline"></span>
							</div>
						</div>								 
						<button class='right radius btn-main' id="editGroupButton">Update Group</button>
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

<script type="text/template" id="group_template">
<a href="" class="pageTitle">Groups</a>
	<div class="block cf">
      <button data-reveal-id="addGroupModal" id="addNewGroup" class="radius btn-main">Add Group</button>
			<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="group_data">
			</table>
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
	<script src="js/model/groupModel.js"></script>
	<script src="js/view/GroupView.js"></script>
	<script>
		var groupView = new GroupView();
		groupView.render();
		var addGroup = new addGroup({
			el : $("#addGroupModal")
		});
		addGroup.render();
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
										$("#addNewGroup").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addNewGroup").css("display", "none");
										$("#flag").val("R");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#addNewGroup").css("display", "block");
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
