<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="customtag" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="local"/>
  <fmt:message key="local.title.clients" var="title"/>
  <fmt:message key="local.email" var="email"/>
  <fmt:message key="local.name" var="name"/>
  <fmt:message key="local.phone" var="phone"/>
  <fmt:message key="local.status" var="status"/>
  <fmt:message key="local.unblock" var="unblock"/>
  <fmt:message key="local.block" var="block"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
</head>
<body>
<%@ include file="/jsp/fragment/header.jsp" %>
	<p></p>
   
            <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_user_by_login" />
                <input type="text" required placeholder ="${email}" name="login"/>
                <input type="submit" value="${search}"/>
            </form>
            
            <%@ include file="/jsp/fragment/error_info.jsp" %>
   <br/>
    <c:if test = "${not empty requestScope.users}"> 
		<table>
			<thead bgcolor="#c9c9c9" align="center">
				<tr>
					<th>${email}</th>
					<th>${name}</th>
					<th>${phone}</th>
					<th>${status}</th>
				</tr>
			</thead>
		<c:forEach var="user" items="${requestScope.users}">
			<tr align="center" valign="center">
			   				
				<td>${user.login}</td>
				
				<td>${user.name}</td>
				
				<td>${user.phone}</td>
				
				<td>${user.status}</td>
				
				<td>
					<c:if test="${user.status == 'BLOCKED'}">
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="unblock_user"/>
						<input type="hidden" name="userId" value="${user.userId}"/>
						<input type="submit" value="${unblock}"/>
					</form>
					</c:if>
					<c:if test="${user.status == 'ACTIVE'}">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="block_user"/>
						<input type="hidden" name="userId" value="${user.userId}"/>
						<input type="submit" value="${block}"/>
					</form>
                    </c:if>
				</td>
			</tr>
        </c:forEach>
		</table> 
		</c:if>  
	<br/>	
	<mytag:copyright/>
</body>
</html>