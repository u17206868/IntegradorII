package Servlets;

import DAO.UsuarioDAO;
import Entidad.Usuario;
import Entidad.Rol;
import conexion.Conexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        Conexion conexion = new Conexion();
        usuarioDAO = new UsuarioDAO(conexion);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Entrando al método doGet de UserServlet");

        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        List<Rol> roles = usuarioDAO.obtenerRoles(); // Obtener roles
        if (usuarios == null) {
            LOGGER.log(Level.SEVERE, "Error al obtener usuarios: la lista es null");
        } else if (usuarios.isEmpty()) {
            LOGGER.log(Level.INFO, "No se encontraron usuarios.");
        } else {
            LOGGER.log(Level.INFO, "Usuarios obtenidos: {0}", usuarios.size());
            for (Usuario usuario : usuarios) {
                LOGGER.log(Level.INFO, "Usuario: {0} {1}", new Object[]{usuario.getNombre(), usuario.getApellido()});
            }
        }

        request.setAttribute("usuarios", usuarios);
        request.setAttribute("roles", roles); // Pasar roles al JSP
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                agregarUsuario(request, response);
                break;
            case "update":
                modificarUsuario(request, response);
                break;
            case "delete":
                eliminarUsuario(request, response);
                break;
            default:
                doGet(request, response);
                break;
        }
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        int idRol = Integer.parseInt(request.getParameter("rol"));
        String contrasena = request.getParameter("contrasena");

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setIdRol(idRol);
        nuevoUsuario.setContrasena(contrasena);

        if (usuarioDAO.registrarUsuario(nuevoUsuario)) {
            LOGGER.log(Level.INFO, "Usuario agregado con éxito: {0} {1}", new Object[]{nombre, apellido});
        } else {
            LOGGER.log(Level.SEVERE, "Error al agregar usuario: {0} {1}", new Object[]{nombre, apellido});
        }

        doGet(request, response);
    }

    private void modificarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        int idRol = Integer.parseInt(request.getParameter("rol"));

        Usuario usuarioModificado = new Usuario();
        usuarioModificado.setIdUsuario(idUsuario);
        usuarioModificado.setNombre(nombre);
        usuarioModificado.setApellido(apellido);
        usuarioModificado.setCorreo(correo);
        usuarioModificado.setTelefono(telefono);
        usuarioModificado.setIdRol(idRol);

        if (usuarioDAO.actualizarUsuario(usuarioModificado)) {
            LOGGER.log(Level.INFO, "Usuario modificado con éxito: {0} {1}", new Object[]{nombre, apellido});
        } else {
            LOGGER.log(Level.SEVERE, "Error al modificar usuario: {0} {1}", new Object[]{nombre, apellido});
        }

        doGet(request, response);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

        if (usuarioDAO.eliminarUsuario(idUsuario)) {
            LOGGER.log(Level.INFO, "Usuario eliminado con éxito: ID={0}", idUsuario);
        } else {
            LOGGER.log(Level.SEVERE, "Error al eliminar usuario: ID={0}", idUsuario);
        }

        doGet(request, response);
    }
}
