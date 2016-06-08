<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<body>
<div class="div-for-form">
    <div class="title-for-form">
        Увага! Щоб користуватися системою, слід ввести
        логін та пароль <p><strong>ТАКИЙ ЖЕ ЯК ПРИ ЛОГІНІЗАЦІЇ "ServiceDesk"</strong></p>
    </div>
    <form  method="POST" action="${baseURL}/login">
        <div> <input type="text" placeholder="логін" name="login"></div>
        <div> <input type="password" placeholder="пароль" name="password"></div>
        <button class="submit-sign-in">вхід</button>
    </form>
</div>
</body>
</html>
