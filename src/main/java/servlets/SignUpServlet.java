package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import models.Usuario;
import utils.DatabaseConnection;

/**
 * SignUpServlet - MIGRACIÓN de SignUpView.java
 * PRESERVA: Toda la lógica de registro de usuarios y validaciones
 * AÑADE: Manejo HTTP GET/POST y validaciones web
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GET: Mostrar formulario de registro
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Mostrar formulario de registro
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    /**
     * POST: Procesar registro de usuario
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener datos del formulario (PRESERVADO orden de SignUpView.java)
        String autorizacion = request.getParameter("autorizacion");
        String primerNombre = request.getParameter("primerNombre");
        String segundoNombre = request.getParameter("segundoNombre");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");
        String confirmarCorreo = request.getParameter("confirmarCorreo");
        String telefonoMovil = request.getParameter("telefonoMovil");
        String gradoAcademico = request.getParameter("gradoAcademico");
        String sede = request.getParameter("sede");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String numeroDocumento = request.getParameter("numeroDocumento");
        String contrasena = request.getParameter("contrasena");
        String repetirContrasena = request.getParameter("repetirContrasena");
        
        // Validaciones (EQUIVALE a las validaciones de SignUpView.java)
        String error = validarFormulario(autorizacion, primerNombre, segundoNombre, apellidos, 
                correo, confirmarCorreo, telefonoMovil, gradoAcademico, sede, tipoDocumento, 
                numeroDocumento, contrasena, repetirContrasena);
        
        if (error != null) {
            request.setAttribute("error", error);
            // Mantener datos en el formulario
            setFormAttributes(request, autorizacion, primerNombre, segundoNombre, apellidos, 
                    correo, confirmarCorreo, telefonoMovil, gradoAcademico, sede, tipoDocumento, 
                    numeroDocumento);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
            return;
        }
        
        // PRESERVADO: Misma lógica de creación de usuario que SignUpView.java - CORREGIDO
        String nombreCompleto = primerNombre + (segundoNombre.isEmpty() ? "" : " " + segundoNombre);
        // Constructor: Usuario(nombre, apellido, email, username, password)
        Usuario usuario = new Usuario(nombreCompleto, apellidos, correo, correo, contrasena);
        
        DatabaseConnection db = new DatabaseConnection();
        String resultado = db.insertUsuario(usuario);
        db.closeConnection();
        
        if ("EXITO".equals(resultado)) {
            // EQUIVALE a: JOptionPane.showMessageDialog "¡Usuario registrado correctamente!"
            response.sendRedirect("login?registroExitoso=true");
        } else {
            // Manejo específico de errores
            String mensajeError;
            switch (resultado) {
                case "ERROR_CONEXION":
                    mensajeError = "Error: Conexión no establecida con la base de datos. Verifique que el servidor MySQL esté ejecutándose.";
                    break;
                case "ERROR_EMAIL_DUPLICADO":
                    mensajeError = "Error: El correo electrónico ya está registrado. Por favor, use un correo diferente.";
                    break;
                case "ERROR_USERNAME_DUPLICADO":
                    mensajeError = "Error: El nombre de usuario ya está registrado. Por favor, use un nombre diferente.";
                    break;
                case "ERROR_DATOS_DUPLICADOS":
                    mensajeError = "Error: Los datos ingresados ya están registrados. Verifique el correo y número de documento.";
                    break;
                case "ERROR_BD":
                    mensajeError = "Error: Problema con la base de datos. Intente nuevamente más tarde.";
                    break;
                case "ERROR_INSERCION":
                    mensajeError = "Error: No se pudo completar el registro. Intente nuevamente.";
                    break;
                default:
                    mensajeError = "Error inesperado durante el registro. Contacte al administrador del sistema.";
                    break;
            }
            
            request.setAttribute("error", mensajeError);
            setFormAttributes(request, autorizacion, primerNombre, segundoNombre, apellidos, 
                    correo, confirmarCorreo, telefonoMovil, gradoAcademico, sede, tipoDocumento, 
                    numeroDocumento);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }
    }
    
    /**
     * PRESERVADO: Validaciones equivalentes a la lógica de SignUpView.java validateForm
     */
    private String validarFormulario(String autorizacion, String primerNombre, String segundoNombre, 
            String apellidos, String correo, String confirmarCorreo, String telefonoMovil, 
            String gradoAcademico, String sede, String tipoDocumento, String numeroDocumento, 
            String contrasena, String repetirContrasena) {
        
        // Autorización de datos
        if (isEmpty(autorizacion) || "Seleccione...".equals(autorizacion)) {
            return "Debe autorizar el tratamiento de datos.";
        }
        
        if (!"Sí".equals(autorizacion)) {
            return "Debe autorizar el tratamiento de datos para continuar con el registro.";
        }
        
        // Campos obligatorios
        if (isEmpty(primerNombre)) return "El primer nombre es obligatorio.";
        if (isEmpty(segundoNombre)) return "El segundo nombre es obligatorio.";
        if (isEmpty(apellidos)) return "Los apellidos son obligatorios.";
        if (isEmpty(correo)) return "El correo es obligatorio.";
        if (isEmpty(confirmarCorreo)) return "Debe confirmar el correo.";
        if (isEmpty(telefonoMovil)) return "El teléfono móvil es obligatorio.";
        if (isEmpty(gradoAcademico)) return "El grado académico es obligatorio.";
        if (isEmpty(sede)) return "La sede es obligatoria.";
        if (isEmpty(tipoDocumento) || "Seleccione...".equals(tipoDocumento)) {
            return "Debe seleccionar el tipo de documento.";
        }
        if (isEmpty(numeroDocumento)) return "El número de documento es obligatorio.";
        if (isEmpty(contrasena)) return "La contraseña es obligatoria.";
        if (isEmpty(repetirContrasena)) return "Debe repetir la contraseña.";
        
        // Validación de correos (PRESERVADO de SignUpView emailsMatch)
        if (!correo.equals(confirmarCorreo)) {
            return "Los correos no coinciden.";
        }
        
        // Validación de formato de correo
        if (!isValidEmail(correo)) {
            return "El formato del correo no es válido.";
        }
        
        // Validación de contraseñas (PRESERVADO de SignUpView pwdsMatch)
        if (!contrasena.equals(repetirContrasena)) {
            return "Las contraseñas no coinciden.";
        }
        
        // Validación de contraseña (PRESERVADO del hint "Mínimo 6 caracteres y 2 símbolos")
        if (contrasena.length() < 6) {
            return "La contraseña debe tener mínimo 6 caracteres.";
        }
        
        // Contar símbolos en la contraseña
        int simbolos = 0;
        for (char c : contrasena.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                simbolos++;
            }
        }
        if (simbolos < 2) {
            return "La contraseña debe tener al menos 2 símbolos.";
        }
        
        // Validación de teléfono
        if (!telefonoMovil.matches("\\d{10}")) {
            return "El teléfono debe tener exactamente 10 dígitos.";
        }
        
        // Validación de documento
        if (!numeroDocumento.matches("\\d+")) {
            return "El número de documento debe contener solo números.";
        }
        
        return null; // Sin errores
    }
    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    }
    
    private void setFormAttributes(HttpServletRequest request, String autorizacion, String primerNombre, 
            String segundoNombre, String apellidos, String correo, String confirmarCorreo, 
            String telefonoMovil, String gradoAcademico, String sede, String tipoDocumento, 
            String numeroDocumento) {
        
        request.setAttribute("autorizacion", autorizacion);
        request.setAttribute("primerNombre", primerNombre);
        request.setAttribute("segundoNombre", segundoNombre);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("correo", correo);
        request.setAttribute("confirmarCorreo", confirmarCorreo);
        request.setAttribute("telefonoMovil", telefonoMovil);
        request.setAttribute("gradoAcademico", gradoAcademico);
        request.setAttribute("sede", sede);
        request.setAttribute("tipoDocumento", tipoDocumento);
        request.setAttribute("numeroDocumento", numeroDocumento);
    }
}
