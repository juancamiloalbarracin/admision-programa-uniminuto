<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>App administrativa - Panel Principal</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        /* Header similar al LoginView */
        .header {
            background-color: rgb(0, 38, 76);
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .header h1 {
            margin: 0;
            font-size: 20px;
            font-weight: bold;
        }
        
        .user-info {
            font-size: 14px;
        }
        
        .logout-btn {
            background-color: rgba(255,255,255,0.2);
            color: white;
            border: 1px solid rgba(255,255,255,0.3);
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
            margin-left: 15px;
        }
        
        .logout-btn:hover {
            background-color: rgba(255,255,255,0.3);
        }
        
        /* Contenido principal */
        .main-content {
            flex: 1;
            padding: 30px;
            max-width: 1200px;
            margin: 0 auto;
            width: 100%;
            box-sizing: border-box;
        }
        
        /* Mensaje de bienvenida */
        .welcome-message {
            background-color: #e8f5e8;
            color: #2e7d32;
            border: 1px solid #4caf50;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 16px;
            font-weight: bold;
        }
        
        /* Dashboard grid */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        
        .dashboard-card {
            background: white;
            border: 2px inset #d0d0d0;
            border-radius: 5px;
            padding: 20px;
            text-align: center;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
        }
        
        .dashboard-card h3 {
            color: rgb(0, 38, 76);
            margin-top: 0;
            font-size: 18px;
        }
        
        .dashboard-card p {
            color: #666;
            margin: 10px 0 15px 0;
        }
        
        .card-btn {
            background-color: rgb(0, 38, 76);
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            width: 80%;
        }
        
        .card-btn:hover {
            background-color: rgb(0, 30, 60);
        }
        
        /* Footer */
        .footer {
            background-color: rgb(240, 235, 245);
            padding: 15px;
            text-align: center;
        }
        
        .footer p {
            margin: 2px 0;
            font-size: 10px;
            color: #333;
        }
        
        /* Información del usuario */
        .user-profile {
            background: white;
            border: 2px inset #d0d0d0;
            border-radius: 5px;
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .user-profile h2 {
            color: rgb(0, 38, 76);
            margin-top: 0;
        }
        
        .user-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 10px;
            margin-top: 15px;
        }
        
        .user-detail {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 4px;
            border-left: 4px solid rgb(0, 38, 76);
        }
        
        .user-detail strong {
            color: rgb(0, 38, 76);
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <h1>App administrativa</h1>
        <div style="display: flex; align-items: center;">
            <div class="user-info">
                Bienvenido, ${userEmail}
            </div>
            <a href="logout" class="logout-btn">Cerrar Sesión</a>
        </div>
    </div>
    
    <!-- Contenido principal -->
    <div class="main-content">
        <!-- Mostrar mensaje de bienvenida después del login -->
        <c:if test="${not empty welcomeMessage}">
            <div class="welcome-message">
                ${welcomeMessage}
            </div>
        </c:if>
        
        <!-- Perfil del usuario -->
        <div class="user-profile">
            <h2>Información del Usuario</h2>
            <div class="user-details">
                <c:if test="${not empty usuario}">
                    <div class="user-detail">
                        <strong>Nombre:</strong> ${usuario.nombre}
                    </div>
                    <div class="user-detail">
                        <strong>Apellido:</strong> ${usuario.apellido}
                    </div>
                    <div class="user-detail">
                        <strong>Usuario:</strong> ${usuario.username}
                    </div>
                    <div class="user-detail">
                        <strong>Email:</strong> ${usuario.email}
                    </div>
                </c:if>
                <c:if test="${empty usuario}">
                    <div class="user-detail">
                        <strong>Email:</strong> ${userEmail}
                    </div>
                </c:if>
            </div>
        </div>
        
        <!-- Dashboard de funcionalidades -->
        <h2 style="color: rgb(0, 38, 76); text-align: center;">Panel de Control</h2>
        
        <div class="dashboard-grid">
            <!-- Información Personal -->
            <div class="dashboard-card">
                <h3>📝 Información Personal</h3>
                <p>Gestiona tu información personal, documentos y datos de contacto</p>
                <a href="info-personal" class="card-btn">Gestionar</a>
            </div>
            
            <!-- Información Académica -->
            <div class="dashboard-card">
                <h3>🎓 Información Académica</h3>
                <p>Administra tu información académica, sede, programa y metodología</p>
                <a href="info-academica" class="card-btn">Gestionar</a>
            </div>
            
            <!-- Radicación -->
            <div class="dashboard-card">
                <h3>📄 Radicación de Solicitudes</h3>
                <p>Radica solicitudes administrativas y trámites universitarios</p>
                <a href="radicar" class="card-btn">Radicar</a>
            </div>
            
            <!-- Perfil -->
            <div class="dashboard-card">
                <h3>⚙️ Mi Perfil</h3>
                <p>Actualiza tu información de usuario y preferencias de cuenta</p>
                <a href="perfil" class="card-btn">Ver Perfil</a>
            </div>
        </div>
    </div>
    
    <!-- Footer -->
    <div class="footer">
        <p>© 2025 Sistema de Inscripción. Todos los derechos reservados.</p>
        <p>Corporación Universitaria Minuto de Dios – UNIMINUTO.</p>
        <p>Línea nacional gratuita: 01 8000 11 93 90. Bogotá: 593300</p>
    </div>
</body>
</html>
