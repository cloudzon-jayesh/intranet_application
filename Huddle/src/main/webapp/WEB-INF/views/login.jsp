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
</style>
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

			<section class="top-bar-section">
				<!-- Right Nav Section -->
				<ul class="right">
					<li><a href="#">Home</a></li>
					<li><a href="#">Company</a></li>
					<li><a href="#">Careers</a></li>
					<li><a href="#">Log In</a></li>
				</ul>

				<!-- Left Nav Section -->

			</section>
		</nav>
	</header>
	<div class="main-container">
		<div class="content-container">
			<div class="row outer-title">
				<div class="large-12 medium-12 small-12 columns">
					<p>
						Employee portal<br> <span>log in</span>
					</p>
				</div>
				<hr>
			</div>
			<div class="row login-container">
				<div class="large-7 medium-12 small-12 columns">
					<form>
						<label for="email">Email <input type="text" name="email"></label>
						<label for="pswd">Password <input type="password"
							name="pswd"></label>
						<p>Forgot My Password</p>
						<button class="right radius">Log In</button>
					</form>
				</div>
				<div class="large-5 medium-12 small-12 columns">
					<div class="login-img">
						<img src='<c:url value="_img/peoples4.png" ></c:url>'
							alt="employees" width="150">
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
				<div>�2015 CloudZon. All rights reserved.</div>
			</div>
		</div>
	</footer>
	<script src='<c:url value="js/jquery.js"></c:url>'></script>
	<script src='<c:url value="js/web-fonts.js"></c:url>'></script>
	<script src='<c:url value="js/sticky-footer.js"></c:url>'></script>
	<script src='<c:url value="js/foundation.js"></c:url>'></script>
	<script src='<c:url value="js/foundation.topbar.js"></c:url> '></script>
	<script>
		$(document).foundation();
	</script>

</body>