import { useState, useEffect } from 'react';
import { useAuth } from '../utils/AuthContext';
import { solicitudesService } from '../services/api';
import './Radicar.css';

const Radicar = () => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    tipoSolicitud: '',
    asunto: '',
    descripcion: '',
    justificacion: '',
    documentosAdjuntos: '',
    fechaSolicitud: new Date().toISOString().split('T')[0],
    estado: 'PENDIENTE',
    observaciones: '',
    prioridad: 'NORMAL'
  });
  const [solicitudes, setSolicitudes] = useState([]);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [showForm, setShowForm] = useState(true);

  useEffect(() => {
    loadSolicitudes();
  }, []);

  const loadSolicitudes = async () => {
    try {
      const response = await solicitudesService.getSolicitudes();
      if (response && response.success) {
        setSolicitudes(response.data || []);
      }
    } catch (error) {
      console.error('Error cargando solicitudes:', error);
    }
  };

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
      const response = await solicitudesService.radicarSolicitud(formData);
      
      if (response.success) {
        setMessage(`Solicitud radicada exitosamente. Número de radicado: ${response.numeroRadicado}`);
        setFormData({
          tipoSolicitud: '',
          asunto: '',
          descripcion: '',
          justificacion: '',
          documentosAdjuntos: '',
          fechaSolicitud: new Date().toISOString().split('T')[0],
          estado: 'PENDIENTE',
          observaciones: '',
          prioridad: 'NORMAL'
        });
        loadSolicitudes(); // Recargar lista
      } else {
        setMessage('Error: ' + response.message);
      }
    } catch (error) {
      setMessage('Error al radicar la solicitud');
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-instructions">
        Diligencie el siguiente formulario para radicar una solicitud oficial. Todos los campos marcados con asterisco (*) son obligatorios. 
        Una vez enviada, podrá hacer seguimiento del estado de su solicitud en la sección correspondiente.
      </div>

      {/* Botones para alternar vista */}
      <div style={{marginBottom: '20px'}}>
        <button 
          className={`btn ${showForm ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setShowForm(true)}
          style={{marginRight: '10px'}}
        >
          Nueva Solicitud
        </button>
        <button 
          className={`btn ${!showForm ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => setShowForm(false)}
          style={{marginRight: '10px'}}
        >
          Mis Solicitudes
        </button>
        {!showForm && (
          <button 
            className="btn btn-secondary"
            onClick={loadSolicitudes}
            disabled={loading}
          >
            {loading ? 'Consultando...' : 'Actualizar Lista'}
          </button>
        )}
      </div>

      {showForm ? (
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            {/* Sección Información Básica */}
            <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
              INFORMACIÓN BÁSICA DE LA SOLICITUD
            </div>

            <div className="form-group">
              <label>Tipo de solicitud <span className="required">*</span></label>
              <select 
                name="tipoSolicitud" 
                value={formData.tipoSolicitud}
                onChange={handleChange}
                className="form-control"
                required
              >
                <option value="">Seleccione...</option>
                <option value="Cambio de Programa">Cambio de Programa</option>
                <option value="Transferencia">Transferencia</option>
                <option value="Reingreso">Reingreso</option>
                <option value="Certificado de Notas">Certificado de Notas</option>
                <option value="Certificado de Estudio">Certificado de Estudio</option>
                <option value="Homologación">Homologación</option>
                <option value="Retiro Temporal">Retiro Temporal</option>
                <option value="Cancelación de Matrícula">Cancelación de Matrícula</option>
                <option value="Validación de Conocimientos">Validación de Conocimientos</option>
                <option value="Otra">Otra</option>
              </select>
            </div>
            <div className="form-group">
              <label>Prioridad</label>
              <select 
                name="prioridad" 
                value={formData.prioridad}
                onChange={handleChange}
                className="form-control"
              >
                <option value="NORMAL">Normal</option>
                <option value="ALTA">Alta</option>
                <option value="URGENTE">Urgente</option>
              </select>
            </div>

            <div className="form-group" style={{gridColumn: '1 / -1'}}>
              <label>Asunto <span className="required">*</span></label>
              <input 
                type="text" 
                name="asunto" 
                value={formData.asunto}
                onChange={handleChange}
                className="form-control"
                required
                placeholder="Ingrese un asunto claro y conciso"
              />
            </div>

            <div className="form-group" style={{gridColumn: '1 / -1'}}>
              <label>Descripción detallada <span className="required">*</span></label>
              <textarea 
                name="descripcion" 
                value={formData.descripcion}
                onChange={handleChange}
                className="form-control"
                rows="4"
                required
                placeholder="Describa detalladamente su solicitud..."
              />
            </div>

            <div className="form-group" style={{gridColumn: '1 / -1'}}>
              <label>Justificación <span className="required">*</span></label>
              <textarea 
                name="justificacion" 
                value={formData.justificacion}
                onChange={handleChange}
                className="form-control"
                rows="4"
                required
                placeholder="Explique las razones que justifican su solicitud..."
              />
            </div>

            {/* Sección Documentos */}
            <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
              DOCUMENTOS DE SOPORTE
            </div>

            <div className="form-group" style={{gridColumn: '1 / -1'}}>
              <label>Documentos adjuntos (opcional)</label>
              <textarea 
                name="documentosAdjuntos" 
                value={formData.documentosAdjuntos}
                onChange={handleChange}
                className="form-control"
                rows="3"
                placeholder="Liste los documentos que adjunta como soporte a su solicitud..."
              />
            </div>

            <div className="form-group">
              <label>Fecha de solicitud</label>
              <input 
                type="date" 
                name="fechaSolicitud" 
                value={formData.fechaSolicitud}
                onChange={handleChange}
                className="form-control"
                readOnly
              />
            </div>
            <div className="form-group">
              <label>Estado</label>
              <input 
                type="text" 
                value="PENDIENTE"
                className="form-control"
                readOnly
                style={{backgroundColor: '#f8f9fa'}}
              />
            </div>

            <div className="form-group" style={{gridColumn: '1 / -1'}}>
              <label>Observaciones adicionales</label>
              <textarea 
                name="observaciones" 
                value={formData.observaciones}
                onChange={handleChange}
                className="form-control"
                rows="3"
                placeholder="Agregue cualquier información adicional relevante..."
              />
            </div>
          </div>

          {message && (
            <div className={`alert ${message.includes('Error') ? 'alert-error' : 'alert-success'}`}>
              {message}
            </div>
          )}

          <button 
            type="submit" 
            className="btn"
            disabled={loading}
            style={{marginTop: '20px'}}
          >
            {loading ? 'Radicando...' : 'Radicar Solicitud'}
          </button>
        </form>
      ) : (
        <div>
          <h3 style={{color: '#00264C', marginBottom: '20px'}}>MIS SOLICITUDES</h3>
          {solicitudes.length === 0 ? (
            <div className="alert alert-info">
              No tiene solicitudes registradas.
            </div>
          ) : (
            <div className="solicitudes-list">
              {solicitudes.map((solicitud, index) => (
                <div key={index} className="info-card" style={{marginBottom: '15px', textAlign: 'left'}}>
                  <h4 style={{color: '#00264C', marginBottom: '10px'}}>{solicitud.asunto}</h4>
                  <p><strong>Tipo:</strong> {solicitud.tipoSolicitud}</p>
                  <p><strong>Fecha:</strong> {solicitud.fechaSolicitud}</p>
                  <p><strong>Estado:</strong> 
                    <span style={{
                      color: solicitud.estado === 'APROBADA' ? 'green' : 
                             solicitud.estado === 'RECHAZADA' ? 'red' : '#00264C',
                      fontWeight: 'bold'
                    }}>
                      {solicitud.estado}
                    </span>
                  </p>
                  <p><strong>Descripción:</strong> {solicitud.descripcion}</p>
                  {solicitud.observaciones && (
                    <p><strong>Observaciones:</strong> {solicitud.observaciones}</p>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Radicar;
