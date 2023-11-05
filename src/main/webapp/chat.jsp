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
    <style>
        .chat-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
        }

        .message-list {
            list-style: none;
            padding: 0;
        }

        .message-item {
            background-color: #f2f2f2;
            margin: 10px 0;
            padding: 10px;
            border-radius: 10px;
        }

        #chat-form {
            display: flex;
            flex-direction: column;
        }

        input[type="text"] {
            margin-bottom: 10px;
        }

        button[type="submit"] {
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="chat-container">
    <ul class="message-list" id="message-list">
        <!-- Aquí se mostrarán los mensajes -->
    </ul>
    <form id="chat-form">
        <input type="text" id="mensaje" name="mensaje" placeholder="Escribe un mensaje" class="form-control">
        <input type="text" id="destinatario" name="user" placeholder="A quién va dirigido" class="form-control">
        <button type="submit" class="btn btn-primary">Enviar</button>
    </form>
</div>
<script>

    // informacion Sesion
    var urlParams = new URLSearchParams(window.location.search);
    const user = urlParams.get('user');

    console.log(user)

    const messageList = document.getElementById("message-list");
    const chatForm = document.getElementById("chat-form");
    const mensajeInput = document.getElementById("mensaje");
    const destinatarioInput = document.getElementById("destinatario");

    const socket = new WebSocket("ws://localhost:8080/chatOnline/chat");

    socket.addEventListener("open", (event) => {
        console.log("Conexión WebSocket abierta.");
    });

    socket.addEventListener("message", (event) => {
        if (event.data === "reload") {
            setTimeout(function() {
                location.reload();
            }, 1000);
        }
        const mensajeRecibido = event.data;
        receiveMessage(mensajeRecibido);
    });

    socket.addEventListener("close", (event) => {
        console.log("Conexión WebSocket cerrada.");
    });

    function addMessage(message) {
        const messageItem = document.createElement("li");
        messageItem.className = "message-item";
        messageItem.textContent = message;
        messageList.appendChild(messageItem);
    }

    chatForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const mensaje = mensajeInput.value;
        const destinatario = destinatarioInput.value;
        const messageData = {mensaje, destinatario, user};
        // Envía el mensaje al servidor WebSocket
        socket.send(JSON.stringify(messageData));
        mensajeInput.value = "";
    });

    function receiveMessage(message) {
        addMessage(message);
    }

</script>
</body>
</html>
