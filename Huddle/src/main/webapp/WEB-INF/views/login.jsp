<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>CloudZon.huddle | Log In</title>

<link rel="icon" type="image/x-icon"
	href='<c:url value="_img/huddle.ico"></c:url>' />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Mono'
	rel='stylesheet' type='text/css'>

<!-- CUSTOM STYLE -->

<link rel="stylesheet"
	href='<c:url value="css/foundation.min.css"></c:url>' />
<link rel="stylesheet" type="text/css"
	href='<c:url value="css/slick.css"></c:url>' />
<link rel="stylesheet" type="text/css"
	href='<c:url value="css/style.css"></c:url>' />

<style type="text/css">
.errorText
		{
		 	color:red;
		}
</style>
<script src='<c:url value="js/jquery.js"></c:url>'></script>
	<script src='<c:url value="js/web-fonts.js"></c:url>'></script>
	<script src='<c:url value="js/sticky-footer.js"></c:url>'></script>
	<script src='<c:url value="js/foundation.js"></c:url>'></script>
	<script src='<c:url value="js/foundation.topbar.js"></c:url> '></script>
	<script src="js/foundation.reveal.js"></script>
	<%-- <script src='<c:url value="js/ajaxJQuery/login.js"></c:url>'></script> --%>
	<!-- backbone js -->
	<script src='<c:url value="/js/underscore-min.js"></c:url>'></script>
	<script src='<c:url value="/js/backbone-min.js"></c:url>'></script>
	<script src='<c:url value="js/view/LoginView.js"></c:url>'></script>
	<script src='<c:url value="js/model/LoginModel.js"></c:url>'></script>
	
</head>
<body>
	<header>
		<nav class="top-bar" data-topbar role="navigation">
			<ul class="title-area">
				<li class="name">
					<h1>
						<a href="#">CloudZon<span>.huddle</span><img
							src='<c:url value="_img/talk.png"></c:url>' alt="chat"
							height="16" width="16" hspace="3" style="padding-bottom: 10px"></a>
					</h1>
				</li>
				<!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
				<li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
			</ul>

			<!-- <section class="top-bar-section">
				Right Nav Section
				<ul class="right">
					<li><a href="#">Home</a></li>
					<li><a href="#">Company</a></li>
					<li><a href="#">Careers</a></li>
					<li><a href="#">Log In</a></li>
				</ul>

				Left Nav Section

			</section> -->
		</nav>
	</header>
	
	<div class="main-container" id="main-container">
		<div class="content-container">
			<div class="row outer-title">
				<div class="large-12 medium-12 small-12 columns">
					<p>
						Employee portal<br> <span>log in</span>
					</p>
				</div>
				<hr>
			</div>
			<script type="text/template" id="main_template">
			<div class="row login-container">
				<div class="large-7 medium-12 small-12 columns">
				
					<form name="loginUser" method="post" onsubmit="return false;">
						<label for="email">Email </label>
						<div class="control-group lUserName">
							<input type="text"  name="email" id="lUserName" >
							<span class="help-inline"></span>
						</div> 
						<label for="pswd">Password </label>
						<div class="lPassword control-group">
							<input type="password" name="pswd" id="lPassword" >
							<span class="help-inline"></span>
						</div>
						<p data-reveal-id="forgetPasswordModal">Forgot My Password</p>
						
						<button class="right radius" id="login_button">Log
							In</button>
					</form>
				
					<span id="error"></span>
				</div>
				<div class="large-5 medium-12 small-12 columns">
					<div class="login-img">
						<img src='<c:url value="_img/peoples4.png" ></c:url>'
							alt="employees" width="150">
					</div>
				</div>
			</div>
			</script>
					<script>
						var loginView = new LoginView({
							el : $("#main-container"),
						});
						loginView.render(); 
					</script>
		</div>
	</div>
	<div id="forgetPasswordModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		<div class="main-container" id="main-container">
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
							Forget Password <br><span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-7 medium-12 small-12 columns">
						<form name="forgetForm" method="post">
							<label for="email">Email </label>
							<div class="control-group lUserName">
								<input type="text" name="email" id="lUserName"> <span
									class="help-inline"></span>
							</div>
							<button class="right radius" id="login_button">Get Password</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer>
		<div class="row" id="top-footer">
			<div class="large-12 medium-12 small-12 columns">
				<div>
					<a href="#"><img src='<c:url value="_img/g.png" ></c:url>'
						alt="google+"></a>
				</div>
				<div>
					<a href="#"><img src='<c:url value="_img/tweet.png" ></c:url>'
						alt="twitter"></a>
				</div>
				<div>
					<a href="#"><img src='<c:url value="_img/face.png"></c:url>'
						alt="facebook"></a>
				</div>
			</div>
		</div>
		<div class="row" id="bottom-footer">
			<div class="large-12 medium-12 small-12 columns">
				<div>©2015 CloudZon. All rights reserved.</div>
			</div>
		</div>
	</footer>
	
	
	<script>
		$(document).foundation();
	</script>
	
</body>