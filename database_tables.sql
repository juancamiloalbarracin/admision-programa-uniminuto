-- Script para crear las tablas necesarias para consultar datos almacenados

-- Tabla para información personal (ya existe la funcionalidad pero necesitamos verificar la tabla)
CREATE TABLE IF NOT EXISTS info_personal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    tipo_documento VARCHAR(50),
    numero_documento VARCHAR(50),
    nombres VARCHAR(255),
    apellidos VARCHAR(255),
    fecha_nacimiento DATE,
    lugar_nacimiento VARCHAR(255),
    genero VARCHAR(50),
    estado_civil VARCHAR(50),
    direccion TEXT,
    telefono VARCHAR(20),
    celular VARCHAR(20),
    email_personal VARCHAR(255),
    nombre_contacto_emergencia VARCHAR(255),
    telefono_contacto_emergencia VARCHAR(20),
    relacion_contacto_emergencia VARCHAR(100),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_email (user_email)
);

-- Tabla para información académica
CREATE TABLE IF NOT EXISTS info_academica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    tipo_formacion VARCHAR(100),
    institucion_educativa VARCHAR(255),
    programa_academico VARCHAR(255),
    fecha_graduacion DATE,
    numero_tarjeta_profesional VARCHAR(100),
    universidad_titulo VARCHAR(255),
    titulo_obtenido VARCHAR(255),
    fecha_titulo DATE,
    idiomas TEXT,
    certificaciones_adicionales TEXT,
    experiencia_laboral TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_email_academica (user_email)
);

-- Tabla para solicitudes radicadas
CREATE TABLE IF NOT EXISTS solicitudes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    numero_radicado VARCHAR(100) UNIQUE,
    tipo_solicitud VARCHAR(100),
    dependencia_dirigida VARCHAR(255),
    asunto VARCHAR(500),
    descripcion TEXT,
    documentos_adjuntos TEXT,
    estado VARCHAR(50) DEFAULT 'Radicada',
    fecha_radicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email_solicitudes (user_email),
    INDEX idx_numero_radicado (numero_radicado),
    INDEX idx_fecha_radicacion (fecha_radicacion)
);

-- Insertar algunos datos de ejemplo para pruebas (opcional)
-- INSERT INTO info_personal (user_email, nombres, apellidos, tipo_documento, numero_documento, telefono) 
-- VALUES ('test@example.com', 'Juan Carlos', 'Pérez López', 'Cédula de Ciudadanía', '12345678', '3001234567');
