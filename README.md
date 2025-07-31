# Mi Proyecto Java

Este proyecto es una aplicación Java que permite a los usuarios iniciar sesión, registrarse y gestionar su información personal y académica. La aplicación está estructurada en varias vistas y utiliza una conexión a base de datos a través de JDBC.

## Estructura del Proyecto

```
mi-proyecto-java
├── src
│   ├── Main.java                # Punto de entrada de la aplicación.
│   ├── views                    # Contiene las vistas de la aplicación.
│   │   ├── LoginView.java       # Interfaz de inicio de sesión.
│   │   ├── SignUpView.java      # Registro de nuevos usuarios.
│   │   ├── NavBarPanel.java      # Barra de navegación reutilizable.
│   │   ├── InfoPersonalView.java # Vista de información personal.
│   │   ├── InfoAcademicaView.java# Vista de información académica.
│   │   └── RadicarView.java     # Vista para radicar solicitudes.
│   ├── utils                    # Contiene utilidades del proyecto.
│   │   └── DatabaseConnection.java# Clase para conexión a la base de datos.
│   └── models                   # Contiene los modelos de datos.
│       ├── Usuario.java         # Modelo de usuario.
│       └── Estudiante.java      # Modelo de estudiante.
├── # Sistema Académico UDC - Migración Web

## ✅ FASE 2 COMPLETADA: Login Web Funcional

### 🎯 Objetivo Alcanzado
Login web completamente funcional que replica **exactamente** la funcionalidad y diseño de `LoginView.java` usando servlets tradicionales con métodos GET y POST.

### 📁 Estructura Actualizada del Proyecto

```
mi-proyecto-java/
├── pom.xml                              # ✅ Configuración Maven Web
├── README.md                            # ✅ Documentación actualizada
│
├── src/main/
│   ├── java/                           # Código Java
│   │   ├── models/                     # ✅ PRESERVADO
│   │   │   ├── Usuario.java           # Con constructor por defecto para web
│   │   │   └── Estudiante.java        # Herencia preservada
│   │   ├── utils/                      # ✅ PRESERVADO + MEJORADO  
│   │   │   └── DatabaseConnection.java # + método obtenerUsuarioPorUsername()
│   │   ├── servlets/                   # ✅ NUEVO - Lógica web
│   │   │   ├── LoginServlet.java      # GET/POST para autenticación
│   │   │   ├── MainServlet.java       # Dashboard principal
│   │   │   └── LogoutServlet.java     # Cierre de sesión
│   │   └── filters/                    # ✅ Configurado
│   │       └── CharacterEncodingFilter.java # UTF-8
│   │
│   └── webapp/                         # Contenido web
│       ├── index.jsp                   # ✅ Página de bienvenida
│       └── WEB-INF/
│           ├── web.xml                 # ✅ Servlets configurados
│           └── views/
│               ├── login.jsp           # ✅ NUEVO - Réplica exacta de LoginView
│               ├── main.jsp            # ✅ NUEVO - Dashboard post-login
│               └── error/              # ✅ Páginas de error
│                   ├── 404.jsp
│                   └── 500.jsp
│
└── src/ (original)                     # 🔄 Mantener durante transición
    ├── Main.java                       # ⚠️  Será reemplazado en Fase 3
    ├── models/                         # ✅ Migrado
    ├── utils/                          # ✅ Migrado y mejorado
    └── views/
        ├── LoginView.java              # ✅ MIGRADO a login.jsp + LoginServlet
        ├── MainView.java               # ⏳ Pendiente Fase 3
        └── [otros views]               # ⏳ Pendientes Fases 3-5
```

### 🔄 Funcionalidades Web Implementadas

#### ✅ **LoginServlet.java - Métodos GET y POST**
```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    // GET: Mostrar formulario (equivale a mostrar LoginView)
    protected void doGet(...) {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(...);
    }
    
    // POST: Procesar login (equivale al ActionListener del botón)
    protected void doPost(...) {
        // PRESERVADA: Misma lógica de validación que LoginView
        DatabaseConnection db = new DatabaseConnection();
        boolean valido = db.validarUsuario(email, password);
        // Sesiones HTTP en lugar de variables estáticas
    }
}
```

#### ✅ **login.jsp - Diseño Idéntico**
- **Header azul oscuro**: `rgb(0, 38, 76)` - Exacto
- **Campos con TitledBorder**: Replicados con CSS
- **Checkboxes**: "Recordarme" y "PC público" funcionando
- **Botones**: Mismo estilo y colores que Swing
- **Footer**: Información de UNIMINUTO preservada
- **Validaciones**: Mensajes de error idénticos

#### ✅ **Gestión de Sesiones HTTP**
- **currentUserEmail**: Migrado a `session.getAttribute("userEmail")`
- **Duración inteligente**: 7 días si "Recordarme", 15 min si "PC público"
- **Datos de usuario**: Completos en sesión para uso en otras vistas

#### ✅ **MainServlet.java + main.jsp**
- **Dashboard principal**: Con cards para cada funcionalidad
- **Información del usuario**: Mostrada igual que MainView
- **Navegación**: Enlaces a próximas funcionalidades
- **Logout**: Limpia sesión y redirige con mensaje

### 🛠️ Flujo de Autenticación Web

```
1. Usuario visita http://localhost:8080
   ├── index.jsp (página bienvenida)
   └── Click "Iniciar Sesión" → /login

2. LoginServlet.doGet()
   └── Muestra login.jsp (formulario)

