<%@ page import="java.util.List" %>
<%@ page import="ru.itis.mydiary.entity.Note" %>
<%@ page import="ru.itis.mydiary.repositories.UserRepository" %>
<%@ page import="ru.itis.mydiary.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/diary15.css">
</head>

<%
    Long nId = Long.valueOf(request.getParameter("nId"));
    Long start = (Long) request.getAttribute("start");
    int step = (int) request.getAttribute("step");
    String title = (String) request.getAttribute("title");
    String csrf_token = (String) request.getAttribute("csrf_token");
    boolean flag = (boolean) request.getAttribute("flag");
    UserRepository userRepository = (UserRepository) request.getServletContext().getAttribute("userRepository");

    String context = request.getContextPath();
    User user = (User) request.getAttribute("user");
    List<Note> notes = (List<Note>) request.getAttribute("notes");
%>

<%@ include file="components/header.jsp" %>

<div class="content">
    <aside class="sidebar-left">
        <div class="sidebar-left-button-container">
            <a class="button green-elem" href="${pageContext.request.contextPath}/invites">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="m23.99,11.042c.071.879-.246,1.74-.869,2.364-1.489,1.415-3.292,3.117-3.532,3.291-.178.13-.384.192-.588.192-.309,0-.613-.143-.809-.41-.322-.44-.23-1.056.201-1.385.23-.19,1.883-1.745,3.332-3.121.189-.19.295-.477.271-.771-.024-.297-.174-.56-.424-.739-.38-.274-.976-.19-1.355.189-.286.286-.715.371-1.088.217-.373-.154-.617-.517-.619-.921l-.02-6.201c0-.415-.335-.749-.747-.749-.2,0-.388.078-.53.22-.13.131-.199.304-.209.487l-.004,2.292c0,.552-.448,1-1,1s-1-.448-1-1l.006-3.256c0-.41-.334-.744-.746-.744-.2,0-.388.078-.528.22-.141.142-.218.33-.217.53l-.015,1.263c-.006.465-.332.866-.787.965-.452.101-.918-.128-1.117-.55-.096-.203-.474-.43-.836-.43-.289,0-.556.169-.679.43-.236.5-.833.712-1.332.476s-.712-.833-.476-1.332c.572-1.21,2.039-1.822,3.313-1.458.114-.486.363-.938.728-1.305.519-.521,1.21-.809,1.946-.809.971,0,1.821.51,2.31,1.272.363-.172.759-.272,1.173-.272,1.515,0,2.747,1.231,2.747,2.746l.015,4.607c.775-.136,1.586.02,2.237.489.722.52,1.177,1.322,1.248,2.201Zm-8.791-2.234c.52.522.805,1.216.801,1.953v7.239c0,3.309-2.691,6-6,6h-1.592c-1.804,0-3.5-.702-4.776-1.979l-2.726-2.599c-.64-.64-.957-1.502-.886-2.381.071-.878.525-1.681,1.248-2.201.65-.469,1.46-.624,2.237-.488l.015-4.61c0-1.511,1.232-2.743,2.747-2.743.414,0,.81.1,1.173.273.488-.763,1.338-1.273,2.31-1.273.736,0,1.427.288,1.947.81.369.371.613.831.725,1.33.263-.084.538-.142.829-.142.737,0,1.429.288,1.949.811Zm-1.199,1.948c0-.206-.077-.395-.218-.538-.142-.143-.331-.221-.532-.221-.414,0-.75.336-.75.749,0,.009-.005.017-.005.026v3.228c0,.552-.447,1-1,1s-1-.447-1-1v-5.246c0-.205-.076-.393-.217-.535-.141-.142-.329-.22-.528-.22-.411,0-.746.334-.746.744l.01,5.256c0,.553-.448,1-1,1s-1-.447-1-1l-.008-4.29c-.009-.184-.078-.358-.209-.489-.141-.142-.33-.22-.53-.22-.412,0-.747.334-.747.746l-.02,6.204c-.001.404-.246.768-.619.922-.373.153-.803.068-1.088-.218-.381-.381-.976-.463-1.356-.189-.249.18-.399.442-.423.739-.023.294.082.581.29.789l2.726,2.599c.915.915,2.109,1.409,3.379,1.409h1.592c2.206,0,4-1.794,4-4v-7.244Z"></path>
                </svg>
                <!-- friends -->
            </a>
            <a class="button green-elem" href="${pageContext.request.contextPath}/settings">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path>
                    <path d="M21.294,13.9l-.444-.256a9.1,9.1,0,0,0,0-3.29l.444-.256a3,3,0,1,0-3-5.2l-.445.257A8.977,8.977,0,0,0,15,3.513V3A3,3,0,0,0,9,3v.513A8.977,8.977,0,0,0,6.152,5.159L5.705,4.9a3,3,0,0,0-3,5.2l.444.256a9.1,9.1,0,0,0,0,3.29l-.444.256a3,3,0,1,0,3,5.2l.445-.257A8.977,8.977,0,0,0,9,20.487V21a3,3,0,0,0,6,0v-.513a8.977,8.977,0,0,0,2.848-1.646l.447.258a3,3,0,0,0,3-5.2Zm-2.548-3.776a7.048,7.048,0,0,1,0,3.75,1,1,0,0,0,.464,1.133l1.084.626a1,1,0,0,1-1,1.733l-1.086-.628a1,1,0,0,0-1.215.165,6.984,6.984,0,0,1-3.243,1.875,1,1,0,0,0-.751.969V21a1,1,0,0,1-2,0V19.748a1,1,0,0,0-.751-.969A6.984,6.984,0,0,1,7.006,16.9a1,1,0,0,0-1.215-.165l-1.084.627a1,1,0,1,1-1-1.732l1.084-.626a1,1,0,0,0,.464-1.133,7.048,7.048,0,0,1,0-3.75A1,1,0,0,0,4.79,8.992L3.706,8.366a1,1,0,0,1,1-1.733l1.086.628A1,1,0,0,0,7.006,7.1a6.984,6.984,0,0,1,3.243-1.875A1,1,0,0,0,11,4.252V3a1,1,0,0,1,2,0V4.252a1,1,0,0,0,.751.969A6.984,6.984,0,0,1,16.994,7.1a1,1,0,0,0,1.215.165l1.084-.627a1,1,0,1,1,1,1.732l-1.084.626A1,1,0,0,0,18.746,10.125Z"></path>
                </svg>
                <!-- settings -->
            </a>
            <%if (flag) {%>
            <button type="button" class="button green-elem" onclick="overlayMenu.showModal()">
                <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="m12 0a12 12 0 1 0 12 12 12.013 12.013 0 0 0 -12-12zm0 22a10 10 0 1 1 10-10 10.011 10.011 0 0 1 -10 10zm5-10a1 1 0 0 1 -1 1h-3v3a1 1 0 0 1 -2 0v-3h-3a1 1 0 0 1 0-2h3v-3a1 1 0 0 1 2 0v3h3a1 1 0 0 1 1 1z"></path>
                </svg>
                <!-- create -->
            </button>
            <%}%>
            <a class="button green-elem"
               href="${pageContext.request.contextPath}/profile">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M10,22.03c-.77,0-1.51-.3-2.09-.88L1.18,14.82c-1.57-1.57-1.57-4.09-.02-5.64,0,0,.01-.01,.02-.02L7.93,2.81c.84-.85,2.09-1.1,3.22-.63s1.84,1.52,1.85,2.74v2.06h7.03c2.19,0,3.97,1.8,3.97,4.01v1.98c0,2.21-1.78,4.01-3.97,4.01h-7.03v2.06c0,1.23-.71,2.28-1.85,2.75-.38,.16-.77,.23-1.15,.23ZM2.57,10.61c-.76,.77-.75,2.01,.01,2.78l6.72,6.33c.45,.45,.95,.29,1.09,.24,.14-.06,.61-.3,.61-.9v-3.05c0-.55,.45-1,1-1h8.03c1.09,0,1.97-.9,1.97-2.01v-1.98c0-1.11-.89-2.01-1.97-2.01H12c-.55,0-1-.45-1-1v-3.06c0-.6-.47-.84-.61-.9-.14-.06-.64-.22-1.07,.21L2.57,10.61Z"></path>
                </svg>
                <!-- back -->
            </a>
        </div>
    </aside>

    <main>
        <h1 class="beautiful-main-message">
            <%=title%>
        </h1>

        <input id="search" name="search" type="text" class="search-input" placeholder="search...">

        <%if (flag) {%>
        <div class="main-button-container">
            <button class="add-participant-button button" onclick="addUserToNotebook.showModal()">+ Друг</button>
            <button class="add-participant-button button" onclick="addSpectatorToNotebook.showModal()">+ Зритель</button>
        </div>
        <%}%>

        <div class="diaries-container">
            <%
                for (var note : notes) {%>
            <div class="diary diary-container">
                <a class="title-to-description "
                   name='<%=note.getTitle()%>'
                   description='<%=userRepository.findById(note.getCreatorId()).get().getNickname()%>'
                   href="<%=context%>/note?nId=<%=note.getId()%>">
                    <h6>[<%=note.getCreationDate().toString()%>]</h6>
                </a>

                <%if (note.getCreatorId() == user.getId()) {%>
                <a href="<%=context%>/deleteNote?nId=<%=note.getId()%>&bId=<%=request.getParameter("nId")%>">
                    <svg class="notebook-icon" xmlns="http://www.w3.org/2000/svg" id="trash" viewBox="0 0 24 24">
                        <path d="M21,4H17.9A5.009,5.009,0,0,0,13,0H11A5.009,5.009,0,0,0,6.1,4H3A1,1,0,0,0,3,6H4V19a5.006,5.006,0,0,0,5,5h6a5.006,5.006,0,0,0,5-5V6h1a1,1,0,0,0,0-2ZM11,2h2a3.006,3.006,0,0,1,2.829,2H8.171A3.006,3.006,0,0,1,11,2Zm7,17a3,3,0,0,1-3,3H9a3,3,0,0,1-3-3V6H18Z"></path>
                        <path d="M10,18a1,1,0,0,0,1-1V11a1,1,0,0,0-2,0v6A1,1,0,0,0,10,18Z"></path>
                        <path d="M14,18a1,1,0,0,0,1-1V11a1,1,0,0,0-2,0v6A1,1,0,0,0,14,18Z"></path>
                    </svg>
                </a>
                <%}%>
            </div>
            <%}%>
        </div>



        <div class="navigation-button-container">
            <a class="next-prev-button button"
               href="${pageContext.request.contextPath}/diary?nId=<%=nId%>&start=<%=start-step%>&step=<%=step%>">
                Назад
            </a>
            <a class="next-prev-button button"
               href="${pageContext.request.contextPath}/diary?nId=<%=nId%>&start=<%=start+step%>&step=<%=step%>">
                Вперед
            </a>
        </div>
    </main>
