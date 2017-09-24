<%--用户信息展示页面: admin_user_list--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/systemHeader.jsp" %>
<%@include file="include/systemNavigator.jsp" %>

<script>
    $(function () {

        $("#addForm").submit(function () {
            if (checkEmpty("name", "用户名称"))
                return true;
            return false;
        });
    });

</script>

<title>用户管理</title>


<div class="workingArea">
    <h1 class="label label-info">用户管理</h1>

    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>用户名称</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="u">

                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="include/systemPage.jsp" %>
    </div>


</div>

<%@include file="include/systemFooter.jsp" %>
