<%@ page import="ru.itis.mydiary.entity.Note" %>
<%@ page import="ru.itis.mydiary.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary16.css">
</head>

<body>
<%@ include file="components/header.jsp" %>


<%
    User user = (User) request.getAttribute("user");
    Note note = (Note) request.getAttribute("note");
    String role = (String) request.getAttribute("role");
    String success = (String) request.getAttribute("success");
%>

<div class="content">
    <aside class="sidebar-left">
        <div class="sidebar-left-button-container">
            <a class="button green-elem">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12,16a4,4,0,1,1,4-4A4,4,0,0,1,12,16Zm0-6a2,2,0,1,0,2,2A2,2,0,0,0,12,10Zm6,13A6,6,0,0,0,6,23a1,1,0,0,0,2,0,4,4,0,0,1,8,0,1,1,0,0,0,2,0ZM18,8a4,4,0,1,1,4-4A4,4,0,0,1,18,8Zm0-6a2,2,0,1,0,2,2A2,2,0,0,0,18,2Zm6,13a6.006,6.006,0,0,0-6-6,1,1,0,0,0,0,2,4,4,0,0,1,4,4,1,1,0,0,0,2,0ZM6,8a4,4,0,1,1,4-4A4,4,0,0,1,6,8ZM6,2A2,2,0,1,0,8,4,2,2,0,0,0,6,2ZM2,15a4,4,0,0,1,4-4A1,1,0,0,0,6,9a6.006,6.006,0,0,0-6,6,1,1,0,0,0,2,0Z"></path>
                </svg>
                <!-- friends -->
            </a>
            <a class="button green-elem">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path>
                    <path d="M21.294,13.9l-.444-.256a9.1,9.1,0,0,0,0-3.29l.444-.256a3,3,0,1,0-3-5.2l-.445.257A8.977,8.977,0,0,0,15,3.513V3A3,3,0,0,0,9,3v.513A8.977,8.977,0,0,0,6.152,5.159L5.705,4.9a3,3,0,0,0-3,5.2l.444.256a9.1,9.1,0,0,0,0,3.29l-.444.256a3,3,0,1,0,3,5.2l.445-.257A8.977,8.977,0,0,0,9,20.487V21a3,3,0,0,0,6,0v-.513a8.977,8.977,0,0,0,2.848-1.646l.447.258a3,3,0,0,0,3-5.2Zm-2.548-3.776a7.048,7.048,0,0,1,0,3.75,1,1,0,0,0,.464,1.133l1.084.626a1,1,0,0,1-1,1.733l-1.086-.628a1,1,0,0,0-1.215.165,6.984,6.984,0,0,1-3.243,1.875,1,1,0,0,0-.751.969V21a1,1,0,0,1-2,0V19.748a1,1,0,0,0-.751-.969A6.984,6.984,0,0,1,7.006,16.9a1,1,0,0,0-1.215-.165l-1.084.627a1,1,0,1,1-1-1.732l1.084-.626a1,1,0,0,0,.464-1.133,7.048,7.048,0,0,1,0-3.75A1,1,0,0,0,4.79,8.992L3.706,8.366a1,1,0,0,1,1-1.733l1.086.628A1,1,0,0,0,7.006,7.1a6.984,6.984,0,0,1,3.243-1.875A1,1,0,0,0,11,4.252V3a1,1,0,0,1,2,0V4.252a1,1,0,0,0,.751.969A6.984,6.984,0,0,1,16.994,7.1a1,1,0,0,0,1.215.165l1.084-.627a1,1,0,1,1,1,1.732l-1.084.626A1,1,0,0,0,18.746,10.125Z"></path>
                </svg>
                <!-- settings -->
            </a>
            <a class="button green-elem"
               href="${pageContext.request.contextPath}/diary?nId=<%=note.getNotebookId()%>">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M10,22.03c-.77,0-1.51-.3-2.09-.88L1.18,14.82c-1.57-1.57-1.57-4.09-.02-5.64,0,0,.01-.01,.02-.02L7.93,2.81c.84-.85,2.09-1.1,3.22-.63s1.84,1.52,1.85,2.74v2.06h7.03c2.19,0,3.97,1.8,3.97,4.01v1.98c0,2.21-1.78,4.01-3.97,4.01h-7.03v2.06c0,1.23-.71,2.28-1.85,2.75-.38,.16-.77,.23-1.15,.23ZM2.57,10.61c-.76,.77-.75,2.01,.01,2.78l6.72,6.33c.45,.45,.95,.29,1.09,.24,.14-.06,.61-.3,.61-.9v-3.05c0-.55,.45-1,1-1h8.03c1.09,0,1.97-.9,1.97-2.01v-1.98c0-1.11-.89-2.01-1.97-2.01H12c-.55,0-1-.45-1-1v-3.06c0-.6-.47-.84-.61-.9-.14-.06-.64-.22-1.07,.21L2.57,10.61Z"></path>
                </svg>
                <!-- back -->
            </a>
        </div>
        <p></p>
    </aside>

    <main>
        <%if (role.equals("creator")) {%>
        <form action="${pageContext.request.contextPath}/note?nId=<%=note.getId()%>" method="post" class="main-note-form">
            <input type="hidden" name="csrf_token" value="<%=request.getAttribute("csrf_token")%>"/>

            <input id="title" name="title" type="text" class="input-note-title" value="<%=note.getTitle()%>" required>
            <textarea id="message" name="message" class="input-note-text"><%=note.getText() != null ? note.getText() : ""%></textarea>

            <input type="submit" class="note-form-button button" value="save">
        </form>
        <%if (success.equals("true")) {%>
        <div class="notification show" id="myNotification" style="background-color: forestgreen">
            SUCCESS
            <button onclick="closeNotification()">X</button>
        </div>
        <%} else if (success.equals("false")) {%>
        <div class="notification show" id="myNotification" style="background-color: red">
            INCORRECT DATA
            <button onclick="closeNotification()">X</button>
        </div>
        <%}%>
        <%} else if (role.equals("spectator")) {%>
        <h1 class="beautiful-main-message"><%=note.getTitle()%>
        </h1>
        <p><%=note.getText()%>
        </p>
        <%}%>
    </main>
</div>

<%@ include file="components/footer.jsp" %>

<div class="hidded dialogs">

</div>
</body>


<script>
    function closeNotification() {
        document.getElementById("myNotification").classList.remove("show");
    }
</script>

</html>