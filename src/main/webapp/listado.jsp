<%-- 
    Document   : listado
    Created on : 29 oct 2023, 11:34:02
    Author     : ANGHELO
--%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.Usuario" %>
<%@ page import="dao.UsuarioJpaController" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<link href="css/crub.css" rel="stylesheet" type="text/css"/>

<%
    // Recupera la lista de usuarios de la base de datos
    UsuarioJpaController usuDao = new UsuarioJpaController();
    List<Usuario> usu = usuDao.findUsuarioEntities();

    // Verifica si hay un usuario en la sesión
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Informacion del Usuario</title>
    </head>
    <body>
        <h1>Informacion del Usuario</h1><br>
        <a href="Principal.html" class="btn btn-primary">Regresar</a>
        <table>
            <tr>
                <th>Código</th>
                <th>Usuario</th>
                <th>Nombre</th>
                <th>Opciones</th>

            </tr>
            <%
                if (usuarioLogueado != null) {
                    List<Usuario> usuariosMostrados = new ArrayList<>();
                    for (Usuario usuario : usu) {
                        // Filtra los usuarios para mostrar solo el usuario logueado
                        if (usuario.getCodiUsua() == usuarioLogueado.getCodiUsua()) {
                            usuariosMostrados.add(usuario);
                        }
                    }

                    for (Usuario usuario : usuariosMostrados) {
            %>
            <!-- Código para mostrar la información del usuario -->
            <tr>
                <td><%= usuario.getCodiUsua()%></td>
                <td><%= usuario.getLogiUsua()%></td>
                <td><%= usuario.getNombUsua()%></td>
              <td>
                <a href="editarUsua.jsp?codigo=<%= usuario.getCodiUsua()%>">Editar</a>
                <a href="eliminarUsua.jsp?codigo=<%= usuario.getCodiUsua()%>">Eliminar</a>
            </td>
            </tr>
            <%
                }
            } else {
            %>
            <p>Debes iniciar sesión para ver la Informacion del Usuario</p>
            <%
                }
            %>
        </table>
    </head>
</body>
</html>
