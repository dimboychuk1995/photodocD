<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<body>
<div class="div-for-form">
    <div class="title-for-form">
        Пошук PTO:
    </div>
    <form  id="search" method="GET" action="${baseURL}/contract">
        <div> <input type="text" placeholder="Номер лічильника" name="counterNum"></div>
        <div> <input type="text" placeholder="Номер договору" name="contractNum"></div>

        <c:if test="${ sessionScope.user.rem.id == 0 || empty sessionScope.user}">
            <select id="rem_id" name="rem_id">
                <c:forEach items="${rems}" var="rem">
                    <option value="${rem.id}">${rem.name}</option>
                </c:forEach>
            </select>
        </c:if>
        <c:if test="${!empty sessionScope.user.rem.id}">
            <div>Період</div><br/>
            <div>
                <span>з </span><input type="text" name="start_date" placeholder="дата" class="date-for-search date"><span>   по   </span>
                <input type="text" name="end_date" placeholder="дата" class="date-for-search date">
            </div>
            <c:if test="${ sessionScope.user.rem.id != 0}">
                <input type="hidden" name="rem_id" value="${sessionScope.user.rem.id}">
            </c:if>
            <input type="hidden" name="user_name" value="${sessionScope.user.full_name}">
        </c:if>
        <div class="upload_file_container show-input">
            Вид документів
            <select name="value_id">
                <option value="2">Фото</option>
                <option value="1">Документи</option>
            </select>
        </div>
        <button class="submit-sign-in">пошук</button>
    </form>
    <script>
        $('.date').datepicker();
        $('#search').submit(function(){
            var check = $('#rem_id option:selected').val();
            if(check != 0){
                return true;
            }
            alert("Виберіть РЕМ");
            return false;
        });
    </script>
</div>
</body>
</html>
