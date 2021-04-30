<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="customtag" prefix="mytag"%>

<html>
	<head>
		<meta charset="utf-8">
	<fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="local"/>
    <fmt:message key="local.locbutton.name.en" var="en_button"/>
    <fmt:message key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message key="local.title.main" var="title"/>
    <fmt:message key="local.slogan" var="slogan"/>
    <fmt:message key="local.look_for" var="look_for"/>
    <fmt:message key="local.search" var="search"/>
    <fmt:message key="local.basket" var="basket"/>
    <fmt:message key="local.catalog" var="catalog"/>
    <fmt:message key="local.profile" var="profile"/>
    <fmt:message key="local.personal_data" var="personal_data"/>
    <fmt:message key="local.orders" var="orders"/>
    <fmt:message key="local.sign_out" var="sign_out"/>
    <fmt:message key="local.sign_in" var="sign_in"/>
    <fmt:message key="local.sign_up" var="sign_up"/>
    <fmt:message key="local.login" var="login"/>
    <fmt:message key="local.email" var="email"/>
    <fmt:message key="local.password" var="password"/>
    <fmt:message key="local.in_stock" var="in_stock"/>
    <fmt:message key="local.on_order" var="on_order"/>
    <fmt:message key="local.add_to_basket" var="add_to_basket"/>
    <fmt:message key="local.welcome" var="welcome" />
    <fmt:message key="local.add_product_to_catalog" var="add_product_to_catalog" />
    <fmt:message key="local.lang" var="lang" />
    <fmt:message key="local.forgot_password" var="forgot_password"/>
    <title>${title}</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/style.css">
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

	<br />
	<p></p>
	<a href="${pageContext.request.contextPath}/jsp/admin/adding_product.jsp">${add_product_to_catalog}</a>
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
	
	<div class="contant clearfix">
		<div class="left">
		    <c:if test="${sessionScope.login == null}">
			<div class="login">
				<div class="login_text">${sign_in}<a href="${pageContext.request.contextPath}/jsp/registration.jsp">${sign_up}</a></div>
				<div class="login_input">
				<form action="${pageContext.request.contextPath}/controller" method="post">
				<input type="hidden" name="command" value="sign_in"/>
					<p><label>${login}<p><input type="text" required placeholder="${email}" name="login" class="log"></p></label></p>
            		<p><label>${password}<p><input type="password" required placeholder="${password}" name="password" class="log"></p></label></p>
					<a href="${pageContext.request.contextPath}/jsp/forgot_password.jsp">${forgot_password}</a>
            		<input type="submit" value="${sign_in}" class="button" onclick="fun1()"/>
				</form>
				</div>
			</div>
			</c:if>
			<c:if test="${sessionScope.login != null}">
			<div class="welcom">
                <div>
                    <p>${welcome}, ${sessionScope.login}!</p>
                    <a href="${pageContext.request.contextPath}/controller?command=sign_out">${sign_out}</a>
                </div>
            </div>
			</c:if>
			
		</div>
		<div class="center">
			<c:if test="${sessionScope.productCategories != null}">
			<div class="categories">
				<c:forEach var="category" items="${sessionScope.productCategories}">
				<figure class="category">
					<div class="image"><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}"  title="${category.categoryName}"><img src="${pageContext.request.contextPath}/img/${category.imageName}" alt="${category.categoryName}"></a></div>
					<div class="name"><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}" title="${category.categoryName}">${category.categoryName}</a></div>
				</figure>
				</c:forEach>
			</div>
			</c:if>
			<c:if test="${requestScope.products != null}">
			<div class="products">
                <c:forEach var="product" items="${requestScope.products}">
                    <figure class="product">
                        <div class="image"><img src="${pageContext.request.contextPath}/img/${product.imageName}" alt="${product.productName}"></div>
                        <div class="name"><p>${product.productName}</p></div>
                        <div class="price"><p>${product.price}$</p></div>
                        <c:if test="${product.amount > 0}">
                        <div class="status"><p>${in_stock}</p></div>
                        </c:if>
                        <c:if test="${product.amount < 1}">
                        <div class="status"><p>${on_order}</p></div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/controller" method="post" >
                            <input type="hidden" name="command" value="add_product_to_basket"/>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="submit" value="${add_to_basket}"/>
                        </form>
                    </figure>
                </c:forEach>
			</div>
			</c:if>
		</div>
	
		<hr/>
	</div>

	<mytag:copyright/>
</body>
</html>
