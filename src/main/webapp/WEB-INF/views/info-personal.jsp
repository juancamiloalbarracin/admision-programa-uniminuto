<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Información Personal - App Administrativa</title>
    <style>
        /* REPLICANDO EXACTAMENTE el diseño de InfoPersonalView.java */
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
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .header h1 {
            margin: 0;
            font-size: 24px;
            font-weight: bold;
        }
        
        /* Formulario - Fondo (250, 245, 250) */
        .form-container {
            flex: 1;
            background-color: rgb(250, 245, 250);
            padding: 20px 40px;
            overflow-y: auto;
        }
        
        /* Instrucciones - Fondo (230, 230, 250) con borde azul */
        .instructions {
            background-color: rgb(230, 230, 250);
            border: 1px solid rgb(0, 38, 76);
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-size: 14px;
            line-height: 1.4;
        }
        
        /* Grid de dos columnas como GridBagLayout */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px 16px;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        /* Títulos de sección */
        .section-title {
            grid-column: 1 / -1;
            background-color: rgb(0, 38, 76);
            color: white;
            padding: 10px;
            text-align: center;
            font-weight: bold;
            font-size: 16px;
            margin: 10px 0;
            border-radius: 4px;
        }
        
        /* Campos de formulario */
        .form-group {
            position: relative;
            margin-bottom: 8px;
        }
        
        .form-group.full-width {
            grid-column: 1 / -1;
        }
        
        .form-input, .form-select {
            width: 100%;
            height: 35px;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            background-color: white;
        }
        
        .form-select {
            cursor: pointer;
        }
        
        .form-label {
            position: absolute;
            top: -8px;
            left: 12px;
            background: white;
            padding: 0 4px;
            font-size: 12px;
            color: #333;
            font-weight: normal;
        }
        
        .form-label .required {
            color: red;
            font-weight: bold;
        }
        
        /* Botones */
        .button-panel {
            grid-column: 1 / -1;
            display: flex;
            gap: 20px;
            justify-content: center;
            margin-top: 30px;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 24px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            min-width: 200px;
            height: 45px;
        }
        
        .btn-primary {
            background-color: rgb(0, 38, 76);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: rgb(0, 30, 60);
        }
        
        .btn-secondary {
            background-color: rgb(220, 220, 220);
            color: black;
        }
        
        .btn-secondary:hover {
            background-color: rgb(200, 200, 200);
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
        
        /* Mensaje de éxito */
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
        
        /* Responsive */
        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            
            .form-container {
                padding: 15px 20px;
            }
            
            .button-panel {
                flex-direction: column;
                align-items: center;
            }
        }
    </style>
