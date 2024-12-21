<%--<%@ page contentType="multipart/form-data;charset=UTF-8" language="java" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getRequestURI();
    String nowHeaderElem = "now-header-elem";
    String headerElem = "header-elem";
%>
<link rel="stylesheet" type="text/css" href="<%=path%>}/css/diary15.css">
<header>
    <div class="maintext">
        <h2 class="beautiful-header-message">Мои Дневники</h2>
    </div>

    <div class="header-refs">
        <a class="button <%=path.contains("settings") ? nowHeaderElem : headerElem%>" href="${pageContext.request.contextPath}/settings">
            Настройки
        </a>
        <a class="button <%=path.contains("invites") ? nowHeaderElem : headerElem%>" href="${pageContext.request.contextPath}/invites">
            Приглашения
        </a>
        <a class="button <%=path.contains("profile") ? nowHeaderElem : headerElem%>" href="${pageContext.request.contextPath}/profile">
            Профиль
        </a>
    </div>
</header>