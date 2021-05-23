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
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basket/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(5)">
<%@ include file="/jsp/fragment/header.jsp" %>
<%@ include file="/jsp/fragment/error_info.jsp" %>
	<p></p>
	<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
	
	<form action="${pageContext.request.contextPath}/controller" method="post">
	<input type="hidden" name="command" value="add_product_to_supply"/>
	<br />
	<input type="number" name="productId" required placeholder="${article}" min="1" max="9223372036854775807"/>
    <br />
	<input type="number" name="amountProduct" required placeholder="${amount}" min="1" max="99"/>
    <br />
    <input type="submit" value="${add}">
    </form>
	
	   <c:if test = "${not empty sessionScope.suppliedProducts}"> 
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
	
	<mytag:copyright/>
</body>
</html>