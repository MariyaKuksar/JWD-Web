<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="customtag" prefix="mytag"%>

<html>
<head>
<meta charset="utf-8">
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="local" />
<fmt:message key="local.title.main" var="title" />
<fmt:message key="local.locbutton.name.en" var="en_button" />
<fmt:message key="local.locbutton.name.ru" var="ru_button" />
<fmt:message key="local.sign_in" var="sign_in" />
<fmt:message key="local.sign_up" var="sign_up" />
<fmt:message key="local.sign_out" var="sign_out" />
<fmt:message key="local.welcome" var="welcome" />
<fmt:message key="local.search" var="search" />
<fmt:message key="local.add_to_basket" var="add_to_basket" />
<fmt:message key="local.add_product_to_catalog" var="add_product_to_catalog" />
<fmt:message key="local.basket" var="basket" />
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

	<br />
	<a href="${pageContext.request.contextPath}/jsp/login.jsp">${sign_in}</a>
	<br />
	<p></p>
	<a href="${pageContext.request.contextPath}/jsp/registration.jsp">${sign_up}</a>
	<br />
	<p>${welcome}, ${sessionScope.login}!</p>
	<a href="${pageContext.request.contextPath}/controller?command=sign_out">${sign_out}</a>
	<br />
	<p></p>
	<a href="${pageContext.request.contextPath}/jsp/admin/adding_product.jsp">${add_product_to_catalog}</a>
	<p></p>
	<form action="${pageContext.request.contextPath}/controller" method="post">
		<input type="text" name="productName"> 
		<input type="hidden" name="command" value="find_products_by_name" /> 
		<input type="submit" name="submit" value="${search}">
	</form>
	
	<br />
	<p></p>
	<a href="${pageContext.request.contextPath}/controller?command=go_to_basket_page">${basket}</a>
	<br />
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
	
	<c:forEach var="category" items="${sessionScope.productCategories}">
		<img id="product_category_img" src="${pageContext.request.contextPath}/img/${category.imageName}" />
		<a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}">${category.categoryName}</a>
	</c:forEach>
	
	
	<c:forEach var="product" items="${requestScope.products}">
		<img id="product_img" src="${pageContext.request.contextPath}/img/${product.imageName}" />
			<tr>
				<td><c:out value="${product.productName}" /></td>
				<td><c:out value="${product.price}$" /></td>
			</tr>
			<form action="${pageContext.request.contextPath}/controller" method="post" >
			<input type="hidden" name="command" value="add_product_to_basket" /> 
			<input type="hidden" name="productId" value="${product.productId}" />
			<input type="submit" value="${add_to_basket}" />
		</form>
	</c:forEach>
	<mytag:copyright/>
</body>
</html>