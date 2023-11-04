<%--
  Created by IntelliJ IDEA.
  User: davidarroyo
  Date: 4/11/23
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>

</head>
<body>
    <form action="EnviarMensajeServlet" method="post">
        <input type="text" name="mensaje" placeholder="Escribe un mensaje">

        <input type="text" name="user" placeholder="Aquien va dirigido">

        <input type="submit" value="Enviar">
    </form>
</body>
</html>
