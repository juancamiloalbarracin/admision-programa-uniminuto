import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './utils/AuthContext';
import Login from './pages/Login';
import './App.css';

// Componente de prueba simple
function TestDashboard() {
  return (
    <div className="app-container">
      <header className="app-header">Test App</header>
      <main className="app-content">
        <div className="info-cards">
          <div className="info-card">
            <h3>Test Card</h3>
            <p>Si ves esto, la aplicación está funcionando.</p>
          </div>
        </div>
      </main>
      <nav className="app-navigation">
        <button className="nav-button">Test Button</button>
      </nav>
    </div>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/test" element={<TestDashboard />} />
            <Route path="/" element={<Navigate to="/test" replace />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
