<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/fullcalendar.css" rel='stylesheet'>
<link href="css/fullcalendar.print.css" rel='stylesheet' media="print"> 
<script type="text/javascript" src="https://www.dropbox.com/static/api/2/dropins.js" id="dropboxjs" data-app-key="03vyg5780kyqqiq"></script>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<!-- <script type="text/javascript" src="js/fullcalendar.js"></script> -->

</head>
<body>
<img id="chooser-image" src="img/dropbox-logo.png" style="height: 25px; width: 25px;"/>
<script>
    document.getElementById("chooser-image").onclick = function () {
        Dropbox.choose({
            linkType: "direct",
            success: function (files) {
                alert(files[0].link);
            }
        });
    };
</script>
	<div id='calendar'></div>

	<div id="container">
	</div>
	<label id="textLink"></label> 
    <a id="link"></a>
    <!-- <a href="https://www.dropbox.com/s/uydn5845n9ybz3p/test.png?dl=0">Download</a> -->
<script type="text/javascript">
    // add an event listener to a Chooser button
    var button = Dropbox.createChooseButton({
            success: function(files) {
                //var linkTag = document.getElementById('link');
                var linkTag = document.getElementById('textLink');
                linkTag.href = files[0].link;
                //linkTag.textContent += files[0].link;
                for (var i = 0; i < files.length; i++){
                	linkTag.textContent += "" + files[i].link + ", ";
                }
            },
            linkType: 'direct',
            multiselect: true
        });
        document.getElementById('container').appendChild(button);
        
       /*  $(document).ready(function() {

            // page is now ready, initialize the calendar...

        	$('#calendar').fullCalendar({
    			header: {
    				left: 'prev,next today',
    				center: 'title',
    				right: 'month,basicWeek,basicDay'
    			},
    			events : [{
    				title :"hello",
    				start : "2015-03-04"
    			}],
    			defaultDate: '2015-02-12',
    			editable: true,
    			eventLimit: true, // allow "more" link when too many events
    			dayClick: function(date, jsEvent, view) {

    		        alert('Clicked on: ' + date.format());

    		        // change the day's background color just for fun
    		        $(this).css('background-color', 'red');

    		    }
    		});
    		

        }); */
</script>	
</body>
</html>