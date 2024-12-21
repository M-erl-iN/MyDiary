<%--
  User: Эрлан
  Date: 05.12.2024
  Time: 14:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.mydiary.entity.User" %>
<html>
<head>
    <title>Main Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary15.css">
</head>

<body>
<main>
    <h1>${sessionScope.get("cookie_token")}</h1>
    <div class="main-container">
        <a href="${pageContext.request.contextPath}/logout">..Logout..</a>
        <h1>Список пользователей:</h1>
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Электронная почта</th>
            </tr>
            <%
            List<User> users = (List<User>) request.getAttribute("users");
            for (var user : users) {%>
            <tr>
                <td><%=user.getId()%></td>
                <td><%=user.getNickname()%></td>
                <td><%=user.getEmail()%></td>
            </tr>
            <%}%>
        </table>
    </div>
</main>
</body>
</html>
