import { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../utils/AuthContext';
import { infoPersonalService } from '../services/api';
import './InfoPersonal.css';

const InfoPersonal = () => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    primerNombre: '',
    segundoNombre: '', 
    apellidos: '',
    sexo: '',
    grupoSanguineo: '',
    tipoDocumento: '',
    numeroDocumento: '',
    fechaExpedicion: '',
    fechaNacimiento: '',
    paisNacimiento: '',
    ciudadNacimiento: '',
    telefonoCelular: '',
    correo: '',
    tipoDireccion: '', 
    direccion: '',
    casaApto: '',
    sisben: '',
    eps: '',
    discapacidad: '',
    primerAcudiente: '',
    segundoAcudiente: '',
    apellidosAcudiente: '',
    telefonoAcudiente: '',
    correoAcudiente: ''
  });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [initialDataLoaded, setInitialDataLoaded] = useState(false);

  const loadData = useCallback(async (showMessage = false) => {
    if (showMessage) {
      setLoading(true);
      setMessage('');
    }
    
    try {
      const response = await infoPersonalService.getInfo();
      if (response && response.success && response.data) {
        // Solo actualizar si no es una carga manual (showMessage=true) o si los datos están vacíos
        if (showMessage) {
          setFormData(response.data);
        } else {
          // En carga inicial, solo actualizar si formData está vacío
          setFormData(prevData => {
            const hasData = Object.values(prevData).some(value => value && value.trim() !== '');
            return hasData ? prevData : response.data;
          });
        }
        if (showMessage) {
          setMessage('Datos consultados exitosamente');
        }
      } else {
        if (showMessage) {
          setMessage('No se encontraron datos guardados anteriormente');
        }
      }
    } catch (error) {
      console.error('Error cargando información personal:', error);
      if (showMessage) {
        setMessage('Error al consultar los datos');
      }
    } finally {
      if (showMessage) {
        setLoading(false);
      }
    }
  }, []);

  useEffect(() => {
    if (!initialDataLoaded) {
      loadData();
      setInitialDataLoaded(true);
    }
  }, [initialDataLoaded]);

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setFormData(prev => {
      // Solo actualizar si el valor realmente cambió
      if (prev[name] === value) {
        return prev;
      }
      return {
        ...prev,
        [name]: value
      };
    });
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      const response = await infoPersonalService.saveInfo(formData);
      
      if (response.success) {
        setMessage('Información personal guardada exitosamente');
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
        Ingrese su información en cada una de las siguientes secciones. Los campos con un asterisco (*) rojo son obligatorios 
        y deben completarse para enviar la solicitud. Pueden ser requeridas preguntas adicionales en base a sus respuestas a una pregunta anterior.
      </div>

      <form onSubmit={handleSubmit}>
        <div className="form-grid">
          {/* Fila 1 */}
          <div className="form-group">
            <label>Primer nombre <span className="required">*</span></label>
            <input 
              key="primerNombre-input"
              type="text" 
              name="primerNombre" 
              value={formData.primerNombre || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>
          <div className="form-group">
            <label>Segundo nombre <span className="required">*</span></label>
            <input 
              key="segundoNombre-input"
              type="text" 
              name="segundoNombre" 
              value={formData.segundoNombre || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Fila 2 - Apellidos span completo */}
          <div className="form-group" style={{gridColumn: '1 / -1'}}>
            <label>Apellidos <span className="required">*</span></label>
            <input 
              key="apellidos-input"
              type="text" 
              name="apellidos" 
              value={formData.apellidos || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Fila 3 */}
          <div className="form-group">
            <label>Sexo Biológico <span className="required">*</span></label>
            <select 
              name="sexo" 
              value={formData.sexo}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="Masculino">Masculino</option>
              <option value="Femenino">Femenino</option>
            </select>
          </div>
          <div className="form-group">
            <label>Grupo Sanguíneo RH <span className="required">*</span></label>
            <select 
              name="grupoSanguineo" 
              value={formData.grupoSanguineo}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="A+">A+</option>
              <option value="A-">A-</option>
              <option value="B+">B+</option>
              <option value="B-">B-</option>
              <option value="AB+">AB+</option>
              <option value="AB-">AB-</option>
              <option value="O+">O+</option>
              <option value="O-">O-</option>
            </select>
          </div>

          {/* Sección Documento de Identidad */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            DOCUMENTO DE IDENTIDAD
          </div>

          {/* Fila 4 */}
          <div className="form-group">
            <label>Tipo de documento <span className="required">*</span></label>
            <select 
              name="tipoDocumento" 
              value={formData.tipoDocumento}
              onChange={handleChange}
              className="form-control"
              required
            >
              <option value="">Seleccione...</option>
              <option value="Cédula de Ciudadanía">Cédula de Ciudadanía</option>
              <option value="Tarjeta de Identidad">Tarjeta de Identidad</option>
              <option value="Cédula de Extranjería">Cédula de Extranjería</option>
              <option value="Pasaporte">Pasaporte</option>
            </select>
          </div>
          <div className="form-group">
            <label>Número de documento <span className="required">*</span></label>
            <input 
              key="numeroDocumento-input"
              type="text" 
              name="numeroDocumento" 
              value={formData.numeroDocumento || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Fila 5 */}
          <div className="form-group">
            <label>Fecha de expedición (DD/MM/AAAA) <span className="required">*</span></label>
            <input 
              key="fechaExpedicion-input"
              type="date" 
              name="fechaExpedicion" 
              value={formData.fechaExpedicion || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>
          <div className="form-group">
            <label>Fecha de nacimiento (DD/MM/AAAA) <span className="required">*</span></label>
            <input 
              key="fechaNacimiento-input"
              type="date" 
              name="fechaNacimiento" 
              value={formData.fechaNacimiento || ''}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Fila 6 */}
          <div className="form-group">
            <label>País de nacimiento <span className="required">*</span></label>
            <input 
              type="text" 
              name="paisNacimiento" 
              value={formData.paisNacimiento}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>
          <div className="form-group">
            <label>Ciudad de nacimiento <span className="required">*</span></label>
            <input 
              type="text" 
              name="ciudadNacimiento" 
              value={formData.ciudadNacimiento}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Sección Contacto */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            INFORMACIÓN DE CONTACTO
          </div>

          {/* Fila 7 */}
          <div className="form-group">
            <label>Teléfono celular <span className="required">*</span></label>
            <input 
              type="tel" 
              name="telefonoCelular" 
              value={formData.telefonoCelular}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>
          <div className="form-group">
            <label>Correo electrónico <span className="required">*</span></label>
            <input 
              type="email" 
              name="correo" 
              value={formData.correo}
              onChange={handleChange}
              className="form-control"
              required
            />
          </div>

          {/* Sección Dirección */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            DIRECCIÓN DE RESIDENCIA
          </div>

          <div className="form-group">
            <label>Tipo de dirección</label>
            <select 
              name="tipoDireccion" 
              value={formData.tipoDireccion}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Casa">Casa</option>
              <option value="Apartamento">Apartamento</option>
              <option value="Finca">Finca</option>
            </select>
          </div>
          <div className="form-group">
            <label>Dirección</label>
            <input 
              type="text" 
              name="direccion" 
              value={formData.direccion}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          {/* Sección Información Adicional */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            INFORMACIÓN ADICIONAL
          </div>

          <div className="form-group">
            <label>SISBEN</label>
            <select 
              name="sisben" 
              value={formData.sisben}
              onChange={handleChange}
              className="form-control"
            >
              <option value="">Seleccione...</option>
              <option value="Si">Sí</option>
              <option value="No">No</option>
            </select>
          </div>
          <div className="form-group">
            <label>EPS</label>
            <input 
              type="text" 
              name="eps" 
              value={formData.eps}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          {/* Sección Acudiente */}
          <div style={{gridColumn: '1 / -1', fontWeight: 'bold', fontSize: '16px', color: '#00264C', margin: '20px 0 10px 0'}}>
            INFORMACIÓN DEL ACUDIENTE
          </div>

          <div className="form-group">
            <label>Primer nombre del acudiente</label>
            <input 
              type="text" 
              name="primerAcudiente" 
              value={formData.primerAcudiente}
              onChange={handleChange}
              className="form-control"
            />
          </div>
          <div className="form-group">
            <label>Segundo nombre del acudiente</label>
            <input 
              type="text" 
              name="segundoAcudiente" 
              value={formData.segundoAcudiente}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          <div className="form-group" style={{gridColumn: '1 / -1'}}>
            <label>Apellidos del acudiente</label>
            <input 
              type="text" 
              name="apellidosAcudiente" 
              value={formData.apellidosAcudiente}
              onChange={handleChange}
              className="form-control"
            />
          </div>

          <div className="form-group">
            <label>Teléfono del acudiente</label>
            <input 
              type="tel" 
              name="telefonoAcudiente" 
              value={formData.telefonoAcudiente}
              onChange={handleChange}
              className="form-control"
            />
          </div>
          <div className="form-group">
            <label>Correo del acudiente</label>
            <input 
              type="email" 
              name="correoAcudiente" 
              value={formData.correoAcudiente}
              onChange={handleChange}
              className="form-control"
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
            {loading ? 'Guardando...' : 'Guardar Información'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default InfoPersonal;
