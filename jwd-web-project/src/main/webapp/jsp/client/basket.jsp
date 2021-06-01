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
  <fmt:message key="local.product" var="product"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.quantity" var="quantity"/>
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
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(0)">
<div class="wrapper">
	<%@ include file="/jsp/fragment/header.jsp" %>
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	 <c:if test = "${not empty requestScope.order.products}"> 
		<table>
			<thead bgcolor="#c9c9c9" align="center">
				<tr>
					<th colspan="2">${product}</th>
					<th>${price}</th>
					<th>${quantity}</th>
					<th>${availability}</th>
					<th>${cost}</th>
					<th>${delete}</th>
				</tr>
			</thead>
		<c:forEach var="product" items="${requestScope.order.products}">
			<tr align="center" valign="center">
				<td>
					<img id="product_img" src="${pageContext.request.contextPath}/upload?imageName=${product.key.imageName}" />
				</td>
			
				<td>${product.key.productName}</td>
				
				<td>${product.key.price}$</td>
				
				<td>			
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="change_quantity_of_product_in_basket"/>
						<input type="hidden" name="productId" value="${product.key.productId}"/>
						<input type="number" name="quantityOfProduct" required placeholder="${product.value}" min="1" max="10"/>
						<input type="submit" value="${save}"/>
					</form>
				</td>
				
				<td>
					<c:if test="${product.key.quantity-product.value >= 0}">${in_stock}</c:if>
					<c:if test="${product.key.quantity-product.value < 0}">${on_order}</c:if>
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
            <div class="final_price">${in_total}: ${requestScope.order.cost}$</div>
            <div class="delivery_and_payment">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="checkout"/>
                    <input type="hidden" name="cost" value="${requestScope.order.cost}"/>
                    <div>
                        <label for="paymentMethod">${payment_method}:</label>
                        <select size="1" id="paymentMethod" name="paymentMethod" required>
                                <c:forEach var="paymentMethod" items="${requestScope.paymentMethodList}">
                                    <option value="${paymentMethod}"><fmt:message key="local.payment_method.${paymentMethod}"/></option>
                                </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label>${delivery_method}:</label>
                        <c:forEach var="deliveryMethod" items="${requestScope.deliveryMethodList}">
                            <input type="radio" name="deliveryMethod" value="${deliveryMethod}" id="${deliveryMethod}_method_input" onclick="openDiv('${deliveryMethod}_method_info')" required/><label for="${deliveryMethod}_method_input"><fmt:message key="local.delivery_method.${deliveryMethod}"/></label>
                        </c:forEach>
                    </div>
                    <div id="method_info">
                        <div id="DELIVERY_method_info">
                            <label>${address}:</label>
                            <br/>
                            <label>${city}:<input type="text" name="city" placeholder="${city}" pattern="[a-zA-Zа-яА-Я-\s\.]{1,45}"/></label>
                            <label>${street}:<input type="text" name="street" placeholder="${street}" pattern="[\da-zA-Zа-яА-Я-\s\.]{1,45}"/></label>
                            <label>${house}:<input type="text" name="house" placeholder="${house}" pattern="\d{1,3}[\s-\/]?[абвгдАБВГД\d]?"/></label>
                            <label>${apartment}:<input type="number" name="apartment" placeholder="${apartment}" min="1" max="9999"/></label>
                        </div>
                    </div>
                    <input type="submit" value="${checkout}"/>
                </form>
            </div>
        </div>
      </c:if>  
</div>      
<mytag:copyright/>
<script>
    function openDiv(openId) {
		var openDiv = document.getElementById(openId);
		var allMethodInfoDiv = document.getElementById("method_info");
		if (allMethodInfoDiv.hasChildNodes()) {
			var children = allMethodInfoDiv.children;
			for (var i = 0; i < children.length; i++) {
				children[i].style.display = "none";
			}
		}
		if (openDiv != null) {
			openDiv.style.display = "block";
		}
    }
</script>
 
</body>
</html>