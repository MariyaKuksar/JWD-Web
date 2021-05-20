<%@ page isErrorPage="true" language="java" contentType="text/html; charset=utf-8"
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
  <fmt:message key="local.title.error" var="title"/>
  <fmt:message key="local.error" var="error"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
</head>
<body>
<%@ include file="/jsp/fragment/header.jsp" %>
	<p>${error}</p>
	<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
	<img src="${pageContext.request.contextPath}/upload?imageName=error.jpg">
	<mytag:copyright/>
</body>
</html>