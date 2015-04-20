<!doctype html>
<html class="no-js" lang="en">
<head>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Permission</title>
<link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/foundation.min.css" />
<link rel="stylesheet" type="text/css" href="css/slick.css"/>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/smoothness/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    	.cf:before, .cf:after { content: ""; display: table; }
		.cf:after { clear: both; }
		.cf { zoom: 1; }
		.block{display:block; background-color:#fff; box-shadow: 0 0 16px #ccc; padding:1% 13px; max-width:89%; margin: 0 auto;}
		.btn-main{padding: 5px 12px;margin-top: 15px;float: right;margin-right: 32px; }
		.pageTitle{margin:20px 70px; display:block; font-size:20px; color:#000;}
		.error{border:1px solid red;}
		.input-left-main{width:100%; float:left;}
		.input-main{width:100%; float:left; margin-bottom:20px;}
		
		.input-main-input{ border-radius:3px !important; padding:4px 10px !important; height:auto; width:80% !important; float:left;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		
		.btn-main{padding:9px 56px; background-color:#000; color:#fff; border:none;  border-radius:5px; cursor:pointer; margin-bottom:20px;}
		
		.add-btn{width:auto; padding:5px 25px; margin-bottom:25px; background-color:#000; color:#fff; border-radius:5px; border:none}
		@media (max-width:640px) {
		
		.content-container{margin:0;}
		
		.btn-main{padding:10px 0; width:100%; float:left; margin-top:5px;}
		footer{padding:30px 0 0}
		.career-container li a div{width:100% !important;}
		.input-left-main{width:100%; float:right;}
		.input-right-main{width:100%; float:left; border:1px solid #ccc; padding:20px 0 0 2%; margin-top:10px; margin-bottom:10px;}
		.input-main-input{ border-radius:3px !important; padding:4px 1% !important; height:auto; width:98% !important; float:left;}
		
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
  <!-- <div class="content-container">
    <div class="row outer-title">
      <div class="large-12 medium-12 small-12 columns text-main">
        <p>Employee Permision</p>
      </div>
    </div>
    </div> -->
    </div>
     <!-- <div class="row login-container">
    <div class="large-7  medium-12 small-12 columns input-block">
    <span id="error"></span>
      <div class="input-left-main">
        <div class="input-main" style="border:1px solid #ccc; margin:0 0 20px 0; padding : 10px; ">
        <label>Select Group</label>
          <select>
          	<option>Admin</option>
          	<option>Senior</option>
          	<option>Java</option>
          	<option>.Net</option>
          	<option>UI Developer</option>
          	<option>Mobile</option>
          	<option>Q.A	</option>
          	<option>Ui Designer/Ui developer</option>
          	<option>Trainee</option>
          </select>
        </div>
				<table style="width: 100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th>No</th>
						<th>Screen Name</th>
						<th>Permission</th>
					</tr>
					<tr>
						<td style="border: 1px solid #ccc; text-align: center">1</td>
						<td style="border: 1px solid #ccc; text-align: center">Event
							1</td>
						<td style="border: 1px solid #ccc; text-align: center"><input
							name="" type="checkbox" value=""> &nbsp;R <input name=""
							type="checkbox" value=""> &nbsp;W <input name=""
							type="checkbox" value=""> &nbsp;D</td>

					</tr>
					<tr>
						<td style="border: 1px solid #ccc; text-align: center">2</td>
						<td style="border: 1px solid #ccc; text-align: center">Event
							2</td>
						<td style="border: 1px solid #ccc; text-align: center"><input
							name="" type="checkbox" value=""> &nbsp;R <input name=""
							type="checkbox" value=""> &nbsp;W <input name=""
							type="checkbox" value=""> &nbsp;D</td>
					</tr>
					<tr>
						<td style="border: 1px solid #ccc; text-align: center">3</td>
						<td style="border: 1px solid #ccc; text-align: center">Event3</td>
						<td style="border: 1px solid #ccc; text-align: center"><input
							name="" type="checkbox" value=""> &nbsp;R <input name=""
							type="checkbox" value=""> &nbsp;W <input name=""
							type="checkbox" value=""> &nbsp;D</td>
					</tr>
					<tr>
						<td colspan="3" style="border: 1px solid #ccc; text-align: center"><input
							value="Set Permission" class="add-btn" type="submit"></td>
					</tr>
				</table>
			</div>
      </div>
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

<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/web-fonts.js"></script>
	<script src="js/sticky-footer.js"></script>
	<script src="js/foundation.js"></script>
	<script src="js/foundation.topbar.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/model/PermissionModel.js"></script>
	<script src="js/view/PermissionView.js"></script>

<script type="text/template" id="group_template">
	<a href="" class="pageTitle">Employee Permision</a>
	<div class="block cf">
	      <div class="input-left-main" >
			<span id='msgBox' style='color:#008CBA;margin:0 0 20px 10px; padding :0 0 10px 0;'></span>        
			<div class="input-main" id="group_data" style="border:1px solid #ccc; margin:0 0 20px 0; padding : 10px; ">
	          Select Group : 
			</div>
				<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="event_data">
				</table>
				<input value='Set Permission' class='add-btn right' id='addButton' type='Button'>
		</div>
  </div>
 </script>
	<script>
var permissionView = new PermissionView();
permissionView.render();
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
										$("#addButton").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#addButton").css("display", "none");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#addButton").css("display", "block");
									});
									</script>
								</c:if>	
							</c:forEach>
						</c:if>
					</c:forEach>
				</c:if>
				</c:if>
</body>
