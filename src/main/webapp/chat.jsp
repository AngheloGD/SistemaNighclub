<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.Cookie" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ayuda</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
        <link rel="icon" type="image/x-icon" href="data:image/x-icon;,">
        <link href="css/ChatStyle.css" rel="stylesheet" type="text/css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    </head>
    <body>
        <div class="flex-container">
            <div>
                <button class="button red" onclick="goToPrincipalPage()">Ir a la página principal</button>
            </div>
            <div class="center-container">
                <h2>Ayuda al cliente</h2>
            </div>
        </div>
<script>
            function verificarSesion() {
                $.ajax({
                    url: 'validarSesion',
                    type: 'GET',
                    dataType: 'json',
                    success: function (response) {
                        if (response.resultado === "ok") {
                            //$("#usernameSpan").text(response.logiUsua);
                        } else {
                            // El usuario no está autenticado, redirigir a index.html
                            console.log("Redirigiendo a index.html");
                            window.location.replace("index.html");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("Error: " + textStatus + ", " + errorThrown);
                        // Si ocurre un error, redirigir a index.html
                        window.location.replace("index.html");
                    }
                });
            }
            $(document).ready(function () {
                verificarSesion();
            });
        </script>
        <%
            // Verificar la sesión en cada carga de la página
            boolean tokenPresente = false;
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        tokenPresente = true;
                        break;
                    }
                }
            }

            if (!tokenPresente) {
        %>
        <div style="color: red; font-size: 18px; margin: 20px;">Error: Inicia sesión para acceder a esta página.</div>
        <%
                // Puedes redirigir al usuario a la página de inicio de sesión u otra página según tus necesidades.
                return;
            }
        %>

        <form>
            <input id="newUserField" name="user" value="" type="text"> 
            <input id="newUserButton" onclick="join();" value="Entrar" type="button">
            <br/><br/>

            <!-- Nueva div para la imagen del bailarín -->
            <div class="dancer-image"></div>

            <!-- Nueva div para la animación de carga -->
            <div class="loading-image hidden"></div>

            <div id="chatRoomField" class="chat-message hidden"></div>
            <br/>
            <input id="sendField" name="message" value="" type="text" class="hidden" disabled>
            <input id="sendButton" onclick="send_message();" value="Enviar" type="button" class="hidden" disabled>
        </form>
        
        <script type="text/javascript" src="websocket.js"></script>

        <script>
    // Función para redirigir a principal.html
    function goToPrincipalPage() {
        window.location.href = 'Principal.html';
    }

    $(document).ready(function () {
        // Obtener el nombre de usuario almacenado en LocalStorage
        var username = localStorage.getItem('username');

        // Verificar si se ha iniciado sesión y si se ha obtenido el nombre de usuario
        if (username) {
            // Insertar el nombre de usuario en el campo de entrada
            $("#newUserField").val(username);
        }
    });
        </script>
    </body>
</html>
