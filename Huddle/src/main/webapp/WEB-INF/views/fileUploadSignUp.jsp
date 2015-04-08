<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Sign Up</title>
<link rel="icon" type="image/x-icon" href="img/huddle.ico" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Mono'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/foundation.min.css" />
<link rel="stylesheet" type="text/css" href="css/slick.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/smoothness/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.errorText {color: red;}
.browser-select {border: 1px solid #999;padding: 7px;}
footer {padding: 30px 0 0}
.career-container li a div {width: 100% !important;}
#image-container"{width: 280px;min-height: 280px; height:300px; max-height:300px; float: left;margin: 3px;padding: 3px;}
</style>
<script src="js/jquery.js"></script>
<script src="js/web-fonts.js"></script>
<script src="js/sticky-footer.js"></script>
<script src="js/foundation.js"></script>
<script src="js/foundation.topbar.js"></script>
<script src="js/jquery-ui.js"></script>
<!-- backbone js  -->
<!-- <script src="js/underscore.js"></script>
<script src="js/underscore-min.js"></script>
<script src="js/backbone-min.js"></script>
<script src="js/view/FileUploadSignUpView.js"></script> -->
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
	<div class="main-container" id="main-container">
		<div class="content-container">
			<div class="row outer-title">
				<div class="large-12 medium-12 small-12 columns text-main">
					<p>Employee Sign Up Profile Picture</p>
				</div>
			</div>
		</div>
	</div>
	<!-- <script type="text/template" id="main_template"> -->
	<div class="row login-container"  align="center">

		<div class="large-7  medium-12 small-12 columns input-block" align="center">

			<div id="imgdiv" style="width: 280px; height: 300px; border:solid 1px; vertical-align:middle; display: inline-block;">
			<img id="image-container" src='<c:url value="images/profilePicture/profile-pic-img.png"></c:url>' />
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
	<!-- </script> -->
	<!-- <script>
		var fileUploadSignUpView = new FileUploadSignUpView({
			el : $("#main-container"),
		});
		fileUploadSignUpView.render();
	</script>  -->
	<footer>
		<div class="row" id="top-footer">
			<div class="large-12 medium-12 small-12 columns">
				<div>
					<a href="#"><img src="_img/g.png" alt="google+"></a>
				</div>
				<div>
					<a href="#"><img src="_img/tweet.png" alt="twitter"></a>
				</div>
				<div>
					<a href="#"><img src="_img/face.png" alt="facebook"></a>
				</div>
			</div>
		</div>
		<div class="row" id="bottom-footer">
			<div class="large-12 medium-12 small-12 columns">
				<div>©2015 CloudZon. All rights reserved.</div>
			</div>
		</div>
		</script>




	</footer>
	

	<script>
	
		$(document).ready(function() {
			var url = (document.URL).split("=");
			var email = url[1];
			//console.log("email : " + email);
			$("#hemail").val(email);
			console.log($("#hemail").val());
		});
		 var fileInput = document.getElementById('fileinput');
		    var image = document.getElementById('image-container');

		    document.getElementById('load-image').addEventListener('click', function(){

		        var fileUrl = window.URL.createObjectURL(fileInput.files[0]);
		       // document.getElementById("imgdiv").style.backgroundImage = "url('" + fileUrl + "')";
		        image.src = fileUrl;

		    });
		$(document).foundation();
</script>
		</body>
	