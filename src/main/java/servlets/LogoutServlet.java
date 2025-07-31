package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import utils.DatabaseConnection;

/**
 * LogoutServlet - Maneja el cierre de sesión
 * Limpia la sesión HTTP y redirige al login
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Limpiar el email del usuario actual de DatabaseConnection
            DatabaseConnection.setCurrentUserEmail(null);
            
            // Invalidar la sesión HTTP
            session.invalidate();
        }
        
        // Redirigir al login con mensaje de logout exitoso
        response.sendRedirect("login?logout=true");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
