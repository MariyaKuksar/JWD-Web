<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="local"/>
  <fmt:message key="local.title.basket" var="title"/>
  <fmt:message key="local.locbutton.name.en" var="en_button"/>
  <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
  <title>${title}</title>
</head>
<body>
    <header>
        <form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			<input type="hidden" name="command" value="en" /> 
			<input type="submit" value="${en_button}" />
		</form>
		<form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
			<input type="hidden" name="command" value="ru" /> 
			<input type="submit" value="${ru_button}" />
		</form>
	</header>
	<p></p>
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
	
		<c:forEach var="product" items="${requestScope.order.products}">
		<img id="product_img" src="${pageContext.request.contextPath}/img/${product.key.imageName}" />
			<tr>
				<td><c:out value="${product.key.productName}" /></td>
				<td><c:out value="${product.key.price}$" /></td>
				<td><c:out value="${product.value}" /></td>
			</tr>
		</form>
	</c:forEach>
	
</body>
</html>