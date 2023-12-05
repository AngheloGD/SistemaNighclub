<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Roles</title>
    <!-- Agrega los enlaces a Bootstrap CSS y JS desde un CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</head>
<body style="background-image: url('assets/img/contraseña.jpg'); background-size: cover; background-position: center; background-attachment: fixed; margin: 0; padding: 0; font-family: Arial, sans-serif;">

    <div class="form-container" style="max-width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #000; border-radius: 5px; background-color: #333; color: #fff;">

        <h1 style="text-align: center; color: #ff0000;">Registro de Roles</h1>

        <form action="agregarRol" method="post">
            <label for="nombreRol" style="color: #ff0000;">Nombre del Rol:</label>
            <input type="text" id="nombreRol" name="nombreRol" required style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #333; color: #fff; border: 1px solid #ff0000;">

            <input type="submit" value="Registrar Rol" id="registrarRolBtn" style="background-color: #ff0000; color: #fff; padding: 10px 20px; border: none; cursor: pointer;">
        </form>

        <div class="alert alert-success" role="alert" id="mensajeAlerta" style="display: none; background-color: #ff0000; color: #fff; padding: 10px; text-align: center; margin-top: 10px;">
            Registro exitoso
        </div>
    </div>

    <!-- Agrega aquí el script para cifrar si es necesario -->

</body>
</html>
