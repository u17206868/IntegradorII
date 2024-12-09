package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CerrarSesionServlet")
public class CerrarSesionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cerrarSesion(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cerrarSesion(request, response);
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidar la sesión para cerrar la sesión del usuario
        HttpSession session = request.getSession(false); // Obtener la sesión existente
        if (session != null) {
            session.invalidate(); // Invalidar la sesión
        }

        // Redirigir al login o página de inicio
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
