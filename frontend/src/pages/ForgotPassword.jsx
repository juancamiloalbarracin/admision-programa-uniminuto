import { useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!email) {
      setError('Por favor ingrese su correo electrónico');
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError('Por favor ingrese un correo electrónico válido');
      return;
    }

    setLoading(true);
    setError('');

    try {
      // Simulación - en producción conectar con API
      await new Promise(resolve => setTimeout(resolve, 2000));
      setSuccess(true);
    } catch (error) {
      setError('Error al enviar el correo. Intente nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div className="login-container">
        <div className="login-header">
          Sistema de Admisiones Uniminuto
        </div>
        
        <div className="login-content">
          <div className="login-title">
            ✅ Correo Enviado
          </div>
          
          <div className="alert alert-success">
            <h4>Revise su correo electrónico</h4>
            <p>Hemos enviado las instrucciones para restablecer su contraseña a <strong>{email}</strong></p>
            <p>Si no recibe el correo en unos minutos, revise su carpeta de spam.</p>
          </div>
          
          <div className="login-buttons">
            <Link to="/login" className="btn btn-full-width">
              Volver al Login
            </Link>
            
            <div className="signup-link">
              <button 
                onClick={() => {setSuccess(false); setEmail(''); setError('');}}
                style={{ background: 'none', border: 'none', color: '#00264C', textDecoration: 'underline', cursor: 'pointer' }}
              >
                Enviar de nuevo
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="login-container">
      <div className="login-header">
        Sistema de Admisiones Uniminuto
      </div>
      
      <div className="login-content">
        <div className="login-title">
          Recuperar Contraseña
        </div>
        
        <p style={{ textAlign: 'center', color: '#6b7280', marginBottom: '25px', lineHeight: '1.5' }}>
          Ingrese su correo electrónico y le enviaremos las instrucciones para restablecer su contraseña.
        </p>
        
        <form onSubmit={handleSubmit}>
          <div className="login-field">
            <label>Correo Electrónico *</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={loading}
              placeholder="ejemplo@correo.com"
              className={error ? 'error' : ''}
            />
            {error && <span className="field-error">{error}</span>}
          </div>

          <div className="login-buttons">
            <button 
              type="submit" 
              className="btn btn-full-width"
              disabled={loading}
            >
              {loading ? 'Enviando...' : 'Enviar Instrucciones'}
            </button>
            
            <div className="signup-link">
              <Link to="/login">← Volver al Login</Link>
            </div>
            
            <div className="signup-link">
              ¿No tienes cuenta? <Link to="/signup">Crear Cuenta</Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ForgotPassword;
