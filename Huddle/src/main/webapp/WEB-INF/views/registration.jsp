<!doctype html>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>CloudZon.huddle | Sign Up</title>
    <link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <link rel="stylesheet" type="text/css" href="css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/smoothness/jquery-ui.min.css" rel="stylesheet" type="text/css" />
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
            <h1><a href="#">CloudZon<span>.huddle</span><img src="_img/talk.png" alt="chat" height="16"width="16" hspace="3" style="padding-bottom:10px"></a></h1>
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
           <p>Employee Sign Up</p>
          </div>
          
        </div>

		</div>
      </div>
    </div>
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
<script src="js/web-fonts.js"></script>
<script src="js/sticky-footer.js"></script>
<script src="js/foundation.js"></script>
<script src="js/foundation.topbar.js"></script>
<script src="js/jquery-ui.js"></script>
    <!-- backbone js -->
<script src="js/underscore.js"></script>
<script src="js/underscore-min.js"></script>
<script src="js/backbone-min.js"></script>
<script src="js/view/SignUpView.js"></script>
<script src="js/model/SignUpModel.js"></script>

        <script type="text/template" id="main_template">
	<div class="row login-container">
		<div class="large-7  medium-12 small-12 columns input-block">
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
				<div class="input-right-main" >
					<span class="title">Group</span>
					<div class="control-group groupName" id="groupName">
						<span class="help-inline"></span>
					</div>
				</div>
				<button class="right radius btn-main" id="signUp_button">Sign
					Up</button>
		
		</form>
	</div>
	</script>
	<script>
		var signUpView = new SignUpView({
			el : $("#main-container"),
		});
		signUpView.render();
	</script>
	<script src="js/ajaxJQuery/registration.js"></script>
	<script>
		$(document).ready(function() {
			$("#mobileNumber").keydown(function(event) 
			{
				// Allow only backspace and delete
				if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 ||(event.keyCode > 95 && event.keyCode < 106)) 
				{
					// let it happen, don't do anything
				}
				else 
				{
					// Ensure that it is a number and stop the keypress
					if (event.keyCode < 48 || event.keyCode > 57 ) 
					{
						event.preventDefault();	
					}	
				}
			});
		});
		$(document).foundation();
	</script>


</body>