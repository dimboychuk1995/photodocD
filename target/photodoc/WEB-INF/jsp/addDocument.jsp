<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}"/>
<html>
<body>
<div class="title-for-form">
    Щоб дадати фотодокумент, <br>потрібно:
</div>
<form method="POST" action="${baseURL}/contract/addDocument" class="form-uploading-photo" enctype="multipart/form-data">
    <input type="hidden" name="counter_id" value="${counter_id}">
    <input type="hidden" name="rem_id" value="${rem_id}">
    <input type="hidden" name="value_type_id" value="<%=request.getParameter("value_id")%>">
    <input type="hidden" name="search_counter" class="hidden" value="${search_counter}">
    <input type="hidden" name="search_contract" class="hidden" value="${search_contract}">
    <input type="hidden" name="start_date" class="hidden" value="${start_date}">
    <input type="hidden" name="end_date" class="hidden" value="${end_date}">
    <input type="hidden" name="user_name" value ="${sessionScope.user.full_name}">
    <div class="upload_file_container">
        Вибрати фотографію або сканований документ
        <input type="file" name="file" id="fileinput" multiple/>
        <div id="check_for_file"></div>
    </div>
    <div class="upload_file_container show-input">
        Вибрати тип фотодокументу

        <select id="type_id" name="type_id"  doctype="<%=request.getParameter("document_type")%>" filename="<%=request.getParameter("filename")%>" docid ="<%=request.getParameter("id")%>">
            <c:forEach items="${typesDoc}" var="type">
                <c:if test="${value_id == 2}">
                    <c:if test="${type.id < 5}">
                        <option value="${type.id}">${type.name} </option>
                    </c:if>
                    <c:if test="${type.id >= 5}">
                        <option value="${type.id}" disabled>${type.name} </option>
                    </c:if>
                    <c:if test="${doctype == type.id}">
                        <option value="${type.id}" selected>${type.name} </option>
                    </c:if>
                </c:if>
                <c:if test="${value_id == 1}">
                    <c:if test="${type.id < 5}">
                        <option value="${type.id}" disabled>${type.name} </option>
                    </c:if>
                    <c:if test="${type.id >= 5}">
                        <option value="${type.id}">${type.name} </option>
                    </c:if>
                    <c:if test="${doctype == type.id}">
                        <option value="${type.id}" selected>${type.name} </option>
                    </c:if>
                </c:if>
            </c:forEach>
        </select>

    </div>
    <div class="upload_file_container show-input confirm">
        Вказати дату
        <input type="text" class="date" name="date">
    </div>
    <div class="container-for-submit">
        <button id="save" class="submit">Додати фотодокумент</button>
    </div>

</form>
<script>
    $(document).ready(function(){
        var doctype = $('#type_id').attr('doctype');
        if (doctype !== 'null'){
            // alert(typeof doctype);
            $('#type_id > :nth-child('+(doctype)+ ')').prop('selected', true);
            $("#type_id").hide();
        }
    });

    $('#fileinput').click(function(){
        var doctype = $('#type_id').attr('doctype');

        if (doctype !== 'null'){
            // alert(typeof doctype);
            // $("#type_id").hide();
        }
    });
    $('.show-input').click(function(){
        $(this).find('input,select').css({visibility:"visible"});
    });
    $('#save').click(function(){

        var doctype = $('#type_id').attr('doctype');
        var filename = $('#type_id').attr('filename');
        var id = $('#type_id').attr('docid');
        if (doctype !== 'null'){
            $.post("/photodoc/contract/delete",{'id':id, 'filename':filename},function(){
                // alert("Видалено успішно!");

            });
            //$('#type_id > :nth-child('+doctype+ ')').prop('selected', true);
            // $('#type_id').prop('disabled', 'disabled');
        }

    });
    $('#check_for_file').click(function(){
        $(this).prev().trigger('click');
    });
    $('.date').datepicker();
    $( ".date" ).datepicker( "option", "dateFormat", "dd.mm.yy" );
    $('.date').datepicker('setDate', new Date());
    var listTypesImgs = ['jpg', 'jpeg', 'png', 'gif','bmp', 'pdf'], checkImg = true;
    $('.upload_file_container input[type="file"]').change(function(){
        if($(this).val()!=''){
            if(listTypesImgs.indexOf($(this).val().substr(-3).toLowerCase()) +1 || $(this).val().substr(-4).toLowerCase() == 'jpeg'){
                $('#check_for_file').addClass('cheked-file');
                $('#check_for_file').text($(this).val().replace('C:\\fakepath\\', ''));
                $('#check_for_file').css({color: '#7f7e7a'});
                checkImg = true;
            }else{
                $('#check_for_file').removeClass('cheked-file');
                $('#check_for_file').text('невірний формат');
                $('#check_for_file').css({color: 'red'});
                checkImg = false;
            }

        }else{
            $('#check_for_file').removeClass('cheked-file');
            $('#check_for_file').text('');
        }
    })
    $('.form-uploading-photo').submit(function(e){
        var check = true;
        $(this).find('input:not(.hidden),select').each(function(index){
            if($(this).val() == 0 || $(this).val() == '' || !checkImg){
                $(this).css({visibility:"visible"});
                $(this).css({border: "1px solid red"});
                $(this).siblings('#check_for_file').text('виберіть файл');
                $(this).siblings('#check_for_file').css({color: 'red'});
                check = false;
            }else{
                $(this).css({border: "1px solid #2c7b00"});
                $(this).siblings('#check_for_file').css({color: '#7f7e7a'});
            }
        })
        if(check === false){
            return false;
        };
        $(".submit").attr("disabled","disabled");
    });

</script>



</body>
</html>
