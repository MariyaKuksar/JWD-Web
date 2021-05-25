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
  <fmt:message key="local.title.supply" var="title"/>
  <fmt:message key="local.amount" var="amount"/>
  <fmt:message key="local.accept" var="accept"/>
  <fmt:message key="local.pc" var="pc"/>
  <fmt:message key="local.add" var="add"/>
  <fmt:message key="local.article" var="article"/>
  <fmt:message key="local.product" var="product"/>
  <fmt:message key="local.adding_product" var="adding_product"/>
  <fmt:message key="local.arrived_products" var="arrived_products"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/supply/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(2)">
<div class="wrapper">
	<%@ include file="/jsp/fragment/header.jsp" %>
	
	<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
	<div class="main">
	<p>${adding_product}:</p>
	<form class="supply_form" action="${pageContext.request.contextPath}/controller" method="post">
		<input type="hidden" name="command" value="add_product_to_supply"/>
		<label>${article}:</label>
		<input type="number" name="productId" required placeholder="" min="1" max="9223372036854775807"/>
		<label>${amount}:</label>
		<input type="number" name="amountProduct" required placeholder="" min="1" max="99"/>
		<input type="submit" value="${add}">
    </form>
	
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	<c:if test = "${not empty sessionScope.suppliedProducts}"> 
	<p>${arrived_products}:</p>
	<table>
		<thead bgcolor="#c9c9c9" align="center">
			<tr>
				<th>${article}</th>
				<th colspan="2">${product}</th>
				<th>${amount}</th>
			</tr>
		</thead>
	<c:forEach var="product" items="${sessionScope.suppliedProducts}">
		<tr align="center" valign="center">
			<td>${product.key.productId}</td>
			
			<td>
				<img id="product_img" src="${pageContext.request.contextPath}/upload?imageName=${product.key.imageName}" />
			</td>
		
			<td>${product.key.productName}</td>
			
			<td>${product.value} ${pc}</td>
		</tr>
	</c:forEach>
	</table> 
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="accept_products"/>
	<input type="submit" value="${accept}"/>
	</form>
	</c:if>
	</div>
</div>	
	<mytag:copyright/>
</body>
</html>