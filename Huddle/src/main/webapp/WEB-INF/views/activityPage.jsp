<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Activity</title>
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

		.errorText{color:red;}
    	.error{border:1px solid red;}
		.input-left-main{width:100%; float:left;}
		.input-main{width:100%; float:left; margin-bottom:20px;}
		
		.input-main-input{ border-radius:3px !important; padding:4px 10px !important; height:auto; width:80% !important; float:left;}
		.input-main label{margin-bottom:10px;}
		.input-block{width:100%; float:left;}
		
		.btn-main{padding:9px 56px;  background-color:#000; color:#fff; border:none;  border-radius:5px; cursor:pointer; margin-bottom:20px;}
		
		.add-btn{width:auto; padding:5px 25px; background-color:#000; color:#fff; border-radius:5px; border:none}
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
  
    </div>
	<div id="addActivityModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
							Add Activity <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-8 medium-12 small-12 columns">
						<div class="input-left-main">
							<div class="input-main"
								style="border: 1px solid #ccc; margin: 0 0 20px 0; padding: 10px;">
								<input type="hidden" Id="hidId">
								
						<label for="activityName">Activity Name </label>
						<div class="control-group activityName">
							<input type="text"  name="activityName" id="activityName" >
							<span class="help-inline"></span>
						</div> 
						<label for="activityLink">Activity Link </label>
						<div class="activityLink control-group">
							<input type="text" name="activityLink" id="activityLink" >
							<span class="help-inline"></span>
						</div>
						<button class='right radius btn-main' id="addActivityButton">Add Activity</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="editActivityModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
		<script type="text/template" id="edit_template">
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
							Update Activity <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-8 medium-12 small-12 columns">
						<div class="input-left-main">
							<div class="input-main"
								style="border: 1px solid #ccc; margin: 0 0 20px 0; padding: 10px;">
								<input type="hidden" Id="hidId">
								
						<label for="activityName">Activity Name </label>
						<div class="control-group activityName">
							<input type="text"  name="activityName" id="editActivityName" >
							<span class="help-inline"></span>
						</div> 
						<label for="activityLink">Activity Link </label>
						<div class="activityLink control-group">
							<input type="text" name="activityLink" id="editActivityLink" >
							<span class="help-inline"></span>
						</div>
						<button class='right radius btn-main' id="editActivityButton">Update Activity</button>
							</div>
						</div>
					</div>
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

<script type="text/template" id="activity_template">
<div class="content-container">
    <div class="row outer-title">
      <div class="large-12 medium-12 small-12 columns text-main">
        <p>Add Group</p>
      </div>
    </div>
    </div>
<div class="row login-container">
    <div class="large-7  medium-12 small-12 columns input-block">
      <button data-reveal-id="addActivityModal" class="radius btn-main">Add Activity</button>
			<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="activity_data">
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
	<script src="js/model/activityModel.js"></script>
	<script src="js/view/activityView.js"></script>
	<script>
		var activityView = new activityView();
		activityView.render();
		var addActivity = new addActivity({
			el : $("#addActivityModal")
		});
		addActivity.render();
	</script>
	<script>
    $(document).foundation();
    </script>
</body>
