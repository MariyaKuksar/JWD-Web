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
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
</head>
<body>
<%@ include file="/jsp/fragment/header.jsp" %>
<%@ include file="/jsp/fragment/error_info.jsp" %>
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
	<input type="text" name="productName" required placeholder="${product_name}" maxlength="45"/>
	<br />
	<input type="text" name="price" required placeholder="${price}" pattern="\d{1,8}(\.\d{2})?"/>
	<br />
    <input type="file" name="file" value="" height="150">
    <br />
    <input type="submit" value="${add_to_catalog}">
</form>
	<mytag:copyright/>
</body>
</html>