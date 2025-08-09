import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function InfoAcademica() {
  const [formData, setFormData] = useState({
    institucionEgreso: '',
    añoEgreso: '',
    modalidadBachillerato: '',
    puntajeIcfes: '',
    colegioPublico: 'Si',
    carreraPrimera: '',
    carreraSegunda: '',
    jornada: 'Diurna'
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Información académica guardada (versión simple)');
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
        <h1>🎓 Información Académica</h1>
        <p>Gestiona tu información de estudios</p>
      </div>

      <form onSubmit={handleSubmit} style={{
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '5px',
        boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
      }}>
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Institución de Egreso:
            </label>
            <input
              type="text"
              name="institucionEgreso"
              value={formData.institucionEgreso}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Año de Egreso:
            </label>
            <input
              type="number"
              name="añoEgreso"
              value={formData.añoEgreso}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Modalidad de Bachillerato:
            </label>
            <select
              name="modalidadBachillerato"
              value={formData.modalidadBachillerato}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            >
              <option value="">Seleccionar...</option>
              <option value="Académico">Académico</option>
              <option value="Técnico">Técnico</option>
              <option value="Normalista">Normalista</option>
            </select>
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Puntaje ICFES:
            </label>
            <input
              type="number"
              name="puntajeIcfes"
              value={formData.puntajeIcfes}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              ¿Colegio Público?:
            </label>
            <select
              name="colegioPublico"
              value={formData.colegioPublico}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            >
              <option value="Si">Sí</option>
              <option value="No">No</option>
            </select>
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Jornada Preferida:
            </label>
            <select
              name="jornada"
              value={formData.jornada}
              onChange={handleChange}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
            >
              <option value="Diurna">Diurna</option>
              <option value="Nocturna">Nocturna</option>
            </select>
          </div>
        </div>

        <div style={{ marginTop: '20px' }}>
          <h3 style={{ color: '#00264C', marginBottom: '15px' }}>Opciones de Carrera</h3>
          
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
            <div>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
                Primera Opción:
              </label>
              <select
                name="carreraPrimera"
                value={formData.carreraPrimera}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
              >
                <option value="">Seleccionar carrera...</option>
                <option value="Ingeniería de Sistemas">Ingeniería de Sistemas</option>
                <option value="Administración">Administración</option>
                <option value="Contaduría">Contaduría</option>
                <option value="Derecho">Derecho</option>
              </select>
            </div>

            <div>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
                Segunda Opción:
              </label>
              <select
                name="carreraSegunda"
                value={formData.carreraSegunda}
                onChange={handleChange}
                style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '3px' }}
              >
                <option value="">Seleccionar carrera...</option>
                <option value="Ingeniería de Sistemas">Ingeniería de Sistemas</option>
                <option value="Administración">Administración</option>
                <option value="Contaduría">Contaduría</option>
                <option value="Derecho">Derecho</option>
              </select>
            </div>
          </div>
        </div>

        <div style={{ marginTop: '20px', textAlign: 'center' }}>
          <button 
            type="submit"
            style={{
              backgroundColor: '#00264C',
              color: 'white',
              padding: '10px 30px',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer',
              fontSize: '16px',
              marginRight: '10px'
            }}
          >
            Guardar Información
          </button>
          
          <Link 
            to="/"
            style={{
              backgroundColor: '#6c757d',
              color: 'white',
              padding: '10px 30px',
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

export default InfoAcademica;
