package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import utils.DatabaseConnection;

@WebServlet("/api/solicitudes/*")
public class SolicitudesApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.equals("/list")) {
            handleListSolicitudes(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Endpoint no válido\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.equals("/radicar")) {
            handleRadicarSolicitud(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Endpoint no válido\"}");
        }
    }

    private void handleListSolicitudes(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Verificar autenticación
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"error\":\"Sesión no válida\"}");
            return;
        }
        
        String userEmail = (String) session.getAttribute("userEmail");
        
        try {
            DatabaseConnection db = new DatabaseConnection();
            String sql = "SELECT * FROM radicados WHERE email = ? ORDER BY fecha_radicado DESC";
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            stmt.setString(1, userEmail);
            
            ResultSet rs = stmt.executeQuery();
            
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"data\":[");
            
            boolean hasResults = false;
            while (rs.next()) {
                if (hasResults) {
                    json.append(",");
                }
                
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"numeroRadicado\":\"RAD-").append(rs.getInt("id")).append("\","); // Generar número basado en ID
                json.append("\"tipoSolicitud\":\"").append(escapeJson(rs.getString("tipo_solicitud"))).append("\",");
                json.append("\"dependenciaDirigida\":\"").append(escapeJson(rs.getString("email_notificacion"))).append("\","); // Usar email_notificacion como dependencia
                json.append("\"asunto\":\"").append(escapeJson(rs.getString("asunto"))).append("\",");
                json.append("\"descripcion\":\"").append(escapeJson(rs.getString("descripcion"))).append("\",");
                json.append("\"documentosAdjuntos\":\"\","); // La tabla radicados no tiene esta columna
                json.append("\"estado\":\"Radicada\","); // Estado fijo ya que no existe en radicados
                json.append("\"fechaRadicacion\":\"").append(rs.getTimestamp("fecha_radicado")).append("\",");
                json.append("\"telefonoContacto\":\"").append(escapeJson(rs.getString("telefono_contacto"))).append("\"");
                json.append("}");
                
                hasResults = true;
            }
            
            json.append("],");
            
            if (hasResults) {
                json.append("\"message\":\"Solicitudes encontradas\"}");
            } else {
                json.append("\"message\":\"No se encontraron solicitudes radicadas\"}");
            }
            
            out.write(json.toString());
            db.closeConnection();
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"Error al consultar la base de datos\"}");
        }
    }

    private void handleRadicarSolicitud(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Verificar autenticación
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"error\":\"Sesión no válida\"}");
            return;
        }
        
        String userEmail = (String) session.getAttribute("userEmail");
        
        DatabaseConnection db = new DatabaseConnection();
        try {
            // Leer el cuerpo de la petición
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            
            // Parsear JSON manualmente
            String jsonData = requestBody.toString();
            String tipoSolicitud = extractJsonValue(jsonData, "tipoSolicitud");
            String dependenciaDirigida = extractJsonValue(jsonData, "dependenciaDirigida");
            String asunto = extractJsonValue(jsonData, "asunto");
            String descripcion = extractJsonValue(jsonData, "descripcion");
            // Nota: documentosAdjuntos no se usa porque la tabla radicados no tiene esta columna
            
            // Validaciones básicas
            if (tipoSolicitud.isEmpty() || asunto.isEmpty() || descripcion.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\":\"Todos los campos obligatorios deben ser completados\"}");
                return;
            }
            
            try {
                // Adaptar los datos a las columnas de la tabla radicados
                String sql = "INSERT INTO radicados (email, tipo_solicitud, asunto, descripcion, " +
                           "telefono_contacto, email_notificacion) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
                
                PreparedStatement stmt = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1, userEmail);
                stmt.setString(2, tipoSolicitud);
                stmt.setString(3, asunto);
                stmt.setString(4, descripcion);
                stmt.setString(5, ""); // telefono_contacto - no viene en la request pero es necesario
                stmt.setString(6, dependenciaDirigida); // usar dependencia como email_notificacion
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Obtener el ID generado automáticamente
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    String numeroRadicado = "RAD-" + System.currentTimeMillis(); // Generar número basado en timestamp
                    if (generatedKeys.next()) {
                        numeroRadicado = "RAD-" + generatedKeys.getInt(1);
                    }
                    
                    StringBuilder json = new StringBuilder();
                    json.append("{");
                    json.append("\"success\":true,");
                    json.append("\"message\":\"Solicitud radicada exitosamente\",");
                    json.append("\"numeroRadicado\":\"").append(numeroRadicado).append("\"");
                    json.append("}");
                    out.write(json.toString());
                } else {
                    out.write("{\"success\":false,\"message\":\"No se pudo radicar la solicitud\"}");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"error\":\"Error al guardar en la base de datos\"}");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\":\"Error al procesar la petición\"}");
        } finally {
            db.closeConnection();
        }
    }

    private String generarNumeroRadicado() {
        // Formato: RAD-YYYYMMDD-XXXX donde XXXX es un número aleatorio
        java.time.LocalDate fecha = java.time.LocalDate.now();
        String fechaStr = fecha.toString().replace("-", "");
        
        Random random = new Random();
        int numeroAleatorio = 1000 + random.nextInt(9000); // Número de 4 dígitos
        
        return "RAD-" + fechaStr + "-" + numeroAleatorio;
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "";
        
        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) return "";
        
        return json.substring(startIndex, endIndex);
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
