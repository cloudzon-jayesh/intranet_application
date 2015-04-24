<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>CloudZon.huddle | Employee Details</title>
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
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="css/jquery.dataTables.css" />
<link type="text/css" rel="stylesheet" href="css/dataTables.responsive.css" />
<link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.5/css/jquery.dataTables.css" />
    <style type="text/css">
    	 cf:before, .cf:after { content: ""; display: table; }
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
		.reveal-modal { top: 100px !important; position: fixed;}
		.reveal-modal-bg {position: fixed;}
		}
th {border: 2px;}
.paging-nav {text-align: right;	padding-top: 2px;}
.paging-nav a {	margin: auto 1px;
	text-decoration: none;
	display: inline-block;
	padding: 1px 7px;
	background: #91b9e6;
	color: white;
	border-radius: 3px;
}
.paging-nav .selected-page {background: #187ed5;font-weight: bold;}
.hidden {display: none;}
</style>
</head>

<body>
<header>
		<nav class="top-bar" data-topbar role="navigation">
			<ul class="title-area">
				<li class="name">
					<h1>
						<a href="dashboard">CloudZon<span>.huddle</span><img
							src="_img/talk.png" alt="chat" height="16" width="16" hspace="3"
							style="padding-bottom: 10px"></a>
					</h1>
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
            				<li><a id="logOutBtn" href="user/logout.json">Logout</a></li>
            			</ul>
            			</li>
            		</c:if>
				</ul>
				<!-- Left Nav Section -->
			</section>
		</nav>
</header>
	<div class="main-container" id="main-container"></div>
	  <input type="hidden" Id="flag">
	<footer>
	<div class="row" id="top-footer">
		<div class="large-12 medium-12 small-12 columns">
			<div>
				<a href="#"><img src="img/g.png" alt="google+"></a>
			</div>
			<div>
				<a href="#"><img src="img/tweet.png" alt="twitter"></a>
			</div>
			<div>
				<a href="#"><img src="img/face.png" alt="facebook"></a>
			</div>
		</div>
	</div>
	<div class="row" id="bottom-footer">
		<div class="large-12 medium-12 small-12 columns">
			<div>©2015 CloudZon. All rights reserved.</div>
		</div>
	</div>
	</footer>
	
	<script type="text/template" id="employee_template">
		<a href="" class="pageTitle">Employee Details</a>
		<div class="block cf">
			<button href="#" id="new_emp_btn" data-reveal-id="firstModal" class="radius btn-main">Add New Employee</button>
			<form method="post">
			<table id="employee_data" cellspacing="0" style="width: 100%;">
			</table>
			</form>
		</div>				
	</script>
	
	 
	<div id="firstModal" class="reveal-modal" data-reveal aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
	</div>
	<script type="text/template" id="main_template">
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	<a href="" class="pageTitle">Employee Sign Up</a>
		<div class="block cf">
			<span id="error"></span>
			<form onSubmit="return false;">
			<div class="input-left-main">
				<div class="input-main">
					<label for="name">First Name</label>
					<div class="control-group firstName">
						<input type="text" id="firstName" maxlength="40" name="firstName">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="name">Last Name</label>
					<div class="control-group lastName">
						<input type="text" id="lastName" maxlength="40" name="lastName">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="name">Mobile Number : </label>
					<div class="control-group mobileNumber">
						<input type="text" id="mobileNumber" maxlength="12"
							name="mobileNumber"> <span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="email">Email</label>
					<div class="control-group email">
						<input type="text" id="email" maxlength="40" name="email">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="pswd">Password</label>
					<div class="control-group password">
						<input type="password" maxlength="40" id="password"
							name="password"> <span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="repswd">Retype Password</label>
					<div class="control-group retypePassword">
						<input type="password" maxlength="40" id="retypePassword"
							name="retypePassword"> <span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main">
					<label for="bday">Birthday</label>
					<div class="control-group dob" id="bdateSection">
						<input type="text" class="datepicker" id="b_date"
							readonly="readonly" autocomplete="off" name="birthday">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="input-main ">
					<label for="joiningDate">Joining Date</label>
					<div class="control-group joiningDate" id="JdateSection">
						<input type="text" class="datepicker" id="j_date"
							readonly="readonly" autocomplete="off" name="joiningDate">
						<span class="help-inline"></span>
					</div>
				</div>
			</div>
			<div class="input-right-main">
				<span class="title">Group</span>
				<div class="control-group groupName" id="groupName">
					<span class="help-inline"></span>
				</div>
			</div>
			<button  id="signUp_button" class='right radius btn-main open-second'>Sign Up</button>
		</form>
	</div>
	</script>
	<div id="secondModal" class="reveal-modal" data-reveal
		aria-labelledby="secondModalTitle" aria-hidden="true" role="dialog">
		<a class="close-reveal-modal" aria-label="Close">&#215;</a>
		<a href="" class="pageTitle">Employee Sign Up Profile Picture</a>
		<div class="block cf">
			<div id="imgdiv" style="width: 280px; height: 300px; border:solid 1px; vertical-align:middle; margin : 0px 35%; display: inline-block;">
				<img id="image-container" style="width: 280px; height: 300px;" src='<c:url value="images/profilePicture/profile-pic-img.png"></c:url>' />
			</div>
				<form enctype="multipart/form-data" 
				 method="post" action="user/uploadProfile.json" >	
					<input type="hidden" name="hemail" id="hemail" />			 
					<label for="profilePic">Select Profile Photo</label> <input
						type="file" id="fileinput" name="fileinput" accept="image/*"
						class="browser-select"> <br>
					 <button  class=" radius" type="button" id="load-image" >Preview</button>
					<button class=" radius" id="upload_button">Upload</button>
				</form>
		</div>
	</div>
	<div id="thirdModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
	<script type="text/template" id="edit_template">
	<a class="close-reveal-modal" aria-label="Close">&#215;</a>
	<a href="" class="pageTitle">Employee Update Details</a>
		<div class="block cf">
			<span id="error"></span>
			<form onSubmit="return false;">
				<div class="input-left-main">
					<div class="input-main">
						<label for="name">First Name</label>
						<div class="control-group editFirstName">
							<input type="text" id="editFirstName" maxlength="40" name="editFirstName">
							<span class="help-inline"></span>
						</div>
					</div>

					<div class="input-main">
						<label for="name">Last Name</label>
						<div class="control-group editLastName">
							<input type="text" id="editLastName" maxlength="40" name="editLastName">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main">
						<label for="name">Mobile Number : </label>
						<div class="control-group editMobileNumber">
							<input type="text" id="editMobileNumber" maxlength="12"
								name="editMobileNumber"> <span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main">
						<label for="email">Email</label>
						<div class="control-group editEmail">
							<input type="text" id="editEmail" maxlength="40" name="editEmail" readonly="readonly">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main">
						<label for="pswd">Password</label>
						<div class="control-group editPassword">
							<input type="password" maxlength="40" id="editPassword"
								name="editPassword" readonly="readonly"> <span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main">
						<label for="repswd">Retype Password</label>
						<div class="control-group editRetypePassword">
							<input type="password" maxlength="40" id="editRetypePassword"
								name="editRetypePassword" readonly="readonly"> <span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main">
						<label for="bday">Birthday</label>
						<div class="control-group dob" id="bdateSection">
							<input type="text" class="datepicker" id="editB_date"
								readonly="readonly" autocomplete="off" name="birthday">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="input-main ">
						<label for="joiningDate">Joining Date</label>
						<div class="control-group joiningDate" id="JdateSection">
							<input type="text" class="datepicker" id="editJ_date"
								readonly="readonly" autocomplete="off" name="joiningDate">
							<span class="help-inline"></span>
						</div>
					</div>
				</div>
				<div class="input-right-main" >
					<span class="title">Group</span>
					<div class="control-group groupName" id="editGroupName">
						<span class="help-inline"></span>
					</div>
				</div>
				<button class="right radius btn-main" id="update_button">Update</button>
		</form>
	</div>
	</script>
	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/web-fonts.js"></script>
	<script src="js/sticky-footer.js"></script>
	<script src="js/foundation.js"></script>
	<script src="js/foundation.reveal.js"></script>
	<script src="js/foundation.topbar.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<script src="js/paging.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/view/EmployeeView.js"></script>
	<script src="js/view/SignUpView.js"></script>
	<script src="js/model/SignUpModel.js"></script>
	
	<script src="js/model/EditSignUpModel.js"></script>
	<script src="js/view/EditEmployeeView.js"></script>
	<script src="js/model/EditEmployeeModel.js"></script>
	<script>
		var employeeView = new EmployeeView();
		employeeView.render();
	</script>
	<script>
		var signUpView = new SignUpView({
			el : $("#firstModal"),
		});
		signUpView.render();
		
		var fileInput = document.getElementById('fileinput');
	    var image = document.getElementById('image-container');
	    document.getElementById('load-image').addEventListener('click', function()
	    {
	        var fileUrl = window.URL.createObjectURL(fileInput.files[0]);
    	    image.src = fileUrl;
	    });
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
										$("#new_emp_btn").css("display", "none");
										$("#employee_data .editButton").css("display", "none");
										$(".editButton").hide();
									});
									</script>
								</c:if>
								<c:if test="${per eq ('R')}" >
								<script>
									$(document).ready(function() {
										console.log('r');
										$("#new_emp_btn").css("display", "none");
										$("#employee_data .editButton").css("display", "none");
										$(".editButton").hide();
										$("#flag").val("R");
									});
									</script>
								</c:if>
								<c:if test="${per eq ('W')}" >
									<script>
									$(document).ready(function() {
										console.log('w');
										$("#new_emp_btn").css("display", "block");
										$("#employee_data .editButton").css("display", "block");
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
</html>