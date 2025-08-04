# APIs REST Propias - Documentación

## Descripción General

El proyecto incluye **APIs REST propias** desarrolladas completamente desde cero que exponen toda la funcionalidad del sistema académico como servicios web.

## APIs Disponibles

### 1. **API de Autenticación** - `/api/auth/*`

#### **POST /api/auth/login**
Autenticar usuario y obtener token JWT propio.

**Request:**
```json
{
  "email": "usuario@ejemplo.com",
  "password": "password123",
  "recordarme": true,
  "pcPublico": false
}
```

**Response Exitoso:**
```json
{
  "success": true,
  "message": "¡Bienvenido!",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "sessionId": "B8F4C2A1...",
  "user": {
    "email": "usuario@ejemplo.com",
    "nombres": "Juan",
    "apellidos": "Pérez",
    "username": "juan.perez"
  }
}
```

#### **POST /api/auth/logout**
Cerrar sesión del usuario.

**Response:**
```json
{
  "success": true,
  "message": "Sesión cerrada exitosamente"
}
```

#### **GET /api/auth/profile**
Obtener información del perfil del usuario autenticado.

**Response:**
```json
{
  "success": true,
  "user": {
    "email": "usuario@ejemplo.com",
    "nombres": "Juan",
    "apellidos": "Pérez",
    "username": "juan.perez"
  }
}
```

### 2. **API de Información Personal** - `/api/info-personal/*`

#### **GET /api/info-personal/get**
Obtener información personal del usuario.

**Response:**
```json
{
  "success": true,
  "data": {
    "nombres": "Juan Carlos",
    "apellidos": "Pérez González",
    "fechaNacimiento": "1995-03-15",
    "tipoDocumento": "Cédula de Ciudadanía",
    "numeroDocumento": "12345678",
    "genero": "Masculino",
    "telefono": "3001234567"
  },
  "message": "Información personal encontrada"
}
```

#### **POST /api/info-personal/save**
Guardar información personal del usuario.

**Request:**
```json
{
  "nombres": "Juan Carlos",
  "apellidos": "Pérez González",
  "fechaNacimiento": "1995-03-15",
  "lugarNacimiento": "Bogotá, Colombia",
  "tipoDocumento": "Cédula de Ciudadanía",
  "numeroDocumento": "12345678",
  "genero": "Masculino",
  "estadoCivil": "Soltero",
  "direccion": "Calle 123 #45-67",
  "telefono": "3001234567",
  "celular": "3009876543",
  "emailPersonal": "juan@personal.com"
}
```

### 3. **API de Información Académica** - `/api/info-academica/*`

#### **GET /api/info-academica/get**
Obtener información académica del usuario.

#### **POST /api/info-academica/save**
Guardar información académica del usuario.

**Request:**
```json
{
  "institucionBachillerato": "Colegio San José",
  "anioBachillerato": "2013",
  "promedioAcademico": "4.2",
  "tipoInstitucion": "Privada",
  "modalidadEstudio": "Presencial",
  "enfasisArea": "Ciencias",
  "pruebasEstado": "ICFES Saber 11",
  "puntajePruebas": "320",
  "experienciaLaboral": "2 años en sector tecnológico",
  "certificacionesAdicionales": "Curso de programación"
}
```

### 4. **API de Radicación** - `/api/radicar/*`

#### **GET /api/radicar/mis-solicitudes**
Obtener todas las solicitudes del usuario.

**Response:**
```json
{
  "success": true,
  "solicitudes": [
    {
      "id": 1,
      "tipoSolicitud": "Solicitud de Admisión",
      "asunto": "Admisión Ingeniería",
      "descripcion": "Solicito admisión al programa...",
      "fechaRadicado": "2025-01-15T10:30:00",
      "telefonoContacto": "3001234567",
      "emailNotificacion": "juan@ejemplo.com"
    }
  ],
  "message": "Solicitudes encontradas"
}
```

