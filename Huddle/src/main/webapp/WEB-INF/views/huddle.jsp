<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>CloudZon.huddle | Welcome</title>
    <link rel="icon" type="image/x-icon" href="img/huddle.ico"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Mono' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/foundation.min.css" />
    <link rel="stylesheet" type="text/css" href="css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
<style type="text/css" media="screen">
</style>
</head>
  <body>
      <header>
      <nav class="top-bar" data-topbar role="navigation">
        <ul class="title-area">
          <li class="name">
            <h1><a href="#">CloudZon<span>.huddle</span><img src="img/talk.png" alt="chat" height="16"width="16" hspace="3" style="padding-bottom:10px"></a></h1>
          </li>
           <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
          <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
        </ul>

        <section class="top-bar-section">
          <!-- Right Nav Section -->
          <c:set value="${requestScope['javax.servlet.forward.servlet_path']}" var="req"></c:set>
          <ul class="right">
            <li><a href="huddle">Home</a></li>
            <li><a href="company">Company</a></li>
            <li><a href="careers">Careers</a></li>
            
             <c:if test="${ sessionUser !=null}">
            <li class="has-dropdown"><a href="#">Hi, <c:out value="${sessionUser.getUsername() }"></c:out></a>
            	<ul class="dropdown">
				<li><a href="setGroup">Add Group</a></li>
				<li><a href="setActivity">Add Activity</a></li>
				<li><a href="setPermission">Add Permission</a></li>
				<li><a href="setEvent">Add Event</a></li>
				<li><a href="setMeeting">Add Meeting</a></li>
				<li><a href="setProject">Add Project</a></li>
				<li><a href="setDocument">Add Document</a></li>
				<li><a id="logOutBtn" href="user/logout.json">Logout</a></li>            
            	</ul>
            </li>
            </c:if> 	
           </ul>
          <!-- Left Nav Section -->
        </section>
      </nav>
    </header>
    <div class="main-container">
      <div class="content-container">
        <div class="row" id="box-A">
          <div class="large-12 medium-12 small-12 columns">
            <div  class="slider-container">
              <div class="huddle-slider">
                <div><img src="img/sampleSlide.png"/></div>
                <div><img src="img/sampleSlide.1.png"/></div>
                <div><img src="img/sampleSlide.2.png"/></div>
              </div>
            </div>
          </div>
          <hr>
        </div>
        <div class="row" id="box-B">
          <div class="large-4 medium-12 small-12 columns">
           <a href="employee"><div class="box-B">
             <div class="box-title">employees</div>
             <div><img src="img/person2.png" alt="log in"></div>
            </div></a>
          </div>
          <div class="large-4 medium-12 small-12 columns">
            <a href="company"><div class="box-B">
             <div class="box-title">company</div>
             <div><img src="img/companyA.png" alt="about us"></div>
            </div></a>
          </div>
          <div class="large-4 medium-12 small-12 columns">
            <a href="careers"><div class="box-B">
             <div class="box-title">careers</div>
             <div><img src="img/careers.png" alt="hiring"></div>
            </div></a>
          </div>
        </div>
      </div>
    </div>
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
    	<!-- backbone js -->
	<script src="js/underscore-min.js"></script>
	<script src="js/backbone-min.js"></script>
    <script src="js/view/HuddleView.js"></script>
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

  </body>
