<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Upload File</title>
	</head>
	<body>

		<form action="jaxrs/upload/file" method="POST" enctype="multipart/form-data">
			File to upload: <br/><input type="file" name="file"/><br/>
			Name:<input type="text" name="name"/><br/>
			<input type="submit" value="Upload"/>
      	</form>
   </body> 
</html>