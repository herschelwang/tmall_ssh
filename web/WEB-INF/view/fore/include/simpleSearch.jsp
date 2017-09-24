<%--搜索栏--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<!-- jstl自定义变量 作用是取出部署的应用程序名，这样不管如何部署，所用路径都是正确的。-->
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<!-- 设置所有网页链接的基本路径 -->
<base href="${ctx}"/>


<div class="simpleSearchOutDiv">
    <a href="${contextPath}">
        <img id="simpleLogo" class="simpleLogo" src="${ctx}/img/site/simpleLogo.png">
    </a>

    <form action="foresearch" method="post">
        <div class="simpleSearchDiv pull-right">
            <input type="text" placeholder="平衡车 原汁机" value="${param.keyword}" name="keyword">
            <button class="searchButton" type="submit">搜天猫</button>
            <div class="searchBelow">
                <c:forEach items="${cs}" var="c" varStatus="st">
                    <c:if test="${st.count>=8 and st.count<=11}">
					<span>
						<a href="forecategory?category.id=${c.id}">
                                ${c.name}
                        </a>
						<c:if test="${st.count!=11}">
                            <span>|</span>
                        </c:if>
					</span>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </form>
    <div style="clear:both"></div>
</div>