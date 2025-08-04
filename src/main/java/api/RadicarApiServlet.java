package api;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.DatabaseConnection;

/**
 * RadicarApiServlet - API REST PROPIA para sistema de radicación
 * MIGRA: RadicarServlet.java a endpoints JSON
 * PRESERVA: Toda la lógica de radicación original
 */
@WebServlet("/api/radicar/*")
public class RadicarApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Verificar autenticación
        if (!isAuthenticated(request)) {
            sendErrorResponse(response, 401, "Sesión no válida");
            return;
        }
        
        if ("/mis-solicitudes".equals(pathInfo)) {
            handleGetMisSolicitudes(request, response);
        } else {
            sendErrorResponse(response, 404, "Endpoint no encontrado");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Verificar autenticación
        if (!isAuthenticated(request)) {
            sendErrorResponse(response, 401, "Sesión no válida");
            return;
        }
        
        if ("/create".equals(pathInfo)) {
            handleCreateSolicitud(request, response);
        } else {
            sendErrorResponse(response, 404, "Endpoint no encontrado");
        }
    }

    /**
     * API PROPIA: GET /api/radicar/mis-solicitudes
     * PRESERVA: Misma lógica que RadicarServlet para obtener solicitudes
     */
    private void handleGetMisSolicitudes(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            String userEmail = (String) request.getSession().getAttribute("userEmail");
            List<Map<String, Object>> solicitudes = obtenerMisSolicitudes(userEmail);
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("solicitudes", solicitudes);
            responseData.put("message", solicitudes.isEmpty() ? "No tiene solicitudes radicadas" : "Solicitudes encontradas");
            
            response.setStatus(200);
            response.getWriter().write(objectMapper.writeValueAsString(responseData));
            
        } catch (Exception e) {
            sendErrorResponse(response, 500, "Error al obtener solicitudes: " + e.getMessage());
        }
    }

    /**
     * API PROPIA: POST /api/radicar/create
     * PRESERVA: Todas las validaciones del RadicarServlet original
     */
    private void handleCreateSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            // Leer JSON del request
            SolicitudRequest solicitudReq = objectMapper.readValue(request.getReader(), SolicitudRequest.class);
            String userEmail = (String) request.getSession().getAttribute("userEmail");
            
            // PRESERVADO: Validaciones del servlet original
            String validationError = validateSolicitud(solicitudReq);
            if (validationError != null) {
                sendErrorResponse(response, 400, validationError);
                return;
            }
            
            // PRESERVADO: Misma lógica de guardado que RadicarServlet
            DatabaseConnection db = new DatabaseConnection();
            boolean success = db.insertRadicado(
                userEmail,
                solicitudReq.getTipoSolicitud(),
                solicitudReq.getAsunto(),
                solicitudReq.getDescripcion(),
                solicitudReq.getTelefonoContacto(),
                solicitudReq.getEmailNotificacion()
            );
            db.closeConnection();
            
            Map<String, Object> responseData = new HashMap<>();
            if (success) {
                responseData.put("success", true);
                responseData.put("message", "Solicitud radicada exitosamente");
            } else {
                responseData.put("success", false);
                responseData.put("message", "Error al radicar la solicitud");
            }
            
            response.setStatus(success ? 200 : 500);
            response.getWriter().write(objectMapper.writeValueAsString(responseData));
            
        } catch (Exception e) {
            sendErrorResponse(response, 500, "Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    /**
     * PRESERVADO: Obtener solicitudes del usuario (equivale a viewBtn logic de RadicarView.java)
     */
    private List<Map<String, Object>> obtenerMisSolicitudes(String userEmail) {
        List<Map<String, Object>> solicitudes = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();
        
        try {
            String sql = "SELECT id, tipo_solicitud, asunto, descripcion, fecha_radicado, telefono_contacto, email_notificacion FROM radicados WHERE email = ? ORDER BY fecha_radicado DESC";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> solicitud = new HashMap<>();
                solicitud.put("id", rs.getInt("id"));
                solicitud.put("tipoSolicitud", rs.getString("tipo_solicitud"));
                solicitud.put("asunto", rs.getString("asunto"));
                solicitud.put("descripcion", rs.getString("descripcion"));
                solicitud.put("fechaRadicado", rs.getTimestamp("fecha_radicado"));
                solicitud.put("telefonoContacto", rs.getString("telefono_contacto"));
                solicitud.put("emailNotificacion", rs.getString("email_notificacion"));
                solicitudes.add(solicitud);
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar solicitudes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        
        return solicitudes;
    }
    
    /**
     * PRESERVADO: Validaciones originales del RadicarServlet
     */
    private String validateSolicitud(SolicitudRequest solicitud) {
        if (isEmpty(solicitud.getTipoSolicitud()) || "Seleccione tipo de solicitud...".equals(solicitud.getTipoSolicitud())) {
            return "Debe seleccionar un tipo de solicitud.";
        }
        if (isEmpty(solicitud.getAsunto())) {
            return "El asunto es obligatorio.";
        }
        if (isEmpty(solicitud.getDescripcion())) {
            return "La descripción/justificación es obligatoria.";
        }
        if (!solicitud.isAceptaTerminos()) {
            return "Debe aceptar los términos y condiciones para procesar la solicitud.";
        }
        
        return null; // Sin errores
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    private boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userEmail") != null;
    }
    
    private void sendErrorResponse(HttpServletResponse response, int status, String message) 
            throws IOException {
        response.setStatus(status);
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    
    // Clase interna para deserializar JSON
    public static class SolicitudRequest {
        private String tipoSolicitud;
        private String asunto;
        private String descripcion;
        private String telefonoContacto;
        private String emailNotificacion;
        private boolean aceptaTerminos;
        
        // Getters y setters
        public String getTipoSolicitud() { return tipoSolicitud; }
        public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }
        public String getAsunto() { return asunto; }
        public void setAsunto(String asunto) { this.asunto = asunto; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getTelefonoContacto() { return telefonoContacto; }
        public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
        public String getEmailNotificacion() { return emailNotificacion; }
        public void setEmailNotificacion(String emailNotificacion) { this.emailNotificacion = emailNotificacion; }
        public boolean isAceptaTerminos() { return aceptaTerminos; }
        public void setAceptaTerminos(boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }
    }
}
