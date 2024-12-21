<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary16.css">
    <title>Войти</title>
</head>

<body>
<div class="content">
    <main class="main-in-form">
        <h1 class="beautiful-main-message">Добрый день, Пользователь!)</h1>
        <form action="${pageContext.request.contextPath}/signIn" method="post" class="sign-in-form">
            <input type="hidden" name="csrf_token" value="<%=request.getAttribute("csrf_token")%>"/>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <h2 style='color: red'><%=message%></h2>
            <%}%>

            <label for="email">Почта:</label><br/>
            <input id="email" name="email" type="email" placeholder="почта..." required><br/>

            <label for="password">Пароль:</label><br/>
            <input id="password" name="password" type="password" placeholder="пароль..." required><br/>

            <div class="remember-block">
                <input id="remember" name="remember" type="checkbox">
                <label for="remember">Запомнить меня?</label><br/>
            </div>

            <div class="sign-up-in-block">
                <a href="${pageContext.request.contextPath}/signUp">/Sign Up</a>
                <input type="submit" value="Войти" class="form-button">
            </div>
        </form>
    </main>
</div>

</body>
</html>