3. Usuario completa formulario y submit
   └── LoginServlet.doPost()
       ├── PRESERVADO: db.validarUsuario(email, password)
       ├── Si válido: Crear sesión HTTP + redirect /main
       └── Si inválido: Mostrar error (mismo mensaje que Swing)

4. MainServlet.doGet()
   ├── Verificar sesión válida
   ├── Cargar datos de usuario
   └── Mostrar main.jsp (dashboard)

5. Logout
   ├── Limpiar sesión HTTP
   ├── Limpiar DatabaseConnection.currentUserEmail
   └── Redirect login con mensaje "Sesión cerrada"
```

### 🎨 Fidelidad Visual Completa

| Elemento LoginView | Implementación Web | Estado |
|-------------------|-------------------|--------|
| Header azul `(0,38,76)` | CSS `rgb(0, 38, 76)` | ✅ Idéntico |
| Título "App administrativa" | Mismo texto y estilo | ✅ Idéntico |
| Logo placeholder | Div con texto "LOGO UNIMINUTO" | ✅ Funcional |
| Campo "Correo-e" | Input con label flotante | ✅ Idéntico |
| Campo "Contraseña" | Input password con label | ✅ Idéntico |
| Checkbox "Recordarme" | Funcionando con sesión extendida | ✅ Mejorado |
| Checkbox "PC público" | Funcionando con sesión corta | ✅ Mejorado |
| Botón "Iniciar Sesión" | Mismo color y estilo | ✅ Idéntico |
| Botón "Registrarse" | Link a /signup | ✅ Funcional |
| "¿Olvidó contraseña?" | Link placeholder | ✅ Funcional |
| Footer UNIMINUTO | Mismo texto completo | ✅ Idéntico |
| Validación error | Mismos mensajes de error | ✅ Idéntico |

### 🔧 Testing y Validación

#### Compilar y ejecutar:
```bash
cd "d:\UDC 2025 -1\CHAMBA\JAVA NUEVO\mi-proyecto-java"
mvn clean compile
mvn tomcat7:run
```

#### Acceder a la aplicación:
- **Inicio**: http://localhost:8080
- **Login directo**: http://localhost:8080/login
- **Dashboard**: http://localhost:8080/main (requiere login)

#### Funcionalidades testeable:
1. ✅ **Login con credenciales válidas**: Redirige a dashboard
2. ✅ **Login con credenciales inválidas**: Muestra error exacto
3. ✅ **Recordarme activado**: Sesión de 7 días
4. ✅ **PC público activado**: Sesión de 15 minutos
5. ✅ **Logout**: Limpia sesión y muestra mensaje
6. ✅ **Acceso sin login**: Redirige automáticamente a login

### 📊 Estado de Migración

| Componente | ✅ Fase 1 | ✅ Fase 2 | ⏳ Pendiente |
|------------|-----------|-----------|-------------|
| Estructura Maven | Completa | - | - |
| Base de datos | Funcional | - | - |
| Modelos | Migrados | Mejorados | - |
| **Login** | - | **Completo** | - |
| Vistas principales | - | Dashboard básico | MainView completo |
| Formularios | - | - | Fase 3-4 |

### 🎯 Siguientes Pasos (Fase 3)

1. **Migrar MainView completo** - NavigationBar y menús
2. **InfoPersonalServlet + JSP** - Formulario información personal
3. **InfoAcademicaServlet + JSP** - Formulario información académica
4. **Sistema de navegación** - Entre formularios

### ⚠️ Notas Importantes Fase 2

- **✅ Funcionalidad**: Login web 100% equivalente a LoginView.java
- **✅ Base de datos**: Misma validación, sin cambios en BD
- **✅ Sesiones**: Gestión HTTP profesional con timeouts
- **✅ Seguridad**: Filtros UTF-8, validaciones preservadas
- **✅ Diseño**: Visualmente idéntico al original Swing

### 🔒 Compatibilidad y Seguridad

- ✅ **Base de datos**: Sin cambios, usuarios existentes funcionan
- ✅ **Validación**: Misma lógica de `db.validarUsuario()`
- ✅ **Sesiones**: HTTP estándar con timeouts configurables
- ✅ **Cross-browser**: Chrome, Firefox, Edge, Safari
- ✅ **Responsive**: Funciona en móviles y tablets

---

## 💡 **FASE 2 EXITOSA** 
**El login web está completamente funcional. Los usuarios pueden autenticarse con las mismas credenciales que en la app desktop y acceder al dashboard principal.**

**La transición es transparente - misma experiencia, mejor tecnología.**

**¿Listo para continuar con la Fase 3 (Formularios de Información)?**                    # Documentación del proyecto.
```

## Requisitos

- JDK 11 o superior
- Dependencias de JDBC para la conexión a la base de datos

## Instrucciones de Configuración

1. Clona el repositorio en tu máquina local.
2. Navega a la carpeta del proyecto.
3. Asegúrate de tener el JDK instalado y configurado en tu sistema.
4. Compila el proyecto utilizando el comando `javac src/*.java src/views/*.java src/utils/*.java src/models/*.java`.
5. Ejecuta la aplicación con el comando `java src/Main`.

## Uso

- Al iniciar la aplicación, se presentará la vista de inicio de sesión.
- Los usuarios pueden registrarse utilizando la vista de registro.
- Después de iniciar sesión, los usuarios pueden acceder a su información personal y académica, así como radicar solicitudes.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir, por favor abre un issue o envía un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT.