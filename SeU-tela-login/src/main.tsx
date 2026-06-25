import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './Pages/Login'
import Cadastro from './Pages/Cadastro'
import CalendarioAdmin from './Pages/Calendario'


createRoot(document.getElementById('root')!).render(
  <StrictMode>
     <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/admin/calendario" element={<CalendarioAdmin />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
