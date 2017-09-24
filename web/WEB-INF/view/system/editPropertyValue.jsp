<%--编辑产品属性值页面: admin_propertyValue_edit--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/systemHeader.jsp" %>
<%@include file="include/systemNavigator.jsp" %>


<title>编辑产品属性值</title>

<script>
    $(function () {
        $("input.pvValue").keyup(function () {
            var value = $(this).val();
            var page = "admin_propertyValue_update";
            var pvid = $(this).attr("pvid");
            var parentSpan = $(this).parent("span");
            parentSpan.css("border", "1px solid yellow");
            $.post(
                page,
                {"propertyValue.value": value, "propertyValue.id": pvid},
                function (result) {
                    if ("success" == result)
                        parentSpan.css("border", "1px solid green");
                    else
                        parentSpan.css("border", "1px solid red");
                }
            );
        });
    });
</script>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?category.id=${product.category.id}">${product.category.name}</a></li>
        <li class="active">${product.name}</li>
        <li class="active">编辑产品属性</li>
    </ol>

    <div class="editPVDiv">
        <c:forEach items="${propertyValues}" var="propertyValue">
            <div class="eachPV">
                <span class="pvName">${propertyValue.property.name}</span>
                <span class="pvValue">
                    <input class="pvValue" pvid="${propertyValue.id}"
                           type="text" value="${propertyValue.value}"></span>
            </div>
        </c:forEach>
        <div style="clear:both"></div>
    </div>

</div>

