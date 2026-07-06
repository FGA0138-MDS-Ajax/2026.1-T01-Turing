import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Login from './Pages/Login'
import Cadastro from './Pages/Cadastro'
import TelaInicial from './Pages/TelaInicial';
import CalendarioAdmin from './Pages/Calendario'
import Agendar from './Pages/Agendar' 
import AgendarSala from './Pages/AgendarSala';
import Agendamentos from './Pages/Agendamentos';
import Usuario from './Pages/Perfil';
import Admin from './Pages/Admin'

import './index.css'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
     <BrowserRouter>
      <Routes>
        {/* Rotas Públicas */}
        <Route path="/login" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/" element={<TelaInicial />} />
        <Route path="/home" element={<TelaInicial />} />
        <Route path="/perfil" element={<Usuario />} />
        
        {/* Rotas (Temporariamente sem o ProtectedRoute para você poder trabalhar) */}
        <Route path="/agendar" element={<Agendar />} />
        <Route path="/agendar/sala/:id" element={<AgendarSala />} />
        <Route path="/agendamentos" element={<Agendamentos />} />

        {/* Rotas de Admin (Temporariamente sem o AdminRoute) */}
        <Route path="/admin/calendario" element={<CalendarioAdmin />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)