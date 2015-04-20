<!doctype html>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>CloudZon.huddle | Welcome</title>
    <link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <link rel="stylesheet" type="text/css" href="css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
	
	<style>
	.cf:before, .cf:after { content: ""; display: table; }
.cf:after { clear: both; }
.cf { zoom: 1; }
.clear { clear: both; }
.clearfix:after { content: "."; display: block; height: 0; clear: both; visibility: hidden; }
.clearfix { display: inline-block; }
	.dashboard-block{width:100%; display:block; background-color:#fff; box-shadow: 0 0 16px #ccc; padding:1% 0%;}
	.dashboard-block-main{width:100%; display:block; margin-bottom:25px; }
	.block{width:260px;  background-color:#fff; border:2px solid #018bb9;  margin-left:25px; float:left; margin-bottom: 25px; position: relative; min-height: 302px;}
	.scroll{overflow-y: auto;height:250px;}
	.block h2{font-size:24px;  font-family: "Open Sans",sans-serif; color:#018bb9; display:block; text-align:center; margin-bottom:10px; }
	.block h3{font-size:14px;  font-family: "Open Sans",sans-serif; color:#018bb9; display:block; background:url(img/osr-icon.png) no-repeat left 4px; padding:0 0 0 30px; margin-left:20px; margin-bottom:2px;}
	.block p{font-size:12px; text-align:right;  font-family: "Open Sans",sans-serif; color:#b8b8b8; display:block; text-align:center; margin-bottom:15px; margin-bottom:15px; }
	.block .more-btn{width:100%; font-size:20px; color:#fff; background-color:#018bb9; text-align:center; padding:15px 0px; display:block;}
	.block .more-btn:hover{background-color:#000; color:#fff;}
	</style>
	
	
  </head>
  <body>
    <header>
      <nav class="top-bar" data-topbar role="navigation">
        <ul class="title-area">
          <li class="name">
            <h1><a href="dashboard">CloudZon<span>.huddle</span><img src="img/talk.png" alt="chat" height="16"width="16" hspace="3" style="padding-bottom:10px"></a></h1>
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
    <input type="hidden" Id="hidUser" value='<c:out value="${sessionUser.getUsername() }"></c:out>'>
    <div class="main-container">
      <div class="content-container">
       <div class="dashboard-block cf"> 
       <div class="block" id="meetingDIV"></div>
       <div class="block" id="eventDIV"></div>
       <div class="block" id="projectDIV"></div>
       <div class="block" id="documentDIV"></div>
       <div class="block" id="discussionDIV"></div>
       <!--  <div class="block">
        <div class="scroll">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p><p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <p>last modified at sep 2, 2015
	   last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div>
	   </div> -->
	  <!--  <div class="dashboard-block-main cf"> -->
	   <!--
	  <div class="block">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div>
		<div class="block">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div> -->
       <!--  </div> -->
    
	 <!--  <div id="divBlock" class="dashboard-block-main cf">  -->
	   <!-- <div class="block">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div>
	  <div class="block">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div>
		<div class="block">
	   <h2>Projects</h2>
	   <h3>OSR</h3>
	   <p>last modified at sep 2, 2015</p>
	    <h3>Gratzeez</h3>
	   <p>last modified at sep 2, 2015</p>
	   <a href="#" class="more-btn">More</a>
	   </div> -->
       <!--  </div> -->
      </div>
    </div>
	</div>
	<div class=""></div>
    <footer>
     <div class="row" id="top-footer">
      <div class="large-12 medium-12 small-12 columns">
        <div><a href="#"><img src="img/g.png" alt="google+"></a></div>
        <div><a href="#"><img src="img/tweet.png" alt="twitter"></a></div>
        <div><a href="#"><img src="img/face.png" alt="facebook"></a></div>
      </div>
     </div>
     <div class="row" id="bottom-footer">
      <div class="large-12 medium-12 small-12 columns">
        <div>©2015 CloudZon. All rights reserved.</div>
      </div>
     </div>
    </footer>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/web-fonts.js"></script>
    <script type="text/javascript" src="js/sticky-footer.js"></script>
    <script type="text/javascript" src="js/foundation.js"></script>
    <script type="text/javascript" src="js/foundation.topbar.js"></script>
    <script type="text/javascript" src="js/slick.min.js"></script>
    <script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
    <script src="js/view/dashboardView.js"></script>
    <script>
    $(document).foundation();
    
    /* Slick slider */
    $(document).ready(function(){
                $('.huddle-slider').slick({
            dots: true, // Current slide indicator dots
            infinite: true, // Infinite looping
            swipe: true, // Enables swipe
                  touchMove: true, // Enables slide moving with touch
            cssEase: 'ease', // CSS3 easing
            easing: 'linear', // animate() fallback easing
            accessibility: true, // Enables tabbing and arrow key navigation
          });
            });

    /*end slider */

    </script>
<script>
		var dashboardView = new dashboardView({
			el : $("#main-container"),
		});
		dashboardView.render();  
	</script>
  </body>
