package servlet;

import dao.EmpleadoJpaController;
import dto.Empleado;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "ObTrab", urlPatterns = {"/ObTrab"})
public class ObTrab extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            EmpleadoJpaController emplDAO = new EmpleadoJpaController();
            //llamo al valor de la cookies y comparo con el token del usuario
            List<Empleado> empleados = emplDAO.mostrasclientes();
            if (empleados != null && !empleados.isEmpty()) {
                JSONArray empleadosJSON = new JSONArray();

                for (Empleado empleado : empleados) {
                    JSONObject empleadoJSON = new JSONObject();
                    empleadoJSON.put("nombre", empleado.getNombempl());
                    empleadosJSON.put(empleadoJSON);
                }
                out.print(empleadosJSON.toString());
            } else {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"No se encontraron empleados\"}");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
