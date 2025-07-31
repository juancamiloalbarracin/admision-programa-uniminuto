package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

/**
 * RadicarServlet - MIGRACIÓN de RadicarView.java
 * PRESERVA: Toda la lógica de radicación de solicitudes y consulta de radicados
 * AÑADE: Manejo HTTP GET/POST y validaciones web
 */
@WebServlet("/radicar")
public class RadicarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Mostrar formulario de radicación o lista de solicitudes
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar que el usuario esté logueado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String userEmail = (String) session.getAttribute("userEmail");
        String action = request.getParameter("action");
        
        if ("mis-solicitudes".equals(action)) {
            // Mostrar lista de solicitudes del usuario (PRESERVADO de RadicarView.viewBtn)
            List<String> solicitudes = obtenerMisSolicitudes(userEmail);
            request.setAttribute("solicitudes", solicitudes);
            request.setAttribute("showSolicitudes", true);
        }
        
        // Mostrar formulario de radicación
        request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
    }

    /**
     * POST: Procesar radicación de solicitud
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String userEmail = (String) session.getAttribute("userEmail");
        
        // Obtener datos del formulario (PRESERVADO de RadicarView.submitBtn)
        String tipoSolicitud = request.getParameter("tipoSolicitud");
        String asunto = request.getParameter("asunto");
        String descripcion = request.getParameter("descripcion");
        String telefonoContacto = request.getParameter("telefonoContacto");
        String emailNotificacion = request.getParameter("emailNotificacion");
        String aceptaTerminos = request.getParameter("aceptaTerminos");
        
        // Validaciones (EQUIVALE a las validaciones de RadicarView.java)
        if (isEmpty(tipoSolicitud) || "Seleccione tipo de solicitud...".equals(tipoSolicitud)) {
            request.setAttribute("error", "Debe seleccionar un tipo de solicitud.");
            setFormAttributes(request, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion);
            request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
            return;
        }
        
        if (isEmpty(asunto)) {
            request.setAttribute("error", "El asunto es obligatorio.");
            setFormAttributes(request, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion);
            request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
            return;
        }
        
        if (isEmpty(descripcion)) {
            request.setAttribute("error", "La descripción/justificación es obligatoria.");
            setFormAttributes(request, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion);
            request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
            return;
        }
        
        if (!"on".equals(aceptaTerminos)) {
            request.setAttribute("error", "Debe aceptar los términos y condiciones para procesar la solicitud.");
            setFormAttributes(request, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion);
            request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
            return;
        }
        
        // PRESERVADO: Misma lógica de guardado que RadicarView.java
        DatabaseConnection db = new DatabaseConnection();
        boolean success = db.insertRadicado(
            userEmail, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion
        );
        db.closeConnection();
        
        if (success) {
            // EQUIVALE a: JOptionPane.showMessageDialog "Solicitud radicada exitosamente"
            response.sendRedirect("radicar?solicitudRadicada=true");
        } else {
            request.setAttribute("error", "Error al radicar la solicitud. Inténtelo nuevamente.");
            setFormAttributes(request, tipoSolicitud, asunto, descripcion, telefonoContacto, emailNotificacion);
            request.getRequestDispatcher("/WEB-INF/views/radicar.jsp").forward(request, response);
        }
    }
    
    /**
     * PRESERVADO: Obtener solicitudes del usuario (equivale a viewBtn logic de RadicarView.java)
     */
    private List<String> obtenerMisSolicitudes(String userEmail) {
        List<String> solicitudes = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        
        try {
            String sql = "SELECT id, tipo_solicitud, asunto, fecha_radicado FROM radicados WHERE email = ? ORDER BY fecha_radicado DESC";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String item = "[" + rs.getInt("id") + "] " + rs.getString("tipo_solicitud")
                    + " - " + rs.getString("asunto")
                    + " (" + rs.getTimestamp("fecha_radicado") + ")";
                solicitudes.add(item);
            }
            
            if (solicitudes.isEmpty()) {
                solicitudes.add("No tiene solicitudes radicadas");
            }
            
        } catch (Exception e) {
            solicitudes.add("Error al cargar solicitudes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        
        return solicitudes;
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    private void setFormAttributes(HttpServletRequest request, String tipoSolicitud, String asunto,
            String descripcion, String telefonoContacto, String emailNotificacion) {
        
        request.setAttribute("tipoSolicitud", tipoSolicitud);
        request.setAttribute("asunto", asunto);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("telefonoContacto", telefonoContacto);
        request.setAttribute("emailNotificacion", emailNotificacion);
    }
}
