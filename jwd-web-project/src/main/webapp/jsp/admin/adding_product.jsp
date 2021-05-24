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
  <fmt:message key="local.title.adding_product" var="title"/>
  <fmt:message key="local.product_name" var="product_name"/>
  <fmt:message key="local.price" var="price"/>
  <fmt:message key="local.add_to_catalog" var="add_to_catalog"/>
  <fmt:message key="local.add_product" var="add_product"/>
  <fmt:message key="local.upload_image" var="upload_image"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adding_product/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(0)">
<%@ include file="/jsp/fragment/header.jsp" %>

	<div class="main">
	<p>${add_product}</p>
	<c:set var="currentPage" value="${pageContext.request.requestURI}" scope="session"> </c:set>
	
	<form class="add_product_form" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" method="post">
		<input type="hidden" name="command" value="add_product_to_catalog"/>
		<div>
			<label for="category"><fmt:message key="local.category"/>:</label>
			<select size="1" id="category" name="categoryId" required>
								<c:forEach var="category" items="${sessionScope.productCategories}">
									<option value="${category.categoryId}"><fmt:message key="local.category.${category.categoryName}"/></option>
								</c:forEach>
			</select>
			<label>${product_name}:</label>
			<input type="text" name="productName" required placeholder="${product_name}" maxlength="45"/>
		</div>
		<div>
			<label>${price}:</label>
			<input type="text" name="price" required placeholder="${price}" pattern="\d{1,8}(\.\d{2})?"/> $
		</div>
		<div>
			<label>${upload_image}:</label>
			<input type="file" name="file" value="" height="150">
		</div>
		<input type="submit" value="${add_to_catalog}">
	</form>
	</div>
	
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	<mytag:copyright/>
</body>
</html>