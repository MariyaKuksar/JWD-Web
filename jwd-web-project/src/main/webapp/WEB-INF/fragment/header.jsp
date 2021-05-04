<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="utf-8">
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="local"/>
  <fmt:message key="local.locbutton.name.en" var="en_button"/>
  <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
  <fmt:message key="local.slogan" var="slogan"/>
  <fmt:message key="local.look_for" var="look_for"/>
  <fmt:message key="local.search" var="search"/>
  <fmt:message key="local.basket" var="basket"/>
  <fmt:message key="local.catalog" var="catalog"/>
  <fmt:message key="local.profile" var="profile"/>
  <fmt:message key="local.personal_data" var="personal_data"/>
  <fmt:message key="local.orders" var="orders"/>
  <fmt:message key="local.lang" var="lang" />
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common/header.css">
</head>
<body>
 	<header>
		<div class="header_top">
			<div >
				<span class="slogan">
				${slogan}
				</span>
				<form action="${pageContext.request.contextPath}/controller" method="get">
				    <input type="hidden" name="command" value="find_products_by_name" />
					<input type="text" required placeholder ="${look_for}" name="productName"/>
					<input type="submit" value="${search}"/>
				</form>
				<nav class="menu">
						<ul class="menu1" >
							<li><a href="${pageContext.request.contextPath}/controller?command=go_to_basket_page">${basket}</a></li>
							<li><a href="${pageContext.request.contextPath}/controller?command=go_to_main_page">${catalog}</a>
								<ul class="menu2">
								<c:forEach var="category" items="${sessionScope.productCategories}">
									<li><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}">${category.categoryName}</a></li>
								</c:forEach>
								</ul>
							</li>
							<c:if test="${sessionScope.role == 'CLIENT'}">
							<li><a href="#nogo">${profile}</a>
								<ul class="menu2">
									<li><a href="#nogo">${personal_data}</a></li>
									<li><a href="#nogo">${orders}</a></li>
								</ul>
							</li>
							</c:if>
							<li><a href="#nogo">${lang}</a>
								<ul class="menu2">
									<li>
									    <form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
                                            <input type="hidden" name="command" value="en" />
                                            <input type="submit" value="${en_button}" />
                                        </form>
									</li>
									<li>
									    <form action="${pageContext.request.contextPath}/controller" method="post" class="locale">
                                            <input type="hidden" name="command" value="ru" />
                                            <input type="submit" value="${ru_button}" />
                                        </form>
									</li>
								</ul>
							</li>
						</ul>
				</nav>
			</div>
		</div>
	</header>
	</body>
</html>