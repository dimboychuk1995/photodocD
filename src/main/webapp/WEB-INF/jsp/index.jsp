<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<body>
<head>

</head>
<div class="all-block">
    <div class="content-block sign-in">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <a href="${baseURL}/login/logout"> <div class="out"></div></a>
            </c:when>
            <c:otherwise>
                <a href="${baseURL}/login"><div class="in"></div></a>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="content-block review">
        <a href="${baseURL}/contract">
            <div></div>
        </a>
    </div>
    <div class="content-block report">
        <!-- <a href="${baseURL}/report">-->
        <a href="#">
            <div></div>
        </a>
    </div>
</div>

<div id="element_to_pop_up" style="display:none;">
    <h1>Виберіть РЕМ:</h1>
    <form id="report" action="${baseURL}/report">
        <select id="rem_id" name="rem_id">
            <c:forEach items="${rems}" var="rem">
                <option value="${rem.id}">${rem.name}</option>
            </c:forEach>
        </select>
<button class="submit">сформувати звіт</button>
</form>
</div>
</div>

<script>
    $('.report').click(function(){
        $('#element_to_pop_up').bPopup({
            easing: 'easeOutBack', //uses jQuery easing plugin
            speed: 450,
            transition: 'slideDown'})
    });
    $('#report').submit(function(){
        var check = $('#rem_id option:selected').val();
        if(check != 0){
            return true;
        }
        alert("Виберіть РЕМ");
        return false;
    });
</script>

</body>
</html>
