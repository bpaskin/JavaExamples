<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Secured Page</title>
</head>
<body>
you have accessed a secured page!

click link to <a href="${pageContext.request.contextPath}/logoff">logoff</a>
</body>
</html>