# APIs REST Propias - Documentación

## 🚀 Configuración Base para Postman

**URL Base del Servidor:** `http://localhost:8080`
**Content-Type:** `application/json`

## Descripción General

El proyecto incluye **APIs REST propias** desarrolladas completamente desde cero que exponen toda la funcionalidad del sistema académico como servicios web.

## 📋 APIs Disponibles para Probar

### 1. **API de Autenticación** - `/api/auth/*`

#### **🔐 POST /api/auth/login**
Autenticar usuario y obtener token JWT propio.

**Configuración Postman:**
- **URL:** `POST http://localhost:8080/api/auth/login`
- **Headers:** `Content-Type: application/json`

**Request:**
```json
{
  "email": "test@example.com",
  "password": "12345678",
  "recordarme": false,
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
    "email": "test@example.com",
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
## 🔧 **Pasos para Probar en Postman**

### **Paso 1: Configurar Environment**
1. Abre Postman
2. Clic en "Environments" → "Create Environment"
3. Nombre: "Local Development"
4. Variables:
   ```
   base_url: http://localhost:8080
   session_id: (vacío inicialmente)
   ```

### **Paso 2: Probar Autenticación**
1. **Registrar un usuario:**
   - URL: `POST {{base_url}}/api/users/simple/register`
   - Body:
   ```json
   {
     "nombres": "Test",
     "apellidos": "User",
     "email": "test@example.com",
     "password": "12345678",
     "tipoDocumento": "Cédula de Ciudadanía",
     "numeroDocumento": "12345678",
     "telefono": "3001234567"
   }
   ```

2. **Hacer login:**
   - URL: `POST {{base_url}}/api/auth/login`
   - Body:
   ```json
   {
     "email": "test@example.com",
     "password": "12345678",
     "recordarme": false,
     "pcPublico": false
   }
   ```
   - **Importante:** Copia el `JSESSIONID` de las cookies de respuesta

### **Paso 3: Configurar Session ID**
1. En la respuesta del login, ve a la pestaña "Headers"
2. Busca `Set-Cookie: JSESSIONID=VALOR_AQUI`
3. Copia el valor del JSESSIONID
4. Para las siguientes peticiones, agrega en Headers:
   ```
   Cookie: JSESSIONID=EL_VALOR_COPIADO
   ```

### **Paso 4: Probar APIs de Consulta**

#### **📄 Información Personal**
- **Consultar:** `GET {{base_url}}/api/info-personal/get`
- **Guardar:** `POST {{base_url}}/api/info-personal/save`
  ```json
  {
    "nombres": "Juan Carlos",
    "apellidos": "Pérez López",
    "tipoDocumento": "Cédula de Ciudadanía",
    "numeroDocumento": "12345678",
    "fechaNacimiento": "1990-05-15",
    "lugarNacimiento": "Bogotá",
    "genero": "Masculino",
    "estadoCivil": "Soltero",
    "direccion": "Calle 123 #45-67",
    "telefono": "6012345678",
    "celular": "3001234567",
    "emailPersonal": "juan@email.com"
  }
  ```

#### **🎓 Información Académica**
- **Consultar:** `GET {{base_url}}/api/info-academica/get`
- **Guardar:** `POST {{base_url}}/api/info-academica/save`
  ```json
  {
    "tipoFormacion": "Universitaria",
    "institucionEducativa": "Universidad Nacional",
    "programaAcademico": "Ingeniería de Sistemas",
    "fechaGraduacion": "2015-12-15",
    "numeroTarjetaProfesional": "12345",
    "universidadTitulo": "Universidad Nacional",
    "tituloObtenido": "Ingeniero de Sistemas",
    "fechaTitulo": "2015-12-15",
    "idiomas": "Español, Inglés",
    "certificacionesAdicionales": "AWS, Java",
    "experienciaLaboral": "5 años en desarrollo"
  }
  ```

#### **📝 Solicitudes**
- **Consultar Lista:** `GET {{base_url}}/api/solicitudes/list`
- **Radicar Nueva:** `POST {{base_url}}/api/solicitudes/radicar`
  ```json
  {
    "tipoSolicitud": "Consulta General",
    "dependenciaDirigida": "Secretaría Académica",
    "asunto": "Solicitud de certificados",
    "descripcion": "Necesito certificados de notas para trámite externo",
    "documentosAdjuntos": "documento1.pdf"
  }
  ```

### **Paso 5: Respuestas Esperadas**

#### **Consulta Sin Datos:**
```json
{
  "success": true,
  "data": {},
  "message": "No se encontró información registrada"
}
```

#### **Consulta Con Datos:**
```json
{
  "success": true,
  "data": {
    "nombres": "Juan Carlos",
    "apellidos": "Pérez López"
    // ... más campos
  },
  "message": "Información encontrada"
}
```

#### **Solicitud Radicada:**
```json
{
  "success": true,
  "message": "Solicitud radicada exitosamente",
  "numeroRadicado": "RAD-20250804-1234"
}
```

---

## ⚡ **Prueba Rápida con cURL**

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8080/api/users/simple/register \
  -H "Content-Type: application/json" \
  -d '{"nombres":"Test","apellidos":"User","email":"test@test.com","password":"12345678","tipoDocumento":"CC","numeroDocumento":"12345678","telefono":"3001234567"}'

# 2. Login (guarda cookies)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{"email":"test@test.com","password":"12345678"}'

# 3. Consultar información personal
curl -X GET http://localhost:8080/api/info-personal/get \
  -H "Content-Type: application/json" \
  -b cookies.txt
```

---

## ⚠️ **Notas Importantes**

1. **✅ Servidor:** Debe estar ejecutándose en `localhost:8080`
2. **🔐 Sesión:** APIs requieren estar logueado (excepto login/registro)
3. **🍪 Cookies:** Usar el JSESSIONID del login en Headers
4. **🗄️ Base de Datos:** Ejecutar `CREATE_TABLES.sql` antes de probar
5. **📊 CORS:** Configurado para `localhost:5174`

## Características de las APIs Propias

✅ **JWT Tokens Propios**: Sistema de autenticación con tokens personalizados
✅ **CORS Configurado**: Listo para llamadas desde React
✅ **Validaciones Completas**: Mantiene todas las validaciones originales
✅ **Manejo de Errores**: Respuestas JSON estructuradas
✅ **Compatibilidad**: Funciona con las sesiones HTTP existentes
✅ **RESTful**: Endpoints siguiendo convenciones REST
✅ **Documentación**: APIs completamente documentadas
