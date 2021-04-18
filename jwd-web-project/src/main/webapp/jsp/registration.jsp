<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <meta charset="utf-8">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="local"/>
    <fmt:message key="local.title.registration" var="title"/>
    <fmt:message key="local.locbutton.name.en" var="en_button"/>
    <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message key="local.email" var="email"/>
    <fmt:message key="local.password" var="password"/>
    <fmt:message key="local.sign_up" var="sign_up"/>
    <fmt:message key="local.back" var="back"/>
    <fmt:message key="local.name" var="name"/>
    <fmt:message key="local.phone" var="phone"/>
    <title>${title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration/style.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
</head>
<body>
<div>
	<div id="registration-form">
	  <header>
		<ul class="clearfix">
		<c:if test="${sessionScope.locale == 'en'}">
        <li class="active">
        </c:if>
        <c:if test="${sessionScope.locale != 'en'}">
        <li>
        </c:if>
			<form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			  <input type="hidden" name="command" value="en"/>
			  <input type="submit" value="${en_button}"/>
			</form>
		  </li>
		<c:if test="${sessionScope.locale == 'ru'}">
        <li class="active">
        </c:if>
        <c:if test="${sessionScope.locale != 'ru'}">
        <li>
        </c:if>
			<form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			  <input type="hidden" name="command" value="ru"/>
			  <input type="submit" value="${ru_button}"/>
			</form>
		  </li>
		</ul>
		<h1>${title}</h1>
		<a href="${pageContext.request.contextPath}/jsp/main.jsp">${back}</a>
	  </header>
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

</body>
</html>