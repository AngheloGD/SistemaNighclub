<%-- 
    Document   : editarUsua
    Created on : 30 oct 2023, 20:54:10
    Author     : ANGHELO
--%>
<%@ page import="dto.Usuario" %>
<%@ page import="dao.UsuarioJpaController" %>
<%@ page import="javax.persistence.EntityManagerFactory" %>
<%@ page import="javax.persistence.Persistence" %>
<%@ page import="java.io.PrintWriter" %>

<%
    String codigo = request.getParameter("codigo");
    if (codigo != null) {
        int codigoUsuario = Integer.parseInt(codigo);

        // Inicializa el EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaNightclub_war_1.0-SNAPSHOTPU");

        // Aquí deberías cargar el usuario a editar desde la base de datos
        UsuarioJpaController usuDao = new UsuarioJpaController(emf);
        Usuario usuarioAEditar = usuDao.findUsuario(codigoUsuario);

        if (usuarioAEditar != null) {
            String nombre = request.getParameter("nombre");
            String logi = request.getParameter("logiUsua");

            if (nombre != null || logi != null) {
                if (nombre != null) {
                    usuarioAEditar.setNombUsua(nombre);
                }
                if (logi != null) {
                    usuarioAEditar.setLogiUsua(logi);
                }

                try {
                    usuDao.edit(usuarioAEditar);
%>
<!DOCTYPE html>

<html>

    <head>
        <title>Editar Usuario</title>

    </head>
    <body>
        <h1>Editar Usuario</h1>
        <p>Usuario editado con éxito.</p>
    </body>
</html>
<%
    // Después de editar, redirige a la página de listado.jsp
    response.sendRedirect("listado.jsp");
} catch (Exception ex) {
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar Usuario</title>
    </head>
    <body>
        <h1>Editar Usuario</h1>
        <p>Error al editar el usuario.</p>
    </body>
</html>
<%
    }
} else {
%>
<!DOCTYPE html>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link href="css/EditarUsuario.css" rel="stylesheet" type="text/css"/>
<html>
    <head>
        <title>Editar Usuario</title>
    </head>
    <body>

        <form method="post" action="editarUsua.jsp">
            <h1>Editar Usuario</h1>
            Código: <%= usuarioAEditar.getCodiUsua()%><br>
            Usuario: <input type="text" name="logiUsua" value="<%= usuarioAEditar.getLogiUsua()%>"><br>
            Nombre: <input type="text" name="nombre" value="<%= usuarioAEditar.getNombUsua()%>"><br>
            <input type="hidden" name="codigo" value="<%= usuarioAEditar.getCodiUsua()%>">
            <input type="submit" value="Guardar Cambios">
        </form>
    </body>
</html>
<%
    }
} else {
%>
<p>El usuario no se encontró en la base de datos.</p>
<%
    }
} else {
%>
<p>Parámetro de código faltante.</p>
<%
    }
%>