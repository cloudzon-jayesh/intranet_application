<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link type="text/css" rel="stylesheet" href="css/jquery.dataTables.css" />
<link type="text/css" rel="stylesheet" href="css/dataTables.responsive.css" />
<style type="text/css">
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
</style>


</head>
<body>
<header>
		<nav class="top-bar" data-topbar role="navigation">
			<ul class="title-area">
				<li class="name">
					<h1>
						<a href="#">CloudZon<span>.huddle</span><img
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
					<li><a href="huddle">Home</a></li>
					<li><a href="company">Company</a></li>
					<li><a href="careers">Careers</a></li>
					<li><a href="#">Log In</a></li>
				</ul>

				<!-- Left Nav Section -->

			</section>
		</nav>
	</header>
	<div class="main-container" id="main-container"></div>
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
			<div class="content-container">
			<div class="row outer-title">
				<div class="large-12 medium-12 small-12 columns">
					<p>
						Employee Details<br> <span>cloudZon culture</span>
					</p>
					<button id="new_emp_btn">Add New Employee</button>
				</div>
				<hr>
			</div>

			<div class="row">
				<div class="large-12 medium-12 small-12 columns">
					<form method="post">
					<table id="employee_data" cellspacing="0" style="width: 100%;">
						
					</table>
					</form>
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
	<script src="js/paging.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.responsive.js"></script>
	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
	<script src="js/view/EmployeeView.js"></script>
	<script>
		var employeeView = new EmployeeView();
		employeeView.render();

		$(document).foundation();
	</script>
</body>
</html>