<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Register</title>
		<style>
			th { font-size:1.4em; text-align:center; padding-top:5px; padding-bottom:5px; background-color:#A7C942; color:#fff; }
			td { color:#000; background-color:#EAF2D3; padding-left:5px; padding-right:5px; }
			h3 { color:red; font-size:2.0em}
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
	<body>
		<a href="index.html">Home</a>
		<p>
			<c:if test="${not empty sessionScope.message}">
				<h3>${sessionScope.message} </h3>
			</c:if>
		</p>
		<FORM action="register" method="post">
			<table>
				<tr>
					<th colspan="2">Enter your logon details</th>
				</tr>
				<tr>
					<td>user:</td>
					<td><INPUT type="text" name="username" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td>password:</td>
					<td><INPUT type="password" name="password" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td colspan="2"><INPUT type="submit" name="register" value="register"></td>
				</tr>
			</table>
		</FORM>
				<p>
			<c:if test="${not empty sessionScope.qrcode}">
				<h2>Welcome, ${user}</h2>
				<br>
				Download Google Authenticator to your phone then either scan the QR Code or enter the secret code.
				<br>
				<a href="https://itunes.apple.com/us/app/google-authenticator/id388497605" target="_other"><img src="images/AppleStore.svg"></a>
				&nbsp;
				<a href="https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2" target="_other"><img src="images/GooglePlay.png"></a>
				<br>
				<b>secret code: ${sessionScope.secret}</b>
			</c:if>
			<br>
			<c:if test="${not empty sessionScope.qrcode}">
				<img src="data:image/jpg;base64,${sessionScope.qrcode}"/>
			</c:if>
		</p>
	</body>
</html>