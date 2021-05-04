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
  <fmt:message key="local.save" var="save"/>
  <fmt:message key="local.delete" var="delete"/>
  <fmt:message key="local.checkout" var="checkout"/>
  <fmt:message key="local.in_total" var="in_total"/>
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
	
	 <c:if test = "${not empty requestScope.order.products}">  
		<c:forEach var="product" items="${requestScope.order.products}">
		<img id="product_img" src="${pageContext.request.contextPath}/upload?url=/Users/User/Desktop/img/${product.key.imageName}" />
			<tr>
				<td><c:out value="${product.key.productName}" /></td>
				<td><c:out value="${product.key.price}$" /></td>
			</tr>
			<form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="change_amount_of_product_in_basket"/>
        <input type="hidden" name="productId" value="${product.key.productId}"/>
        <input type="text" name="amountProduct" required placeholder="${product.value}" />
        <input type="submit" value="${save}"/>
            </form>
            <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="remove_product_from_basket"/>
        <input type="hidden" name="productId" value="${product.key.productId}"/>
        <input type="submit" value="${delete}"/>
            </form>
        </c:forEach>
      
     <p>${in_total}: ${requestScope.order.cost}$</p>
     
      <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="checkout"/>
        <input type="hidden" name="price" value="${requestScope.order.cost}"/>
        <input type="submit" value="${checkout}"/>
            </form>   
      </c:if>  
</body>
</html>