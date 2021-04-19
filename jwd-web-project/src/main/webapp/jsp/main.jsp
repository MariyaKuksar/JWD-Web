<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <meta charset="utf-8">
    <fmt:setLocale value="${locale}"/>
  <fmt:setBundle basename="local"/>
  <fmt:message key="local.title.main" var="title"/>
  <fmt:message key="local.locbutton.name.en" var="en_button"/>
  <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
  <fmt:message key="local.sign_in" var="sign_in"/>
  <fmt:message key="local.sign_up" var="sign_up"/>
  <fmt:message key="local.sign_out" var="sign_out"/>
  <fmt:message key="local.welcome" var="welcome"/>
  <title>${title}</title>
</head>
<body>
	  <header>
			<form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			  <input type="hidden" name="command" value="en"/>
			  <input type="submit" value="${en_button}"/>
			</form>
			<form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			  <input type="hidden" name="command" value="ru"/>
			  <input type="submit" value="${ru_button}"/>
			</form>
	  </header>
		
		  <br />
		  <a class="info" href="${pageContext.request.contextPath}/jsp/login.jsp">${sign_in}</a>
		  <br />
		  <a class="info" href="${pageContext.request.contextPath}/jsp/registration.jsp">${sign_up}</a>
		  <br />
		  <p>${welcome}, ${sessionScope.login}!</p>
        <a href="controller?command=sign_out">${sign_out}</a>
</body>
</html>