package servlet;

import dto.Rol;
import dao.RolJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adria
 */
@WebServlet("/agregarRol")
public class AgregarRol extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreRol = request.getParameter("nombre");
        

        // Crear un nuevo objeto Rol con el nombre ingresado
        Rol nuevoRol = new Rol();
        nuevoRol.setNombRol(nombreRol);

        // Insertar el nuevo rol en la base de datos
        RolJpaController rolDao = new RolJpaController();
        try {
            rolDao.create(nuevoRol);
        } catch (Exception ex) {
            Logger.getLogger(AgregarRol.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Redireccionar de vuelta a la página de roles
        response.sendRedirect("Roles.jsp");
    }
}
