<%--<%@ page contentType="multipart/form-data;charset=UTF-8" language="java" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getRequestURI();
    String nowHeaderElem = "now-header-elem";
    String headerElem = "header-elem";
%>
<link rel="stylesheet" type="text/css" href="<%=path%>}/css/diary16.css">
<header>
    <div class="maintext">
        <a href="${pageContext.request.contextPath}/logout">
            <svg class="notebook-icon" xmlns="http://www.w3.org/2000/svg" id="exitFromNotebook" viewBox="0 0 24 24">
                <path d="m24,4v16c0,2.206-1.794,4-4,4h-3c-.552,0-1-.448-1-1s.448-1,1-1h3c1.103,0,2-.897,2-2V4c0-1.103-.897-2-2-2h-3c-.552,0-1-.448-1-1s.448-1,1-1h3c2.206,0,4,1.794,4,4Zm-7.015,10.45l-5.293,5.272c-.508.509-1.195.778-1.907.778-.369,0-.744-.072-1.104-.221-1.033-.425-1.677-1.352-1.681-2.418v-1.861H3c-1.654,0-3-1.346-3-3v-2c0-1.654,1.346-3,3-3h4v-1.859c.005-1.07.649-1.997,1.682-2.421,1.055-.433,2.238-.215,3.012.559l5.29,5.267c1.352,1.353,1.351,3.551,0,4.903Zm-1.414-3.488l-5.29-5.267c-.245-.245-.593-.224-.838-.125-.132.055-.441.22-.442.576v2.854c0,.552-.448,1-1,1H3c-.551,0-1,.449-1,1v2c0,.551.449,1,1,1h5c.552,0,1,.448,1,1v2.857c.001.353.31.519.442.573.244.101.593.122.836-.123l5.293-5.272c.57-.571.57-1.501,0-2.073Z"></path>
            </svg>
        </a>
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