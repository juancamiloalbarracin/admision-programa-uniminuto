import { Link } from 'react-router-dom';

const TermsAndConditions = () => {
  return (
    <div style={{ maxWidth: '800px', margin: '20px auto', padding: '20px', backgroundColor: '#ffffff', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0,0,0,0.1)' }}>
      <div style={{ background: '#00264C', color: 'white', padding: '20px', borderRadius: '8px', marginBottom: '30px', textAlign: 'center' }}>
        <h1>Términos y Condiciones</h1>
        <p>Sistema de Admisiones Uniminuto</p>
      </div>

      <div style={{ lineHeight: '1.6', color: '#374151' }}>
        <h2 style={{ color: '#00264C', marginTop: '30px' }}>1. Aceptación de Términos</h2>
        <p>
          Al acceder y utilizar el Sistema de Admisiones Uniminuto, usted acepta estar sujeto a estos términos y condiciones de uso. 
          Si no está de acuerdo con alguna parte de estos términos, no debe utilizar nuestro servicio.
        </p>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>2. Uso del Sistema</h2>
        <p>
          El sistema está destinado exclusivamente para el proceso de admisiones a los programas académicos de la Corporación Universitaria Minuto de Dios - UNIMINUTO.
        </p>
        <ul>
          <li>Debe proporcionar información veraz y actualizada</li>
          <li>Es responsable de mantener la confidencialidad de su cuenta</li>
          <li>No debe compartir sus credenciales de acceso</li>
          <li>Debe notificar inmediatamente cualquier uso no autorizado</li>
        </ul>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>3. Información Personal</h2>
        <p>
          La información personal que proporcione será utilizada únicamente para el proceso de admisiones y será tratada de acuerdo con nuestra política de privacidad y la normatividad vigente de protección de datos personales.
        </p>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>4. Responsabilidades del Usuario</h2>
        <ul>
          <li>Completar toda la información requerida de manera precisa</li>
          <li>Subir documentos legibles y actualizados</li>
          <li>Cumplir con los plazos establecidos para el proceso</li>
          <li>Seguir las instrucciones proporcionadas por la institución</li>
        </ul>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>5. Disponibilidad del Servicio</h2>
        <p>
          UNIMINUTO se esfuerza por mantener el sistema disponible, pero no garantiza que el servicio esté libre de interrupciones. 
          Podemos realizar mantenimientos programados y actualizaciones que puedan afectar temporalmente la disponibilidad.
        </p>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>6. Modificaciones</h2>
        <p>
          UNIMINUTO se reserva el derecho de modificar estos términos en cualquier momento. 
          Las modificaciones entrarán en vigor inmediatamente después de su publicación en el sistema.
        </p>

        <h2 style={{ color: '#00264C', marginTop: '30px' }}>7. Contacto</h2>
        <p>
          Para cualquier pregunta sobre estos términos y condiciones, puede contactarnos a través de los canales oficiales de UNIMINUTO.
        </p>

        <div style={{ background: '#f8fafc', padding: '20px', borderRadius: '8px', marginTop: '30px', border: '1px solid #e2e8f0' }}>
          <p style={{ margin: '0', fontSize: '14px', color: '#64748b' }}>
            <strong>Última actualización:</strong> Agosto 2025<br />
            <strong>Versión:</strong> 1.0
          </p>
        </div>
      </div>

      <div style={{ textAlign: 'center', marginTop: '30px', paddingTop: '20px', borderTop: '1px solid #e5e7eb' }}>
        <Link 
          to="/signup" 
          style={{ 
            display: 'inline-block',
            background: 'linear-gradient(135deg, #00264C 0%, #0033CC 100%)',
            color: 'white',
            padding: '12px 24px',
            borderRadius: '8px',
            textDecoration: 'none',
            fontWeight: '600',
            marginRight: '15px'
          }}
        >
          Aceptar y Continuar
        </Link>
        <Link 
          to="/login" 
          style={{ 
            display: 'inline-block',
            background: '#6b7280',
            color: 'white',
            padding: '12px 24px',
            borderRadius: '8px',
            textDecoration: 'none',
            fontWeight: '600'
          }}
        >
          Volver
        </Link>
      </div>
    </div>
  );
};

export default TermsAndConditions;
