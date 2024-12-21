<%@ page import="ru.itis.mydiary.entity.Notebook" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.mydiary.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String context = request.getContextPath();
    User user = (User) request.getAttribute("user");
    String csrf_token = (String) request.getAttribute("csrf_token");
    List<Notebook> notebooks = (List<Notebook>) request.getAttribute("notebooks");
%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=context%>/css/diary16.css">
</head>
<body>
<%@ include file="components/header.jsp" %>
<div class="content">
    <aside class="sidebar-left">
        <div class="sidebar-left-button-container">
            <a class="button green-elem" href="<%=context%>/invites">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="m23.99,11.042c.071.879-.246,1.74-.869,2.364-1.489,1.415-3.292,3.117-3.532,3.291-.178.13-.384.192-.588.192-.309,0-.613-.143-.809-.41-.322-.44-.23-1.056.201-1.385.23-.19,1.883-1.745,3.332-3.121.189-.19.295-.477.271-.771-.024-.297-.174-.56-.424-.739-.38-.274-.976-.19-1.355.189-.286.286-.715.371-1.088.217-.373-.154-.617-.517-.619-.921l-.02-6.201c0-.415-.335-.749-.747-.749-.2,0-.388.078-.53.22-.13.131-.199.304-.209.487l-.004,2.292c0,.552-.448,1-1,1s-1-.448-1-1l.006-3.256c0-.41-.334-.744-.746-.744-.2,0-.388.078-.528.22-.141.142-.218.33-.217.53l-.015,1.263c-.006.465-.332.866-.787.965-.452.101-.918-.128-1.117-.55-.096-.203-.474-.43-.836-.43-.289,0-.556.169-.679.43-.236.5-.833.712-1.332.476s-.712-.833-.476-1.332c.572-1.21,2.039-1.822,3.313-1.458.114-.486.363-.938.728-1.305.519-.521,1.21-.809,1.946-.809.971,0,1.821.51,2.31,1.272.363-.172.759-.272,1.173-.272,1.515,0,2.747,1.231,2.747,2.746l.015,4.607c.775-.136,1.586.02,2.237.489.722.52,1.177,1.322,1.248,2.201Zm-8.791-2.234c.52.522.805,1.216.801,1.953v7.239c0,3.309-2.691,6-6,6h-1.592c-1.804,0-3.5-.702-4.776-1.979l-2.726-2.599c-.64-.64-.957-1.502-.886-2.381.071-.878.525-1.681,1.248-2.201.65-.469,1.46-.624,2.237-.488l.015-4.61c0-1.511,1.232-2.743,2.747-2.743.414,0,.81.1,1.173.273.488-.763,1.338-1.273,2.31-1.273.736,0,1.427.288,1.947.81.369.371.613.831.725,1.33.263-.084.538-.142.829-.142.737,0,1.429.288,1.949.811Zm-1.199,1.948c0-.206-.077-.395-.218-.538-.142-.143-.331-.221-.532-.221-.414,0-.75.336-.75.749,0,.009-.005.017-.005.026v3.228c0,.552-.447,1-1,1s-1-.447-1-1v-5.246c0-.205-.076-.393-.217-.535-.141-.142-.329-.22-.528-.22-.411,0-.746.334-.746.744l.01,5.256c0,.553-.448,1-1,1s-1-.447-1-1l-.008-4.29c-.009-.184-.078-.358-.209-.489-.141-.142-.33-.22-.53-.22-.412,0-.747.334-.747.746l-.02,6.204c-.001.404-.246.768-.619.922-.373.153-.803.068-1.088-.218-.381-.381-.976-.463-1.356-.189-.249.18-.399.442-.423.739-.023.294.082.581.29.789l2.726,2.599c.915.915,2.109,1.409,3.379,1.409h1.592c2.206,0,4-1.794,4-4v-7.244Z"></path>
                </svg>
                <!-- friends -->
            </a>
            <a class="button green-elem" href="<%=context%>/settings">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path>
                    <path d="M21.294,13.9l-.444-.256a9.1,9.1,0,0,0,0-3.29l.444-.256a3,3,0,1,0-3-5.2l-.445.257A8.977,8.977,0,0,0,15,3.513V3A3,3,0,0,0,9,3v.513A8.977,8.977,0,0,0,6.152,5.159L5.705,4.9a3,3,0,0,0-3,5.2l.444.256a9.1,9.1,0,0,0,0,3.29l-.444.256a3,3,0,1,0,3,5.2l.445-.257A8.977,8.977,0,0,0,9,20.487V21a3,3,0,0,0,6,0v-.513a8.977,8.977,0,0,0,2.848-1.646l.447.258a3,3,0,0,0,3-5.2Zm-2.548-3.776a7.048,7.048,0,0,1,0,3.75,1,1,0,0,0,.464,1.133l1.084.626a1,1,0,0,1-1,1.733l-1.086-.628a1,1,0,0,0-1.215.165,6.984,6.984,0,0,1-3.243,1.875,1,1,0,0,0-.751.969V21a1,1,0,0,1-2,0V19.748a1,1,0,0,0-.751-.969A6.984,6.984,0,0,1,7.006,16.9a1,1,0,0,0-1.215-.165l-1.084.627a1,1,0,1,1-1-1.732l1.084-.626a1,1,0,0,0,.464-1.133,7.048,7.048,0,0,1,0-3.75A1,1,0,0,0,4.79,8.992L3.706,8.366a1,1,0,0,1,1-1.733l1.086.628A1,1,0,0,0,7.006,7.1a6.984,6.984,0,0,1,3.243-1.875A1,1,0,0,0,11,4.252V3a1,1,0,0,1,2,0V4.252a1,1,0,0,0,.751.969A6.984,6.984,0,0,1,16.994,7.1a1,1,0,0,0,1.215.165l1.084-.627a1,1,0,1,1,1,1.732l-1.084.626A1,1,0,0,0,18.746,10.125Z"></path>
                </svg>
                <!-- settings -->
            </a>

            <button type="button" class="button green-elem" onclick="overlayMenu.showModal()">
                <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="m12 0a12 12 0 1 0 12 12 12.013 12.013 0 0 0 -12-12zm0 22a10 10 0 1 1 10-10 10.011 10.011 0 0 1 -10 10zm5-10a1 1 0 0 1 -1 1h-3v3a1 1 0 0 1 -2 0v-3h-3a1 1 0 0 1 0-2h3v-3a1 1 0 0 1 2 0v3h3a1 1 0 0 1 1 1z"></path>
                </svg>
                <!-- create -->
            </button>
        </div>
    </aside>

    <main>
        <h1 class="beautiful-main-message">Добрый день, <%= user.getNickname()%>
        </h1>

        <input id="search" name="search" type="text" class="search-input" placeholder="search...">

        <div class="diaries-container">
            <%
                for (var notebook : notebooks) {%>
            <div class="diary diary-container">
                <a class="title-to-description "
                   name='<%=notebook.getTitle()%>'
                   description='<%=notebook.getDescription()%>'
                   href="<%=context%>/diary?nId=<%=notebook.getId()%>">
                </a>

                <a href="<%=context%>/deleteDiary?nId=<%=notebook.getId()%>">
                    <%if (notebook.getCreatorId() == user.getId()) {%>
                    <svg class="notebook-icon" xmlns="http://www.w3.org/2000/svg" id="trash" viewBox="0 0 24 24">
                        <path d="M21,4H17.9A5.009,5.009,0,0,0,13,0H11A5.009,5.009,0,0,0,6.1,4H3A1,1,0,0,0,3,6H4V19a5.006,5.006,0,0,0,5,5h6a5.006,5.006,0,0,0,5-5V6h1a1,1,0,0,0,0-2ZM11,2h2a3.006,3.006,0,0,1,2.829,2H8.171A3.006,3.006,0,0,1,11,2Zm7,17a3,3,0,0,1-3,3H9a3,3,0,0,1-3-3V6H18Z"></path>
                        <path d="M10,18a1,1,0,0,0,1-1V11a1,1,0,0,0-2,0v6A1,1,0,0,0,10,18Z"></path>
                        <path d="M14,18a1,1,0,0,0,1-1V11a1,1,0,0,0-2,0v6A1,1,0,0,0,14,18Z"></path>
                    </svg>
                    <%} else {%>
                    <svg class="notebook-icon" xmlns="http://www.w3.org/2000/svg" id="exitFromNotebook" viewBox="0 0 24 24">
                        <path d="m24,4v16c0,2.206-1.794,4-4,4h-3c-.552,0-1-.448-1-1s.448-1,1-1h3c1.103,0,2-.897,2-2V4c0-1.103-.897-2-2-2h-3c-.552,0-1-.448-1-1s.448-1,1-1h3c2.206,0,4,1.794,4,4Zm-7.015,10.45l-5.293,5.272c-.508.509-1.195.778-1.907.778-.369,0-.744-.072-1.104-.221-1.033-.425-1.677-1.352-1.681-2.418v-1.861H3c-1.654,0-3-1.346-3-3v-2c0-1.654,1.346-3,3-3h4v-1.859c.005-1.07.649-1.997,1.682-2.421,1.055-.433,2.238-.215,3.012.559l5.29,5.267c1.352,1.353,1.351,3.551,0,4.903Zm-1.414-3.488l-5.29-5.267c-.245-.245-.593-.224-.838-.125-.132.055-.441.22-.442.576v2.854c0,.552-.448,1-1,1H3c-.551,0-1,.449-1,1v2c0,.551.449,1,1,1h5c.552,0,1,.448,1,1v2.857c.001.353.31.519.442.573.244.101.593.122.836-.123l5.293-5.272c.57-.571.57-1.501,0-2.073Z"></path>
                    </svg>
                    <%}%>
                </a>
            </div>
            <%}%>
        </div>
        <div class="navigation-button-container">
            <%
                Long start = (Long) request.getAttribute("start");
                int step = (int) request.getAttribute("step");
            %>
            <a class="next-prev-button button"
               href="<%=context%>/profile?start=<%=start-step%>&step=<%=step%>">
                Назад
            </a><a class="next-prev-button button"
                   href="<%=context%>/profile?start=<%=start+step%>&step=<%=step%>">
            Вперед
        </a>
        </div>
    </main>
</div>

<%@ include file="components/footer.jsp" %>

<div class="hidded dialogs">
    <dialog id="overlayMenu">
        <form action="<%=context%>/createDiary" method="post" class="creation-form">
            <input type="hidden" name="csrf_token" value="<%=csrf_token%>"/>
            <h1 class="beautiful-main-message">Новая тетрадь</h1>

            <label for="title">Название:</label><br/>
            <input id="title" name="title" type="text" placeholder="название..." value="Тетрадь" required><br/>

            <label for="title">Описание:</label><br/>
            <input id="description" name="description" type="text" placeholder="описание..." value="Описание" required><br/>

            <button type="button" class="button form-button" onclick="overlayMenu.close()">
                Назад
            </button>
            <input type="submit" value="Создать" class="button form-button">
        </form>
    </dialog>
</div>
<script>
    const searchInput = document.getElementById('search');
    const items = document.querySelectorAll('.diary');

    searchInput.addEventListener('input', function () {
        const query = this.value.toLowerCase();

        items.forEach(item => {
            const text = item.getAttribute('name').toLowerCase();
            item.style.display = text.includes(query) ? '' : 'none';
        });
    });
</script>
</body>
</html>