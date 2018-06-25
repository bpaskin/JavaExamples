<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Logon Page</title>
		<style>
			th { font-size:1.4em; text-align:center; padding-top:5px; padding-bottom:5px; background-color:#A7C942; color:#fff; }
			td { color:#000; background-color:#EAF2D3; padding-left:5px; padding-right:5px; }
	
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
	<body>
		<P>Welcome</P>
		<p>
			<c:if test="${not empty param['error']}">
				<c:out value="Error trying to log on to the system"/>
			</c:if>
		</p>
		<FORM action="j_security_check" method="post">
			<table>
				<tr>
					<th colspan="2">Enter your logon details</th>
				</tr>
				<tr>
					<td>user:</td>
					<td><INPUT type="text" name="j_username" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td>password:</td>
					<td><INPUT type="password" name="j_password" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td>code:</td>
					<td><INPUT type="text" name="j_code" size="20" maxlength="6"></td>
				</tr>
				<tr>
					<td colspan="2"><INPUT type="submit" name="login" value="Login"></td>
				</tr>
			</table>
		</FORM>
		<p>
			<a href="register.jsp">Register Here</a>
		</p>
	</body>
</html>