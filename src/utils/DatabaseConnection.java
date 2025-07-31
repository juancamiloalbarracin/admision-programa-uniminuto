package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/app_administrativa";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    
    private Connection connection;
    // Email del usuario logueado, usada para relacionar datos
    private static String currentUserEmail;

    public DatabaseConnection() {
        try {
            // Intentar conectar directamente a la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
            // Asegurar que todas las tablas existen
            createTables();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos. Intentando crear la base de datos...");
            try {
                // Conectar sin especificar base de datos para crearla
                String baseURL = "jdbc:mysql://localhost:3306/";
                Connection tempConnection = DriverManager.getConnection(baseURL, USER, PASSWORD);
                
                // Crear la base de datos
                java.sql.Statement stmt = tempConnection.createStatement();
                stmt.execute("CREATE DATABASE IF NOT EXISTS app_administrativa");
                stmt.execute("USE app_administrativa");
                
                // Crear la tabla usuarios
                String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(100) NOT NULL, " +
                    "apellido VARCHAR(100) NOT NULL, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "UNIQUE (email)" +
                    ")";
                stmt.execute(createTable);
                // Asegurar índice en usuarios.email aunque la tabla ya existiera sin índice
                try {
                    stmt.execute("ALTER TABLE usuarios ADD UNIQUE (email)");
                } catch (SQLException ignore) {
                    // índice ya existente, ignorar
                }
                // Crear tabla de información personal
                String createPersonal = "CREATE TABLE IF NOT EXISTS informacion_personal (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "primer_nombre VARCHAR(100), " +
                    "segundo_nombre VARCHAR(100), " +
                    "apellidos VARCHAR(200), " +
                    "sexo_biologico VARCHAR(50), " +
                    "grupo_sanguineo VARCHAR(50), " +
                    "tipo_documento VARCHAR(100), " +
                    "numero_documento VARCHAR(100), " +
                    "fecha_expedicion_documento VARCHAR(20), " +
                    "pais_nacimiento VARCHAR(100), " +
                    "ciudad_nacimiento VARCHAR(100), " +
                    "fecha_nacimiento VARCHAR(20), " +
                    "telefono_celular VARCHAR(50), " +
                    "correo_electronico VARCHAR(100), " +
                    "tipo_direccion VARCHAR(100), " +
                    "direccion TEXT, " +
                    "casa_apto_barrio VARCHAR(200), " +
                    "tiene_sisben VARCHAR(10), " +
                    "tiene_eps VARCHAR(10), " +
                    "tiene_discapacidad VARCHAR(10), " +
                    "primer_nombre_acudiente VARCHAR(100), " +
                    "segundo_nombre_acudiente VARCHAR(100), " +
                    "apellidos_acudiente VARCHAR(200), " +
                    "telefono_acudiente VARCHAR(50), " +
                    "correo_acudiente VARCHAR(100), " +
                    "UNIQUE (email), " +
                    "FOREIGN KEY (email) REFERENCES usuarios(email)" +
                ")";
                stmt.execute(createPersonal);
                // Asegurar tamaño de grupo_sanguineo aunque la tabla existiera previa con tamaño menor
                try {
                    stmt.execute("ALTER TABLE informacion_personal MODIFY grupo_sanguineo VARCHAR(50)");
                } catch (SQLException ignore) {
                }
                // Crear tabla de información académica
                String createAcademica = "CREATE TABLE IF NOT EXISTS informacion_academica (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "nivel VARCHAR(50), " +
                    "sede VARCHAR(100), " +
                    "grado_academico VARCHAR(50), " +
                    "periodo_admision VARCHAR(100), " +
                    "metodologia VARCHAR(100), " +
                    "jornada VARCHAR(50), " +
                    "plan_decision VARCHAR(100), " +
                    "grado_seleccionado VARCHAR(100), " +
                    "pais VARCHAR(100), " +
                    "grado_obtenido VARCHAR(100), " +
                    "fecha_graduacion VARCHAR(20), " +
                    "UNIQUE (email), " +
                    "FOREIGN KEY (email) REFERENCES usuarios(email)" +
                ")";
                stmt.execute(createAcademica);
                // Crear tabla de radicados
                String createRadicados = "CREATE TABLE IF NOT EXISTS radicados (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "tipo_solicitud VARCHAR(100), " +
                    "asunto VARCHAR(200), " +
                    "descripcion TEXT, " +
                    "telefono_contacto VARCHAR(50), " +
                    "email_notificacion VARCHAR(100), " +
                    "fecha_radicado DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (email) REFERENCES usuarios(email)" +
                ")";
                stmt.execute(createRadicados);
                
                tempConnection.close();
                
                // Ahora conectar a la base de datos creada
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Base de datos creada y conexión establecida.");
                // Asegurar que las tablas secundarias existen
                createTables();
                
            } catch (SQLException ex) {
                System.err.println("Error al crear la base de datos:");
                System.err.println("URL: " + URL);
                System.err.println("Usuario: " + USER);
                System.err.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeQuery(String query) {
        // Implementation for executing a query can be added here
    }
    
    // Método para insertar un usuario en la base de datos
    public boolean insertUsuario(models.Usuario usuario) {
        if (connection == null) {
            System.err.println("No hay conexión a la base de datos");
            return false;
        }
        
        String sql = "INSERT INTO usuarios (nombre, apellido, username, password, email) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getEmail());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para validar usuario y contraseña
    /**
     * Inserta o actualiza la información personal de un usuario.
     * Usa REPLACE INTO para sobreescribir si ya existe.
     */
    public boolean upsertInformacionPersonal(
            String email, String primerNombre, String segundoNombre,
            String apellidos, String sexoBiologico, String grupoSanguineo,
            String tipoDocumento, String numeroDocumento, String fechaExpedicionDocumento,
            String paisNacimiento, String ciudadNacimiento, String fechaNacimiento,
            String telefonoCelular, String correoElectronico, String tipoDireccion,
            String direccion, String casaAptoBarrio, String tieneSisben,
            String tieneEps, String tieneDiscapacidad,
            String primerNombreAcudiente, String segundoNombreAcudiente,
            String apellidosAcudiente, String telefonoAcudiente, String correoAcudiente) {
        // Asegurar existencia del usuario antes de insertar datos personales
        ensureUserExists(email);
        String sql = "REPLACE INTO informacion_personal (" +
            "email, primer_nombre, segundo_nombre, apellidos, sexo_biologico, grupo_sanguineo, " +
            "tipo_documento, numero_documento, fecha_expedicion_documento, pais_nacimiento, ciudad_nacimiento, fecha_nacimiento, " +
            "telefono_celular, correo_electronico, tipo_direccion, direccion, casa_apto_barrio, " +
            "tiene_sisben, tiene_eps, tiene_discapacidad, primer_nombre_acudiente, segundo_nombre_acudiente, apellidos_acudiente, telefono_acudiente, correo_acudiente) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(sql)) {
            int i = 1;
            stmt.setString(i++, email);
            stmt.setString(i++, primerNombre);
            stmt.setString(i++, segundoNombre);
            stmt.setString(i++, apellidos);
            stmt.setString(i++, sexoBiologico);
            stmt.setString(i++, grupoSanguineo);
            stmt.setString(i++, tipoDocumento);
            stmt.setString(i++, numeroDocumento);
            stmt.setString(i++, fechaExpedicionDocumento);
            stmt.setString(i++, paisNacimiento);
            stmt.setString(i++, ciudadNacimiento);
            stmt.setString(i++, fechaNacimiento);
            stmt.setString(i++, telefonoCelular);
            stmt.setString(i++, correoElectronico);
            stmt.setString(i++, tipoDireccion);
            stmt.setString(i++, direccion);
            stmt.setString(i++, casaAptoBarrio);
            stmt.setString(i++, tieneSisben);
            stmt.setString(i++, tieneEps);
            stmt.setString(i++, tieneDiscapacidad);
            stmt.setString(i++, primerNombreAcudiente);
            stmt.setString(i++, segundoNombreAcudiente);
            stmt.setString(i++, apellidosAcudiente);
            stmt.setString(i++, telefonoAcudiente);
            stmt.setString(i++, correoAcudiente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta o actualiza la información académica de un usuario.
     */
    public boolean upsertInformacionAcademica(
            String email, String nivel, String sede, String gradoAcademico,
            String periodoAdmision, String metodologia, String jornada,
            String planDecision, String gradoSeleccionado, String pais,
            String gradoObtenido, String fechaGraduacion) {
        // Asegurar existencia del usuario antes de insertar datos académicos
        ensureUserExists(email);
        String sql = "REPLACE INTO informacion_academica (" +
            "email, nivel, sede, grado_academico, periodo_admision, metodologia, jornada, plan_decision, grado_seleccionado, pais, grado_obtenido, fecha_graduacion) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(sql)) {
            int i = 1;
            stmt.setString(i++, email);
            stmt.setString(i++, nivel);
            stmt.setString(i++, sede);
            stmt.setString(i++, gradoAcademico);
            stmt.setString(i++, periodoAdmision);
            stmt.setString(i++, metodologia);
            stmt.setString(i++, jornada);
            stmt.setString(i++, planDecision);
            stmt.setString(i++, gradoSeleccionado);
            stmt.setString(i++, pais);
            stmt.setString(i++, gradoObtenido);
            stmt.setString(i++, fechaGraduacion);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta un nuevo radicado para un usuario.
     */
    public boolean insertRadicado(
            String email, String tipoSolicitud, String asunto,
            String descripcion, String telefonoContacto, String emailNotificacion) {
        // Asegurar existencia del usuario antes de insertar radicado
        ensureUserExists(email);
        String sql = "INSERT INTO radicados (email, tipo_solicitud, asunto, descripcion, telefono_contacto, email_notificacion) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, tipoSolicitud);
            stmt.setString(3, asunto);
            stmt.setString(4, descripcion);
            stmt.setString(5, telefonoContacto);
            stmt.setString(6, emailNotificacion);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean validarUsuario(String username, String password) {
        if (connection == null) {
            System.err.println("No hay conexión a la base de datos");
            return false;
        }
        
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            java.sql.ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Asigna el email del usuario que ha iniciado sesión.
     */
    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }
    /**
     * Obtiene el email del usuario que ha iniciado sesión.
     */
    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    /**
     * Crea las tablas necesarias en la base de datos si no existen.
     */
    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Asegurar que estamos en la base de datos correcta
            stmt.execute("USE app_administrativa");
            // Crear la tabla usuarios
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "apellido VARCHAR(100) NOT NULL, " +
                "username VARCHAR(50) UNIQUE NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "UNIQUE (email)" +
                ")";
            stmt.execute(createTable);
            // Asegurar índice único en usuarios.email si no existiera
            try {
                stmt.execute("ALTER TABLE usuarios ADD UNIQUE (email)");
            } catch (SQLException ignore) {
                // Puede fallar si el índice ya existe, se ignora
            }

            // Crear tabla de información personal
            String createPersonal = "CREATE TABLE IF NOT EXISTS informacion_personal (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "primer_nombre VARCHAR(100), " +
                "segundo_nombre VARCHAR(100), " +
                "apellidos VARCHAR(200), " +
                "sexo_biologico VARCHAR(50), " +
                "grupo_sanguineo VARCHAR(50), " +
                "tipo_documento VARCHAR(100), " +
                "numero_documento VARCHAR(100), " +
                "fecha_expedicion_documento VARCHAR(20), " +
                "pais_nacimiento VARCHAR(100), " +
                "ciudad_nacimiento VARCHAR(100), " +
                "fecha_nacimiento VARCHAR(20), " +
                "telefono_celular VARCHAR(50), " +
                "correo_electronico VARCHAR(100), " +
                "tipo_direccion VARCHAR(100), " +
                "direccion TEXT, " +
                "casa_apto_barrio VARCHAR(200), " +
                "tiene_sisben VARCHAR(10), " +
                "tiene_eps VARCHAR(10), " +
                "tiene_discapacidad VARCHAR(10), " +
                "primer_nombre_acudiente VARCHAR(100), " +
                "segundo_nombre_acudiente VARCHAR(100), " +
                "apellidos_acudiente VARCHAR(200), " +
                "telefono_acudiente VARCHAR(50), " +
                "correo_acudiente VARCHAR(100), " +
                "UNIQUE (email), " +
                "FOREIGN KEY (email) REFERENCES usuarios(email)" +
            ")";
            stmt.execute(createPersonal);
            // Asegurar tamaño de grupo_sanguineo en tabla existente
            try {
                stmt.execute("ALTER TABLE informacion_personal MODIFY grupo_sanguineo VARCHAR(50)");
            } catch (SQLException ignore) {
            }

            // Crear tabla de información académica
            String createAcademica = "CREATE TABLE IF NOT EXISTS informacion_academica (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "nivel VARCHAR(50), " +
                "sede VARCHAR(100), " +
                "grado_academico VARCHAR(50), " +
                "periodo_admision VARCHAR(100), " +
                "metodologia VARCHAR(100), " +
                "jornada VARCHAR(50), " +
                "plan_decision VARCHAR(100), " +
                "grado_seleccionado VARCHAR(100), " +
                "pais VARCHAR(100), " +
                "grado_obtenido VARCHAR(100), " +
                "fecha_graduacion VARCHAR(20), " +
                "UNIQUE (email), " +
                "FOREIGN KEY (email) REFERENCES usuarios(email)" +
            ")";
            stmt.execute(createAcademica);

            // Crear tabla de radicados
            String createRadicados = "CREATE TABLE IF NOT EXISTS radicados (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "tipo_solicitud VARCHAR(100), " +
                "asunto VARCHAR(200), " +
                "descripcion TEXT, " +
                "telefono_contacto VARCHAR(50), " +
                "email_notificacion VARCHAR(100), " +
                "fecha_radicado DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (email) REFERENCES usuarios(email)" +
            ")";
            stmt.execute(createRadicados);
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Asegura que exista un registro en usuarios para el email dado.
     */
    private void ensureUserExists(String email) {
        if (email == null || email.isEmpty()) return;
        try (PreparedStatement check = connection.prepareStatement("SELECT 1 FROM usuarios WHERE email = ?")) {
            check.setString(1, email);
            ResultSet rs = check.executeQuery();
            if (!rs.next()) {
                try (PreparedStatement ins = connection.prepareStatement(
                        "INSERT INTO usuarios(nombre, apellido, username, password, email) VALUES (?, ?, ?, ?, ?)")) {
                    ins.setString(1, "Desconocido");
                    ins.setString(2, "Desconocido");
                    ins.setString(3, email);
                    ins.setString(4, "");
                    ins.setString(5, email);
                    ins.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
