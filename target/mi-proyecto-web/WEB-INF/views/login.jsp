<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>App administrativa - Iniciar Sesión</title>
    <style>
        /* REPLICANDO EXACTAMENTE el diseño de LoginView.java */
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: white;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        /* Header - Color azul oscuro (0, 38, 76) */
        .header {
            background-color: rgb(0, 38, 76);
            color: white;
            text-align: center;
            padding: 15px 0;
        }
        
        .header h1 {
            margin: 0;
            font-size: 20px;
            font-weight: bold;
        }
        
        /* Centro - Equivale al panel center de Swing */
        .center {
            flex: 1;
            background-color: white;
            padding: 20px 50px;
            display: flex;
            flex-direction: column;
            align-items: center;
            max-width: 400px;
            margin: 0 auto;
            width: 100%;
            box-sizing: border-box;
        }
        
        /* Logo */
        .logo {
            width: 100px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 15px;
            background-color: transparent;
        }
        
        .logo img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }
        
        /* Título "Iniciar Sesión" */
        .login-title {
            font-size: 18px;
            font-weight: bold;
            color: rgb(0, 38, 76);
            margin: 20px 0;
        }
        
        /* Formulario */
        .form-container {
            width: 100%;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        
        /* Campos de texto - Replicando TitledBorder de Swing */
        .form-group {
            position: relative;
            margin-bottom: 10px;
        }
        
        .form-input {
            width: 100%;
            height: 35px;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        
        .form-label {
            position: absolute;
            top: -8px;
            left: 12px;
            background: white;
            padding: 0 4px;
            font-size: 12px;
            color: #666;
        }
        
        /* Checkboxes */
        .checkbox-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
            margin: 10px 0 15px 0;
        }
        
        .checkbox-item {
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .checkbox-item input[type="checkbox"] {
            margin: 0;
        }
        
        .checkbox-item label {
            font-size: 14px;
            color: #333;
            cursor: pointer;
        }
        
        /* Botones - Replicando el estilo Swing */
        .button-panel {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 10px;
            margin-top: 15px;
            width: 100%;
        }
        
        .btn {
            background-color: rgb(0, 38, 76);
            color: white;
            border: none;
            padding: 12px 24px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            border-radius: 4px;
            width: 200px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn:hover {
            background-color: rgb(0, 30, 60);
        }
        
        .btn:active {
            background-color: rgb(0, 25, 50);
        }
        
        /* Enlace "¿Olvidó su contraseña?" */
        .forgot-password {
            color: #0066cc;
            font-size: 12px;
            text-decoration: none;
            margin-top: 10px;
        }
        
        .forgot-password:hover {
            text-decoration: underline;
        }
        
        /* Footer - Color de fondo (240, 235, 245) */
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
        
        /* Mensajes de error */
        .error-message {
            background-color: #ffebee;
            color: #c62828;
            border: 1px solid #ef5350;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }
        
        /* Mensaje de bienvenida */
        .success-message {
            background-color: #e8f5e8;
            color: #2e7d32;
            border: 1px solid #4caf50;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <!-- Header - Equivale al header de LoginView -->
    <div class="header">
        <h1>App administrativa</h1>
    </div>
    
    <!-- Centro - Equivale al panel center de LoginView -->
    <div class="center">
        <!-- Logo - Imagen real de UNIMINUTO -->
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo-uniminuto.png" 
                 alt="Logo UNIMINUTO" 
                 style="max-width: 100px; max-height: 60px; object-fit: contain;">
        </div>
        
        <!-- Título - Equivale al JLabel "Iniciar Sesión" -->
        <h2 class="login-title">Iniciar Sesión</h2>
        
        <!-- Mostrar mensajes de error (equivale a JOptionPane.showMessageDialog) -->
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <!-- Mostrar mensaje de bienvenida si viene de registro exitoso -->
        <c:if test="${param.registroExitoso == 'true'}">
            <div class="success-message">
                ¡Usuario registrado correctamente! Ahora puede iniciar sesión con sus credenciales.
            </div>
        </c:if>
        
        <!-- Mostrar mensaje de bienvenida si viene de registro exitoso (parámetro alternativo) -->
        <c:if test="${param.registered == 'true'}">
            <div class="success-message">
                ¡Registro exitoso! Ahora puede iniciar sesión.
            </div>
        </c:if>
        
        <!-- Mostrar mensaje de logout exitoso -->
        <c:if test="${param.logout == 'true'}">
            <div class="success-message">
                Sesión cerrada exitosamente. ¡Hasta pronto!
            </div>
        </c:if>
        
        <!-- Formulario - Equivale a los campos de LoginView -->
        <form method="post" action="login" class="form-container">
            <!-- Campo email - Equivale a emailField con TitledBorder "Correo-e" -->
            <div class="form-group">
                <input type="email" 
                       name="email" 
                       id="email" 
                       class="form-input" 
                       value="${email}" 
                       required 
                       placeholder=" ">
                <label for="email" class="form-label">Correo-e</label>
            </div>
            
            <!-- Campo password - Equivale a passwordField con TitledBorder "Contraseña" -->
            <div class="form-group">
                <input type="password" 
                       name="password" 
                       id="password" 
                       class="form-input" 
                       required 
                       placeholder=" ">
                <label for="password" class="form-label">Contraseña</label>
            </div>
            
            <!-- Checkboxes - Equivale a los JCheckBox de LoginView -->
            <div class="checkbox-group">
                <div class="checkbox-item">
                    <input type="checkbox" name="recordarme" id="recordarme">
                    <label for="recordarme">Recordarme</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" name="pcPublico" id="pcPublico">
                    <label for="pcPublico">Conectado desde un PC público</label>
                </div>
            </div>
            
            <!-- Botones - Equivale al buttonPanel de LoginView -->
            <div class="button-panel">
                <!-- Botón login - Equivale al loginBtn con ActionListener -->
                <button type="submit" class="btn">Iniciar Sesión</button>
                
                <!-- Botón registrarse - Equivale al registerBtn -->
                <a href="signup" class="btn" style="text-decoration: none;">Registrarse</a>
                
                <!-- Enlace contraseña - Equivale al JLabel "¿Olvidó su contraseña?" -->
                <a href="#" class="forgot-password">¿Olvidó su contraseña?</a>
            </div>
        </form>
    </div>
    
    <!-- Footer - Equivale al panel footer de LoginView -->
    <div class="footer">
        <p>© 2025 Sistema de Inscripción. Todos los derechos reservados.</p>
        <p>Corporación Universitaria Minuto de Dios – UNIMINUTO.</p>
        <p>Línea nacional gratuita: 01 8000 11 93 90. Bogotá: 593300</p>
    </div>
    
    <script>
        // JavaScript para mejorar la experiencia del usuario
        document.addEventListener('DOMContentLoaded', function() {
            // Focus automático en el campo email
            document.getElementById('email').focus();
            
            // Validación de PC público - reduce tiempo de sesión
            const pcPublicoCheckbox = document.getElementById('pcPublico');
            const recordarmeCheckbox = document.getElementById('recordarme');
            
            pcPublicoCheckbox.addEventListener('change', function() {
                if (this.checked) {
                    recordarmeCheckbox.checked = false;
                    recordarmeCheckbox.disabled = true;
                } else {
                    recordarmeCheckbox.disabled = false;
                }
            });
        });
    </script>
</body>
</html>
