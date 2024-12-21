<%@ page import="ru.itis.mydiary.entity.User" %>
<%@ page import="java.io.OutputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary16.css">
    <title>Settings</title>
</head>
<%
    User user = (User) request.getAttribute("user");
%>

<%@ include file="components/header.jsp" %>

<body>
<div class="content">
    <main>
        <form action="${pageContext.request.contextPath}/downloadImage" method="post" enctype="multipart/form-data" class="setting-image-form">
            <label for="file">
                <img class="profile-image" src="${pageContext.request.contextPath}/images?imId=<%=user.getImageId()%>">
            </label>
            <input id="file" name="file" type="file" accept="image/jpeg" style="display: none"><br/>
            <input type="submit" value="Сохранить" class="form-button">
        </form>

        <form action="${pageContext.request.contextPath}/settings" method="post" class="settings-form">
            <input type="hidden" name="csrf_token" value="<%=request.getAttribute("csrf_token")%>"/>
<%--            TODO: адаптировать под backend обработку--%>
            <%String message = (String) request.getAttribute("message");
                if (message != null) {%><h2 style='color: red'><%=message%></h2><%}%>

            <label for="nickname">ник</label><br/>
            <input id="nickname" name="nickname" type="text" placeholder="ник..." value="<%=user.getNickname()%>"><br/>

            <label for="email">почта</label><br/>
            <input id="email" name="email" type="email" placeholder="почта..." value="<%=user.getEmail()%>"><br/>

            <label for="phone">телефон</label><br/>
            <input id="phone" name="phone" type="number" placeholder="телефон..." value="<%=user.getPhone()%>"><br/>

            <a href="${pageContext.request.contextPath}/profile">Мои Дневники</a>
            <input type="submit" value="Сохранить" class="form-button">
        </form>
    </main>
</div>

<%@ include file="components/footer.jsp" %>

</body>
</html>