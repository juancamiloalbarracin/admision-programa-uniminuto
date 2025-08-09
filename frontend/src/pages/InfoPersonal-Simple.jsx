import React, { useState } from 'react';
import { Link } from 'react-router-dom';

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
    <div style={{ 
      backgroundColor: '#FAF5FA',
      minHeight: '100vh',
      padding: '0',
      overflowY: 'auto' // Scroll vertical
    }}>
      {/* Header exacto como Java Swing */}
      <div style={{
        backgroundColor: '#00264C',
        color: 'white',
        height: '60px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'sticky', // Header fijo al hacer scroll
        top: 0,
        zIndex: 100
      }}>
        <h1 style={{
          fontSize: '24px',
          fontWeight: 'bold',
          margin: 0,
          fontFamily: 'Arial'
        }}>
          Información Personal
        </h1>
      </div>

      {/* Formulario centrado con scroll */}
      <div style={{
        padding: '20px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'flex-start',
        minHeight: 'calc(100vh - 60px)' // Altura completa menos el header
      }}>
        <div style={{
          maxWidth: '1000px', // Ancho máximo del formulario
          width: '100%',
          margin: '0 auto'
        }}>
          {/* Instrucciones */}
          <div style={{
            backgroundColor: '#E6E6FA',
            border: '1px solid #00264C',
            padding: '15px',
            marginBottom: '20px',
            fontSize: '14px',
            lineHeight: '1.4',
            fontFamily: 'Arial',
            borderRadius: '5px'
          }}>
            Ingrese su información en cada una de las siguientes secciones. Los campos con un asterisco (*) rojo son obligatorios 
            y deben completarse para enviar la solicitud. Pueden ser requeridas preguntas adicionales en base a sus respuestas a una pregunta anterior.
          </div>

          <form onSubmit={handleSubmit}>
            <div style={{
              display: 'grid',
              gridTemplateColumns: '1fr 1fr',
              gap: '12px 15px', // Más espacio entre campos
              backgroundColor: 'white',
              padding: '30px',
              border: '1px solid #ddd',
              borderRadius: '5px',
              boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
              marginBottom: '20px' // Espacio al final para scroll
            }}>
            
            {/* Fila 1: Nombres */}
            <FormField label="Primer nombre" name="primerNombre" />
            <FormField label="Segundo nombre" name="segundoNombre" />

            {/* Fila 2: Apellidos - ocupa 2 columnas */}
            <FormField label="Apellidos" name="apellidos" gridColumn="1 / -1" />

            {/* Fila 3: Sexo y Grupo Sanguíneo */}
            <FormField 
              label="Sexo Biológico" 
              name="sexo" 
              options={["Seleccione...", "Masculino", "Femenino"]} 
            />
            <FormField 
              label="Grupo Sanguíneo RH" 
              name="grupoSanguineo" 
              options={["Seleccione...", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"]} 
            />

            {/* Título - Documento de Identidad */}
            <SectionTitle>DOCUMENTO DE IDENTIDAD</SectionTitle>

            {/* Fila 4: Tipo y Número de Documento */}
            <FormField 
              label="Tipo de documento" 
              name="tipoDocumento" 
              options={["Seleccione...", "Cédula de Ciudadanía", "Tarjeta de Identidad", "Cédula de Extranjería", "Pasaporte"]} 
            />
            <FormField label="Número de documento" name="numeroDocumento" />

            {/* Fila 5: Fechas */}
            <FormField label="Fecha de expedición (DD/MM/AAAA)" name="fechaExpedicion" type="date" />
            <FormField label="Fecha de nacimiento (DD/MM/AAAA)" name="fechaNacimiento" type="date" />

            {/* Fila 6: Lugar de nacimiento */}
            <FormField label="País de nacimiento" name="paisNacimiento" />
            <FormField label="Ciudad de nacimiento" name="ciudadNacimiento" />

            {/* Fila 7: Contacto */}
            <FormField label="Teléfono celular" name="telefonoCelular" />
            <FormField label="Correo electrónico" name="correo" type="email" />

            {/* Título - Dirección */}
            <SectionTitle>DIRECCIÓN</SectionTitle>

            {/* Fila 8: Dirección */}
            <FormField 
              label="Tipo de dirección" 
              name="tipoDireccion" 
              options={["Seleccione...", "Casa", "Apartamento", "Finca", "Oficina", "Otro"]} 
            />
            <FormField label="Dirección" name="direccion" />

            {/* Fila 9: Casa/Apto - ocupa 2 columnas */}
            <FormField label="Casa, apartamento o barrio" name="casaApto" gridColumn="1 / -1" />

            {/* Título - Información Adicional */}
            <SectionTitle>INFORMACIÓN ADICIONAL</SectionTitle>

            {/* Fila 10: Sisben y EPS */}
            <FormField 
              label="¿Tiene Sisben?" 
              name="sisben" 
              options={["Seleccione...", "Sí", "No"]} 
            />
            <FormField 
              label="¿Tiene EPS?" 
              name="eps" 
              options={["Seleccione...", "Sí", "No"]} 
            />

            {/* Fila 11: Discapacidad - ocupa 2 columnas */}
            <FormField 
              label="¿Tiene discapacidad?" 
              name="discapacidad" 
              options={["Seleccione...", "Sí", "No"]} 
              gridColumn="1 / -1"
            />

            {/* Título - Datos Acudiente */}
            <SectionTitle>DATOS ACUDIENTE</SectionTitle>

            {/* Fila 12: Nombres Acudiente */}
            <FormField label="Primer nombre" name="primerAcudiente" />
            <FormField label="Segundo nombre" name="segundoAcudiente" />

            {/* Fila 13: Apellidos Acudiente - ocupa 2 columnas */}
            <FormField label="Apellidos" name="apellidosAcudiente" gridColumn="1 / -1" />

            {/* Fila 14: Contacto Acudiente */}
            <FormField label="Teléfono celular" name="telefonoAcudiente" />
            <FormField label="Correo Acudiente" name="correoAcudiente" type="email" />

            {/* Botones */}
            <div style={{ 
              gridColumn: '1 / -1', 
              textAlign: 'center',
              marginTop: '20px'
            }}>
              <button 
                type="submit"
                style={{
                  backgroundColor: '#00264C',
                  color: 'white',
                  padding: '12px 30px',
                  border: 'none',
                  borderRadius: '5px',
                  cursor: 'pointer',
                  fontSize: '16px',
                  fontWeight: 'bold',
                  marginRight: '10px',
                  fontFamily: 'Arial'
                }}
              >
                Guardar Solicitud
              </button>
              
              <Link 
                to="/"
                style={{
                  backgroundColor: '#6c757d',
                  color: 'white',
                  padding: '12px 30px',
                  textDecoration: 'none',
                  borderRadius: '5px',
                  display: 'inline-block',
                  fontSize: '16px'
                }}
              >
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
