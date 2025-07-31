package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import models.Usuario;

/**
 * MainServlet - MIGRACIÓN de MainView.java
 * Maneja la vista principal después del login exitoso
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar que el usuario esté logueado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login");
            return;
        }
        
        // Obtener datos del usuario de la sesión
        String userEmail = (String) session.getAttribute("userEmail");
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Pasar datos a la vista
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("usuario", usuario);
        
        // Verificar si es un login recién exitoso
        String welcome = request.getParameter("welcome");
        if ("true".equals(welcome)) {
            request.setAttribute("welcomeMessage", "¡Bienvenido!");
        }
        
        // Mostrar la vista principal
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
