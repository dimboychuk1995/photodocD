<%@ page import="oe.roma.photodoc.domain.User" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="baseURL" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<body>
<div class ="information_and_docs">
    <div class="information-about-object">
        <img src="/photodoc/resources/img/questions_icon_blue.png">
        <div></div>
    </div>
    <div class="docs_about_object">
        <ul class="docs_about_object_ul">

        </ul>
    </div>
</div>


<ul class="list-imgs-for-counter">
</ul>
<c:if test="${!empty sessionScope.user}">
    <div class="wrapper-add-photo">
        <a href="#">
            <c:set var="value_id" scope="session">
                <%=request.getParameter("value_id")%>
            </c:set>
            <c:if test="${value_id == 2}">
                    <div class="add-photo unable">Додати фотодокумент</div>
            </c:if>
            <c:if test="${value_id == 1}">
                <c:if test="${sessionScope.user.role/25 == 1 || sessionScope.user.role/25 == 2 || sessionScope.user.role/25 == 4 || sessionScope.user.role/25 == 5}">
                    <div class="add-photo unable">Додати скандокумент</div>
                </c:if>
            </c:if>
        </a>
    </div>
</c:if>
<div class="title-for-form">
    Результат:
</div>
<div class="block-results">
    <ul class="list-result">
        <c:forEach var="customer" items="${searchResult}">
            <li title="${fn:replace(customer.name, '\"', '\'')}" contractNum="<%=request.getParameter("contractNum")%>" counter_num="<%=request.getParameter("counterNum")%>">${customer.name}
                <ul class="list-objects-for-photo">
                    <c:forEach var="object" items="${customer.objects}">
                        <li title="${object.name}" counterId="${object.counter_id}" counter_num="${object.counter_number}" remid="${rem_id}" address="${object.address}" inspector="${object.inspector}">${object.name}</li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
    <ul class="list-imgs-for-object">
    </ul>
</div>

