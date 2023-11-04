<%--
  Created by IntelliJ IDEA.
  User: davidarroyo
  Date: 4/11/23
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Iniciar Sesión o Crear Cuenta</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
  <h2 class="col mx-auto my-auto text-center mt-4">Iniciar Sesión o Crear Cuenta</h2>
  <form id="loginForm" action="procesarLogin" method="post">
    <div class="form-group">
      <label for="email">User:</label>
      <input type="text" class="form-control" id="email" name="user" required>
    </div>
    <div class="form-group">
      <label for="password">Contraseña:</label>
      <input type="password" class="form-control" id="password" name="password" required>
    </div>
    <div class="form-check">
      <input type="radio" class="form-check-input" id="crearCuenta" name="accion" value="crearCuenta">
      <label class="form-check-label" for="crearCuenta">Crear Cuenta</label>
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
  </form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
