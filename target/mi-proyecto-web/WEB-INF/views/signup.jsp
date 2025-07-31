<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Sistema UDC</title>
    <style>
        /* ESTILOS PRESERVADOS de SignUpView.java visual */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Arial, sans-serif;
            background-color: #faf5fa;
            color: #333;
            line-height: 1.4;
        }
        
        .header {
            background: linear-gradient(135deg, #00264c, #003366);
            color: white;
            padding: 20px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header h1 {
            font-size: 28px;
            margin-bottom: 8px;
        }
        
        .header p {
            font-size: 16px;
            opacity: 0.9;
        }
        
        .container {
            max-width: 600px;
            margin: 30px auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .nav-buttons {
            background: #ecf0f1;
            padding: 15px 20px;
            border-bottom: 1px solid #bdc3c7;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        
        .nav-btn {
            padding: 8px 16px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        
        .nav-btn:hover {
            background: #2980b9;
        }
        
        .nav-btn.current {
            background: #e74c3c;
        }
        
        .form-container {
            padding: 30px;
            max-height: 70vh;
            overflow-y: auto;
        }
        
        /* Panel de información legal (REPLICACIÓN de SignUpView legalText) */
        .legal-panel {
            background: #faf5fa;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
            font-size: 12px;
            line-height: 1.4;
            text-align: justify;
        }
        
        .legal-panel h3 {
            color: #00264c;
            margin-bottom: 10px;
            font-size: 14px;
        }
        
        .form-field {
            margin-bottom: 15px;
        }
        
        .form-field label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #00264c;
            font-size: 14px;
        }
        
        .required {
            color: #e74c3c;
        }
        
        .form-field input,
        .form-field select {
            width: 100%;
            padding: 10px;
            border: 2px solid #bdc3c7;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
            background: white;
            transition: border-color 0.3s;
        }
        
        .form-field input:focus,
        .form-field select:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 8px rgba(52, 152, 219, 0.3);
        }
        
        .form-field select {
            cursor: pointer;
        }
        
        /* Password help text */
        .password-help {
            font-size: 11px;
            color: #c0392b;
            margin-top: 5px;
            font-style: italic;
        }
        
        .error-message {
            background: #fee;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        
        .success-message {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        
        .form-actions {
            margin-top: 30px;
            text-align: center;
            padding-top: 20px;
            border-top: 1px solid #ecf0f1;
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            min-width: 120px;
        }
        
        .btn-primary {
            background: #00264c;
            color: white;
        }
        
        .btn-primary:hover:not(:disabled) {
            background: #003366;
            transform: translateY(-2px);
        }
        
        .btn-primary:disabled {
            background: #ccc;
            color: #666;
            cursor: not-allowed;
            transform: none;
        }
        
        .btn-secondary {
            background: #95a5a6;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #7f8c8d;
        }
        
        /* Estados de validación en tiempo real */
        .field-error {
            border-color: #e74c3c !important;
            background-color: #fdf2f2;
        }
        
        .field-success {
            border-color: #27ae60 !important;
            background-color: #f2f9f2;
        }
        
        .field-warning {
            border-color: #f39c12 !important;
            background-color: #fef9e7;
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                margin: 15px;
                border-radius: 8px;
            }
            
            .form-container {
                padding: 20px;
                max-height: 80vh;
            }
            
            .nav-buttons {
                flex-direction: column;
            }
            
            .nav-btn {
                text-align: center;
            }
            
            .form-actions {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
            }
        }
        
        /* Animaciones */
        .fade-in {
            animation: fadeIn 0.5s ease-in;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        /* Validación visual */
        .validation-message {
            font-size: 12px;
            margin-top: 5px;
            padding: 5px;
            border-radius: 3px;
        }
        
        .validation-error {
            background: #fee;
            color: #c53030;
            border: 1px solid #feb2b2;
        }
        
        .validation-success {
            background: #f0fff4;
            color: #22543d;
            border: 1px solid #9ae6b4;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Registro de Usuario</h1>
        <p>Sistema de inscripción - UNIMINUTO</p>
    </div>

    <div class="container">
        <div class="nav-buttons">
            <a href="login" class="nav-btn">Iniciar Sesión</a>
            <a href="signup" class="nav-btn current">Registrarse</a>
        </div>

        <div class="form-container fade-in">
            <!-- Mensajes de error -->
            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>

            <!-- PANEL LEGAL (PRESERVADO de SignUpView.java legalText) -->
            <div class="legal-panel">
                <h3>📋 Tratamiento de Datos Personales - UNIMINUTO</h3>
                <p>En UNIMINUTO estamos comprometidos con el tratamiento confidencial de tus datos personales. Al registrarte en nuestro sistema, autorizas el tratamiento de la información que suministres de acuerdo con nuestra política de protección de datos personales, la cual puedes consultar en nuestra página web.</p>
                
                <p>Tus datos serán utilizados exclusivamente para los fines académicos y administrativos relacionados con tu proceso de inscripción y matrícula en los programas ofrecidos por la Corporación Universitaria Minuto de Dios - UNIMINUTO.</p>
                
                <p>Garantizamos la seguridad y confidencialidad de tu información personal conforme a las disposiciones legales vigentes en materia de protección de datos personales.</p>
            </div>

            <!-- FORMULARIO DE REGISTRO (MIGRADO de SignUpView.java) -->
            <form method="POST" action="signup" novalidate>
                <div class="form-field">
                    <label for="autorizacion">Autorizo el tratamiento de datos <span class="required">*</span></label>
                    <select id="autorizacion" name="autorizacion" required>
                        <option value="">Seleccione...</option>
                        <option value="Sí" ${autorizacion == 'Sí' ? 'selected' : ''}>Sí</option>
                        <option value="No" ${autorizacion == 'No' ? 'selected' : ''}>No</option>
                    </select>
                </div>

                <div class="form-field">
                    <label for="primerNombre">Primer Nombre <span class="required">*</span></label>
                    <input type="text" id="primerNombre" name="primerNombre" 
                           value="${primerNombre}" placeholder="Ingrese su primer nombre" required>
                </div>

                <div class="form-field">
                    <label for="segundoNombre">Segundo Nombre <span class="required">*</span></label>
                    <input type="text" id="segundoNombre" name="segundoNombre" 
                           value="${segundoNombre}" placeholder="Ingrese su segundo nombre" required>
                </div>

                <div class="form-field">
                    <label for="apellidos">Apellidos <span class="required">*</span></label>
                    <input type="text" id="apellidos" name="apellidos" 
                           value="${apellidos}" placeholder="Ingrese sus apellidos completos" required>
                </div>

                <div class="form-field">
                    <label for="correo">Correo Electrónico <span class="required">*</span></label>
                    <input type="email" id="correo" name="correo" 
                           value="${correo}" placeholder="usuario@ejemplo.com" required>
                    <div id="correoValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-field">
                    <label for="confirmarCorreo">Confirmar Correo Electrónico <span class="required">*</span></label>
                    <input type="email" id="confirmarCorreo" name="confirmarCorreo" 
                           value="${confirmarCorreo}" placeholder="Repita su correo electrónico" required>
                    <div id="confirmarCorreoValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-field">
                    <label for="telefonoMovil">Teléfono Móvil <span class="required">*</span></label>
                    <input type="tel" id="telefonoMovil" name="telefonoMovil" 
                           value="${telefonoMovil}" placeholder="3001234567 (10 dígitos)" required>
                    <div id="telefonoValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-field">
                    <label for="gradoAcademico">Grado Académico <span class="required">*</span></label>
                    <input type="text" id="gradoAcademico" name="gradoAcademico" 
                           value="${gradoAcademico}" placeholder="Ejemplo: Bachiller, Profesional, etc." required>
                </div>

                <div class="form-field">
                    <label for="sede">Sede <span class="required">*</span></label>
                    <input type="text" id="sede" name="sede" 
                           value="${sede}" placeholder="Ejemplo: Bogotá, Medellín, Virtual, etc." required>
                </div>

                <div class="form-field">
                    <label for="tipoDocumento">Tipo de Documento <span class="required">*</span></label>
                    <select id="tipoDocumento" name="tipoDocumento" required>
                        <option value="">Seleccione...</option>
                        <option value="Cédula de Ciudadanía" ${tipoDocumento == 'Cédula de Ciudadanía' ? 'selected' : ''}>Cédula de Ciudadanía</option>
                        <option value="Tarjeta de Identidad" ${tipoDocumento == 'Tarjeta de Identidad' ? 'selected' : ''}>Tarjeta de Identidad</option>
                        <option value="Cédula de Extranjería" ${tipoDocumento == 'Cédula de Extranjería' ? 'selected' : ''}>Cédula de Extranjería</option>
                        <option value="Pasaporte" ${tipoDocumento == 'Pasaporte' ? 'selected' : ''}>Pasaporte</option>
                    </select>
                </div>

                <div class="form-field">
                    <label for="numeroDocumento">Número de Documento <span class="required">*</span></label>
                    <input type="text" id="numeroDocumento" name="numeroDocumento" 
                           value="${numeroDocumento}" placeholder="Solo números" required>
                    <div id="documentoValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-field">
                    <label for="contrasena">Contraseña <span class="required">*</span></label>
                    <input type="password" id="contrasena" name="contrasena" 
                           placeholder="Ingrese su contraseña" required>
                    <div class="password-help">Mínimo 6 caracteres y 2 símbolos</div>
                    <div id="contrasenaValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-field">
                    <label for="repetirContrasena">Repetir Contraseña <span class="required">*</span></label>
                    <input type="password" id="repetirContrasena" name="repetirContrasena" 
                           placeholder="Repita su contraseña" required>
                    <div id="repetirContrasenaValidation" class="validation-message" style="display: none;"></div>
                </div>

                <div class="form-actions">
                    <a href="login" class="btn btn-secondary">Volver</a>
                    <button type="submit" id="registerBtn" class="btn btn-primary" disabled>Registrarse</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // VALIDACIÓN JS simplificada para debugging
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const registerBtn = document.getElementById('registerBtn');
            
            function validateForm() {
                console.log('Validando formulario...');
                
                // Campos requeridos básicos
                const autorizacion = document.getElementById('autorizacion').value;
                const primerNombre = document.getElementById('primerNombre').value.trim();
                const segundoNombre = document.getElementById('segundoNombre').value.trim();
                const apellidos = document.getElementById('apellidos').value.trim();
                const correo = document.getElementById('correo').value.trim();
                const confirmarCorreo = document.getElementById('confirmarCorreo').value.trim();
                const contrasena = document.getElementById('contrasena').value;
                const repetirContrasena = document.getElementById('repetirContrasena').value;
                
                // Validaciones básicas
                let isValid = true;
                let errors = [];
                
                if (autorizacion !== 'Sí') {
                    errors.push('Debe autorizar el tratamiento de datos');
                    isValid = false;
                }
                
                if (!primerNombre) {
                    errors.push('Primer nombre requerido');
                    isValid = false;
                }
                
                if (!apellidos) {
                    errors.push('Apellidos requeridos');
                    isValid = false;
                }
                
                if (!correo || !confirmarCorreo) {
                    errors.push('Correos requeridos');
                    isValid = false;
                } else if (correo !== confirmarCorreo) {
                    errors.push('Los correos no coinciden');
                    isValid = false;
                }
                
                if (!contrasena || !repetirContrasena) {
                    errors.push('Contraseñas requeridas');
                    isValid = false;
                } else if (contrasena !== repetirContrasena) {
                    errors.push('Las contraseñas no coinciden');
                    isValid = false;
                }
                
                // Validación de contraseña simple
                if (contrasena && contrasena.length < 6) {
                    errors.push('Contraseña debe tener al menos 6 caracteres');
                    isValid = false;
                }
                
                console.log('Errores encontrados:', errors);
                console.log('Formulario válido:', isValid);
                
                // Habilitar/deshabilitar botón
                registerBtn.disabled = !isValid;
                
                if (isValid) {
                    registerBtn.style.backgroundColor = '#00264c';
                    registerBtn.style.color = 'white';
                    registerBtn.style.cursor = 'pointer';
                } else {
                    registerBtn.style.backgroundColor = '#ccc';
                    registerBtn.style.color = '#666';
                    registerBtn.style.cursor = 'not-allowed';
                }
                
                return isValid;
            }
            
            // Event listeners en todos los campos
            const fields = form.querySelectorAll('input, select');
            fields.forEach(field => {
                field.addEventListener('input', validateForm);
                field.addEventListener('change', validateForm);
                field.addEventListener('blur', validateForm);
            });
            
            // Validación inicial
            setTimeout(validateForm, 100);
            
            // Debug: mostrar estado del botón
            setInterval(function() {
                console.log('Estado del botón:', registerBtn.disabled ? 'DESHABILITADO' : 'HABILITADO');
            }, 2000);
        });
    </script>
</body>
</html>