</div>

<footer>Footer</footer>

<div class="hidded dialogs">
    <dialog id="overlayMenu">
        <form action="<%=context%>/createNote?nId=<%=request.getParameter("nId")%>" method="post">
            <input type="hidden" name="csrf_token" value="<%=csrf_token%>"/>
            <h1 class="beautiful-main-message">Новая страница</h1>

            <label for="title">Заголовок:</label><br/>
            <input id="title" name="title" type="text" placeholder="название..." value="Тетрадь" required><br/>

            <button type="button" class="button form-button" onclick="overlayMenu.close()">
                Назад
            </button>
            <input type="submit" value="Создать" class="button form-button">
        </form>
    </dialog>

    <%if (flag) {%>

    <dialog id="addUserToNotebook">
        <form action="${pageContext.request.contextPath}/inviteUserToNotebook?nId=<%=request.getParameter("nId")%>"
              method="post">
            <input type="hidden" name="csrf_token" value="<%=csrf_token%>"/>
            <h1 class="beautiful-main-message">Пригласить друга:</h1>

            <label for="userInvite">Введите почту друга:</label><br/>
            <input id="userInvite" name="userInvite" type="text" placeholder="почта..." required><br/>

            <input type="hidden" id="role" name="role" value="2"/>
            <button type="button" class="button form-button" onclick="addUserToNotebook.close()">
                Назад
            </button>
            <input type="submit" value="Пригласить" class="form-button">
        </form>
    </dialog>

    <dialog id="addSpectatorToNotebook">
        <form action="${pageContext.request.contextPath}/inviteUserToNotebook?nId=<%=request.getParameter("nId")%>"
              method="post" class="create-note-form">
            <input type="hidden" name="csrf_token" value="<%=csrf_token%>"/>
            <h1 class="beautiful-main-message">Пригласить читателя:</h1>

            <label for="userInvite">Введите почту читателя:</label><br/>
            <input id="userInvite" name="userInvite" type="text" placeholder="почта..." required><br/>

            <input type="hidden" id="role" name="role" value="3"/>
            <button type="button" class="button form-button" onclick="addSpectatorToNotebook.close()">
                Назад
            </button>
            <input type="submit" value="Пригласить" class="form-button">
        </form>
    </dialog>
    <%}%>
</div>

<script>
    const searchInput = document.getElementById('search');
    const items = document.querySelectorAll('.note');

    searchInput.addEventListener('input', function () {
        const query = this.value.toLowerCase();

        items.forEach(item => {
            const text = item.getAttribute('name').toLowerCase();
            item.style.display = text.includes(query) ? '' : 'none';
        });
    });
</script>

</html>