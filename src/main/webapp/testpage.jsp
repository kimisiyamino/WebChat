<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.10.2021
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <!-- CSS & JS -->
    <link rel="stylesheet" type="text/css" href="chat.css">
</head>
<body>
    <h1>TestPage</h1>

    <form class="form-control type_msg" name="contacts" method="POST" action="/">
        <input class="input-group-append" type="text" name="nickname" id="nickname" placeholder="Type your nickname" />
        <button class="input-group-text send_btn" class="fas fa-location-arrow" type="submit" name="submit_button" value="main_button">Отправить</button>
    </form>


</body>
</html>
