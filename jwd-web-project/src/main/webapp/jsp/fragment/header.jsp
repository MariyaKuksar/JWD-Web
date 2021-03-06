<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
<fmt:message key="local.add_product" var="add_product" />
<fmt:message key="local.supplies" var="supplies" />
<fmt:message key="local.issue" var="issue" />
<fmt:message key="local.clients" var="clients" />
<fmt:message key="local.my_orders" var="my_orders"/>
<fmt:message key="local.sign_out" var="sign_out"/>
<fmt:message key="local.products_in_stock" var="products_in_stock"/>
<fmt:message key="local.products_on_order" var="products_on_order"/>
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
                    <ul class="menu1" id="menu1">
                        <c:if test="${sessionScope.role != 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/controller?command=go_to_basket_page">${basket}</a></li>
                        </c:if>
                        <li><a href="${pageContext.request.contextPath}/controller?command=go_to_main_page">${catalog}</a>
                            <ul class="menu2">
                            <c:forEach var="category" items="${sessionScope.productCategories}">
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_products_from_category&categoryId=${category.categoryId}"><fmt:message key="local.category.${category.categoryName}"/></a></li>
                            </c:forEach>
                            <c:if test="${sessionScope.role == 'ADMIN'}">
                                <li><a href="${pageContext.request.contextPath}/jsp/admin/adding_product.jsp">${add_product}</a></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_products_in_stock&page=1">${products_in_stock}</a></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=show_products_on_order&page=1">${products_on_order}</a></li>
                            </c:if>
                            </ul>
                        </li>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&status=placed">${orders}</a></li>
                        <li><a href="${pageContext.request.contextPath}/jsp/admin/supply.jsp">${supplies}</a>
                        </li>
                        </c:if>
                        <c:if test="${sessionScope.role == 'CLIENT'}">
                        <li><a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page">${profile}</a>
                            <ul class="menu2">
                                <li><a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page">${personal_data}</a></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=go_to_orders_page">${my_orders}</a></li>
                                <li> <form action="${pageContext.request.contextPath}/controller" method="post">
				                     <input type="hidden" name="command" value="sign_out"/>
				                     <input type="submit" value="${sign_out}"/>
				                     </form></li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/controller?command=go_to_clients_page">${clients}</a></li>
                        </c:if>
                        <li><a>${lang}</a>
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