</head>
<body>
    <!-- Header - Equivale al header de InfoPersonalView -->
    <div class="header">
        <h1>Información Personal</h1>
    </div>
    
    <!-- Contenedor del formulario -->
    <div class="form-container">
        <!-- Mostrar mensajes de error -->
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <!-- Instrucciones - Equivale al JTextArea de instrucciones -->
        <div class="instructions">
            Ingrese su información en cada una de las siguientes secciones. Los campos con un asterisco (*) rojo son obligatorios 
            y deben completarse para enviar la solicitud. Pueden ser requeridas preguntas adicionales en base a sus respuestas a una pregunta anterior.
        </div>
        
        <!-- Formulario - Equivale al formPanel con GridBagLayout -->
        <form method="post" action="info-personal" class="form-grid">
            
            <!-- DATOS PERSONALES BÁSICOS -->
            <div class="form-group">
                <input type="text" name="primerNombre" class="form-input" value="${primerNombre}" required placeholder=" ">
                <label class="form-label">Primer nombre <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="segundoNombre" class="form-input" value="${segundoNombre}" required placeholder=" ">
                <label class="form-label">Segundo nombre <span class="required">*</span></label>
            </div>
            
            <div class="form-group full-width">
                <input type="text" name="apellidos" class="form-input" value="${apellidos}" required placeholder=" ">
                <label class="form-label">Apellidos <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <select name="sexoBiologico" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Masculino" ${sexoBiologico == 'Masculino' ? 'selected' : ''}>Masculino</option>
                    <option value="Femenino" ${sexoBiologico == 'Femenino' ? 'selected' : ''}>Femenino</option>
                </select>
                <label class="form-label">Sexo Biológico <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <select name="grupoSanguineo" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="A+" ${grupoSanguineo == 'A+' ? 'selected' : ''}>A+</option>
                    <option value="A-" ${grupoSanguineo == 'A-' ? 'selected' : ''}>A-</option>
                    <option value="B+" ${grupoSanguineo == 'B+' ? 'selected' : ''}>B+</option>
                    <option value="B-" ${grupoSanguineo == 'B-' ? 'selected' : ''}>B-</option>
                    <option value="AB+" ${grupoSanguineo == 'AB+' ? 'selected' : ''}>AB+</option>
                    <option value="AB-" ${grupoSanguineo == 'AB-' ? 'selected' : ''}>AB-</option>
                    <option value="O+" ${grupoSanguineo == 'O+' ? 'selected' : ''}>O+</option>
                    <option value="O-" ${grupoSanguineo == 'O-' ? 'selected' : ''}>O-</option>
                </select>
                <label class="form-label">Grupo Sanguíneo RH <span class="required">*</span></label>
            </div>
            
            <!-- DOCUMENTO DE IDENTIDAD -->
            <div class="section-title">DOCUMENTO DE IDENTIDAD</div>
            
            <div class="form-group">
                <select name="tipoDocumento" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Cédula de Ciudadanía" ${tipoDocumento == 'Cédula de Ciudadanía' ? 'selected' : ''}>Cédula de Ciudadanía</option>
                    <option value="Tarjeta de Identidad" ${tipoDocumento == 'Tarjeta de Identidad' ? 'selected' : ''}>Tarjeta de Identidad</option>
                    <option value="Cédula de Extranjería" ${tipoDocumento == 'Cédula de Extranjería' ? 'selected' : ''}>Cédula de Extranjería</option>
                    <option value="Pasaporte" ${tipoDocumento == 'Pasaporte' ? 'selected' : ''}>Pasaporte</option>
                </select>
                <label class="form-label">Tipo de documento <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="numeroDocumento" class="form-input" value="${numeroDocumento}" required placeholder=" ">
                <label class="form-label">Número de documento <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="fechaExpedicion" class="form-input" value="${fechaExpedicion}" required placeholder="DD/MM/AAAA">
                <label class="form-label">Fecha de expedición (DD/MM/AAAA) <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="fechaNacimiento" class="form-input" value="${fechaNacimiento}" required placeholder="DD/MM/AAAA">
                <label class="form-label">Fecha de nacimiento (DD/MM/AAAA) <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="paisNacimiento" class="form-input" value="${paisNacimiento}" required placeholder=" ">
                <label class="form-label">País de nacimiento <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="ciudadNacimiento" class="form-input" value="${ciudadNacimiento}" required placeholder=" ">
                <label class="form-label">Ciudad de nacimiento <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="tel" name="telefonoCelular" class="form-input" value="${telefonoCelular}" required placeholder=" ">
                <label class="form-label">Teléfono celular <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="email" name="correoElectronico" class="form-input" value="${correoElectronico}" required placeholder=" ">
                <label class="form-label">Correo electrónico <span class="required">*</span></label>
            </div>
            
            <!-- DIRECCIÓN -->
            <div class="section-title">DIRECCIÓN</div>
            
            <div class="form-group">
                <select name="tipoDireccion" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Casa" ${tipoDireccion == 'Casa' ? 'selected' : ''}>Casa</option>
                    <option value="Apartamento" ${tipoDireccion == 'Apartamento' ? 'selected' : ''}>Apartamento</option>
                    <option value="Finca" ${tipoDireccion == 'Finca' ? 'selected' : ''}>Finca</option>
                    <option value="Oficina" ${tipoDireccion == 'Oficina' ? 'selected' : ''}>Oficina</option>
                    <option value="Otro" ${tipoDireccion == 'Otro' ? 'selected' : ''}>Otro</option>
                </select>
                <label class="form-label">Tipo de dirección <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="direccion" class="form-input" value="${direccion}" required placeholder=" ">
                <label class="form-label">Dirección <span class="required">*</span></label>
            </div>
            
            <div class="form-group full-width">
                <input type="text" name="casaAptoBarrio" class="form-input" value="${casaAptoBarrio}" required placeholder=" ">
                <label class="form-label">Casa, apartamento o barrio <span class="required">*</span></label>
            </div>
            
            <!-- INFORMACIÓN ADICIONAL -->
            <div class="section-title">INFORMACIÓN ADICIONAL</div>
            
            <div class="form-group">
                <select name="tieneSisben" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Sí" ${tieneSisben == 'Sí' ? 'selected' : ''}>Sí</option>
                    <option value="No" ${tieneSisben == 'No' ? 'selected' : ''}>No</option>
                </select>
                <label class="form-label">¿Tiene Sisben? <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <select name="tieneEps" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Sí" ${tieneEps == 'Sí' ? 'selected' : ''}>Sí</option>
                    <option value="No" ${tieneEps == 'No' ? 'selected' : ''}>No</option>
                </select>
                <label class="form-label">¿Tiene EPS? <span class="required">*</span></label>
            </div>
            
            <div class="form-group full-width">
                <select name="tieneDiscapacidad" class="form-select" required>
                    <option value="">Seleccione...</option>
                    <option value="Sí" ${tieneDiscapacidad == 'Sí' ? 'selected' : ''}>Sí</option>
                    <option value="No" ${tieneDiscapacidad == 'No' ? 'selected' : ''}>No</option>
                </select>
                <label class="form-label">¿Tiene discapacidad? <span class="required">*</span></label>
            </div>
            
            <!-- DATOS ACUDIENTE -->
            <div class="section-title">DATOS ACUDIENTE</div>
            
            <div class="form-group">
                <input type="text" name="primerNombreAcudiente" class="form-input" value="${primerNombreAcudiente}" required placeholder=" ">
                <label class="form-label">Primer nombre <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="text" name="segundoNombreAcudiente" class="form-input" value="${segundoNombreAcudiente}" required placeholder=" ">
                <label class="form-label">Segundo nombre <span class="required">*</span></label>
            </div>
            
            <div class="form-group full-width">
                <input type="text" name="apellidosAcudiente" class="form-input" value="${apellidosAcudiente}" required placeholder=" ">
                <label class="form-label">Apellidos <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="tel" name="telefonoAcudiente" class="form-input" value="${telefonoAcudiente}" required placeholder=" ">
                <label class="form-label">Teléfono celular <span class="required">*</span></label>
            </div>
            
            <div class="form-group">
                <input type="email" name="correoAcudiente" class="form-input" value="${correoAcudiente}" required placeholder=" ">
                <label class="form-label">Correo Acudiente <span class="required">*</span></label>
            </div>
            
            <!-- Botones - Equivale al buttonPanel de InfoPersonalView -->
            <div class="button-panel">
                <button type="submit" class="btn btn-primary">Guardar Solicitud</button>
                <a href="main" class="btn btn-secondary" style="text-decoration: none; display: inline-flex; align-items: center; justify-content: center;">Volver a Página Inicio</a>
                <c:if test="${editMode}">
                    <button type="button" class="btn btn-primary" onclick="verInformacion()">Ver Información</button>
                </c:if>
            </div>
        </form>
    </div>
    
    <script>
        // JavaScript para mejorar la experiencia del usuario
        document.addEventListener('DOMContentLoaded', function() {
            // Focus automático en el primer campo
            const firstInput = document.querySelector('input[name="primerNombre"]');
            if (firstInput) {
                firstInput.focus();
            }
            
            // Validación de fechas en formato DD/MM/AAAA
            const fechaInputs = document.querySelectorAll('input[placeholder="DD/MM/AAAA"]');
            fechaInputs.forEach(input => {
                input.addEventListener('input', function() {
                    let value = this.value.replace(/\D/g, ''); // Solo números
                    if (value.length >= 2) {
                        value = value.substring(0, 2) + '/' + value.substring(2);
                    }
                    if (value.length >= 5) {
                        value = value.substring(0, 5) + '/' + value.substring(5, 9);
                    }
                    this.value = value;
                });
            });
        });
        
        function verInformacion() {
            // Mostrar información guardada via AJAX
            fetch('info-personal?action=ver', {
                method: 'GET',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    let info = data.info;
                    let mensaje = '=== INFORMACIÓN PERSONAL GUARDADA ===\n\n';
                    
                    mensaje += 'Nombre: ' + (info.primerNombre || '') + ' ' + (info.segundoNombre || '') + '\n';
                    mensaje += 'Apellidos: ' + (info.apellidos || '') + '\n';
                    mensaje += 'Sexo Biológico: ' + (info.sexoBiologico || '') + '\n';
                    mensaje += 'Grupo Sanguíneo: ' + (info.grupoSanguineo || '') + '\n';
                    mensaje += 'Documento: ' + (info.tipoDocumento || '') + ' - ' + (info.numeroDocumento || '') + '\n';
                    mensaje += 'Fecha Expedición: ' + (info.fechaExpedicionDocumento || '') + '\n';
                    mensaje += 'Nacimiento: ' + (info.ciudadNacimiento || '') + ', ' + (info.paisNacimiento || '') + '\n';
                    mensaje += 'Fecha Nacimiento: ' + (info.fechaNacimiento || '') + '\n';
                    mensaje += 'Teléfono: ' + (info.telefonoCelular || '') + '\n';
                    mensaje += 'Correo: ' + (info.correoElectronico || '') + '\n';
                    mensaje += 'Dirección: ' + (info.direccion || '') + '\n';
                    mensaje += 'Casa/Apto/Barrio: ' + (info.casaAptoBarrio || '') + '\n';
                    mensaje += 'SISBEN: ' + (info.tieneSisben || '') + '\n';
                    mensaje += 'EPS: ' + (info.tieneEps || '') + '\n';
                    mensaje += 'Discapacidad: ' + (info.tieneDiscapacidad || '') + '\n';
                    
                    if (info.primerNombreAcudiente) {
                        mensaje += '\n=== INFORMACIÓN DEL ACUDIENTE ===\n';
                        mensaje += 'Nombre: ' + (info.primerNombreAcudiente || '') + ' ' + (info.segundoNombreAcudiente || '') + '\n';
                        mensaje += 'Apellidos: ' + (info.apellidosAcudiente || '') + '\n';
                        mensaje += 'Teléfono: ' + (info.telefonoAcudiente || '') + '\n';
                        mensaje += 'Correo: ' + (info.correoAcudiente || '') + '\n';
                    }
                    
                    alert(mensaje);
                } else {
                    alert('No se encontró información personal guardada para este usuario.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al cargar la información. Intente nuevamente.');
            });
        }
    </script>
</body>
</html>
