<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
		
	</head>
	<body bgcolor="#FFFFFF">
	
	<form method="POST" action="<%=request.getContextPath()%>/j_security_check">
		Nome : <input size="15" name="j_username" class="text" value="admin"> <br />
		Senha: <input type="password" size="8" name="j_password" value="admin" class="text"> <br />
		<input type="submit" value="Logar">
	</form>
	
	<br/></br>
          
	</body>
</html>

