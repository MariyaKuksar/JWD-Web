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
  <fmt:message key="local.title.basket" var="title"/>
  <fmt:message key="local.save" var="save"/>
  <fmt:message key="local.delete" var="delete"/>
  <fmt:message key="local.checkout" var="checkout"/>
  <fmt:message key="local.in_total" var="in_total"/>
  <fmt:message key="local.products" var="products"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.amount" var="amount"/>
  <fmt:message key="local.availability" var="availability"/>
  <fmt:message key="local.cost" var="cost"/>
  <fmt:message key="local.delete" var="delete"/>
  <fmt:message key="local.in_stock" var="in_stock"/>
  <fmt:message key="local.on_order" var="on_order"/>
  <fmt:message key="local.payment_method" var="payment_method"/>
  <fmt:message key="local.delivery_method" var="delivery_method"/>
  <fmt:message key="local.address" var="address"/>
  <fmt:message key="local.city" var="city"/>
  <fmt:message key="local.street" var="street"/>
  <fmt:message key="local.house" var="house"/>
  <fmt:message key="local.apartment" var="apartment"/>
  <title>${title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/basket/style.css" type="text/css" />
</head>
<body>
	<%@ include file="/jsp/fragment/header.jsp" %>
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	 <c:if test = "${not empty requestScope.basket.products}"> 
		<table>
			<thead bgcolor="#c9c9c9" align="center">
				<tr>
					<th colspan="2">${products}</th>
					<th>${price}</th>
					<th>${amount}</th>
					<th>${availability}</th>
					<th>${cost}</th>
					<th>${delete}</th>
				</tr>
			</thead>
		<c:forEach var="product" items="${requestScope.basket.products}">
			<tr align="center" valign="center">
				<td>
					<img id="product_img" src="${pageContext.request.contextPath}/upload?url=/Users/User/Desktop/img/${product.key.imageName}" />
				</td>
			
				<td>${product.key.productName}</td>
				
				<td>${product.key.price}$</td>
				
				<td>			
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="change_amount_of_product_in_basket"/>
						<input type="hidden" name="productId" value="${product.key.productId}"/>
						<input type="text" name="amountProduct" required placeholder="${product.value}" />
						<input type="submit" value="${save}"/>
					</form>
				</td>
				
				<td>
					<c:if test="${product.key.amount-product.value >= 0}">${in_stock}</c:if>
					<c:if test="${product.key.amount-product.value < 0}">${on_order}</c:if>
				</td>
				
				<td><c:out value="${product.key.price * product.value}" />$</td>
				
				<td>
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="remove_product_from_basket"/>
						<input type="hidden" name="productId" value="${product.key.productId}"/>
						<input type="submit" value="${delete}"/>
					</form>
				</td>
			</tr>
        </c:forEach>
		</table> 
		<div class="order">
			<div class="final_price">${in_total}: ${requestScope.basket.cost}$</div>
			<div>
				<form action="${pageContext.request.contextPath}/controller" method="post">
					<input type="hidden" name="command" value="checkout"/>
					  <input type="hidden" name="cost" value="${requestScope.basket.cost}"/>
	    <br />
	    <label>${payment_method}:</label>
	    <br />
	    <select size="1" id="paymentMethod" name="paymentMethod" required>
                        <c:forEach var="paymentMethod" items="${requestScope.basket.paymentMethodList}">
                            <option value="${paymentMethod}"><fmt:message key="local.payment_method.${paymentMethod}"/></option>
                        </c:forEach>
        </select>
        <br />
         <label>${delivery_method}:</label>
	    <br />
	    <select size="1" id="deliveryMethod" name="deliveryMethod" required>
                        <c:forEach var="deliveryMethod" items="${requestScope.basket.deliveryMethodList}">
                            <option value="${deliveryMethod}"><fmt:message key="local.delivery_method.${deliveryMethod}"/></option>
                        </c:forEach>
        </select>
        <br />
        <label>${address}:</label>
	    <br />
	    <input type="text" name="city" placeholder="${city}"/>
	    <br />
		<input type="text" name="street" placeholder="${street}"/>
		<br />
		<input type="text" name="house" placeholder="${house}"/>
		<br />
		<input type="text" name="apartment" placeholder="${apartment}"/>
		<br /> 
        <input type="submit" value="${checkout}"/>
				</form>
			</div>
		</div>
      </c:if>  
      
	<mytag:copyright/>
</body>
</html>