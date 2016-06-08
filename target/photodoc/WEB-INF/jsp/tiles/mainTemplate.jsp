<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" media="screen"
          href="${baseURL}/resources/css/jquery/redmond/jquery-ui-1.9.2.custom.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen"
          href="${baseURL}/resources/css/style.css"/>
    <script type="text/javascript" src="${baseURL}/resources/js/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${baseURL}/resources/js/jquery/jquery.ui.datepicker-uk.js"></script>
    <script type="text/javascript" src="${baseURL}/resources/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="${baseURL}/resources/js/jquery.bpopup.min.js"></script>

</head>
<body>
<div class="main-container">
    <div class="main-conatiner-inner">
        <div class="wrapper">
                        <tiles:insertAttribute name="menu"/>
                    <div class="content">
                        <tiles:insertAttribute name="content"/>
                    </div>
                    <%--<div class="footer">--%>
                        <tiles:insertAttribute name="footer"/>
                    <%--</div>--%>
        </div>
     </div>
    <div class="aboutUs" style="color: #44BB01;">
        Призначення ПЗ полягає в збереженні фотодокументів (фотографій РТО), перегляд збережених фотодокументів, щодо подальшого аналізу та пошуку порушень ПКЕЕ
        <div class="developers">
            <div class="title">Розробники ПЗ:</div>
            Бойчук Д.М. тел. 46-56,
            Клeбан А.Б. тел. 41-18

        </div>
    </div>
    <script>
    $('.link-aboutUs').click(function(){
    $('.aboutUs').bPopup({
    easing: 'easeOutBack', //uses jQuery easing plugin
    speed: 450,
    transition: 'slideDown'})
    })
    </script>
</div>
</body>
</html>
