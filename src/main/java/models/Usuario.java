package models;

/**
 * Clase Usuario que representa un usuario del sistema.
 * MIGRADO: Mantiene toda la funcionalidad original para compatibilidad web.
 */
public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String password;

    // Constructor por defecto para compatibilidad con formularios web
    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String username, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters y Setters - SIN CAMBIOS de la versión original
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validación de credenciales - PRESERVADO de la versión original
     */
    public boolean validateCredentials(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    /**
     * Método auxiliar para validaciones web
     */
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty() &&
               apellido != null && !apellido.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() &&
               username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}'; // Password omitido por seguridad
    }
}
