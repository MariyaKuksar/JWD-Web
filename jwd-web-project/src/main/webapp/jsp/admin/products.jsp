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
  <fmt:message key="local.title.products" var="title"/>
  <fmt:message key="local.article" var="article"/>
  <fmt:message key="local.product" var="product"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.amount" var="amount"/>
  <fmt:message key="local.in_stock" var="in_stock"/>
  <fmt:message key="local.on_order" var="on_order"/>
  <fmt:message key="local.pc" var="pc"/>
  <title>${title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basket/style.css" type="text/css" />
</head>
<body>
	<%@ include file="/jsp/fragment/header.jsp" %>
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	 <c:if test = "${not empty requestScope.products}"> 
	 <p><fmt:message key="${requestScope.title}"/></p>
		<table>
			<thead bgcolor="#c9c9c9" align="center">
				<tr>
				    <th>${article}</th>
					<th colspan="2">${product}</th>
					<th>${price}</th>
					<th>${amount}</th>
				</tr>
			</thead>
		<c:forEach var="product" items="${requestScope.products}">
			<tr align="center" valign="center">
			    <td>${product.productId}</td>
			    
				<td>
					<img id="product_img" src="${pageContext.request.contextPath}/upload?imageName=${product.imageName}" />
				</td>
			
				<td>${product.productName}</td>
				
				<td>${product.price}$</td>
				
				<td>${product.amount} ${pc}</td>
			</tr>
        </c:forEach>
		</table> 
      </c:if>  
      
	<mytag:copyright/>
 
</body>
</html>