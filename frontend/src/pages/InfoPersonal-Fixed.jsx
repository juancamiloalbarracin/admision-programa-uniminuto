import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './InfoPersonal.css';

// Componente para títulos de sección
const SectionTitle = ({ children }) => (
  <div className="section-title">
    {children}
  </div>
);

// Componente para campos del formulario
const FormField = ({ label, type = "text", options, ...props }) => (
  <div className="form-field">
    <label>{label}:</label>
    {type === "select" ? (
      <select {...props}>
        <option value="">Seleccione...</option>
        {options?.map((option, index) => (
          <option key={index} value={option.value || option}>
            {option.label || option}
          </option>
        ))}
      </select>
    ) : (
      <input type={type} {...props} />
    )}
  </div>
);

function InfoPersonal() {
  const [formData, setFormData] = useState({
    // Campos básicos
    primerNombre: '',
    segundoNombre: '',
    apellidos: '',
    sexo: '',
    grupoSanguineo: '',
    
    // Documento de identidad
    tipoDocumento: '',
    numeroDocumento: '',
    fechaExpedicion: '',
    fechaNacimiento: '',
    paisNacimiento: '',
    ciudadNacimiento: '',
    
    // Contacto
    telefonoCelular: '',
    correo: '',
    
    // Dirección
    tipoDireccion: '',
    direccion: '',
    casaApto: '',
    
    // Información adicional
    sisben: '',
    eps: '',
    discapacidad: '',
    
    // Datos acudiente
    primerAcudiente: '',
    segundoAcudiente: '',
    apellidosAcudiente: '',
    telefonoAcudiente: '',
    correoAcudiente: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Información personal guardada exitosamente');
  };

  const SectionTitle = ({ children }) => (
    <div style={{
      backgroundColor: '#E6E6FA',
      color: '#00264C',
      padding: '8px 16px',
      fontWeight: 'bold',
      fontSize: '14px',
      border: '1px solid #00264C',
      marginBottom: '10px',
      textAlign: 'center',
      gridColumn: '1 / -1'
    }}>
      {children}
    </div>
  );

  const FormField = ({ label, name, type = "text", options = null, required = true, gridColumn = null }) => (
    <div style={{ gridColumn, marginBottom: '5px' }}>
      <label style={{ 
        display: 'block', 
        marginBottom: '8px', 
        fontWeight: 'bold',
        fontSize: '13px',
        color: '#333'
      }}>
        {label} {required && <span style={{ color: 'red' }}>*</span>}
      </label>
      {options ? (
        <select
          name={name}
          value={formData[name]}
          onChange={handleChange}
          style={{ 
            width: '100%', 
            padding: '8px', 
            border: '1px solid #ccc', 
            borderRadius: '4px',
            fontSize: '13px',
            backgroundColor: 'white'
          }}
        >
          {options.map(option => (
            <option key={option} value={option === "Seleccione..." ? "" : option}>
              {option}
            </option>
          ))}
        </select>
      ) : (
        <input
          type={type}
          name={name}
          value={formData[name]}
          onChange={handleChange}
          style={{ 
            width: '100%', 
            padding: '8px', 
            border: '1px solid #ccc', 
            borderRadius: '4px',
            fontSize: '13px'
          }}
        />
      )}
    </div>
  );

  return (
    <div className="info-personal-container">
      <div className="info-personal-wrapper">
        {/* Header fijo */}
        <div className="info-personal-header">
          <h2>Información Personal</h2>
        </div>

        {/* Contenido con scroll */}
        <div className="info-personal-content">
          <form onSubmit={handleSubmit}>
            <div className="form-grid">
              
              {/* Nombres - 2 campos */}
              <FormField 
                label="Primer nombre" 
                name="primerNombre"
                value={formData.primerNombre}
                onChange={handleChange}
              />
              <FormField 
                label="Segundo nombre" 
                name="segundoNombre"
                value={formData.segundoNombre}
                onChange={handleChange}
              />

              {/* Apellidos - ocupa 2 columnas en pantallas grandes */}
              <div className="form-field form-field-half-width">
                <label>Apellidos:</label>
                <input 
                  type="text"
                  name="apellidos"
                  value={formData.apellidos}
                  onChange={handleChange}
                />
              </div>

              {/* Sexo y Grupo Sanguíneo */}
              <FormField 
                label="Sexo Biológico" 
                name="sexo"
                type="select"
                value={formData.sexo}
                onChange={handleChange}
                options={["Masculino", "Femenino"]}
              />
              <FormField 
                label="Grupo Sanguíneo RH" 
                name="grupoSanguineo"
                type="select"
                value={formData.grupoSanguineo}
                onChange={handleChange}
                options={["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"]}
              />

              {/* Título - Documento de Identidad */}
              <SectionTitle>DOCUMENTO DE IDENTIDAD</SectionTitle>

              {/* Tipo y Número de Documento */}
              <FormField 
                label="Tipo de documento" 
                name="tipoDocumento"
                type="select"
                value={formData.tipoDocumento}
                onChange={handleChange}
                options={["Cédula de Ciudadanía", "Tarjeta de Identidad", "Cédula de Extranjería", "Pasaporte"]}
              />
              <FormField 
                label="Número de documento" 
                name="numeroDocumento"
                value={formData.numeroDocumento}
                onChange={handleChange}
              />

              {/* Fechas */}
              <FormField 
                label="Fecha de expedición" 
                name="fechaExpedicion"
                type="date"
                value={formData.fechaExpedicion}
                onChange={handleChange}
              />
              <FormField 
                label="Fecha de nacimiento" 
                name="fechaNacimiento"
                type="date"
                value={formData.fechaNacimiento}
                onChange={handleChange}
              />

              {/* Lugar de nacimiento */}
              <FormField 
                label="País de nacimiento" 
                name="paisNacimiento"
                value={formData.paisNacimiento}
                onChange={handleChange}
              />
              <FormField 
                label="Ciudad de nacimiento" 
                name="ciudadNacimiento"
                value={formData.ciudadNacimiento}
                onChange={handleChange}
              />

              {/* Título - Contacto */}
              <SectionTitle>INFORMACIÓN DE CONTACTO</SectionTitle>

              {/* Contacto */}
              <FormField 
                label="Teléfono celular" 
                name="telefonoCelular"
                value={formData.telefonoCelular}
                onChange={handleChange}
              />
              <FormField 
                label="Correo electrónico" 
                name="correo"
                type="email"
                value={formData.correo}
                onChange={handleChange}
              />

              {/* Título - Dirección */}
              <SectionTitle>DIRECCIÓN DE RESIDENCIA</SectionTitle>

              {/* Dirección */}
              <FormField 
                label="Tipo de dirección" 
                name="tipoDireccion"
                type="select"
                value={formData.tipoDireccion}
                onChange={handleChange}
                options={["Casa", "Apartamento", "Finca", "Oficina", "Otro"]}
              />
              <FormField 
                label="Dirección" 
                name="direccion"
                value={formData.direccion}
                onChange={handleChange}
              />

              {/* Casa/Apartamento - ocupa 2 columnas */}
              <div className="form-field form-field-half-width">
                <label>Casa, apartamento o barrio:</label>
                <input 
                  type="text"
                  name="casaApto"
                  value={formData.casaApto}
                  onChange={handleChange}
                />
              </div>

              {/* Título - Información Adicional */}
              <SectionTitle>INFORMACIÓN ADICIONAL</SectionTitle>

              {/* Sisben y EPS */}
              <FormField 
                label="¿Tiene Sisben?" 
                name="sisben"
                type="select"
                value={formData.sisben}
                onChange={handleChange}
                options={["Sí", "No"]}
              />
              <FormField 
                label="¿Tiene EPS?" 
                name="eps"
                type="select"
                value={formData.eps}
                onChange={handleChange}
                options={["Sí", "No"]}
              />

              {/* Discapacidad - ocupa 2 columnas */}
              <div className="form-field form-field-half-width">
                <label>¿Tiene discapacidad?:</label>
                <select 
                  name="discapacidad"
                  value={formData.discapacidad}
                  onChange={handleChange}
                >
                  <option value="">Seleccione...</option>
                  <option value="Sí">Sí</option>
                  <option value="No">No</option>
                </select>
              </div>

              {/* Título - Datos Acudiente */}
              <SectionTitle>INFORMACIÓN DEL ACUDIENTE</SectionTitle>

              {/* Nombres Acudiente */}
              <FormField 
                label="Primer nombre acudiente" 
                name="primerAcudiente"
                value={formData.primerAcudiente}
                onChange={handleChange}
              />
              <FormField 
                label="Segundo nombre acudiente" 
                name="segundoAcudiente"
                value={formData.segundoAcudiente}
                onChange={handleChange}
              />

              {/* Apellidos Acudiente - ocupa 2 columnas */}
              <div className="form-field form-field-half-width">
                <label>Apellidos acudiente:</label>
                <input 
                  type="text"
                  name="apellidosAcudiente"
                  value={formData.apellidosAcudiente}
                  onChange={handleChange}
                />
              </div>

              {/* Contacto Acudiente */}
              <FormField 
                label="Teléfono acudiente" 
                name="telefonoAcudiente"
                value={formData.telefonoAcudiente}
                onChange={handleChange}
              />
              <FormField 
                label="Correo acudiente" 
                name="correoAcudiente"
                type="email"
                value={formData.correoAcudiente}
                onChange={handleChange}
              />

              {/* Botones */}
              <div className="form-actions">
                <button type="submit" className="btn-primary">
                  Guardar Información
                </button>
                <Link to="/" className="btn-secondary">
                  ← Volver al Dashboard
                </Link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default InfoPersonal;
