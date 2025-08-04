package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.DatabaseConnection;

/**
 * InfoAcademicaApiServlet - API REST PROPIA para información académica
 * MIGRA: InfoAcademicaServlet.java a endpoints JSON
 * PRESERVA: Toda la lógica de negocio original
 */
@WebServlet("/api/info-academica/*")
public class InfoAcademicaApiServlet extends HttpServlet {
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
        
        if ("/get".equals(pathInfo) || pathInfo == null) {
            handleGetInfoAcademica(request, response);
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
        
        if ("/save".equals(pathInfo)) {
            handleSaveInfoAcademica(request, response);
        } else {
            sendErrorResponse(response, 404, "Endpoint no encontrado");
        }
    }

    /**
     * API PROPIA: GET /api/info-academica/get
     * PRESERVA: Misma lógica que InfoAcademicaServlet para obtener datos
     */
    private void handleGetInfoAcademica(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            String userEmail = (String) request.getSession().getAttribute("userEmail");
            
            DatabaseConnection db = new DatabaseConnection();
            Map<String, String> infoAcademica = db.obtenerInformacionAcademica(userEmail);
            db.closeConnection();
            
            Map<String, Object> responseData = new HashMap<>();
            
            if (infoAcademica != null && !infoAcademica.isEmpty()) {
                responseData.put("success", true);
                responseData.put("data", infoAcademica);
                responseData.put("message", "Información académica encontrada");
            } else {
                responseData.put("success", true);
                responseData.put("data", new HashMap<>());
                responseData.put("message", "No se encontró información académica registrada");
            }
            
            response.setStatus(200);
            response.getWriter().write(objectMapper.writeValueAsString(responseData));
            
        } catch (Exception e) {
            sendErrorResponse(response, 500, "Error al obtener información académica: " + e.getMessage());
        }
    }

    /**
     * API PROPIA: POST /api/info-academica/save
     * PRESERVA: Todas las validaciones del InfoAcademicaServlet original
     */
    private void handleSaveInfoAcademica(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        try {
            // Leer JSON del request
            InfoAcademicaRequest infoReq = objectMapper.readValue(request.getReader(), InfoAcademicaRequest.class);
            String userEmail = (String) request.getSession().getAttribute("userEmail");
            
            // PRESERVADO: Validaciones del servlet original
            String validationError = validateInfoAcademica(infoReq);
            if (validationError != null) {
                sendErrorResponse(response, 400, validationError);
                return;
            }
            
            // PRESERVADO: Misma lógica de guardado que InfoAcademicaServlet
            DatabaseConnection db = new DatabaseConnection();
            boolean success = db.guardarInformacionAcademica(
                userEmail,
                infoReq.getInstitucionBachillerato(),
                infoReq.getAnioBachillerato(),
                infoReq.getPromedioAcademico(),
                infoReq.getTipoInstitucion(),
                infoReq.getModalidadEstudio(),
                infoReq.getEnfasisArea(),
                infoReq.getPruebasEstado(),
                infoReq.getPuntajePruebas(),
                infoReq.getExperienciaLaboral(),
                infoReq.getCertificacionesAdicionales()
            );
            db.closeConnection();
            
            Map<String, Object> responseData = new HashMap<>();
            if (success) {
                responseData.put("success", true);
                responseData.put("message", "Información académica guardada exitosamente");
            } else {
                responseData.put("success", false);
                responseData.put("message", "Error al guardar la información académica");
            }
            
            response.setStatus(success ? 200 : 500);
            response.getWriter().write(objectMapper.writeValueAsString(responseData));
            
        } catch (Exception e) {
            sendErrorResponse(response, 500, "Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    /**
     * PRESERVADO: Validaciones originales del InfoAcademicaServlet
     */
    private String validateInfoAcademica(InfoAcademicaRequest info) {
        if (isEmpty(info.getInstitucionBachillerato())) {
            return "La institución de bachillerato es obligatoria.";
        }
        if (isEmpty(info.getAnioBachillerato())) {
            return "El año de bachillerato es obligatorio.";
        }
        if (isEmpty(info.getPromedioAcademico())) {
            return "El promedio académico es obligatorio.";
        }
        if (isEmpty(info.getTipoInstitucion()) || "Seleccione...".equals(info.getTipoInstitucion())) {
            return "Debe seleccionar el tipo de institución.";
        }
        if (isEmpty(info.getModalidadEstudio()) || "Seleccione...".equals(info.getModalidadEstudio())) {
            return "Debe seleccionar la modalidad de estudio.";
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
    public static class InfoAcademicaRequest {
        private String institucionBachillerato;
        private String anioBachillerato;
        private String promedioAcademico;
        private String tipoInstitucion;
        private String modalidadEstudio;
        private String enfasisArea;
        private String pruebasEstado;
        private String puntajePruebas;
        private String experienciaLaboral;
        private String certificacionesAdicionales;
        
        // Getters y setters
        public String getInstitucionBachillerato() { return institucionBachillerato; }
        public void setInstitucionBachillerato(String institucionBachillerato) { this.institucionBachillerato = institucionBachillerato; }
        public String getAnioBachillerato() { return anioBachillerato; }
        public void setAnioBachillerato(String anioBachillerato) { this.anioBachillerato = anioBachillerato; }
        public String getPromedioAcademico() { return promedioAcademico; }
        public void setPromedioAcademico(String promedioAcademico) { this.promedioAcademico = promedioAcademico; }
        public String getTipoInstitucion() { return tipoInstitucion; }
        public void setTipoInstitucion(String tipoInstitucion) { this.tipoInstitucion = tipoInstitucion; }
        public String getModalidadEstudio() { return modalidadEstudio; }
        public void setModalidadEstudio(String modalidadEstudio) { this.modalidadEstudio = modalidadEstudio; }
        public String getEnfasisArea() { return enfasisArea; }
        public void setEnfasisArea(String enfasisArea) { this.enfasisArea = enfasisArea; }
        public String getPruebasEstado() { return pruebasEstado; }
        public void setPruebasEstado(String pruebasEstado) { this.pruebasEstado = pruebasEstado; }
        public String getPuntajePruebas() { return puntajePruebas; }
        public void setPuntajePruebas(String puntajePruebas) { this.puntajePruebas = puntajePruebas; }
        public String getExperienciaLaboral() { return experienciaLaboral; }
        public void setExperienciaLaboral(String experienciaLaboral) { this.experienciaLaboral = experienciaLaboral; }
        public String getCertificacionesAdicionales() { return certificacionesAdicionales; }
        public void setCertificacionesAdicionales(String certificacionesAdicionales) { this.certificacionesAdicionales = certificacionesAdicionales; }
    }
}
