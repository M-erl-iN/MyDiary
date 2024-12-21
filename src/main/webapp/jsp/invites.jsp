<%@ page import="java.util.List" %>
<%@ page import="ru.itis.mydiary.dto.InvitePost" %>
<%--
  Created by IntelliJ IDEA.
  User: Эрлан
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary15.css">

</head>

<body>

<%@ include file="components/header.jsp" %>

<div class="content">
    <aside class="sidebar-left">
        <div class="sidebar-left-button-container">
            <a class="button green-elem">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path>
                    <path d="M21.294,13.9l-.444-.256a9.1,9.1,0,0,0,0-3.29l.444-.256a3,3,0,1,0-3-5.2l-.445.257A8.977,8.977,0,0,0,15,3.513V3A3,3,0,0,0,9,3v.513A8.977,8.977,0,0,0,6.152,5.159L5.705,4.9a3,3,0,0,0-3,5.2l.444.256a9.1,9.1,0,0,0,0,3.29l-.444.256a3,3,0,1,0,3,5.2l.445-.257A8.977,8.977,0,0,0,9,20.487V21a3,3,0,0,0,6,0v-.513a8.977,8.977,0,0,0,2.848-1.646l.447.258a3,3,0,0,0,3-5.2Zm-2.548-3.776a7.048,7.048,0,0,1,0,3.75,1,1,0,0,0,.464,1.133l1.084.626a1,1,0,0,1-1,1.733l-1.086-.628a1,1,0,0,0-1.215.165,6.984,6.984,0,0,1-3.243,1.875,1,1,0,0,0-.751.969V21a1,1,0,0,1-2,0V19.748a1,1,0,0,0-.751-.969A6.984,6.984,0,0,1,7.006,16.9a1,1,0,0,0-1.215-.165l-1.084.627a1,1,0,1,1-1-1.732l1.084-.626a1,1,0,0,0,.464-1.133,7.048,7.048,0,0,1,0-3.75A1,1,0,0,0,4.79,8.992L3.706,8.366a1,1,0,0,1,1-1.733l1.086.628A1,1,0,0,0,7.006,7.1a6.984,6.984,0,0,1,3.243-1.875A1,1,0,0,0,11,4.252V3a1,1,0,0,1,2,0V4.252a1,1,0,0,0,.751.969A6.984,6.984,0,0,1,16.994,7.1a1,1,0,0,0,1.215.165l1.084-.627a1,1,0,1,1,1,1.732l-1.084.626A1,1,0,0,0,18.746,10.125Z"></path>
                </svg>
                <!-- settings -->
            </a>
            <a class="button green-elem"
               href="${pageContext.request.contextPath}/profile">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M10,22.03c-.77,0-1.51-.3-2.09-.88L1.18,14.82c-1.57-1.57-1.57-4.09-.02-5.64,0,0,.01-.01,.02-.02L7.93,2.81c.84-.85,2.09-1.1,3.22-.63s1.84,1.52,1.85,2.74v2.06h7.03c2.19,0,3.97,1.8,3.97,4.01v1.98c0,2.21-1.78,4.01-3.97,4.01h-7.03v2.06c0,1.23-.71,2.28-1.85,2.75-.38,.16-.77,.23-1.15,.23ZM2.57,10.61c-.76,.77-.75,2.01,.01,2.78l6.72,6.33c.45,.45,.95,.29,1.09,.24,.14-.06,.61-.3,.61-.9v-3.05c0-.55,.45-1,1-1h8.03c1.09,0,1.97-.9,1.97-2.01v-1.98c0-1.11-.89-2.01-1.97-2.01H12c-.55,0-1-.45-1-1v-3.06c0-.6-.47-.84-.61-.9-.14-.06-.64-.22-1.07,.21L2.57,10.61Z"></path>
                </svg>
                <!-- back -->
            </a>
        </div>
        <p></p>
    </aside>

    <main>
        <h1 class="beautiful-main-message">Ваши приглашения, <%= (String) request.getAttribute("nickname")%>
        </h1>

        <div class="invites-main-container ">
            <div class="invites-box-container"><h1>Меня пригласили: </h1>
                <div class="invites-container">
                    <%
                        List<InvitePost> invites = (List<InvitePost>) request.getAttribute("myInvites");
                        for (var invite : invites) {%>
                    <div class="invite">
                        <h6>-отправитель: <%=invite.getUserSendName()%>
                        </h6>
                        <h6>-получатель: <%=invite.getUserInviteName()%>
                        </h6>
                        <h6>-дневник: <%=invite.getNotebookTitle()%>
                        </h6>
                        <h6>-роль: <%=invite.getRoleName()%>
                        </h6>
                        <a href="${pageContext.request.contextPath}/invites?dId=<%=invite.getInviteId()%>">отклонить</a>
                        <a href="${pageContext.request.contextPath}/invites?cId=<%=invite.getInviteId()%>">принять</a>
                    </div>
                    <%}%>
                </div>
            </div>

            <div class="invites-box-container">
                <h1>Я пригласил: </h1>
                <div class="invites-container">
                    <%
                        invites = (List<InvitePost>) request.getAttribute("mySends");
                        for (var invite : invites) {%>
                    <div class="invite">
                        <h6>-отправитель: <%=invite.getUserSendName()%>
                        </h6>
                        <h6>-получатель: <%=invite.getUserInviteName()%>
                        </h6>
                        <h6>-дневник: <%=invite.getNotebookTitle()%>
                        </h6>
                        <h6>-роль: <%=invite.getRoleName()%>
                        </h6>
                        <a href="${pageContext.request.contextPath}/invites?dId=<%=invite.getInviteId()%>">отменить</a>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="navigation-button-container">
            <%
                Long start = (Long) request.getAttribute("start");
                int step = (int) request.getAttribute("step");
            %>
            <a class="green-elem" href="${pageContext.request.contextPath}/invites?start=<%=start-step%>&step=<%=step%>">-
                Назад</a>
            <a class="green-elem" href="${pageContext.request.contextPath}/invites?start=<%=start+step%>&step=<%=step%>">Вперед
                -</a>
        </div>
    </main>
</div>

<footer>Footer</footer>


<div class="hidded dialogs">
</div>

</body>
</html>