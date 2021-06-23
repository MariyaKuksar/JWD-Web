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
  <fmt:message key="local.access" var="access"/>
  <fmt:message key="local.message" var="message"/>
  <fmt:message key="local.send" var="send"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clients/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(3)">
<div class="wrapper">
<%@ include file="/jsp/fragment/header.jsp" %>
	<div class="main">

            <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_user_by_login" />
				<label>${email}:</label>
                <input type="email" required placeholder ="${email}" name="login" maxlength="45"/>
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
					<th>${access}</th>
					<th colspan="2">${message}</th>
				</tr>
			</thead>
		<c:forEach var="user" items="${requestScope.users}">
			<tr align="center" valign="center">

				<td align="left">${user.login}</td>

				<td>${user.name}</td>

				<td>${user.phone}</td>

				<td><fmt:message key="local.status.${user.status}" /></td>

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
                    <c:if test="${user.status == 'INACTIVE'}">
					<input type="submit" value="${block}" disabled/>
					</c:if>
				</td>
				<td>
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="send_message"/>
						<input type="hidden" name="email" value="${user.login}"/>
						<input type="text" name="message" required placeholder="${message}"/>
						<input type="submit" value="${send}"/>
					</form>
				</td>
			</tr>
        </c:forEach>
		</table>
		</c:if>
	</div>
</div>
<mytag:copyright/>
</body>
</html>