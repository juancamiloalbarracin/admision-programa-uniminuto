<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Radicar Solicitud - Sistema UDC</title>
    <style>
        /* ESTILOS PRESERVADOS de RadicarView.java visual */
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
            max-width: 1000px;
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
            border: none;
            cursor: pointer;
        }
        
        .nav-btn:hover {
            background: #2980b9;
        }
        
        .nav-btn.current {
            background: #e74c3c;
        }
        
        .nav-btn.secondary {
            background: #95a5a6;
        }
        
        .form-container {
            padding: 30px;
        }
        
        /* Panel de información importante (REPLICACIÓN de RadicarView infoArea) */
        .info-panel {
            background: #f0f0f0;
            border: 2px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 25px;
            max-height: 200px;
            overflow-y: auto;
            font-size: 12px;
            line-height: 1.5;
        }
        
        .info-panel h3 {
            color: #00264c;
            margin-bottom: 10px;
            border-bottom: 2px solid #3498db;
            padding-bottom: 5px;
        }
        
        .info-panel p {
            margin-bottom: 10px;
            text-align: justify;
        }
        
        .form-section {
            background: #fafafa;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 25px;
        }
        
        .section-title {
            font-size: 18px;
            font-weight: bold;
            color: #00264c;
            margin-bottom: 15px;
            padding-bottom: 8px;
            border-bottom: 2px solid #3498db;
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
        .form-field select,
        .form-field textarea {
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
        .form-field select:focus,
        .form-field textarea:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 8px rgba(52, 152, 219, 0.3);
        }
        
        .form-field select {
            cursor: pointer;
        }
        
        .form-field textarea {
            resize: vertical;
            min-height: 120px;
            font-family: Arial, sans-serif;
        }
        
        /* Checkbox de términos */
        .terms-section {
            background: #f8f9fa;
            border: 2px solid #007bff;
            border-radius: 8px;
            padding: 15px;
            margin: 20px 0;
        }
        
        .terms-checkbox {
            display: flex;
            align-items: flex-start;
            gap: 10px;
        }
        
        .terms-checkbox input[type="checkbox"] {
            width: auto;
            margin-top: 2px;
            transform: scale(1.2);
        }
        
        .terms-checkbox label {
            font-size: 14px;
            line-height: 1.4;
            cursor: pointer;
        }
        
        /* Documentos adjuntos */
        .attach-section {
            background: #f9f9f9;
            border: 1px dashed #bbb;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            margin-bottom: 20px;
        }
        
        .attach-btn {
            background: #00264c;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }
        
        .attach-btn:hover {
            background: #003366;
        }
        
        .file-info {
            margin-top: 10px;
            font-style: italic;
            color: #666;
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
        }
        
        .btn-primary {
            background: #00264c;
            color: white;
        }
        
        .btn-primary:hover {
            background: #003366;
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: #95a5a6;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #7f8c8d;
        }
        
        .btn-info {
            background: #17a2b8;
            color: white;
        }
        
        .btn-info:hover {
            background: #138496;
        }
        
        /* Panel de solicitudes */
        .solicitudes-panel {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .solicitud-item {
            background: white;
            border: 1px solid #e9ecef;
            border-radius: 5px;
            padding: 12px;
            margin-bottom: 10px;
            font-family: monospace;
            font-size: 13px;
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                margin: 15px;
                border-radius: 8px;
            }
            
            .form-container {
                padding: 20px;
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
        
        /* Estados de validación */
        .field-error {
            border-color: #e74c3c !important;
            background-color: #fdf2f2;
        }
        
        .field-success {
            border-color: #27ae60 !important;
            background-color: #f2f9f2;
        }
        
        /* Animaciones */
        .fade-in {
            animation: fadeIn 0.5s ease-in;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Radicar Solicitud</h1>
        <p>Sistema de radicación de solicitudes administrativas</p>
    </div>

    <div class="container">
        <div class="nav-buttons">
            <a href="main" class="nav-btn">Inicio</a>
            <a href="info-personal" class="nav-btn">Información Personal</a>
            <a href="info-academica" class="nav-btn">Información Académica</a>
            <a href="radicar" class="nav-btn current">Radicar Solicitud</a>
            <button onclick="verMisSolicitudes()" class="nav-btn secondary">Ver Mis Solicitudes</button>
            <a href="logout" class="nav-btn secondary">Cerrar Sesión</a>
        </div>

        <div class="form-container fade-in">
            <!-- Mensajes de error/éxito -->
            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>
            
            <c:if test="${param.solicitudRadicada == 'true'}">
                <div class="success-message">
                    Solicitud radicada exitosamente. Puede consultar el estado en "Ver Mis Solicitudes".
                </div>
            </c:if>

            <!-- Panel de solicitudes (si se solicita) -->
            <c:if test="${showSolicitudes}">
                <div class="solicitudes-panel">
                    <div class="section-title">📋 Mis Solicitudes Radicadas</div>
                    <c:forEach var="solicitud" items="${solicitudes}">
                        <div class="solicitud-item">${solicitud}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- PANEL DE INFORMACIÓN IMPORTANTE (PRESERVADO de RadicarView.java infoArea) -->
            <div class="info-panel">
                <h3>📋 Información Importante y Términos de Solicitud</h3>
                <p>Por favor tenga en cuenta que, una vez enviada la presente solicitud, la misma no podrá ser modificada. Por lo tanto, es importante que la lea con detenimiento y confirme la veracidad de la información antes de su envío.</p>
                
                <p>Una vez se envíe con éxito, recibirá un mensaje de confirmación, con la correspondiente actualización del estatus del envío de su solicitud en la pestaña "mis solicitudes".</p>
                
                <p>Recuerde que para que su solicitud de admisión sea tramitada, debe adjuntar los documentos requeridos de manera completa y los mismos deben ser legibles.</p>
                
                <h3>--- Certificación ---</h3>
                <p><strong>Antes de enviar su solicitud usted manifiesta:</strong></p>
                
                <p>Certifico que toda la información suministrada en el presente formulario es correcta y veraz; y entiendo que, una vez enviada la solicitud, no es posible modificar la información diligenciada.</p>
                
                <p>Certifico y entiendo que UNIMINUTO no está obligado a aceptar la solicitud con el simple registro de mis datos en el presente formulario y que la admisión está condicionada a la verificación del cumplimiento de los requisitos legales e institucionales. En consecuencia, autorizo a UNIMINUTO a verificar toda la información.</p>
                
                <p>Entiendo y acepto que, en caso de que la información aportada no corresponda a la realidad, UNIMINUTO revocará la admisión e iniciará las investigaciones disciplinarias o legales que apliquen.</p>
                
                <p>Con la aceptación de la presente solicitud, declaro de manera libre y voluntaria que me encuentro afiliado al Sistema General de Seguridad Social en Salud y me comprometo a mantener vigente mi afiliación durante la duración de todo el periodo académico al cual me matriculo.</p>
                
                <h3>--- Contrato de Matrícula * ---</h3>
                <p>Declaro en forma libre y voluntaria que he leído, he comprendido y por lo tanto acepto todas y cada una de las estipulaciones contenidas en el presente contrato matrícula y los demás documentos que forman parte integral del mismo, para el periodo académico al cual me estoy postulando. El presente contrato de matricula se entiende firmado con la presente aceptación. Conozco que UNIMINUTO no establece fecha límite para el cierre de los procesos de admisión, y que las inscripciones están sujetas a la disponibilidad de cupos.</p>
            </div>

            <!-- FORMULARIO DE RADICACIÓN (MIGRADO de RadicarView.java) -->
            <form method="POST" action="radicar" novalidate>
                <div class="form-section">
                    <div class="section-title">Datos de la Solicitud</div>
                    
                    <div class="form-field">
                        <label for="tipoSolicitud">Tipo de Solicitud <span class="required">*</span></label>
                        <select id="tipoSolicitud" name="tipoSolicitud" required>
                            <option value="">Seleccione tipo de solicitud...</option>
                            <option value="Certificado de Estudios" ${tipoSolicitud == 'Certificado de Estudios' ? 'selected' : ''}>Certificado de Estudios</option>
                            <option value="Constancia de Matrícula" ${tipoSolicitud == 'Constancia de Matrícula' ? 'selected' : ''}>Constancia de Matrícula</option>
                            <option value="Solicitud de Traslado" ${tipoSolicitud == 'Solicitud de Traslado' ? 'selected' : ''}>Solicitud de Traslado</option>
                            <option value="Solicitud de Reingreso" ${tipoSolicitud == 'Solicitud de Reingreso' ? 'selected' : ''}>Solicitud de Reingreso</option>
                            <option value="Certificado de Notas" ${tipoSolicitud == 'Certificado de Notas' ? 'selected' : ''}>Certificado de Notas</option>
                            <option value="Solicitud de Grado" ${tipoSolicitud == 'Solicitud de Grado' ? 'selected' : ''}>Solicitud de Grado</option>
                            <option value="Cambio de Programa" ${tipoSolicitud == 'Cambio de Programa' ? 'selected' : ''}>Cambio de Programa</option>
                            <option value="Solicitud de Homologación" ${tipoSolicitud == 'Solicitud de Homologación' ? 'selected' : ''}>Solicitud de Homologación</option>
                            <option value="Retiro Temporal" ${tipoSolicitud == 'Retiro Temporal' ? 'selected' : ''}>Retiro Temporal</option>
                            <option value="Otros" ${tipoSolicitud == 'Otros' ? 'selected' : ''}>Otros</option>
                        </select>
                    </div>

                    <div class="form-field">
                        <label for="asunto">Asunto <span class="required">*</span></label>
                        <input type="text" id="asunto" name="asunto" 
                               value="${asunto}" placeholder="Resuma brevemente su solicitud" required>
                    </div>

                    <div class="form-field">
                        <label for="descripcion">Descripción/Justificación <span class="required">*</span></label>
                        <textarea id="descripcion" name="descripcion" 
                                  placeholder="Detalle su solicitud, incluyendo justificación y cualquier información relevante" required>${descripcion}</textarea>
                    </div>
                </div>

                <div class="form-section">
                    <div class="section-title">Documentos Adjuntos</div>
                    
                    <div class="attach-section">
                        <div>📎 <strong>Documentos de Soporte</strong></div>
                        <p>Adjunte los documentos que soporten su solicitud (PDF, DOC, JPG)</p>
                        <input type="file" id="documentos" name="documentos" multiple 
                               accept=".pdf,.doc,.docx,.jpg,.jpeg,.png" style="display: none;">
                        <button type="button" class="attach-btn" onclick="document.getElementById('documentos').click();">
                            Seleccionar Archivos
                        </button>
                        <div id="fileInfo" class="file-info">Ningún archivo seleccionado</div>
                    </div>
                </div>

                <div class="form-section">
                    <div class="section-title">Información de Contacto</div>
                    
                    <div class="form-field">
                        <label for="telefonoContacto">Teléfono de Contacto</label>
                        <input type="tel" id="telefonoContacto" name="telefonoContacto" 
                               value="${telefonoContacto}" placeholder="Número telefónico para contacto">
                    </div>

                    <div class="form-field">
                        <label for="emailNotificacion">Email de Notificación</label>
                        <input type="email" id="emailNotificacion" name="emailNotificacion" 
                               value="${emailNotificacion}" placeholder="Email donde recibirá notificaciones">
                    </div>
                </div>

                <!-- TÉRMINOS Y CONDICIONES (PRESERVADO de RadicarView checkbox) -->
                <div class="terms-section">
                    <div class="terms-checkbox">
                        <input type="checkbox" id="aceptaTerminos" name="aceptaTerminos" required>
                        <label for="aceptaTerminos">
                            <strong>Acepto los términos y condiciones para el procesamiento de solicitudes</strong>
                            <br><small>He leído y acepto toda la información, certificaciones y términos descritos en este formulario. Entiendo que la información proporcionada es veraz y completa.</small>
                        </label>
                    </div>
                </div>

                <div class="form-actions">
                    <a href="main" class="btn btn-secondary">Volver al Inicio</a>
                    <button type="button" onclick="verMisSolicitudes()" class="btn btn-info">Ver Mis Solicitudes</button>
                    <button type="submit" class="btn btn-primary">Radicar Solicitud</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // VALIDACIÓN JS equivalente a la lógica de RadicarView.java
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const requiredFields = form.querySelectorAll('[required]');
            
            // Manejo de archivos (EQUIVALE a RadicarView attachBtn)
            document.getElementById('documentos').addEventListener('change', function() {
                const files = this.files;
                const fileInfo = document.getElementById('fileInfo');
                
                if (files.length > 0) {
                    fileInfo.textContent = files.length + ' archivo(s) seleccionado(s)';
                    fileInfo.style.color = '#27ae60';
                } else {
                    fileInfo.textContent = 'Ningún archivo seleccionado';
                    fileInfo.style.color = '#666';
                }
            });
            
            // Validación en tiempo real
            requiredFields.forEach(field => {
                field.addEventListener('blur', function() {
                    validateField(this);
                });
                
                field.addEventListener('input', function() {
                    if (this.classList.contains('field-error')) {
                        validateField(this);
                    }
                });
            });
            
            // Validación antes del envío (PRESERVA lógica de RadicarView submitBtn)
            form.addEventListener('submit', function(e) {
                let isValid = true;
                
                // Validar campos requeridos
                requiredFields.forEach(field => {
                    if (!validateField(field)) {
                        isValid = false;
                    }
                });
                
                // Validar términos (EQUIVALE a RadicarView terminos.isSelected())
                const terminos = document.getElementById('aceptaTerminos');
                if (!terminos.checked) {
                    alert('Debe aceptar los términos y condiciones para procesar la solicitud.');
                    isValid = false;
                }
                
                if (!isValid) {
                    e.preventDefault();
                    const firstError = form.querySelector('.field-error');
                    if (firstError) {
                        firstError.focus();
                    }
                }
            });
        });
        
        function validateField(field) {
            const value = field.value.trim();
            let isValid = true;
            
            if (field.hasAttribute('required')) {
                if (field.type === 'checkbox') {
                    isValid = field.checked;
                } else {
                    isValid = value !== '' && value !== 'Seleccione tipo de solicitud...';
                }
            }
            
            field.classList.remove('field-error', 'field-success');
            
            if (field.hasAttribute('required')) {
                if (isValid) {
                    field.classList.add('field-success');
                } else {
                    field.classList.add('field-error');
                }
            }
            
            return isValid;
        }
        
        // FUNCIÓN EQUIVALENTE a RadicarView viewBtn (Ver Mis Solicitudes)
        function verMisSolicitudes() {
            window.location.href = 'radicar?action=mis-solicitudes';
        }
        
        // Auto-formateo de teléfono
        document.getElementById('telefonoContacto').addEventListener('input', function() {
            let value = this.value.replace(/\D/g, '');
            if (value.length >= 10) {
                value = value.substring(0, 10);
                this.value = value.replace(/(\d{3})(\d{3})(\d{4})/, '($1) $2-$3');
            } else {
                this.value = value;
            }
        });
        
        // Animación de carga
        setTimeout(() => {
            document.querySelector('.form-container').classList.add('fade-in');
        }, 100);
    </script>
</body>
</html>
