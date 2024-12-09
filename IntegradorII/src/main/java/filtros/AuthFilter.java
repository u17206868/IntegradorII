package filtros;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro si es necesario
    }

    @Override
    
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HttpSession session = httpRequest.getSession(false);

    String loginURI = httpRequest.getContextPath() + "/login.jsp";
    String registerURI = httpRequest.getContextPath() + "/register.jsp";
    String cssPath = httpRequest.getContextPath() + "/css/";
    String imgPath = httpRequest.getContextPath() + "/src/img/"; // Permitir acceso a la carpeta img

    boolean loggedIn = (session != null && session.getAttribute("usuario") != null);
    boolean loginRequest = httpRequest.getRequestURI().equals(loginURI) || httpRequest.getRequestURI().endsWith("LoginServlet");
    boolean registerRequest = httpRequest.getRequestURI().equals(registerURI) || httpRequest.getRequestURI().endsWith("RegistroServlet");
    boolean cssRequest = httpRequest.getRequestURI().startsWith(cssPath);
    boolean imgRequest = httpRequest.getRequestURI().startsWith(imgPath); // Nueva excepción para imágenes

    if (loggedIn || loginRequest || registerRequest || cssRequest || imgRequest) {
        chain.doFilter(request, response);
    } else {
        httpResponse.sendRedirect(loginURI);
    }
}


    @Override
    public void destroy() {
        // Destrucción del filtro si es necesario
    }
}
