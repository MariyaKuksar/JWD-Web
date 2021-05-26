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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration/style.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  
</head>
<body>
<div class="wrapper">
<%@ include file="/jsp/fragment/header.jsp" %>
<div class="main">
    <div id="submit-form">
        <div>
            <h1>${title}</h1>
        </div>

        <c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>

        <fieldset>
            <form action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="forgot_password"/>
              <input type="email" name="login" required placeholder="${email}" maxlength="45"/>
              <input type="submit" value="${send}"/>
            </form>
        </fieldset>
    </div>
   <%@ include file="/jsp/fragment/error_info.jsp" %>
</div>
</div>
<mytag:copyright/>
</body>
</html>