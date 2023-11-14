<%-- 
    Document   : eliminarUsua
    Created on : 30 oct 2023, 20:54:49
    Author     : ANGHELO
--%>
<%@ page import="dto.Usuario" %>
<%@ page import="dao.UsuarioJpaController" %>

<%
    String codigo = request.getParameter("codigo");
    if (codigo != null) {
        int codigoUsuario = Integer.parseInt(codigo);

        UsuarioJpaController usuDao = new UsuarioJpaController();
        Usuario usuarioAEliminar = usuDao.findUsuario(codigoUsuario);

        if (usuarioAEliminar != null) {
            usuDao.destroy(codigoUsuario);
            // Redirige al archivo "index.html" después de eliminar la cuenta.
            response.sendRedirect("index.html");
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