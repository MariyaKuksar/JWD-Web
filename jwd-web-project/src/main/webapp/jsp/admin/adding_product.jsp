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
  <fmt:message key="local.title.adding_product" var="title"/>
  <fmt:message key="local.locbutton.name.en" var="en_button"/>
  <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
  <fmt:message key="local.product_name" var="product_name"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.add_to_catalog" var="add_to_catalog"/>
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
	
	<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
	
	<form action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
	<input type="hidden" name="command" value="add_product_to_catalog"/>
	<br />
	<select size="1" id="category" name="categoryId" required>
                        <c:forEach var="category" items="${sessionScope.productCategories}">
                            <option value="${category.categoryId}">${category.categoryName}</option>
                        </c:forEach>
    </select>
    <br />
	<input type="text" name="productName" required placeholder="${product_name}"/>
	<br />
	<input type="text" name="price" required placeholder="${price}"/>
	<br />
    <input type="file" name="file" value="" height="150">
    <br />
    <input type="submit" value="${add_to_catalog}">
</form>

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
</body>
</html>