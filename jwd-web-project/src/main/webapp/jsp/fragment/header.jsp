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
<fmt:message key="local.report" var="report" />
<fmt:message key="local.supplies" var="supplies" />
<fmt:message key="local.issue" var="issue" />
<fmt:message key="local.clients" var="clients" />
<fmt:message key="local.my_orders" var="my_orders"/>
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
                                <li><a href="#nogo">${report}</a></li>
                            </c:if>
                            </ul>
                        </li>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li><a href="#nogo">${orders}</a></li>
                        <li><a href="#nogo">${supplies}</a>
                            <ul class="menu2">
                                <li><a href="#nogo">${issue}</a></li>
                                <li><a href="#nogo">${report}</a></li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${sessionScope.role == 'CLIENT'}">
                        <li><a href="#nogo">${profile}</a>
                            <ul class="menu2">
                                <li><a href="#nogo">${personal_data}</a></li>
                                <li><a href="#nogo">${my_orders}</a></li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li><a href="#nogo">${clients}</a></li>
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