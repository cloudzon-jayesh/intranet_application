<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700" rel='stylesheet' />
<!-- The main CSS file -->
<style type="text/css">
.progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
.bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
.percent { position:absolute; display:inline-block; top:3px; left:48%; }
</style>
</head>
<body>
	<form onsubmit="return false" enctype="multipart/form-data">
	<!-- 	<input type="hidden" id="hidId" value="1"> -->
		<input type="file" id="file" name="file">
		
		<input type="submit" id="btn" value="Upload File to Server">
		
		<!-- <button id="testDrop">TestDrop</button>
		<div class="progress">
			<div class="bar"></div>
			<div class="percent">0%</div>
		</div>

		<div id="status"></div>
 -->
	</form>
		
		<!-- JavaScript Includes -->
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.form.js"></script>
		<script>
$(document).ready (function() {
	
	$("#btn").click(function() {
		var data = new FormData();
		data.append('file', $("#file")[0].files[0]);
		$.ajax({
				type : "POST",
				url : "user/testDropBox.json",
				data : data,
				cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
			success: function (response) {
				console.log("Add Success");
			},
			error : function(e) {
				console.log("error");
			}
		});
	});
});	
	/* $("#testDrop").click(function(){
		$.ajax({
			url : "user/testDropBox.json"
		});
		
	});
    
	var bar = $('.bar');
	var percent = $('.percent');	
	var status = $('#status');
	
	
	var data = new FormData();
	 data.append('projectId', $("#hidId").val());
	 $.each($("input[name^=images]"), function(i, obj) {
	        $.each(obj.files,function(j,file){
	            data.append('images',file);
	            console.log(file);
	        })
	}); */
	 /*$("#btn").click(function() {
		 
	 	  $.ajax({
			    url: 'user/addProjectImages.json',
			    data:data,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    mimeType: "multipart/form-data",
			    
			    beforeSend: function() {
		        	status.empty();
		        	var percentVal = '0%';
		        	bar.width(percentVal)
		        	percent.html(percentVal);
		    	},
		    	uploadProgress: function(event, position, total, percentComplete) {
			        var percentVal = percentComplete + '%';
		    	    bar.width(percentVal)
		        	percent.html(percentVal);
					//console.log(percentVal, position, total);
		    	},
		    	success: function() {
			        var percentVal = '100%';
		    	    bar.width(percentVal)
		        	percent.html(percentVal);
		    	},
				complete: function(xhr) {
					status.html(xhr.responseText);
				}
	 	 	}); 
	 	 }); */

       
</script>
</body>
</html>