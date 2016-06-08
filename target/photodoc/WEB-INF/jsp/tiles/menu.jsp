<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function () {
                    var currenrMenu = "${active}";
                    if (currenrMenu){
                        $('.${active}').addClass('active');
                    }
                }
        );

    </script>
    <title></title>
</head>
<body>
<div class="wraper-for-header">
    <div class="header">
        <div class="log"></div>
        <div class="header-menu">
            <ul class="nav-menu">
                <li class="li-menu index"><a href="${baseURL}">Головна</a></li>
                <li class="li-menu search"><a href="${baseURL}/contract">Пошук</a></li>
                <li class="li-menu link-aboutUs"><a href="#">Про нас</a></li>
            </ul>
        </div>
    </div>
</div>


</body>
</html>
