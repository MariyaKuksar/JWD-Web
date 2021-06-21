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
  <fmt:message key="local.title.profile" var="title"/>
  <fmt:message key="local.personal_data" var="personal_data"/>
  <fmt:message key="local.email" var="email"/>
  <fmt:message key="local.name" var="name"/>
  <fmt:message key="local.phone" var="phone"/>
  <fmt:message key="local.save" var="save"/>
  <fmt:message key="local.new_password" var="new_password"/>
  <fmt:message key="local.current_password" var="current_password"/>
  <fmt:message key="local.change_password" var="change_password"/>
  <fmt:message key="local.enter_password" var="enter_password"/>
  <fmt:message key="local.edit_profile" var="edit_profile"/>
  <fmt:message key="local.password_rules" var="password_rules"/>
  <title>${title}</title> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/header.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/footer.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/error_info.css" type="text/css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile/style.css" type="text/css" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/selectMenu.js"></script>
</head>
<body onload="selectMenu(2)">
<div class="wrapper">
<%@ include file="/jsp/fragment/header.jsp" %>
<%@ include file="/jsp/fragment/error_info.jsp" %>
	  
<div class="main">
  <header>
    <h1>${edit_profile}</h1>
  </header>
  <p>${personal_data}</p>
  <form action="${pageContext.request.contextPath}/controller" method="post" name="registration" class="edit clearfix">
    <input type="hidden" name="command" value="change_user_data"/>
    <div>
      <label>${name}: 
          <input type="text" name="userName" required placeholder="${name}" value="${user.name}" pattern="[a-zA-Zа-яА-Я-\s]{1,45}" />
      </label>
    </div>
    <div>
      <label>${phone}:
		<input type="tel" name="phone" required placeholder="+375XXXXXXXXX" value="${user.phone}" pattern="\+375\d{9}"/>
      </label>
    </div>
	<div>
		<label>${email}:
			<input type="email" name="login" required placeholder="${email}" value="${user.login}" maxlength="45" />
		</label>
	</div>
	<div>
		<label>${enter_password}:
			<input type="password" name="password" required placeholder="${current_password}" />
		</label>
	</div>
    <input type="submit" value="${save}"/>
  </form>
  <br/>
   <p>${change_password}</p>
  <form action="${pageContext.request.contextPath}/controller" method="post" name="registration" class="edit clearfix">
    <input type="hidden" name="command" value="change_password"/>
	<div>
		<label>${new_password}:
			<input type="password" name="newPassword" placeholder="${password_rules}" pattern="[A-Za-z\d]{5,15}"/>
		</label>
	</div>
	<div>
		<label>${current_password}:
			<input type="password" name="password" required placeholder="${current_password}" />
		</label>
	</div>
    <input type="submit" value="${save}"/>
  </form>
</div>
</div>
<mytag:copyright/>
	
</body>
</html>