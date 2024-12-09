package Servlets;

import DAO.UsuarioDAO;
import Entidad.Usuario;
import conexion.Conexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Conexion conexion = new Conexion();
        usuarioDAO = new UsuarioDAO(conexion);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No se necesita hacer nada en el doGet
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena");

        System.out.println("Datos recibidos: " + nombre + ", " + apellido + ", " + correo + ", " + telefono);

        int idRol = 2; // Asignar automáticamente el rol de Trabajador (id_rol = 2)

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setIdRol(idRol);
        nuevoUsuario.setContrasena(contrasena);

        if (usuarioDAO.registrarUsuario(nuevoUsuario)) {
            System.out.println("Usuario registrado con éxito");
            response.sendRedirect("login.jsp"); // Redirige a la página de login después del registro
        } else {
            System.out.println("Error al registrar el usuario");
            request.setAttribute("error", "Error al registrar el usuario");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
