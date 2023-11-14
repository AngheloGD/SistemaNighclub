/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dto.Cita;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ANGHELO
 */
@WebServlet(name = "RegiCita", urlPatterns = {"/regiCita"})
public class RegiCita extends HttpServlet {

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
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuarioLogueado != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = null;
                try {
                    String fechaStr = request.getParameter("fecha");
                    fecha = dateFormat.parse(fechaStr);
                } catch (Exception e) {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"Error en la conversión de fecha\"}");
                    return;
                }
                String trabajador = request.getParameter("trabajador");
                String servicio = request.getParameter("servicio");

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_SistemaNightclub_war_1.0-SNAPSHOTPU");
                EntityManager em = emf.createEntityManager();

                EntityTransaction tx = em.getTransaction();
                try {
                    tx.begin();

                    Cita nuevaCita = new Cita();
                    nuevaCita.setDiacita(fecha);
                    nuevaCita.setEmplcita(trabajador);
                    nuevaCita.setServcita(servicio);
                    nuevaCita.setCodiUsua(usuarioLogueado);

                    em.persist(nuevaCita);

                    tx.commit();

                    out.print("{\"resultado\":\"ok\"}");
                } catch (Exception ex) {
                    ex.printStackTrace(); // Esto imprimirá el rastro de la excepción en la consola del servidor
                    if (tx != null && tx.isActive()) {
                        tx.rollback();
                    }
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"Error al registrar la cita\"}");

                } finally {
                    em.close();
                }
                emf.close();
            } else {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"Usuario no logueado\"}");
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
