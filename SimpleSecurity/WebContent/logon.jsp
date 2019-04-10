<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logon</title>
</head>
<body>

<FORM action="j_security_check" method="post">
	User: <INPUT type="text" name="j_username" size="20" maxlength="20"><BR/>
	Password: <INPUT type="password" name="j_password" size="20" maxlength="20"><BR/>
	<INPUT type="submit" name="login" value="Login">
</FORM>

</body>
</html>