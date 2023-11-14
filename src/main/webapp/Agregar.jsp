<%-- 
    Document   : Agregar
    Created on : 29 oct 2023, 11:56:40
    Author     : ANGHELO
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registro de Usuarios</title>
        <!-- Agrega los enlaces a Bootstrap CSS y JS desde un CDN -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </head>
<body style="background-image: url('assets/img/contraseña.jpg'); background-size: cover; background-position: center; background-attachment: fixed; margin: 0; padding: 0; font-family: Arial, sans-serif;">

    <div class="form-container" style="max-width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #000; border-radius: 5px; background-color: #333; color: #fff;">

        <h1 style="text-align: center; color: #ff0000;">Registro de Usuarios</h1>

        <form action="agregarUsu" method="post">
            <label for="codiUsua" style="color: #ff0000;">Número de DNI:</label>
            <input type="text" id="codiUsua" name="codiUsua" required style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #333; color: #fff; border: 1px solid #ff0000;">

            <label for="logiUsua" style="color: #ff0000;">Usuario:</label>
            <input type="text" id="logiUsua" name="logiUsua" required style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #333; color: #fff; border: 1px solid #ff0000;">

            <label for="passUsua" style="color: #ff0000;">Contraseña:</label>
            <input type="password" id="passUsua" name="passUsua" required style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #333; color: #fff; border: 1px solid #ff0000;">

            <label for="nombUsua" style="color: #ff0000;">Nombre:</label>
            <input type="text" id="nombUsua" name="nombUsua" required style="width: 100%; padding: 10px; margin-bottom: 10px; background-color: #333; color: #fff; border: 1px solid #ff0000;">

            <input type="submit" value="Registrar" id="registrarBtn" style="background-color: #ff0000; color: #fff; padding: 10px 20px; border: none; cursor: pointer;">
        </form>

        <div class="alert alert-success" role="alert" id="mensajeAlerta" style="display: none; background-color: #ff0000; color: #fff; padding: 10px; text-align: center; margin-top: 10px;">
            Registro exitoso
        </div>
    </div>

        <script src="node_modules/crypto-js/crypto-js.js"></script>

        <script>
            document.querySelector("#registrarBtn").addEventListener("click", function (event) {
                event.preventDefault();

                // Obtener los datos del formulario
                const codiUsua = document.querySelector("#codiUsua").value;
                const logiUsua = document.querySelector("#logiUsua").value;
                const passUsua = document.querySelector("#passUsua").value;
                const nombUsua = document.querySelector("#nombUsua").value;
                const FechaUsuario = new Date();
                const formato = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'};
                const fechaFormateada = FechaUsuario.toLocaleString('es-ES', formato);


                // const CryptoJS = require("crypto-js"); // Importa la biblioteca

                // Cifrar los datos JSON
                //const dataString = JSON.stringify(logiUsua); // Convierte los datos a una cadena JSON
                //const token = CryptoJS.AES.encrypt(dataString, fechaFormateada).toString();

                // Realizar la solicitud POST al servidor
                fetch("agregarUsu", {
                    method: "POST",
                    body: new URLSearchParams({
                        codiUsua: codiUsua,
                        FechaUsuario: fechaFormateada,
                        logiUsua: logiUsua,
                        passUsua: passUsua,
                        nombUsua: nombUsua

                    }),
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                })
                        .then(response => response.text())
                        .then(mensaje => {
                            if (mensaje === "Registro exitoso") {
                                // Mostrar la alerta con el mensaje de éxito
                                document.querySelector(".alert-success").style.display = "block";
                                document.querySelector("#mensajeAlerta").textContent = mensaje;

                                //console.log(token);
                                // Redirigir al usuario a la página de inicio después de 3 segundos
                                setTimeout(function () {
                                    window.location.href = "index.html";
                                }, 3000);
                            } else {
                                // Manejar mensajes de error si es necesario
                                console.error("Error:", mensaje);
                            }
                        })
                        .catch(error => {
                            // Manejar errores, por ejemplo, si la solicitud falla
                            console.error("Error:", error);
                        });
            });

        </script>
    </body>
</html>

