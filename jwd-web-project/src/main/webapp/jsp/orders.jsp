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
  <fmt:message key="local.search" var="search"/>
  <fmt:message key="local.email" var="email"/>
  <fmt:message key="local.process" var="process"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders/style.css" type="text/css" />
</head>
<body>
	<%@ include file="/jsp/fragment/header.jsp" %>
	
   <c:if test="${sessionScope.role == 'ADMIN'}">
   <form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="find_orders_by_status"/>
    <select size="1" id="status" name="status" required >
                        <c:forEach var="orderStatus" items="${requestScope.orderStatusList}">
                            <option value="${orderStatus}"><fmt:message key="local.order_status.${orderStatus}" /></option>
                        </c:forEach>
    </select>
    <input type="submit" value="${search}"/>
   </form>
   <form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="find_order_by_id"/>
    <input type="number" name="orderId" required placeholder="${order_number}" min="1" max="9223372036854775807"/>
    <input type="submit" value="${search}"/>
   </form>
   <form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="find_user_orders"/>
    <input type="email" name="login" required placeholder="${email}" maxlength="45"/>
    <input type="submit" value="${search}"/>
   </form>
   </c:if>
   
   <%@ include file="/jsp/fragment/error_info.jsp" %>
   
    <c:if test = "${not empty requestScope.orders}"> 
	<table>
		<thead bgcolor="#c9c9c9" align="center">
			<tr>
				<th>${order_number}</th>
				<th>${date_of_issue}</th>
				<th>${cost}</th>
				<th>${payment_method}</th>
				<th colspan="2">${delivery_method}</th>
				<th>${status}</th>
				<c:if test="${sessionScope.role == 'CLIENT'}">
				<th>${cancel}</th>
				</c:if>
				<c:if test="${sessionScope.role == 'ADMIN'}">
				<th colspan="2">${process}</th>
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
			
			<c:if test="${sessionScope.role == 'ADMIN'}">
			<td>
				<form action="${pageContext.request.contextPath}/controller" method="post">
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
			</td>
			</c:if>
			<td>
				<form action="${pageContext.request.contextPath}/controller" method="post">
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
			<td>
				<button>${detail}</button>
			</td>
		</tr>
		</c:forEach>
	</table>
    </c:if>

   	<mytag:copyright/>
</body>
</html>