<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary15.css">
</head>

<body>
<div class="content">
    <main class="main-in-form">
        <div class="form-container">
            <h1 class="beautiful-main-message">Добрый день, Пользователь!)</h1>
            <form action="${pageContext.request.contextPath}/signUp" method="post" class="sign-in-form">
                <input type="hidden" name="csrf_token" value="<%=request.getAttribute("csrf_token")%>"/>

                <%
                    String message = (String) request.getAttribute("message");
                    if (message != null) {
                %>
                <h2 style='color: red'><%=message%>
                </h2>
                <%}%>

                <label for="nickname">Ник:</label><br/>
                <input id="nickname" name="nickname" type="text" placeholder="ник..." required><br/>

                <label for="email">Почта:</label><br/>
                <input id="email" name="email" type="email" placeholder="почта..." required><br/>

                <label for="phone">Телефон:</label><br/>
                <input id="phone" name="phone" type="number" placeholder="телефон..." required><br/>

                <label for="password">Пароль:</label><br/>
                <input id="password" name="password" type="password" placeholder="пароль..." required><br/>

                <label for="birthdate">Дата рождения:</label><br/>
                <input id="birthdate" name="birthdate" type="date" placeholder="дата рождения..." required><br/>

                <div class="remember-block">
                    <input id="remember" name="remember" type="checkbox">
                    <label for="remember">Запомнить меня?</label><br/>
                </div>

                <div class="sign-up-in-block">
                    <a href="${pageContext.request.contextPath}/signIn">/Sign In</a>
                    <input type="submit" value="Sign Up" class="form-button">
                </div>
            </form>
        </div>
    </main>
</div>
</body>
</html>