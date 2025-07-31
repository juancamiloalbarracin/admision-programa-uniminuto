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
 * InfoAcademicaServlet - MIGRACIÓN de InfoAcademicaView.java
 * PRESERVA: Toda la lógica de formulario y guardado de información académica
 * AÑADE: Manejo HTTP GET/POST y validaciones web
 */
@WebServlet("/info-academica")
public class InfoAcademicaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Mostrar formulario de información académica o devolver datos via AJAX
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
        
        // Si es una petición AJAX para ver información
        if ("ver".equals(action) && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            DatabaseConnection db = new DatabaseConnection();
            try {
                String sql = "SELECT * FROM informacion_academica WHERE email = ?";
                java.sql.PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, userEmail);
                java.sql.ResultSet rs = stmt.executeQuery();
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                
                if (rs.next()) {
                    // Construir JSON con la información
                    StringBuilder json = new StringBuilder();
                    json.append("{\"success\": true, \"info\": {");
                    json.append("\"nivel\": \"").append(rs.getString("nivel") != null ? rs.getString("nivel") : "").append("\",");
                    json.append("\"sede\": \"").append(rs.getString("sede") != null ? rs.getString("sede") : "").append("\",");
                    json.append("\"gradoAcademico\": \"").append(rs.getString("grado_academico") != null ? rs.getString("grado_academico") : "").append("\",");
                    json.append("\"periodoAdmision\": \"").append(rs.getString("periodo_admision") != null ? rs.getString("periodo_admision") : "").append("\",");
                    json.append("\"metodologia\": \"").append(rs.getString("metodologia") != null ? rs.getString("metodologia") : "").append("\",");
                    json.append("\"jornada\": \"").append(rs.getString("jornada") != null ? rs.getString("jornada") : "").append("\",");
                    json.append("\"planDecision\": \"").append(rs.getString("plan_decision") != null ? rs.getString("plan_decision") : "").append("\",");
                    json.append("\"gradoSeleccionado\": \"").append(rs.getString("grado_seleccionado") != null ? rs.getString("grado_seleccionado") : "").append("\",");
                    json.append("\"pais\": \"").append(rs.getString("pais") != null ? rs.getString("pais") : "").append("\",");
                    json.append("\"gradoObtenido\": \"").append(rs.getString("grado_obtenido") != null ? rs.getString("grado_obtenido") : "").append("\",");
                    json.append("\"fechaGraduacion\": \"").append(rs.getString("fecha_graduacion") != null ? rs.getString("fecha_graduacion") : "").append("\"");
                    json.append("}}");
                    
                    response.getWriter().write(json.toString());
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"No se encontró información\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write("{\"success\": false, \"message\": \"Error al cargar información\"}");
            } finally {
                db.closeConnection();
            }
            return;
        }
        
        // Cargar información existente si existe (para mostrar formulario)
        DatabaseConnection db = new DatabaseConnection();
        try {
            String sql = "SELECT * FROM informacion_academica WHERE email = ?";
            java.sql.PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, userEmail);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Cargar datos existentes en request attributes
                request.setAttribute("nivel", rs.getString("nivel"));
                request.setAttribute("sede", rs.getString("sede"));
                request.setAttribute("gradoAcademico", rs.getString("grado_academico"));
                request.setAttribute("periodoAdmision", rs.getString("periodo_admision"));
                request.setAttribute("metodologia", rs.getString("metodologia"));
                request.setAttribute("jornada", rs.getString("jornada"));
                request.setAttribute("planDecision", rs.getString("plan_decision"));
                request.setAttribute("gradoSeleccionado", rs.getString("grado_seleccionado"));
                request.setAttribute("pais", rs.getString("pais"));
                request.setAttribute("gradoObtenido", rs.getString("grado_obtenido"));
                request.setAttribute("fechaGraduacion", rs.getString("fecha_graduacion"));
                
                request.setAttribute("editMode", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        
        // Mostrar formulario
        request.getRequestDispatcher("/WEB-INF/views/info-academica.jsp").forward(request, response);
    }

    /**
     * POST: Procesar y guardar información académica
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
        
        // Obtener datos del formulario
        String nivel = request.getParameter("nivel");
        String sede = request.getParameter("sede");
        String gradoAcademico = request.getParameter("gradoAcademico");
        String periodoAdmision = request.getParameter("periodoAdmision");
        String metodologia = request.getParameter("metodologia");
        String jornada = request.getParameter("jornada");
        String planDecision = request.getParameter("planDecision");
        String gradoSeleccionado = request.getParameter("gradoSeleccionado");
        String pais = request.getParameter("pais");
        String gradoObtenido = request.getParameter("gradoObtenido");
        String fechaGraduacion = request.getParameter("fechaGraduacion");
        
        // Validaciones básicas (campos obligatorios)
        if (isEmpty(nivel) || "Seleccione...".equals(nivel) ||
            isEmpty(sede) || "Seleccione...".equals(sede) ||
            isEmpty(gradoAcademico) || "Seleccione...".equals(gradoAcademico) ||
            isEmpty(periodoAdmision) || isEmpty(metodologia) ||
            isEmpty(jornada) || "Seleccione...".equals(jornada) ||
            isEmpty(planDecision) || "Seleccione...".equals(planDecision) ||
            isEmpty(gradoSeleccionado) || isEmpty(pais) ||
            isEmpty(gradoObtenido) || isEmpty(fechaGraduacion)) {
            
            request.setAttribute("error", "Por favor, complete todos los campos obligatorios marcados con asterisco (*).");
            // Mantener datos en el formulario
            setFormAttributes(request, nivel, sede, gradoAcademico, periodoAdmision, metodologia,
                    jornada, planDecision, gradoSeleccionado, pais, gradoObtenido, fechaGraduacion);
            request.getRequestDispatcher("/WEB-INF/views/info-academica.jsp").forward(request, response);
            return;
        }
        
        // PRESERVADO: Misma lógica de guardado que InfoAcademicaView.java
        DatabaseConnection db = new DatabaseConnection();
        boolean success = db.upsertInformacionAcademica(
            userEmail, nivel, sede, gradoAcademico, periodoAdmision, metodologia,
            jornada, planDecision, gradoSeleccionado, pais, gradoObtenido, fechaGraduacion
        );
        db.closeConnection();
        
        if (success) {
            // EQUIVALE a: JOptionPane.showMessageDialog "Información académica guardada exitosamente"
            response.sendRedirect("main?infoAcademicaGuardada=true");
        } else {
            request.setAttribute("error", "Error al guardar información académica. Inténtelo nuevamente.");
            setFormAttributes(request, nivel, sede, gradoAcademico, periodoAdmision, metodologia,
                    jornada, planDecision, gradoSeleccionado, pais, gradoObtenido, fechaGraduacion);
            request.getRequestDispatcher("/WEB-INF/views/info-academica.jsp").forward(request, response);
        }
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    private void setFormAttributes(HttpServletRequest request, String nivel, String sede,
            String gradoAcademico, String periodoAdmision, String metodologia, String jornada,
            String planDecision, String gradoSeleccionado, String pais, String gradoObtenido,
            String fechaGraduacion) {
        
        request.setAttribute("nivel", nivel);
        request.setAttribute("sede", sede);
        request.setAttribute("gradoAcademico", gradoAcademico);
        request.setAttribute("periodoAdmision", periodoAdmision);
        request.setAttribute("metodologia", metodologia);
        request.setAttribute("jornada", jornada);
        request.setAttribute("planDecision", planDecision);
        request.setAttribute("gradoSeleccionado", gradoSeleccionado);
        request.setAttribute("pais", pais);
        request.setAttribute("gradoObtenido", gradoObtenido);
        request.setAttribute("fechaGraduacion", fechaGraduacion);
    }
}
