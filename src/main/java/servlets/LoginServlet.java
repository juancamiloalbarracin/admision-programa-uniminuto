package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import utils.DatabaseConnection;
import models.Usuario;

/**
 * LoginServlet - MIGRACIÓN de LoginView.java
 * PRESERVA: Toda la lógica de autenticación original
 * AÑADE: Manejo HTTP GET/POST y sesiones web
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Mostrar formulario de login (equivale a mostrar la ventana Swing)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Si el usuario ya está logueado, redirigir al main
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userEmail") != null) {
            response.sendRedirect("main");
            return;
        }
        
        // Mostrar la página de login
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    /**
     * POST: Procesar login (equivale al ActionListener del botón "Iniciar Sesión")
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener datos del formulario (equivale a emailField.getText() y passwordField.getPassword())
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String recordarme = request.getParameter("recordarme");
        String pcPublico = request.getParameter("pcPublico");
        
        // Validación básica
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor, complete todos los campos.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        // PRESERVADO: Exactamente la misma lógica de validación del LoginView original
        DatabaseConnection db = new DatabaseConnection();
        boolean valido = db.validarUsuario(email.trim(), password);
        
        if (valido) {
            // PRESERVADO: Guardar email de sesión (ahora en sesión HTTP)
            DatabaseConnection.setCurrentUserEmail(email.trim());
            
            // Obtener datos completos del usuario
            Usuario usuario = db.obtenerUsuarioPorUsername(email.trim());
            
            // Crear sesión HTTP (equivale a pasar a MainView)
            HttpSession session = request.getSession(true);
            session.setAttribute("userEmail", email.trim());
            session.setAttribute("usuario", usuario);
            
            // Configurar duración de sesión según preferencias
            if ("on".equals(recordarme)) {
                session.setMaxInactiveInterval(60 * 60 * 24 * 7); // 7 días
            } else {
                session.setMaxInactiveInterval(60 * 30); // 30 minutos
            }
            
            // Agregar atributo para PC público (opcional)
            if ("on".equals(pcPublico)) {
                session.setAttribute("pcPublico", true);
                session.setMaxInactiveInterval(60 * 15); // 15 minutos para PC público
            }
            
            db.closeConnection();
            
            // EQUIVALE a: JOptionPane.showMessageDialog(this, "¡Bienvenido!"); + new MainView();
            response.sendRedirect("main?welcome=true");
            
        } else {
            // PRESERVADO: Mismo mensaje de error que en LoginView
            db.closeConnection();
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            request.setAttribute("email", email); // Mantener email en el formulario
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
