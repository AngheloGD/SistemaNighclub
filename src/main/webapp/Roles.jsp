<%@page import="java.util.List"%>
<%@ page import="dto.Rol"%>
<%@ page import="dao.RolJpaController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Información de Roles</title>
        <link href="css/crub.css" rel="stylesheet" type="text/css"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                            window.location.href = "index.html";
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("Error: " + textStatus + ", " + errorThrown);
                        // Si ocurre un error, redirigir a index.html
                        window.location.href = "index.html";
                    }
                });
            }
            $(document).ready(function () {
                verificarSesion();
            });
        </script>
    </head>
    <body>
        <h1>Información de Roles</h1><br>
        <a href="Principal.html" class="btn btn-primary">Regresar</a>
        <br/><!-- comment -->
        <a href="AgregarRol.jsp" class="btn btn-primary">Agregar Rol</a>
        <table>
            <tr>
                <th>Código de Rol</th>
                <th>Nombre de Rol</th>
                <th>Fecha de Rol</th>
                <th>Opciones</th>
            </tr>
            <%
                // Acceder a la lista de roles desde la base de datos
                RolJpaController rolDao = new RolJpaController();
                List<Rol> roles = rolDao.findRolEntities();

                // Iterar sobre la lista de roles y mostrar la información de cada rol
                for (Rol rol : roles) {
            %>
            <tr>
                <td><%= rol.getCodRol()%></td>
                <td><%= rol.getNombRol()%></td>
                <td><%= rol.getFechRol()%></td>
                <td>
                    <!-- Aquí puedes agregar enlaces para editar o eliminar roles -->
                    <a href="editarRol.jsp?codigo=<%= rol.getCodRol()%>">Editar</a>
                    <a href="eliminarRol.jsp?codigo=<%= rol.getCodRol()%>">Eliminar</a>
                </td>
            </tr>
            <% }%>
        </table>
    </body>
</html>
