import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './utils/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import InfoPersonal from './pages/InfoPersonal';
import InfoAcademica from './pages/InfoAcademica';
import Radicar from './pages/Radicar';
import NavBar from './components/NavBar';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            <Route 
              path="/dashboard" 
              element={
                <ProtectedRoute>
                  <div className="app-container">
                    <header className="app-header">App Administrativa</header>
                    <main className="app-content">
                      <Dashboard />
                    </main>
                    <NavBar />
                  </div>
                </ProtectedRoute>
              } 
            />
            <Route 
              path="/info-personal" 
              element={
                <ProtectedRoute>
                  <div className="app-container">
                    <header className="app-header">Información Personal</header>
                    <main className="app-content">
                      <InfoPersonal />
                    </main>
                    <NavBar />
                  </div>
                </ProtectedRoute>
              } 
            />
            <Route 
              path="/info-academica" 
              element={
                <ProtectedRoute>
                  <div className="app-container">
                    <header className="app-header">Información Académica</header>
                    <main className="app-content">
                      <InfoAcademica />
                    </main>
                    <NavBar />
                  </div>
                </ProtectedRoute>
              } 
            />
            <Route 
              path="/radicar" 
              element={
                <ProtectedRoute>
                  <div className="app-container">
                    <header className="app-header">Radicar Solicitud</header>
                    <main className="app-content">
                      <Radicar />
                    </main>
                    <NavBar />
                  </div>
                </ProtectedRoute>
              } 
            />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
