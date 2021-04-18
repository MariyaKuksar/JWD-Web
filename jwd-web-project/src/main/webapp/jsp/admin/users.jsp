<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Users</title>
</head>
<body>
	<table>
	<c:if test = "${empty users}">
		<h1> no results were found for your search </h1>
		</c:if>
	<c:if test = "${not empty users}">
		<c:forEach var="user" items="${users}" varStatus="status">
			<tr>
				<td><c:out value="${user.userId}" /></td>
				<td><c:out value="${user.name}" /></td>
				<td><c:out value="${user.phone}" /></td>
			</tr>
		</c:forEach>
		</c:if>
	</table>
</body>
</html>