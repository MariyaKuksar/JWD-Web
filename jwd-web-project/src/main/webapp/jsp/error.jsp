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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/style.css" type="text/css" />
</head>
<body>
<div class="wrapper">
	<%@ include file="/jsp/fragment/header.jsp" %>
	<div class="error">
		<p>${error}</p>
		<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
		<div><img src="${pageContext.request.contextPath}/upload?imageName=error.jpg"></div>
	</div>
</div>
<mytag:copyright/>
</body>
</html>