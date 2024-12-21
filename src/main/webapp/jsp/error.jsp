<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ooops! Error</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary16.css">

</head>

<body>
    <div class="container greeting-container">
        <h1>Error:</h1>
        <img src="https://cdn.culture.ru/images/f306845c-56ef-5ce8-8183-68a541619508" class="error-image">
        <%=request.getParameter("err")%>
        <hr>
        <a href="${pageContext.request.contextPath}/">На главную</a>
    </div>
</body>

</html>
