import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { ProtectedRoute } from './components/ProtectedRoute'; // Importe o protetor de aluno
import { AdminRoute } from './components/AdminRoute'; // IMPORTAÇÃO NOVA: Importe o protetor de admin
import Login from './Pages/Login'
import Cadastro from './Pages/Cadastro'
import TelaInicial from './Pages/TelaInicial';
import CalendarioAdmin from './Pages/Calendario'
import Agendar from './Pages/Agendar' 
import AgendarSala from './Pages/AgendarSala';
import Agendamentos from './Pages/Agendar/Agendamentos';
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

        {/* Rotas Protegidas (Para qualquer usuário logado) */}
        <Route path="/agendar" element={
          <ProtectedRoute><Agendar /></ProtectedRoute>
        } />
        <Route path="/agendar/sala/:id" element={
          <ProtectedRoute><AgendarSala /></ProtectedRoute>
        } />
        <Route path="/agendamentos" element={
          <ProtectedRoute><Agendamentos /></ProtectedRoute>
        } />

        {/* Rotas Exclusivas de Admin (Substituímos ProtectedRoute por AdminRoute aqui) */}
        <Route path="/admin/calendario" element={
          <AdminRoute><CalendarioAdmin /></AdminRoute>
        } />
        <Route path="/admin" element={
          <AdminRoute><Admin /></AdminRoute>
        } />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)