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
  <fmt:message key="local.title.forgot_password" var="title"/>
  <fmt:message key="local.change_password" var="change_password"/>
  <fmt:message key="local.email" var="email"/>
  <fmt:message key="local.send" var="send"/>
  <title>${title}</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login/style.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />

</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jsp" %>
<div>
	<div id="login-form">
	  <header>
		<h1>${change_password}</h1>
	  </header>
	  
<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>

	  <fieldset>
		<form action="${pageContext.request.contextPath}/controller" method="post">
		  <input type="hidden" name="command" value="forgot_password"/>
		  <input type="text" name="login" required placeholder="${email}"/>
		  <input type="submit" value="${send}"/>
		</form>
	  </fieldset>
	</div>

	<c:if test="${errorMessageList != null}">
		<c:forEach var="errorMessageKey" items="${errorMessageList}">
		<fmt:message key="${errorMessageKey}" var="error"/>
			<div class="error">
				<h4>${error}</h4>
			</div>
		</c:forEach>
		<c:remove var="errorMessageList"/>
	</c:if>

	<c:if test="${infoMessage != null}">
	<fmt:message key="${infoMessage}" var="message"/>
		<div class="message">
			<h4>${message}</h4>
		</div>
		<c:remove var="infoMessage"/>
	</c:if>
</div>
<mytag:copyright/>
</body>
</html>