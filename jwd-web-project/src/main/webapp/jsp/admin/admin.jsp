<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Admin</title>
</head>
<body>
	<h1>Users</h1>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="find_all_users" /> 
		<input type="submit" name="submit" value="show all users">
	</form>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="text" name="name" value=""> 
		<input type="hidden" name="command" value="find_users_by_name" /> 
		<input type="submit" name="submit" value="search">
	</form>
</body>
</html>

