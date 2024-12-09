package Servlets;

import DAO.UsuarioDAO;
import Entidad.Usuario;
import conexion.Conexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Conexion conexion = new Conexion();
        usuarioDAO = new UsuarioDAO(conexion);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Validar usuario
        Usuario usuario = usuarioDAO.validarUsuario(correo, contrasena);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario); // Guarda el objeto usuario completo
            session.setAttribute("id_rol", usuario.getRol()); // Guarda el rol
            session.setAttribute("idUsuario", usuario.getIdUsuario()); // Guarda el ID del usuario

            // Redirige según el rol del usuario
            String rol = usuario.getRol();
            switch (rol) {
                case "Administrador":
                    response.sendRedirect("adminDashboard.jsp");
                    break;
                case "Trabajador":
                    response.sendRedirect("workerDashboard.jsp");
                    break;
                default:
                    response.sendRedirect("defaultDashboard.jsp");
                    break;
            }
        } else {
            // Si el usuario no es válido, mostrar mensaje de error
            request.setAttribute("errorMessage", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si se llega a acceder al servlet por GET, redirigir al login
        response.sendRedirect("login.jsp");
    }
}
