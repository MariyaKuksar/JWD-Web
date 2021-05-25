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
		<fmt:message key="local.title.main" var="title"/>
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
		<fmt:message key="local.forgot_password" var="forgot_password"/>
		<fmt:message key="local.catalog" var="catalog"/>
		<fmt:message key="local.edit" var="edit"/>
		<fmt:message key="local.save" var="save"/>
		<fmt:message key="local.name" var="name"/>
		<fmt:message key="local.price" var="price"/>
		<fmt:message key="local.show" var="show"/>
		<fmt:message key="local.cheap_first" var="cheap_first"/>
		<fmt:message key="local.expensive_first" var="expensive_first"/>
		<title>${title}</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main/style.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
	</head>
	<c:if test="${sessionScope.role != 'ADMIN'}">
    <body onload="selectMenu(1)">
    </c:if>
    <c:if test="${sessionScope.role == 'ADMIN'}">
    <body onload="selectMenu(0)">
    </c:if>
	<div class="wrapper">
	<%@ include file="/jsp/fragment/header.jsp" %>
	<%@ include file="/jsp/fragment/error_info.jsp" %>
	
	<div class="contant clearfix">
		<div class="left">
		    <c:if test="${sessionScope.login == null}">
			<div class="login">
				<div class="login_text">${sign_in} - <a href="${pageContext.request.contextPath}/jsp/registration.jsp">${sign_up}</a></div>
				<div class="login_input">
				<form action="${pageContext.request.contextPath}/controller" method="post">
				<input type="hidden" name="command" value="sign_in"/>
					<p><label>${login}<p><input type="email" required placeholder="${email}" name="login" class="log" maxlength="45"></p></label></p>
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
				<c:if test="${requestScope.products[0].category.categoryId == category.categoryId}">
				<figure class="category selected">
				</c:if>
				<c:if test="${requestScope.products[0].category.categoryId != category.categoryId}">
				<figure class="category">
				</c:if>
					<div class="image"><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}"  title="${category.categoryName}"><img src="${pageContext.request.contextPath}/upload?imageName=${category.imageName}" alt="${category.categoryName}"></a></div>
					<div class="name"><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}" title="${category.categoryName}"><fmt:message key="local.category.${category.categoryName}"/></a></div>
				</figure>
				</c:forEach>
			</div>
			</c:if>
				
			<c:if test="${requestScope.products != null}">
			<div class="menu3">
				<p><a href="${pageContext.request.contextPath}/controller?command=go_to_main_page">${catalog}</a> > <fmt:message key="local.category.${requestScope.products[0].category.categoryName}"/> </p>
				<p>${show}: </p>
				<c:if test="${param.sortingMethod == 'increase_price'}">
				<div class="filter filter_chosen">
				</c:if>
				<c:if test="${param.sortingMethod != 'increase_price'}">
				<div class="filter">
				</c:if>
					<a href="${currentPage}&sortingMethod=increase_price">${cheap_first}</a>
				</div>
				<c:if test="${param.sortingMethod == 'decrease_price'}">
				<div class="filter filter_chosen">
				</c:if>
				<c:if test="${param.sortingMethod != 'decrease_price'}">
				<div class="filter">
				</c:if>
					<a href="${currentPage}&sortingMethod=decrease_price">${expensive_first}</a>
				</div>
			</div>
			<div class="products">
                <c:forEach var="product" items="${requestScope.products}">
                    <figure class="product">
                        <div class="image"><img src="${pageContext.request.contextPath}/upload?imageName=${product.imageName}" alt="${product.productName}"></div>
                        <div class="name"><p>${product.productName}</p></div>
                        <div class="price"><p>${product.price}$</p></div>
                        <c:if test="${product.amount > 0}">
                        <div class="status"><p>${in_stock}</p></div>
                        </c:if>
                        <c:if test="${product.amount < 1}">
                        <div class="status"><p>${on_order}</p></div>
                        </c:if>
						<c:if test="${sessionScope.role == 'CLIENT'}">
                        <form action="${pageContext.request.contextPath}/controller" method="post" >
                            <input type="hidden" name="command" value="add_product_to_basket"/>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <input type="submit" value="${add_to_basket}"/>
                        </form>
						</c:if>
						<c:if test="${sessionScope.role == 'ADMIN'}">
						<button id="button_edit_${product.productId}" onclick="openEditForm('form_edit_${product.productId}', 'button_edit_${product.productId}')">${edit}</button>
						<form class="form_edit" id="form_edit_${product.productId}" action="${pageContext.request.contextPath}/controller" method="post" >
                            <span>${edit}</span>
							<input type="hidden" name="command" value="change_product_data"/>
                            <input type="hidden" name="productId" value="${product.productId}"/>
                            <div><label>${name}:<input type="text" name="productName" value="${product.productName}"/></label></div>
                            <div><label>${price}:<input type="text" name="price" value="${product.price}"/>$</label></div>
                            <input type="submit" onclick="closeEditForm('form_edit_${product.productId}', 'button_edit_${product.productId}')" value="${save}"/>
                        </form>
						</c:if>
                    </figure>
                </c:forEach>
			</div>
			</c:if>
		</div>
		<hr/>
	</div>
	</div>
	<mytag:copyright/>

	<script>
		function openEditForm(editFormId, editButtonId) {
		  var editForm = document.getElementById(editFormId);
		  editForm.style.display = "block";
		  var editButton = document.getElementById(editButtonId);
		  editButton.style.display = "none";

		}

		function closeEditForm(editFormId, editButtonId) {
		  var editForm = document.getElementById(editFormId);
		  editForm.style.display = "none";
		  var editButton = document.getElementById(editButtonId);
		  editButton.style.display = "block";
		}
	</script>
</body>
</html>
