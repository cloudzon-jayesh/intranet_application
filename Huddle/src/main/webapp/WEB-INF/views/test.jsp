<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://www.dropbox.com/static/api/2/dropins.js" id="dropboxjs" data-app-key="03vyg5780kyqqiq"></script>
</head>
<body>
	<div id="container"></div>
    <a id="link"></a>
    <!-- <a href="https://www.dropbox.com/s/uydn5845n9ybz3p/test.png?dl=0">Download</a> -->
<script type="text/javascript">
    // add an event listener to a Chooser button
    var button = Dropbox.createChooseButton({
            success: function(files) {
                var linkTag = document.getElementById('link');
                linkTag.href = files[0].link;
                linkTag.textContent = files[0].link;
            },
            linkType: 'direct'
        });
        document.getElementById('container').appendChild(button);
</script>
</body>
</html>