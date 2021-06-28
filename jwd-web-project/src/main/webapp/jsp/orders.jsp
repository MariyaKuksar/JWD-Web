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
  <fmt:message key="local.title.orders" var="title"/>
  <fmt:message key="local.order_number" var="order_number"/>
  <fmt:message key="local.date_of_issue" var="date_of_issue"/>
  <fmt:message key="local.cost" var="cost"/>
  <fmt:message key="local.payment_method" var="payment_method"/>
  <fmt:message key="local.delivery_method" var="delivery_method"/>
  <fmt:message key="local.cancel" var="cancel"/>
  <fmt:message key="local.address" var="address"/>
  <fmt:message key="local.status" var="status"/>
  <fmt:message key="local.info" var="info"/>
  <fmt:message key="local.cancel" var="cancel"/>
  <fmt:message key="local.detail" var="detail"/>
  <fmt:message key="local.close_details" var="close_details"/>
  <fmt:message key="local.search" var="search"/>
  <fmt:message key="local.email" var="email"/>
  <fmt:message key="local.process" var="process"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.quantity" var="quantity"/>
  <fmt:message key="local.cost" var="cost"/>
  <fmt:message key="local.payment_method" var="payment_method"/>
  <fmt:message key="local.delivery_method" var="delivery_method"/>
  <fmt:message key="local.address" var="address"/>
  <fmt:message key="local.search_orders_by" var="search_orders_by"/>
  <fmt:message key="local.customer" var="customer"/>
  <title>${title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<c:if test="${sessionScope.role == 'ADMIN'}">
<body onload="selectMenu(1)">
</c:if>
<c:if test="${sessionScope.role == 'CLIENT'}">
<body onload="selectMenu(2)">
</c:if>
<div class="wrapper">
	<%@ include file="/jsp/fragment/header.jsp" %>
	<div class="main">
		<c:if test="${sessionScope.role == 'ADMIN'}">
		<div class="filters">
		<p>${search_orders_by}:</p>
		<form action="${pageContext.request.contextPath}/controller" method="get">
			<label>${status}:</label>
			<input type="hidden" name="command" value="find_orders_by_status"/>
			<select size="1" id="status" name="status" required >
				<c:forEach var="orderStatus" items="${requestScope.orderStatusList}">
					<option value="${orderStatus}"><fmt:message key="local.order_status.${orderStatus}" /></option>
				</c:forEach>
			</select>
			<input type="submit" value="${search}"/>
		</form>
		<form action="${pageContext.request.contextPath}/controller" method="get">
			<label>${order_number}:</label>
			<input type="hidden" name="command" value="find_order_by_id"/>
			<input type="number" name="orderId" required placeholder="№" min="1" max="9223372036854775807"/>
			<input type="submit" value="${search}"/>
		</form>
		<form action="${pageContext.request.contextPath}/controller" method="get">
			<label>${customer}:</label>
			<input type="hidden" name="command" value="find_user_orders"/>
			<input type="email" name="login" required placeholder="${email}" maxlength="45"/>
			<input type="submit" value="${search}"/>
		</form>
		</div>
		</c:if>
		
      <%@ include file="/jsp/fragment/error_info.jsp" %>
      
    <c:if test = "${not empty requestScope.orders}">
	<table class="orders_table">
		<thead bgcolor="#c9c9c9" align="center">
			<tr>
				<th>№</th>
				<th>${date_of_issue}</th>
				<th>${cost}</th>
				<th>${payment_method}</th>
				<th colspan="2">${delivery_method}</th>
				<th>${status}</th>
				<c:if test="${sessionScope.role == 'CLIENT'}">
				<th>${cancel}</th>
				</c:if>
				<c:if test="${sessionScope.role == 'ADMIN'}">
				<th>${process}</th>
				</c:if>
				<th>${info}</th>
			</tr>
		</thead>
		<c:forEach var="order" items="${requestScope.orders}">
		<tr align="center" valign="center">
			<td>${order.orderId}</td>
			<td>
				<fmt:parseDate value="${order.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
				<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime }" />
			</td>
			<td>${order.cost}$</td>
			<td><fmt:message key="local.payment_method.${order.paymentMethod}"/></td>
			<td><fmt:message key="local.delivery_method.${order.deliveryMethod}"/></td>
			<td>
				<c:if test="${order.deliveryMethod == 'DELIVERY'}">
				${address}: ${order.address.city}, ${order.address.street}, ${order.address.house}-${order.address.apartment}
				</c:if>
			</td>
			<td><fmt:message key="local.order_status.${order.orderStatus}" /></td>

			<td valign="center" align="center" >
				<c:if test="${sessionScope.role == 'ADMIN'}">
				<form class="process_order_form" action="${pageContext.request.contextPath}/controller" method="post">
					<input type="hidden" name="command" value="process_order"/>
					<input type="hidden" name="orderId" value="${order.orderId}"/>
					<input type="hidden" name="status" value="${order.orderStatus}"/>
					<c:if test="${order.orderStatus == 'PLACED' || order.orderStatus == 'ACCEPTED' || order.orderStatus == 'READY'}">
					<input type="submit" value="${process}"/>
					</c:if>
					<c:if test="${order.orderStatus != 'PLACED' && order.orderStatus != 'ACCEPTED' && order.orderStatus != 'READY'}">
					<input type="submit" value="${process}" disabled/>
				</c:if>
				</form>
			</c:if>
				<form class="cancel_order_form" action="${pageContext.request.contextPath}/controller" method="post">
					<input type="hidden" name="command" value="cancel_order"/>
					<input type="hidden" name="orderId" value="${order.orderId}"/>
					<input type="hidden" name="status" value="${order.orderStatus}"/>
					<c:if test="${sessionScope.role == 'CLIENT' && order.orderStatus == 'PLACED'}">
					<input type="submit" value="${cancel}"/>
					</c:if>
					<c:if test="${sessionScope.role == 'CLIENT' && order.orderStatus != 'PLACED'}">
					<input type="submit" value="${cancel}" disabled/>
					</c:if>
					<c:if test="${sessionScope.role == 'ADMIN' && order.orderStatus != 'CANCELED'}">
					<input type="submit" value="${cancel}"/>
					</c:if>
					<c:if test="${sessionScope.role == 'ADMIN' && order.orderStatus == 'CANCELED'}">
					<input type="submit" value="${cancel}" disabled/>
					</c:if>
				</form>
			</td>
			<td valign="center" align="center" class="order_info">
			    <c:if test="${sessionScope.role == 'ADMIN'}">
				<form action="${pageContext.request.contextPath}/controller" method="get">
					<input type="hidden" name="command" value="find_user_by_id"/>
					<input type="hidden" name="userId" value="${order.userId}"/>
					<input type="submit" value="${customer}"/>
				</form>
				</c:if>
				<button id="details_id_button_${order.orderId}" onclick="openDetails('products_${order.orderId}', 'details_id_button_${order.orderId}', 'close_details_id_button_${order.orderId}')">${detail}</button>
				<button id="close_details_id_button_${order.orderId}" style="display:none;" onclick="closeDetails('products_${order.orderId}', 'close_details_id_button_${order.orderId}', 'details_id_button_${order.orderId}')">${close_details}</button>
			</td>
		</tr>
		<tr id="products_${order.orderId}" style="display:none;">
			<td colspan="10">

				<table class="products_table" border="1px" bordercolor="c9c9c9">
					<thead bgcolor="#fff" align="center">
						<tr>
							<th colspan="2"><fmt:message key="local.product"/></th>
							<th>${price}</th>
							<th>${quantity}</th>
							<th>${cost}</th>
						</tr>
					</thead>
					<c:forEach var="product" items="${order.products}">
					<tr align="center" valign="center">
						<td>
							<img id="product_img" src="${pageContext.request.contextPath}/upload?imageName=${product.key.imageName}" />
						</td>

						<td>${product.key.productName}</td>

						<td>${product.key.price}$</td>

						<td>${product.value}</td>

						<td><c:out value="${product.key.price * product.value}" />$</td>

					</tr>
					</c:forEach>
				</table>

			</td>
		</tr>
		</c:forEach>
	</table>
    </c:if>
	</div>
</div>
<mytag:copyright/>
<script>
    function openDetails(id, buttonToCloseId, buttonToOpenId) {
		var openDetails = document.getElementById(id);
		openDetails.style.display = "table-row";
		var buttonToClose = document.getElementById(buttonToCloseId);
		buttonToClose.style.display = "none";
		var buttonToOpen = document.getElementById(buttonToOpenId);
		buttonToOpen.style.display = "block";
    }
	function closeDetails(id, buttonToCloseId, buttonToOpenId) {
		var closeDetails = document.getElementById(id);
		closeDetails.style.display = "none";
		var buttonToClose = document.getElementById(buttonToCloseId);
		buttonToClose.style.display = "none";
		var buttonToOpen = document.getElementById(buttonToOpenId);
		buttonToOpen.style.display = "block";
    }
</script>
</body>
</html>