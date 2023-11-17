/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import security.MD5;

/**
 *
 * @author ANGHELO
 */
@WebServlet(name = "LlamarToken", urlPatterns = {"/llamarToken"})
public class LlamarToken extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtiene los parámetros del usuario y la clave desde la solicitud HTTP
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");

            // Calcula el hash MD5 de la clave
            String claveMD5 = MD5.getMd5Hash(clave);

            // Obtiene la longitud de la clave
            int tamaño = clave.length();

            // Inicializa una variable para almacenar el token
            String token = null;

            // Crea una instancia de UsuarioJpaController
            UsuarioJpaController usuDAO = new UsuarioJpaController();

            // Utiliza un switch para determinar qué lógica de generación de token aplicar
            switch (tamaño) {
                case 32:
                    // Si la longitud de la clave es 32, utiliza la lógica existente para obtener un token
                    token = usuDAO.generateJwtToken(usuario, clave);
                    break;
                default:
                    // Si la longitud de la clave no es 32, utiliza la lógica de jjwt desde UsuarioJpaController
                    token = usuDAO.generateJwtToken(usuario, claveMD5);
                    break;
            }

            // Verifica si se generó un token
            if (token != null) {
                // Si se generó un token, imprime la respuesta JSON con el token
                out.print("{\"resultado\":\"" + token + "\"}");
            } else {
                // Si no se generó un token, imprime una respuesta de error en formato JSON
                out.print("{\"resultado\":\"error\"}");
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