#### **POST /api/radicar/create**
Crear nueva solicitud de radicación.

**Request:**
```json
{
  "tipoSolicitud": "Solicitud de Admisión",
  "asunto": "Admisión Ingeniería de Sistemas",
  "descripcion": "Solicito admisión al programa de Ingeniería de Sistemas...",
  "telefonoContacto": "3001234567",
  "emailNotificacion": "notificaciones@ejemplo.com",
  "aceptaTerminos": true
}
```

## Ejemplo de Uso desde React

```javascript
// services/api.js
class ApiService {
  constructor() {
    this.baseURL = 'http://localhost:8080/mi-proyecto-web/api';
    this.token = localStorage.getItem('token');
  }

  async request(endpoint, options = {}) {
    const url = `${this.baseURL}${endpoint}`;
    const config = {
      credentials: 'include', // Para mantener sesiones
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { 'Authorization': `Bearer ${this.token}` })
      },
      ...options
    };

    const response = await fetch(url, config);
    const data = await response.json();
    
    if (!response.ok) {
      throw new Error(data.message || 'Error en la petición');
    }
    
    return data;
  }

  // Métodos de autenticación
  async login(email, password, recordarme = false, pcPublico = false) {
    const data = await this.request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password, recordarme, pcPublico })
    });
    
    if (data.success && data.token) {
      localStorage.setItem('token', data.token);
      this.token = data.token;
    }
    
    return data;
  }

  async logout() {
    const data = await this.request('/auth/logout', { method: 'POST' });
    localStorage.removeItem('token');
    this.token = null;
    return data;
  }

  // Métodos de información personal
  async getInfoPersonal() {
    return this.request('/info-personal/get');
  }

  async saveInfoPersonal(infoData) {
    return this.request('/info-personal/save', {
      method: 'POST',
      body: JSON.stringify(infoData)
    });
  }

  // Métodos de radicación
  async getMisSolicitudes() {
    return this.request('/radicar/mis-solicitudes');
  }

  async createSolicitud(solicitudData) {
    return this.request('/radicar/create', {
      method: 'POST',
      body: JSON.stringify(solicitudData)
    });
  }
}

export default new ApiService();
```

## Componente React de Ejemplo

```jsx
// components/Login.jsx
import React, { useState } from 'react';
import ApiService from '../services/api';

const Login = ({ onLoginSuccess }) => {
  const [credentials, setCredentials] = useState({
    email: '',
    password: '',
    recordarme: false
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await ApiService.login(
        credentials.email,
        credentials.password,
        credentials.recordarme
      );

      if (response.success) {
        onLoginSuccess(response.user);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        placeholder="Email"
        value={credentials.email}
        onChange={(e) => setCredentials({
          ...credentials,
          email: e.target.value
        })}
        required
      />
      
      <input
        type="password"
        placeholder="Contraseña"
        value={credentials.password}
        onChange={(e) => setCredentials({
          ...credentials,
          password: e.target.value
        })}
        required
      />
      
      <label>
        <input
          type="checkbox"
          checked={credentials.recordarme}
          onChange={(e) => setCredentials({
            ...credentials,
            recordarme: e.target.checked
          })}
        />
        Recordarme
      </label>
      
      <button type="submit" disabled={loading}>
        {loading ? 'Iniciando sesión...' : 'Iniciar Sesión'}
      </button>
      
      {error && <div className="error">{error}</div>}
    </form>
  );
};

export default Login;
```

## Características de las APIs Propias

✅ **JWT Tokens Propios**: Sistema de autenticación con tokens personalizados
✅ **CORS Configurado**: Listo para llamadas desde React
✅ **Validaciones Completas**: Mantiene todas las validaciones originales
✅ **Manejo de Errores**: Respuestas JSON estructuradas
✅ **Compatibilidad**: Funciona con las sesiones HTTP existentes
✅ **RESTful**: Endpoints siguiendo convenciones REST
✅ **Documentación**: APIs completamente documentadas
