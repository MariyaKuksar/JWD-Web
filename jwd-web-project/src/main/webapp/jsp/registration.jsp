<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="customtag" prefix="mytag"%>

<html>
<head>
    <meta charset="utf-8">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="local"/>
    <fmt:message key="local.title.registration" var="title"/>
    <fmt:message key="local.email" var="email"/>
    <fmt:message key="local.password" var="password"/>
    <fmt:message key="local.sign_up" var="sign_up"/>
    <fmt:message key="local.name" var="name"/>
    <fmt:message key="local.phone" var="phone"/>
    <title>${title}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration/style.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
    
</head>
<body>
<%@ include file="/jsp/fragment/header.jsp" %>
<div>
	<div id="submit-form">
	  <div>
		<h1>${title}</h1>
	  </div>
	  
<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>

	  <fieldset>
		<form action="${pageContext.request.contextPath}/controller" method="post">
		  <input type="hidden" name="command" value="sign_up"/>
		  <input type="text" name="userName" required placeholder="${name}"/>
		  <input type="text" name="phone" required placeholder="${phone}"/>
		  <input type="text" name="login" required placeholder="${email}"/>
		  <input type="password" name="password" required placeholder="${password}"/>  
		  <input type="submit" value="${sign_up}"/>
		</form>
	  </fieldset>
	</div>
	
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
</div>
<mytag:copyright/>
</body>
</html>