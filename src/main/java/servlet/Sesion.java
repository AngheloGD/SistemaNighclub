/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import javax.servlet.http.HttpSession;

/**
 *
 * @author adria
 */
public class Sesion {

    public static void crearsesion(HttpSession session, int codi, String logi, String nombre, int nivel) {
        session.setAttribute("logueado", "1");
        session.setAttribute("codi", codi);
        session.setAttribute("logi", logi);
        session.setAttribute("nombre", nombre);
        session.setAttribute("nivel", nivel);
    }

    public static boolean sesionvalida(HttpSession session) {
        String logueado = (String) session.getAttribute("logueado");
        if (logueado.equals("1")) {
            return true;
        }
        return false;
    }

    public static void cerrarsesion(HttpSession session) {
        session.removeAttribute("logueado");
        session.removeAttribute("codi");
        session.removeAttribute("logi");
        session.removeAttribute("nombre");
        session.removeAttribute("nivel");
        session.invalidate();
    }

    public static int getCodi(HttpSession session) {
        Object ocodigo = session.getAttribute("codi");
        return Integer.parseInt(ocodigo.toString());
    }

    public static String getLogi(HttpSession session) {
        Object ologi = session.getAttribute("logi");
        return ologi.toString();
    }
}
