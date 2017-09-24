<%--首页/业务页面/分类和轮播/竖状分类菜单右侧的推荐产品列表--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    $(function () {
// 这个是用于随机挑选一个产品作为推荐产品，来进行高亮显示。
// 严格的说，应该是后台设置那个产品是推荐产品，不过这里偷懒了，直接在前端按照20%的概率，随机挑选了一个产品
        $("div.productsAsideCategorys div.row a").each(function () {
            var v = Math.round(Math.random() * 6);
            if (v == 1)
                $(this).css("color", "#87CEFA");
        });
    });

</script>
<%--1.取出每个分类--%>
<c:forEach items="${categorys}" var="c">
    <div cid="${c.id}" class="productsAsideCategorys">
        <%--2.取出每个分类的productsByRow集合--%>
        <c:forEach items="${c.productsByRow}" var="ps">
            <div class="row ">
                <%--3.根据productsByRow集合, 取出每个产品, 把产品的subTitle信息里的第一个单词取出来显示--%>
                <c:forEach items="${ps}" var="p">
                    <c:if test="${!empty p.subTitle}">
                        <a href="foreproduct?product.id=${p.id}">
                            <c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
                                <c:if test="${st.index==0}">
                                    ${title}
                                </c:if>
                            </c:forEach>
                        </a>
                    </c:if>
                </c:forEach>
                <div class="seperator"></div>
            </div>
        </c:forEach>
    </div>
</c:forEach>
	
