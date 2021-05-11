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
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
</head>
<body>
<%@ include file="/jsp/fragment/header.jsp" %>
	<p></p>
   <%@ include file="/jsp/fragment/error_info.jsp" %>
   	<c:forEach var="order" items="${requestScope.orders}">
   	            <br /> 
				<p>${order_number}: ${order.orderId}</p>
				<p>${date_of_issue}: <fmt:parseDate value="${order.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime }" /></p>
				<p>${cost}: ${order.cost}$</p>		
				<p>${payment_method}: <fmt:message key="local.payment_method.${order.paymentMethod}"/></p>
				<p>${delivery_method}: <fmt:message key="local.delivery_method.${order.deliveryMethod}"/></p>
				<c:if test="${order.deliveryMethod == 'DELIVERY'}">
				<p>${address}: ${order.address.city}, ${order.address.street}, ${order.address.house}-${order.address.apartment}</p>	
				</c:if>
				<p><fmt:message key="local.order_status.${order.orderStatus}" /></p>
			 <c:if test="${order.orderStatus == 'PLACED'}">
			<form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="cancel_order"/>
        <input type="hidden" name="orderId" value="${order.orderId}"/>
        <input type="submit" value="${cancel}"/>
            </form>
            </c:if>
        </c:forEach>
	<mytag:copyright/>
</body>
</html>