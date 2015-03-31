<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>CloudZon.huddle | Meetings</title>
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
    </div>
    
	<div id="addMeetingModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
			<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
						
							Add Meeting <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-7  medium-12 small-12 columns input-block">
						<span id="error"></span>
						<form id="meetingForm" method="post" onSubmit="return false;">
							<div class="input-left-main">
							<input type="hidden" Id="hidId">
								<label for="meetingName">Meeting Name </label>
								<div class="control-group meetingName">
									<input type="text" size="40" name="meetingName" id="meetingName" >
									<span class="help-inline"></span>
								</div> 
								<label for="description">Description</label>
								<div class="description control-group">
									<textarea rows="3" cols="10" name="description" id="description"></textarea>
									<span class="help-inline"></span>
								</div>
								<label for="date">Date And Time</label>
								<div class="date control-group">
									<input type="text" name="date" id="date" readonly="readonly" autocomplete="off">
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="input-right-main">
								<span class="title">Group</span>
								<div class="control-group groupName" id="groupName">
									<span class="help-inline"></span>
								</div>
							</div>
							<Button class='radius right btn-main' id="addMeetingButton">Add Meeting</Button>
						</form>
					</div>
				</div>
			</div>
		</div>
				
	<div id="editMeetingModal" class="reveal-modal" data-reveal
		aria-labelledby="firstModalTitle" aria-hidden="true" role="dialog">
		</div>
		<script type="text/template" id="edit_template">
<div class="content-container">
				<div class="row outer-title">
					<div class="large-12 medium-12 small-12 columns">
						<p>
						
							Update Meeting <br>
							<span></span>
						</p>
					</div>
					<hr>
				</div>
				<div class="row login-container">
					<div class="large-7  medium-12 small-12 columns input-block">
						<span id="error"></span>
						<form id="meetingForm" method="post" onSubmit="return false;">
							<div class="input-left-main">
							<input type="hidden" Id="hidId">
								<label for="meetingName">Meeting Name </label>
								<div class="control-group meetingName">
									<input type="text" size="40"  name="meetingName" id="editMeetingName" >
									<span class="help-inline"></span>
								</div> 
								<label for="description">Description</label>
								<div class="description control-group">
									<textarea rows="3" cols="10" name="description" id="editDescription"></textarea>
									<span class="help-inline"></span>
								</div>
								<label for="date">Date And Time</label>
								<div class="date control-group">
									<input type="text" name="date" id="editDate" readonly="readonly" autocomplete="off">
									<span class="help-inline"></span>
								</div>
							</div>
							<div class="input-right-main">
								<span class="title">Group</span>
								<div class="control-group groupName" id="editGroupName">
									<span class="help-inline"></span>
								</div>
							</div>
							<button class='right radius btn-main' id="editMeetingButton">Update Meeting</button>
						</form>
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

<script type="text/template" id="meeting_template">
<div class="content-container">
    <div class="row outer-title">
      <div class="large-12 medium-12 small-12 columns text-main">
        <p>Meetings</p>
<input type="hidden" id="hidUser" value='<c:out value="${sessionUser.getUsername() }"></c:out>'>
      </div>
    </div>
    </div>
<div class="row login-container">
    <div class="large-7  medium-12 small-12 columns input-block">
      <button data-reveal-id="addMeetingModal" class="radius btn-main">Add Meetings</button>
			<table style="width:100%" border="0" cellpadding="0" cellspacing="0" id="meeting_data">
			</table>
		</div>
      </div>
     </div>
  </div>
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
	<script src="js/model/meetingModel.js"></script>
	<script src="js/view/meetingView.js"></script>
	<script>
		var meetingView = new meetingView();
		meetingView.render();
		var addMeeting = new addMeeting({
			el : $("#addMeetingModal")
		});
		addMeeting.render();
	</script>
	<script>
    $(document).foundation();
    </script>
</body>
