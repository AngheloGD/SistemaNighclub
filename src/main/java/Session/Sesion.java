/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Session;

import javax.servlet.http.HttpSession;

/**
 *
 * @author ANGHELO
 */
public class Sesion {

    public static void crearsesion(HttpSession session, String logiUsua, String passUsua) {
        session.setAttribute("logueado", "1");
        session.setAttribute("logiUsua", logiUsua);
        session.setAttribute("passUsua", passUsua);
    }

    public static boolean sesionValida(HttpSession session) {
        if (session != null) {
            String logueado = (String) session.getAttribute("logueado");
            return "1".equals(logueado);
        }
        return false;
    }

    public static void cerrarsesion(HttpSession session) {
        session.removeAttribute("logueado");
        session.removeAttribute("logiUsua");
        session.removeAttribute("passUsua");
        session.invalidate();
    }

    public static String getpassUsua(HttpSession session) {
        Object opass = session.getAttribute("passUsua");
        return opass.toString();
    }

    public static String getLogiUsua(HttpSession session) {
        Object ologi = session.getAttribute("logiUsua");
        return ologi.toString();
    }
}
