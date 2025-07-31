<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Información Académica - Sistema UDC</title>
    <style>
        /* ESTILOS PRESERVADOS de InfoAcademicaView.java visual */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Dialog, Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            line-height: 1.4;
        }
        
        .header {
            background: linear-gradient(135deg, #2c3e50, #34495e);
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
            max-width: 900px;
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
        }
        
        /* REPLICACIÓN exacta de GridBagLayout de InfoAcademicaView.java */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 25px 30px;
            align-items: start;
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
            color: #2c3e50;
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
            color: #2c3e50;
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
            min-height: 80px;
        }
        
        /* Campos específicos de ancho completo */
        .full-width {
            grid-column: 1 / -1;
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
        }
        
        .btn {
            padding: 12px 25px;
            margin: 0 10px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background: #27ae60;
            color: white;
        }
        
        .btn-primary:hover {
            background: #229954;
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: #95a5a6;
            color: white;
        }
        
        .btn-secondary:hover {
            background: #7f8c8d;
        }
        
        /* Responsive design */
        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            
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
    </style>
</head>
<body>
    <div class="header">
        <h1>Información Académica</h1>
        <p>Complete los datos de su trayectoria académica</p>
    </div>

    <div class="container">
        <div class="nav-buttons">
            <a href="main" class="nav-btn">Inicio</a>
            <a href="info-personal" class="nav-btn">Información Personal</a>
            <a href="info-academica" class="nav-btn current">Información Académica</a>
            <a href="radicar" class="nav-btn">Radicar Solicitud</a>
            <a href="logout" class="nav-btn">Cerrar Sesión</a>
        </div>

        <div class="form-container">
            <!-- Mensajes de error/éxito -->
            <c:if test="${not empty error}">
                <div class="error-message">
                    ${error}
                </div>
            </c:if>
            
            <c:if test="${param.infoAcademicaGuardada == 'true'}">
                <div class="success-message">
                    Información académica guardada exitosamente.
                </div>
            </c:if>

            <!-- FORMULARIO REPLICADO de InfoAcademicaView.java -->
            <form method="POST" action="info-academica" novalidate>
                <div class="form-section">
                    <div class="section-title">Datos de Admisión</div>
                    
                    <div class="form-grid">
                        <div class="form-field">
                            <label for="nivel">Nivel <span class="required">*</span></label>
                            <select id="nivel" name="nivel" required>
                                <option value="">Seleccione...</option>
                                <option value="Pregrado" ${nivel == 'Pregrado' ? 'selected' : ''}>Pregrado</option>
                                <option value="Especialización" ${nivel == 'Especialización' ? 'selected' : ''}>Especialización</option>
                                <option value="Maestría" ${nivel == 'Maestría' ? 'selected' : ''}>Maestría</option>
                                <option value="Doctorado" ${nivel == 'Doctorado' ? 'selected' : ''}>Doctorado</option>
                                <option value="Técnico" ${nivel == 'Técnico' ? 'selected' : ''}>Técnico</option>
                                <option value="Tecnológico" ${nivel == 'Tecnológico' ? 'selected' : ''}>Tecnológico</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="sede">Sede <span class="required">*</span></label>
                            <select id="sede" name="sede" required>
                                <option value="">Seleccione...</option>
                                <option value="Bogotá" ${sede == 'Bogotá' ? 'selected' : ''}>Bogotá</option>
                                <option value="Medellín" ${sede == 'Medellín' ? 'selected' : ''}>Medellín</option>
                                <option value="Cali" ${sede == 'Cali' ? 'selected' : ''}>Cali</option>
                                <option value="Barranquilla" ${sede == 'Barranquilla' ? 'selected' : ''}>Barranquilla</option>
                                <option value="Cartagena" ${sede == 'Cartagena' ? 'selected' : ''}>Cartagena</option>
                                <option value="Bucaramanga" ${sede == 'Bucaramanga' ? 'selected' : ''}>Bucaramanga</option>
                                <option value="Pereira" ${sede == 'Pereira' ? 'selected' : ''}>Pereira</option>
                                <option value="Virtual" ${sede == 'Virtual' ? 'selected' : ''}>Virtual</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="gradoAcademico">Grado Académico <span class="required">*</span></label>
                            <select id="gradoAcademico" name="gradoAcademico" required>
                                <option value="">Seleccione...</option>
                                <option value="Bachiller" ${gradoAcademico == 'Bachiller' ? 'selected' : ''}>Bachiller</option>
                                <option value="Técnico" ${gradoAcademico == 'Técnico' ? 'selected' : ''}>Técnico</option>
                                <option value="Tecnólogo" ${gradoAcademico == 'Tecnólogo' ? 'selected' : ''}>Tecnólogo</option>
                                <option value="Profesional" ${gradoAcademico == 'Profesional' ? 'selected' : ''}>Profesional</option>
                                <option value="Especialista" ${gradoAcademico == 'Especialista' ? 'selected' : ''}>Especialista</option>
                                <option value="Magíster" ${gradoAcademico == 'Magíster' ? 'selected' : ''}>Magíster</option>
                                <option value="Doctor" ${gradoAcademico == 'Doctor' ? 'selected' : ''}>Doctor</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="periodoAdmision">Período de Admisión <span class="required">*</span></label>
                            <input type="text" id="periodoAdmision" name="periodoAdmision" 
                                   value="${periodoAdmision}" placeholder="Ejemplo: 2024-1" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <div class="section-title">Modalidad de Estudio</div>
                    
                    <div class="form-grid">
                        <div class="form-field">
                            <label for="metodologia">Metodología <span class="required">*</span></label>
                            <input type="text" id="metodologia" name="metodologia" 
                                   value="${metodologia}" placeholder="Presencial, Virtual, Mixta" required>
                        </div>

                        <div class="form-field">
                            <label for="jornada">Jornada <span class="required">*</span></label>
                            <select id="jornada" name="jornada" required>
                                <option value="">Seleccione...</option>
                                <option value="Diurna" ${jornada == 'Diurna' ? 'selected' : ''}>Diurna</option>
                                <option value="Nocturna" ${jornada == 'Nocturna' ? 'selected' : ''}>Nocturna</option>
                                <option value="Fin de Semana" ${jornada == 'Fin de Semana' ? 'selected' : ''}>Fin de Semana</option>
                                <option value="Mixta" ${jornada == 'Mixta' ? 'selected' : ''}>Mixta</option>
                                <option value="Virtual" ${jornada == 'Virtual' ? 'selected' : ''}>Virtual</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="planDecision">Plan de Decisión <span class="required">*</span></label>
                            <select id="planDecision" name="planDecision" required>
                                <option value="">Seleccione...</option>
                                <option value="Regular" ${planDecision == 'Regular' ? 'selected' : ''}>Regular</option>
                                <option value="Especial" ${planDecision == 'Especial' ? 'selected' : ''}>Especial</option>
                                <option value="Transferencia" ${planDecision == 'Transferencia' ? 'selected' : ''}>Transferencia</option>
                                <option value="Reingreso" ${planDecision == 'Reingreso' ? 'selected' : ''}>Reingreso</option>
                                <option value="Convenio" ${planDecision == 'Convenio' ? 'selected' : ''}>Convenio</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="gradoSeleccionado">Grado Seleccionado <span class="required">*</span></label>
                            <input type="text" id="gradoSeleccionado" name="gradoSeleccionado" 
                                   value="${gradoSeleccionado}" placeholder="Programa académico específico" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <div class="section-title">Información Académica Previa</div>
                    
                    <div class="form-grid">
                        <div class="form-field">
                            <label for="pais">País de Estudios Previos <span class="required">*</span></label>
                            <input type="text" id="pais" name="pais" 
                                   value="${pais}" placeholder="Colombia" required>
                        </div>

                        <div class="form-field">
                            <label for="gradoObtenido">Grado Obtenido <span class="required">*</span></label>
                            <input type="text" id="gradoObtenido" name="gradoObtenido" 
                                   value="${gradoObtenido}" placeholder="Último grado académico obtenido" required>
                        </div>

                        <div class="form-field full-width">
                            <label for="fechaGraduacion">Fecha de Graduación <span class="required">*</span></label>
                            <input type="date" id="fechaGraduacion" name="fechaGraduacion" 
                                   value="${fechaGraduacion}" required>
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Guardar Información Académica</button>
                    <button type="button" class="btn btn-primary" onclick="verInformacionAcademica()">Ver Información</button>
                    <a href="main" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // VALIDACIÓN JS equivalente a la lógica de InfoAcademicaView.java
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const requiredFields = form.querySelectorAll('[required]');
            
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
            
            // Validación antes del envío
            form.addEventListener('submit', function(e) {
                let isValid = true;
                
                requiredFields.forEach(field => {
                    if (!validateField(field)) {
                        isValid = false;
                    }
                });
                
                if (!isValid) {
                    e.preventDefault();
                    alert('Por favor, complete todos los campos obligatorios marcados con asterisco (*).');
                    // Foco en el primer campo con error
                    const firstError = form.querySelector('.field-error');
                    if (firstError) {
                        firstError.focus();
                    }
                }
            });
        });
        
        function validateField(field) {
            const value = field.value.trim();
            const isValid = value !== '' && value !== 'Seleccione...';
            
            field.classList.remove('field-error', 'field-success');
            
            if (isValid) {
                field.classList.add('field-success');
            } else if (value !== '') {
                field.classList.add('field-error');
            }
            
            return isValid;
        }
        
        // Autocompletado y funcionalidades avanzadas
        document.getElementById('pais').addEventListener('input', function() {
            if (this.value.toLowerCase().includes('colombia')) {
                this.value = 'Colombia';
            }
        });
        
        // Formateo de período de admisión
        document.getElementById('periodoAdmision').addEventListener('input', function() {
            let value = this.value.replace(/[^0-9-]/g, '');
            if (value.length >= 4 && !value.includes('-')) {
                value = value.substring(0, 4) + '-' + value.substring(4);
            }
            this.value = value;
        });
        
        // Función para ver información académica guardada
        function verInformacionAcademica() {
            fetch('info-academica?action=ver', {
                method: 'GET',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    let info = data.info;
                    let mensaje = '=== INFORMACIÓN ACADÉMICA GUARDADA ===\n\n';
                    
                    mensaje += 'Nivel: ' + (info.nivel || '') + '\n';
                    mensaje += 'Sede: ' + (info.sede || '') + '\n';
                    mensaje += 'Grado Académico: ' + (info.gradoAcademico || '') + '\n';
                    mensaje += 'Período de Admisión: ' + (info.periodoAdmision || '') + '\n';
                    mensaje += 'Metodología: ' + (info.metodologia || '') + '\n';
                    mensaje += 'Jornada: ' + (info.jornada || '') + '\n';
                    mensaje += 'Plan de Decisión: ' + (info.planDecision || '') + '\n';
                    mensaje += 'Grado Seleccionado: ' + (info.gradoSeleccionado || '') + '\n';
                    mensaje += 'País: ' + (info.pais || '') + '\n';
                    mensaje += 'Grado Obtenido: ' + (info.gradoObtenido || '') + '\n';
                    mensaje += 'Fecha de Graduación: ' + (info.fechaGraduacion || '') + '\n';
                    
                    alert(mensaje);
                } else {
                    alert('No se encontró información académica guardada para este usuario.');
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
