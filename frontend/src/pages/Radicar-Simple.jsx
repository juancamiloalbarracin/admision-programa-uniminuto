import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function Radicar() {
  const [formData, setFormData] = useState({
    tipoSolicitud: '',
    observaciones: '',
    documentos: []
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Solicitud radicada exitosamente (versión simple)');
  };

  return (
    <div style={{ 
      padding: '20px', 
      maxWidth: '800px', 
      margin: '0 auto',
      backgroundColor: '#FAF5FA',
      minHeight: '100vh'
    }}>
      <div style={{
        backgroundColor: '#00264C',
        color: 'white',
        padding: '15px',
        textAlign: 'center',
        marginBottom: '20px',
        borderRadius: '5px'
      }}>
        <h1>📄 Radicar Solicitud</h1>
        <p>Presenta tu solicitud de admisión</p>
      </div>

      <form onSubmit={handleSubmit} style={{
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '5px',
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
      }}>
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Tipo de Solicitud:
          </label>
          <select
            name="tipoSolicitud"
            value={formData.tipoSolicitud}
            onChange={handleChange}
            style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
          >
            <option value="">Seleccionar tipo...</option>
            <option value="Primera vez">Primera vez</option>
            <option value="Reingreso">Reingreso</option>
            <option value="Transferencia">Transferencia</option>
          </select>
        </div>

        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Observaciones:
          </label>
          <textarea
            name="observaciones"
            value={formData.observaciones}
            onChange={handleChange}
            rows="4"
            style={{ 
              width: '100%', 
              padding: '8px', 
              border: '1px solid #ccc', 
              borderRadius: '3px',
              resize: 'vertical'
            }}
            placeholder="Escribe aquí cualquier observación adicional..."
          />
        </div>

        <div style={{ marginBottom: '20px' }}>
          <h3 style={{ color: '#00264C', marginBottom: '15px' }}>Estado de la Solicitud</h3>
          <div style={{
            backgroundColor: '#f8f9fa',
            padding: '15px',
            borderRadius: '5px',
            border: '1px solid #dee2e6'
          }}>
            <p><strong>Estado:</strong> <span style={{ color: '#28a745' }}>✅ Borrador</span></p>
            <p><strong>Fecha de Creación:</strong> {new Date().toLocaleDateString()}</p>
            <p><strong>Último Actualización:</strong> {new Date().toLocaleString()}</p>
          </div>
        </div>

        <div style={{ marginBottom: '20px' }}>
          <h3 style={{ color: '#00264C', marginBottom: '15px' }}>Documentos Requeridos</h3>
          <div style={{
            backgroundColor: '#fff3cd',
            padding: '15px',
            borderRadius: '5px',
            border: '1px solid #ffeaa7'
          }}>
            <ul style={{ margin: 0, paddingLeft: '20px' }}>
              <li>📋 Formulario de inscripción (se genera automáticamente)</li>
              <li>🆔 Copia de documento de identidad</li>
              <li>🎓 Certificado de bachillerato</li>
              <li>📊 Resultados ICFES</li>
              <li>📷 Foto tipo documento</li>
            </ul>
            <p style={{ margin: '10px 0 0 0', fontSize: '14px', color: '#856404' }}>
              <strong>Nota:</strong> Los documentos se adjuntarán en la versión completa del sistema.
            </p>
          </div>
        </div>

        <div style={{ textAlign: 'center' }}>
          <button 
            type="submit"
            style={{
              backgroundColor: '#28a745',
              color: 'white',
              padding: '12px 40px',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer',
              fontSize: '16px',
              marginRight: '10px'
            }}
          >
            📄 Radicar Solicitud
          </button>
          
          <Link 
            to="/"
            style={{
              backgroundColor: '#6c757d',
              color: 'white',
              padding: '12px 30px',
              textDecoration: 'none',
              borderRadius: '5px',
              display: 'inline-block'
            }}
          >
            ← Volver al Dashboard
          </Link>
        </div>
      </form>
    </div>
  );
}

export default Radicar;
