<%@page import="dto.Usuario"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registro de citas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Agregar Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/regis.css" type="text/css"/>
        <script src="js/cargando.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="loader-wrapper">
            <div class="loader"></div>
        </div>
        <div class="container">
            <h1>Citas</h1>

            <div class="user-info">
                <!-- Muestra el nombre de usuario logueado -->
                <p id="usuarioInfo"><%= (session.getAttribute("usuarioLogueado") != null) ? ((Usuario) session.getAttribute("usuarioLogueado")).getNombUsua() : ""%></p>
            </div>

            <div class="card">
                <div class="card-body">
                    <form>
                        <div class="form-group">
                            <label for="fecha">Seleccione el día:</label>
                            <input type="date" class="form-control" id="fecha" name="fecha">
                        </div>
                        <div class="form-group">
                            <label for="trabajadora">Trabajadora:</label>
                            <select class="form-control" id="trabajadora" name="trabajadora">
                                <!-- Opciones para trabajadoras -->
                            </select>
                        </div>
                        <div class="form-group">
                            <label for "servicio">Selecciona un tipo de servicio:</label>
                            <select class="form-control" id="servicio" name="servicio">
                                <option value="Masajes">Masaje</option>
                                <option value="Sex">Sex</option>
                                <option value="Baile">Baile</option>
                            </select>
                        </div>
                        <button type="button" id="btnGuardar" name="btnGuardar" class="btn btn-info" style="background-color: red;">Separar cita</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                // Obtén el nombre de usuario logueado y muestra la información
                $.getJSON("ObTrab", function (data) {
                    if (data.length > 0) {
                        let trabajadoraSelect = $("#trabajadora");
                        trabajadoraSelect.empty();
                        for (let i = 0; i < data.length; i++) {
                            trabajadoraSelect.append($('<option>', {
                                text: data[i].nombre
                            }));
                        }
                    } else {
                        alert("Error al obtener la lista de trabajadoras");
                    }
                });
                $("#btnGuardar").click(function () {
                    let fecha = $("#fecha").val();
                    let trabajador = $("#trabajadora").val();
                    let servicio = $("#servicio").val();
                    let usuarioLogueado = $("#usuarioInfo").text();
                    if (fecha.trim() === "" || trabajador.trim() === "" || servicio.trim() === "") {
                        alert("Por favor, complete todos los campos.");
                        return;
                    }

                    let parametro1 = {
                        fecha: fecha,
                        trabajador: trabajador,
                        servicio: servicio,
                        usuario: usuarioLogueado
                    };
                    let parametrotoken = {usuario: getCookie("usuario"), clave: getCookie("clave")};
                    $.getJSON("llamarToken", parametrotoken, function (data) {
                        const token = getCookie("token");
                        console.log("Token del Servidor: " + data.resultado);
                        console.log(token);
                        if (token === data.resultado) {
                            $.getJSON("regiCita", parametro1, function (data) {
                                if (data.resultado === "ok") {
                                    console.log("Cita Guardada");
                                    console.log(parametrotoken);
                                    alert("Cita registrada con éxito.");
                                    setTimeout(function () {
                                        window.location.href = "Principal.html";
                                    }, 20000);
                                } else {
                                    alert("Error al registrar Cita");
                                }
                            });
                        } else {
                            alert("Error al validar el token");
                        }
                    });

                }
                );
            });
            function getCookie(name) {
                const nameEQ = name + "=";
                const cookies = document.cookie.split(';');
                for (let i = 0; i < cookies.length; i++) {
                    let cookie = cookies[i];
                    while (cookie.charAt(0) === ' ') {
                        cookie = cookie.substring(1, cookie.length);
                    }
                    if (cookie.indexOf(nameEQ) === 0) {
                        return cookie.substring(nameEQ.length, cookie.length);
                    }
                }
                return null;
            }
        </script>
    </body>
</html>
