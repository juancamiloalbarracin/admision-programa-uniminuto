import { useState, useEffect } from 'react';
import { useAuth } from '../utils/AuthContext';
import { infoAcademicaService } from '../services/api';
import './InfoAcademica.css';

const InfoAcademica = () => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    nivelEstudio: '',
    institucionProcedencia: '',
    tituloObtenido: '',
    fechaGrado: '',
    numeroActa: '',
    programaInteres: '',
    modalidadEstudio: '',
    jornadaPreferida: '',
    sedePreferida: '',
    conoceInstitucion: '',
    medioConocimiento: '',
    observacionesAcademicas: '',
    experienciaLaboral: '',
    certificacionesAdicionales: '',
    idiomasManejados: '',
    nivelIngles: '',
    softwareManejado: '',
    expectativasPrograma: ''
  });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const loadData = async (showMessage = false) => {
    if (showMessage) {
      setLoading(true);
      setMessage('');
    }
    
    try {
      const response = await infoAcademicaService.getInfo();
      if (response && response.success && response.data) {
        setFormData(response.data);
        if (showMessage) {
          setMessage('Datos consultados exitosamente');
        }
      } else {
        if (showMessage) {
          setMessage('No se encontraron datos guardados anteriormente');
        }
      }
    } catch (error) {
      console.error('Error cargando información académica:', error);
      if (showMessage) {
        setMessage('Error al consultar los datos');
      }
    } finally {
      if (showMessage) {
        setLoading(false);
      }
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      const response = await infoAcademicaService.saveInfo(formData);
      
      if (response.success) {
        setMessage('Información académica guardada exitosamente');
      } else {
        setMessage('Error: ' + response.message);
      }
    } catch (error) {
      setMessage('Error al guardar la información');
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-instructions">
        Complete la información académica solicitada. Los campos marcados con asterisco (*) son obligatorios.
        Esta información es fundamental para el proceso de admisión y para adaptar el programa a su perfil académico.
      </div>

      <form onSubmit={handleSubmit}>
        <div className="form-grid">
          {/* Sección Formación Académica */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            FORMACIÓN ACADÉMICA PREVIA
          </div>

          <div className="form-group">
            <label>Nivel de estudio alcanzado <span className="required">*</span></label>
            <select 
              name="nivelEstudio" 
              value={formData.nivelEstudio}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="Bachillerato">Bachillerato</option>
              <option value="Técnico">Técnico</option>
              <option value="Tecnológico">Tecnológico</option>
              <option value="Profesional">Profesional</option>
              <option value="Especialización">Especialización</option>
              <option value="Maestría">Maestría</option>
              <option value="Doctorado">Doctorado</option>
            </select>
          </div>
          <div className="form-group">
            <label>Institución de procedencia <span className="required">*</span></label>
            <input 
              type="text" 
              name="institucionProcedencia" 
              value={formData.institucionProcedencia}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          <div className="form-group">
            <label>Título obtenido <span className="required">*</span></label>
            <input 
              type="text" 
              name="tituloObtenido" 
              value={formData.tituloObtenido}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>
          <div className="form-group">
            <label>Fecha de grado</label>
            <input 
              type="date" 
              name="fechaGrado" 
              value={formData.fechaGrado}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          <div className="form-group">
            <label>Número de acta</label>
            <input 
              type="text" 
              name="numeroActa" 
              value={formData.numeroActa}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          {/* Sección Programa de Interés */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            PROGRAMA DE INTERÉS
          </div>

          <div className="form-group">
            <label>Programa de interés <span className="required">*</span></label>
            <select 
              name="programaInteres" 
              value={formData.programaInteres}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="Ingeniería de Sistemas">Ingeniería de Sistemas</option>
              <option value="Administración de Empresas">Administración de Empresas</option>
              <option value="Contaduría Pública">Contaduría Pública</option>
              <option value="Psicología">Psicología</option>
              <option value="Comunicación Social">Comunicación Social</option>
              <option value="Derecho">Derecho</option>
              <option value="Arquitectura">Arquitectura</option>
              <option value="Medicina">Medicina</option>
              <option value="Enfermería">Enfermería</option>
              <option value="Educación">Educación</option>
            </select>
          </div>
          <div className="form-group">
            <label>Modalidad de estudio <span className="required">*</span></label>
            <select 
              name="modalidadEstudio" 
              value={formData.modalidadEstudio}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="Presencial">Presencial</option>
              <option value="Virtual">Virtual</option>
              <option value="Semipresencial">Semipresencial</option>
              <option value="A Distancia">A Distancia</option>
            </select>
          </div>

          <div className="form-group">
            <label>Jornada preferida</label>
            <select 
              name="jornadaPreferida" 
              value={formData.jornadaPreferida}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Diurna">Diurna</option>
              <option value="Nocturna">Nocturna</option>
              <option value="Fin de Semana">Fin de Semana</option>
              <option value="Flexible">Flexible</option>
            </select>
          </div>
          <div className="form-group">
            <label>Sede preferida</label>
            <select 
              name="sedePreferida" 
              value={formData.sedePreferida}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Sede Principal">Sede Principal</option>
              <option value="Sede Norte">Sede Norte</option>
              <option value="Sede Sur">Sede Sur</option>
              <option value="Virtual">Virtual</option>
            </select>
          </div>

          {/* Sección Información Adicional */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            INFORMACIÓN ADICIONAL
          </div>

          <div className="form-group">
            <label>¿Conoce la institución?</label>
            <select 
              name="conoceInstitucion" 
              value={formData.conoceInstitucion}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Sí">Sí</option>
              <option value="No">No</option>
            </select>
          </div>
          <div className="form-group">
            <label>Medio por el cual conoció la institución</label>
            <select 
              name="medioConocimiento" 
              value={formData.medioConocimiento}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Internet">Internet</option>
              <option value="Redes Sociales">Redes Sociales</option>
              <option value="Referencia de conocido">Referencia de conocido</option>
              <option value="Publicidad">Publicidad</option>
              <option value="Ferias educativas">Ferias educativas</option>
              <option value="Medios de comunicación">Medios de comunicación</option>
              <option value="Otro">Otro</option>
            </select>
          </div>

          <div className="form-group" style={{gridColumn: '1 / -1'}}>
            <label>Observaciones académicas</label>
            <textarea 
              name="observacionesAcademicas" 
              value={formData.observacionesAcademicas}
              onChange={handleChange}
              className="form-control"
              rows="3"
              placeholder="Información adicional sobre su formación académica..."
            />
          </div>

          {/* Sección Experiencia y Competencias */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            EXPERIENCIA Y COMPETENCIAS
          </div>

          <div className="form-group" style={{gridColumn: '1 / -1'}}>
            <label>Experiencia laboral</label>
            <textarea 
              name="experienciaLaboral" 
              value={formData.experienciaLaboral}
              onChange={handleChange}
              className="form-control"
              rows="3"
              placeholder="Describa su experiencia laboral relevante..."
            />
          </div>

          <div className="form-group">
            <label>Certificaciones adicionales</label>
            <input 
              type="text" 
              name="certificacionesAdicionales" 
              value={formData.certificacionesAdicionales}
              onChange={handleChange}
              className="form-control"
              placeholder="Ej: Certificaciones técnicas, cursos..."
            />
          </div>
          <div className="form-group">
            <label>Idiomas que maneja</label>
            <input 
              type="text" 
              name="idiomasManejados" 
              value={formData.idiomasManejados}
              onChange={handleChange}
              className="form-control"
              placeholder="Ej: Inglés, Francés, Portugués..."
            />
          </div>

          <div className="form-group">
            <label>Nivel de inglés</label>
            <select 
              name="nivelIngles" 
              value={formData.nivelIngles}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Básico">Básico</option>
              <option value="Intermedio">Intermedio</option>
              <option value="Avanzado">Avanzado</option>
              <option value="Nativo">Nativo</option>
            </select>
          </div>
          <div className="form-group">
            <label>Software que maneja</label>
            <input 
              type="text" 
              name="softwareManejado" 
              value={formData.softwareManejado}
              onChange={handleChange}
              className="form-control"
              placeholder="Ej: Office, Adobe, herramientas específicas..."
            />
          </div>

          <div className="form-group" style={{gridColumn: '1 / -1'}}>
            <label>Expectativas del programa</label>
            <textarea 
              name="expectativasPrograma" 
              value={formData.expectativasPrograma}
              onChange={handleChange}
              className="form-control"
              rows="4"
              placeholder="Describa qué espera obtener del programa académico..."
            />
          </div>
        </div>

        {message && (
          <div className={`alert ${message.includes('Error') ? 'alert-error' : 'alert-success'}`}>
            {message}
          </div>
        )}

        <div className="form-buttons">
          <button 
            type="button" 
            className="btn btn-secondary"
            onClick={() => loadData(true)}
            disabled={loading}
            style={{marginTop: '20px', marginRight: '10px'}}
          >
            {loading ? 'Consultando...' : 'Consultar Datos Guardados'}
          </button>
          
          <button 
            type="submit" 
            className="btn"
            disabled={loading}
            style={{marginTop: '20px'}}
          >
            {loading ? 'Guardando...' : 'Guardar Información Académica'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default InfoAcademica;