<script>

    function getUrlParams() {
        var urlForParse = decodeURIComponent(window.location.search);
        var objectParams = {},
                key, value;
        if (urlForParse.length) {
            var arrayUrlForParse = urlForParse.split('&');
            for (var i = 0; i < arrayUrlForParse.length; i++) {
                key = arrayUrlForParse[i].split('=')[0];
                value = arrayUrlForParse[i].split('=')[1];
                if (i == 0) {
                    objectParams[key.substring(1)] = value;
                } else {
                    objectParams[key] = value;
                }

            }
        }
        return objectParams;
    }
    $('.unable').on('click',function(event){
        alert('виберіть спочатку обєкт!');
        return false;
    });

    $('.list-objects-for-photo li').on('click',function(){
        $('.unable').off('click');
        $('.list-objects-for-photo,.list-objects-for-photo>li').removeAttr("style").parents('li').removeAttr("style");
        $.ajax({
            url: "/photodoc/contract/getFiles",
            data: {
                count_id:$(this).attr("counterid"),
                rem_id:$(this).attr("remid"),
                start_date:getUrlParams().start_date,
                end_date:getUrlParams().end_date
            },
            type: "get",
            dataType: 'json',
            success: function(res){
                if(res.length){
                    $('.docs_about_object_ul').children().remove();
                    for(var i = 0;i<res.length;i++){
                            if (res[i].typeDocument.id !== 5 && res[i].typeDocument.id !== 6 && res[i].typeDocument.id !== 7
                                    && res[i].typeDocument.id !== 8 && res[i].typeDocument.id !== 9 && res[i].typeDocument.id !== 10
                                    && res[i].typeDocument.id !== 11 && res[i].typeDocument.id !== 12 && res[i].typeDocument.id !== 13
                                    && res[i].typeDocument.id !== 14 && res[i].typeDocument.id !== 15 && res[i].typeDocument.id !== 16
                                    && res[i].typeDocument.id !== 17) {
                                if (res[i].value_type_id == <%=request.getParameter("value_id")%>) {
                                    if (res[i].name.substr(-4).toLowerCase() == '.pdf') {
                                        $('.list-imgs-for-object').append('<li><img src="/photodoc/resources/img/pdf_icon.png" onclick="window.open(\'http://10.93.104.55:88/photodoc/' + res[i].name + '\',\'_blank\')";/>' + '<p>' + res[i].typeDocument.name + '</p>' + '<p>' + res[i].date + '</p>' + '<span class="delete hidden" id="' + res[i].id + '" name="' + res[i].name + '">x</span>' + '<span class="replace hidden" id="' + res[i].id + '" name="' + res[i].name + '"document_type="' + res[i].typeDocument.id + '" counter_id="' + res[i]['counter_id'] + '">Замінити</span></li>');
                                    } else {
                                        $('.list-imgs-for-object').append('<li><div><img src="http://10.93.104.55:88/photodoc/' + res[i].name + '" onclick="window.open(\'http://10.93.104.55:88/photodoc/' + res[i].name + '\',\'_blank\')";/></div>' + '<p>' + res[i].typeDocument.name + '</p>' + '<p>' + res[i].date + '</p>' + '<span class="delete hidden" id="' + res[i].id + '" name="' + res[i].name + '">x</span></li>' + '<span class="replace hidden" id="' + res[i].id + '" name="' + res[i].name + '"document_type="' + res[i].typeDocument.id + '" counter_id="' + res[i]['counter_id'] + '">&#8596</span></li>');
                                    }
                                }
                            } else {
                                if (res[i].value_type_id == <%=request.getParameter("value_id")%>) {
                                    if (res[i].name.substr(-4).toLowerCase() == '.pdf') {
                                        $('.docs_about_object_ul').append('<div class="docs_about_object_ul_temp"><li><img src="/photodoc/resources/img/pdf_icon.png" onclick="window.open(\'http://10.93.104.55:88/photodoc/' + res[i].name + '\',\'_blank\')";/>' + '<p>' + res[i].typeDocument.name + '</p>' + '<p>' + res[i].date + '</p>' + '<span class="delete hidden" id="' + res[i].id + '" name="' + res[i].name + '">x</span>' + '<span class="replace hidden" id="' + res[i].id + '" name="' + res[i].name + '"document_type="' + res[i].typeDocument.id + '" counter_id="' + res[i]['counter_id'] + '">&#8596</span></li></div>');
                                    }
                                }
                            }
                    }
                }else{
                    $('.list-imgs-for-object').append('<li>Фото відсутні</li>') ;
                }
            }
        });
        $(this).parent().css({width:"300px",height:"auto"}).end().css({background:"#FF8C00"}).parents('li').css({background:"#FF8C00"});
        var information = {"Адреса":$(this).attr('address'), "Номер лічильника":$(this).attr('counter_num'), "Назва":$(this).text(), "Інспектор":$(this).attr('inspector')},inf ='';
        for(var index in information){
            inf += '<span>'+index +'</span>:' + information[index]+'<br/>';
        }
        $('.information-about-object div').html(inf);
        $('.information-about-object').css({opacity:1});
        $('.add-photo').parents('a').attr("href","contract/addDocument?rem_id="+$(this).attr("remid")+"&counter_id="+$(this).attr("counterid")+"&counterNum="+getUrlParams().counterNum+"&contractNum="+getUrlParams().contractNum+"&start_date="+getUrlParams().start_date+"&end_date="+getUrlParams().end_date + "&value_id="+<%=request.getParameter("value_id")%>);
        $('.unable').removeClass('unable');
        $('.list-imgs-for-object').children().remove();
    });

    $(document).keydown(function(e) {
        var $perm = 0;
        <c:if test="${!empty sessionScope.user.role}">
        $perm = ${sessionScope.user.role}/25;
        </c:if>
        console.log($perm);
        if ($perm == 2 || $perm == 5)  {
            if (e.ctrlKey && e.altKey && e.keyCode == 68) {//ctrl+alt+d - delete
                $('.delete').toggleClass('hidden');
            }
        }
    });
    $(document).keydown(function(e) {
        var $perm = 0;
        <c:if test="${!empty sessionScope.user.role}">
        $perm = ${sessionScope.user.role}/25;
        </c:if>
        console.log($perm);
        if ($perm == 1 || $perm == 2 || $perm == 4 || $perm == 5) {
            if (e.ctrlKey && e.altKey && e.keyCode == 90) {//ctrz+alt+z - replace
                $('.replace').toggleClass('hidden');
            }
        }
    });
    $('.list-imgs-for-object, .docs_about_object_ul').on('click','.delete', function(){
        var li = $(this).parent();
        var id = this.id;
        var filename = $(this).attr("name");
        if(confirm("Ви впевнені,  що хочете видалити файл?")){
            $.post("/photodoc/contract/delete",{'id':id, 'filename':filename},function(){
                alert("Видалено успішно!")
                li.remove();
            }).fail(function() {
                alert("Помилка");
            });
        }
    })

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    $('.list-imgs-for-object, .docs_about_object_ul').on('click','.replace', function(){
        var document_type = $(this).attr("document_type");
        var counter_id = $(this).attr("counter_id");
        var id = this.id;
        var filename = $(this).attr("name");

            if(confirm("Ви впевнені,  що хочете замінити даний файл на інший такого ж типу?")) {
                window.location.href = "contract/addDocument?rem_id=<%=request.getParameter("rem_id")%>&counterNum=<%=request.getParameter("counterNum")%>&contractNum=<%=request.getParameter("contractNum")%>&start_date=<%=request.getParameter("start_date")%>&end_date=<%=request.getParameter("end_date")%>&value_id=<%=request.getParameter("value_id")%>&document_type="+document_type+"&counter_id="+counter_id+"&filename="+filename+"&id="+id;
            }
    })
</script>
</body>
</html>