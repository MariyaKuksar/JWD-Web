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
  <fmt:message key="local.previous" var="previous"/>
  <fmt:message key="local.next" var="next"/>
  <title>${title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(0)">
	<%@ include file="/jsp/fragment/header.jsp" %>
	<%@ include file="/jsp/fragment/error_info.jsp" %>


	<c:if test = "${not empty requestScope.products}">
	<div class="products_in_stock">
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
					<img id="product_img" src="${pageContext.request.contextPath}/img/${product.imageName}" />
				</td>

				<td>${product.productName}</td>

				<td>${product.price}$</td>

				<td>${product.amount} ${pc}</td>
			</tr>
			</c:forEach>
		</table>

		<nav>
        <ul class="pagination">
            <c:if test="${requestScope.page > 1}">
                <li class="page-item"><a class="page-link"
                    href="${sessionScope.currentPage}&page=${requestScope.page-1}">${previous}</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                <c:choose>
                    <c:when test="${requestScope.page == i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                            href="${sessionScope.currentPage}&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${requestScope.page < requestScope.numberOfPages}">
                <li class="page-item"><a class="page-link"
                    href="${sessionScope.currentPage}&page=${requestScope.page+1}">${next}</a>
                </li>
            </c:if>
        </ul>
        </nav>
    </div>
	</c:if>

	<mytag:copyright/>
 
</body>
</html>