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
 * InfoPersonalServlet - MIGRACIÓN de InfoPersonalView.java
 * PRESERVA: Toda la lógica de formulario y guardado de información personal
 * AÑADE: Manejo HTTP GET/POST y validaciones web
 */
@WebServlet("/info-personal")
public class InfoPersonalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Mostrar formulario de información personal o devolver datos via AJAX
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
                String sql = "SELECT * FROM informacion_personal WHERE email = ?";
                java.sql.PreparedStatement stmt = db.getConnection().prepareStatement(sql);
                stmt.setString(1, userEmail);
                java.sql.ResultSet rs = stmt.executeQuery();
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                
                if (rs.next()) {
                    // Construir JSON con la información
                    StringBuilder json = new StringBuilder();
                    json.append("{\"success\": true, \"info\": {");
                    json.append("\"primerNombre\": \"").append(rs.getString("primer_nombre") != null ? rs.getString("primer_nombre") : "").append("\",");
                    json.append("\"segundoNombre\": \"").append(rs.getString("segundo_nombre") != null ? rs.getString("segundo_nombre") : "").append("\",");
                    json.append("\"apellidos\": \"").append(rs.getString("apellidos") != null ? rs.getString("apellidos") : "").append("\",");
                    json.append("\"sexoBiologico\": \"").append(rs.getString("sexo_biologico") != null ? rs.getString("sexo_biologico") : "").append("\",");
                    json.append("\"grupoSanguineo\": \"").append(rs.getString("grupo_sanguineo") != null ? rs.getString("grupo_sanguineo") : "").append("\",");
                    json.append("\"tipoDocumento\": \"").append(rs.getString("tipo_documento") != null ? rs.getString("tipo_documento") : "").append("\",");
                    json.append("\"numeroDocumento\": \"").append(rs.getString("numero_documento") != null ? rs.getString("numero_documento") : "").append("\",");
                    json.append("\"fechaExpedicionDocumento\": \"").append(rs.getString("fecha_expedicion_documento") != null ? rs.getString("fecha_expedicion_documento") : "").append("\",");
                    json.append("\"paisNacimiento\": \"").append(rs.getString("pais_nacimiento") != null ? rs.getString("pais_nacimiento") : "").append("\",");
                    json.append("\"ciudadNacimiento\": \"").append(rs.getString("ciudad_nacimiento") != null ? rs.getString("ciudad_nacimiento") : "").append("\",");
                    json.append("\"fechaNacimiento\": \"").append(rs.getString("fecha_nacimiento") != null ? rs.getString("fecha_nacimiento") : "").append("\",");
                    json.append("\"telefonoCelular\": \"").append(rs.getString("telefono_celular") != null ? rs.getString("telefono_celular") : "").append("\",");
                    json.append("\"correoElectronico\": \"").append(rs.getString("correo_electronico") != null ? rs.getString("correo_electronico") : "").append("\",");
                    json.append("\"direccion\": \"").append(rs.getString("direccion") != null ? rs.getString("direccion") : "").append("\",");
                    json.append("\"casaAptoBarrio\": \"").append(rs.getString("casa_apto_barrio") != null ? rs.getString("casa_apto_barrio") : "").append("\",");
                    json.append("\"tieneSisben\": \"").append(rs.getString("tiene_sisben") != null ? rs.getString("tiene_sisben") : "").append("\",");
                    json.append("\"tieneEps\": \"").append(rs.getString("tiene_eps") != null ? rs.getString("tiene_eps") : "").append("\",");
                    json.append("\"tieneDiscapacidad\": \"").append(rs.getString("tiene_discapacidad") != null ? rs.getString("tiene_discapacidad") : "").append("\",");
                    json.append("\"primerNombreAcudiente\": \"").append(rs.getString("primer_nombre_acudiente") != null ? rs.getString("primer_nombre_acudiente") : "").append("\",");
                    json.append("\"segundoNombreAcudiente\": \"").append(rs.getString("segundo_nombre_acudiente") != null ? rs.getString("segundo_nombre_acudiente") : "").append("\",");
                    json.append("\"apellidosAcudiente\": \"").append(rs.getString("apellidos_acudiente") != null ? rs.getString("apellidos_acudiente") : "").append("\",");
                    json.append("\"telefonoAcudiente\": \"").append(rs.getString("telefono_acudiente") != null ? rs.getString("telefono_acudiente") : "").append("\",");
                    json.append("\"correoAcudiente\": \"").append(rs.getString("correo_acudiente") != null ? rs.getString("correo_acudiente") : "").append("\"");
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
            String sql = "SELECT * FROM informacion_personal WHERE email = ?";
            java.sql.PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, userEmail);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Cargar datos existentes en request attributes
                request.setAttribute("primerNombre", rs.getString("primer_nombre"));
                request.setAttribute("segundoNombre", rs.getString("segundo_nombre"));
                request.setAttribute("apellidos", rs.getString("apellidos"));
                request.setAttribute("sexoBiologico", rs.getString("sexo_biologico"));
                request.setAttribute("grupoSanguineo", rs.getString("grupo_sanguineo"));
                request.setAttribute("tipoDocumento", rs.getString("tipo_documento"));
                request.setAttribute("numeroDocumento", rs.getString("numero_documento"));
                request.setAttribute("fechaExpedicion", rs.getString("fecha_expedicion_documento"));
                request.setAttribute("paisNacimiento", rs.getString("pais_nacimiento"));
                request.setAttribute("ciudadNacimiento", rs.getString("ciudad_nacimiento"));
                request.setAttribute("fechaNacimiento", rs.getString("fecha_nacimiento"));
                request.setAttribute("telefonoCelular", rs.getString("telefono_celular"));
                request.setAttribute("correoElectronico", rs.getString("correo_electronico"));
                request.setAttribute("tipoDireccion", rs.getString("tipo_direccion"));
                request.setAttribute("direccion", rs.getString("direccion"));
                request.setAttribute("casaAptoBarrio", rs.getString("casa_apto_barrio"));
                request.setAttribute("tieneSisben", rs.getString("tiene_sisben"));
                request.setAttribute("tieneEps", rs.getString("tiene_eps"));
                request.setAttribute("tieneDiscapacidad", rs.getString("tiene_discapacidad"));
                request.setAttribute("primerNombreAcudiente", rs.getString("primer_nombre_acudiente"));
                request.setAttribute("segundoNombreAcudiente", rs.getString("segundo_nombre_acudiente"));
                request.setAttribute("apellidosAcudiente", rs.getString("apellidos_acudiente"));
                request.setAttribute("telefonoAcudiente", rs.getString("telefono_acudiente"));
                request.setAttribute("correoAcudiente", rs.getString("correo_acudiente"));
                
                request.setAttribute("editMode", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        
        // Mostrar formulario
        request.getRequestDispatcher("/WEB-INF/views/info-personal.jsp").forward(request, response);
    }

    /**
     * POST: Procesar y guardar información personal
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
        String primerNombre = request.getParameter("primerNombre");
        String segundoNombre = request.getParameter("segundoNombre");
        String apellidos = request.getParameter("apellidos");
        String sexoBiologico = request.getParameter("sexoBiologico");
        String grupoSanguineo = request.getParameter("grupoSanguineo");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String numeroDocumento = request.getParameter("numeroDocumento");
        String fechaExpedicion = request.getParameter("fechaExpedicion");
        String paisNacimiento = request.getParameter("paisNacimiento");
        String ciudadNacimiento = request.getParameter("ciudadNacimiento");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String telefonoCelular = request.getParameter("telefonoCelular");
        String correoElectronico = request.getParameter("correoElectronico");
        String tipoDireccion = request.getParameter("tipoDireccion");
        String direccion = request.getParameter("direccion");
        String casaAptoBarrio = request.getParameter("casaAptoBarrio");
        String tieneSisben = request.getParameter("tieneSisben");
        String tieneEps = request.getParameter("tieneEps");
        String tieneDiscapacidad = request.getParameter("tieneDiscapacidad");
        String primerNombreAcudiente = request.getParameter("primerNombreAcudiente");
        String segundoNombreAcudiente = request.getParameter("segundoNombreAcudiente");
        String apellidosAcudiente = request.getParameter("apellidosAcudiente");
        String telefonoAcudiente = request.getParameter("telefonoAcudiente");
        String correoAcudiente = request.getParameter("correoAcudiente");
        
        // Validaciones básicas (campos obligatorios)
        if (isEmpty(primerNombre) || isEmpty(segundoNombre) || isEmpty(apellidos) || 
            isEmpty(sexoBiologico) || "Seleccione...".equals(sexoBiologico) ||
            isEmpty(grupoSanguineo) || "Seleccione...".equals(grupoSanguineo) ||
            isEmpty(tipoDocumento) || "Seleccione...".equals(tipoDocumento) ||
            isEmpty(numeroDocumento) || isEmpty(fechaExpedicion) || isEmpty(fechaNacimiento) ||
            isEmpty(paisNacimiento) || isEmpty(ciudadNacimiento) || isEmpty(telefonoCelular) ||
            isEmpty(correoElectronico) || isEmpty(tipoDireccion) || "Seleccione...".equals(tipoDireccion) ||
            isEmpty(direccion) || isEmpty(casaAptoBarrio) ||
            isEmpty(tieneSisben) || "Seleccione...".equals(tieneSisben) ||
            isEmpty(tieneEps) || "Seleccione...".equals(tieneEps) ||
            isEmpty(tieneDiscapacidad) || "Seleccione...".equals(tieneDiscapacidad) ||
            isEmpty(primerNombreAcudiente) || isEmpty(segundoNombreAcudiente) ||
            isEmpty(apellidosAcudiente) || isEmpty(telefonoAcudiente) || isEmpty(correoAcudiente)) {
            
            request.setAttribute("error", "Por favor, complete todos los campos obligatorios marcados con asterisco (*).");
            // Mantener datos en el formulario
            setFormAttributes(request, primerNombre, segundoNombre, apellidos, sexoBiologico, grupoSanguineo,
                    tipoDocumento, numeroDocumento, fechaExpedicion, paisNacimiento, ciudadNacimiento,
                    fechaNacimiento, telefonoCelular, correoElectronico, tipoDireccion, direccion,
                    casaAptoBarrio, tieneSisben, tieneEps, tieneDiscapacidad, primerNombreAcudiente,
                    segundoNombreAcudiente, apellidosAcudiente, telefonoAcudiente, correoAcudiente);
            request.getRequestDispatcher("/WEB-INF/views/info-personal.jsp").forward(request, response);
            return;
        }
        
        // PRESERVADO: Misma lógica de guardado que InfoPersonalView.java
        DatabaseConnection db = new DatabaseConnection();
        boolean success = db.upsertInformacionPersonal(
            userEmail, primerNombre, segundoNombre, apellidos, sexoBiologico, grupoSanguineo,
            tipoDocumento, numeroDocumento, fechaExpedicion, paisNacimiento, ciudadNacimiento,
            fechaNacimiento, telefonoCelular, correoElectronico, tipoDireccion, direccion,
            casaAptoBarrio, tieneSisben, tieneEps, tieneDiscapacidad, primerNombreAcudiente,
            segundoNombreAcudiente, apellidosAcudiente, telefonoAcudiente, correoAcudiente
        );
        db.closeConnection();
        
        if (success) {
            // EQUIVALE a: JOptionPane.showMessageDialog "Información personal guardada exitosamente"
            response.sendRedirect("main?infoPersonalGuardada=true");
        } else {
            request.setAttribute("error", "Error al guardar información personal. Inténtelo nuevamente.");
            setFormAttributes(request, primerNombre, segundoNombre, apellidos, sexoBiologico, grupoSanguineo,
                    tipoDocumento, numeroDocumento, fechaExpedicion, paisNacimiento, ciudadNacimiento,
                    fechaNacimiento, telefonoCelular, correoElectronico, tipoDireccion, direccion,
                    casaAptoBarrio, tieneSisben, tieneEps, tieneDiscapacidad, primerNombreAcudiente,
                    segundoNombreAcudiente, apellidosAcudiente, telefonoAcudiente, correoAcudiente);
            request.getRequestDispatcher("/WEB-INF/views/info-personal.jsp").forward(request, response);
        }
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    private void setFormAttributes(HttpServletRequest request, String primerNombre, String segundoNombre,
            String apellidos, String sexoBiologico, String grupoSanguineo, String tipoDocumento,
            String numeroDocumento, String fechaExpedicion, String paisNacimiento, String ciudadNacimiento,
            String fechaNacimiento, String telefonoCelular, String correoElectronico, String tipoDireccion,
            String direccion, String casaAptoBarrio, String tieneSisben, String tieneEps,
            String tieneDiscapacidad, String primerNombreAcudiente, String segundoNombreAcudiente,
            String apellidosAcudiente, String telefonoAcudiente, String correoAcudiente) {
        
        request.setAttribute("primerNombre", primerNombre);
        request.setAttribute("segundoNombre", segundoNombre);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("sexoBiologico", sexoBiologico);
        request.setAttribute("grupoSanguineo", grupoSanguineo);
        request.setAttribute("tipoDocumento", tipoDocumento);
        request.setAttribute("numeroDocumento", numeroDocumento);
        request.setAttribute("fechaExpedicion", fechaExpedicion);
        request.setAttribute("paisNacimiento", paisNacimiento);
        request.setAttribute("ciudadNacimiento", ciudadNacimiento);
        request.setAttribute("fechaNacimiento", fechaNacimiento);
        request.setAttribute("telefonoCelular", telefonoCelular);
        request.setAttribute("correoElectronico", correoElectronico);
        request.setAttribute("tipoDireccion", tipoDireccion);
        request.setAttribute("direccion", direccion);
        request.setAttribute("casaAptoBarrio", casaAptoBarrio);
        request.setAttribute("tieneSisben", tieneSisben);
        request.setAttribute("tieneEps", tieneEps);
        request.setAttribute("tieneDiscapacidad", tieneDiscapacidad);
        request.setAttribute("primerNombreAcudiente", primerNombreAcudiente);
        request.setAttribute("segundoNombreAcudiente", segundoNombreAcudiente);
        request.setAttribute("apellidosAcudiente", apellidosAcudiente);
        request.setAttribute("telefonoAcudiente", telefonoAcudiente);
        request.setAttribute("correoAcudiente", correoAcudiente);
    }
}
