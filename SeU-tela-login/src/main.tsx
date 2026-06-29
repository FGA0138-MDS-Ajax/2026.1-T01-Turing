import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './Pages/Login'
import Cadastro from './Pages/Cadastro'
import TelaInicial from './Pages/TelaInicial';
import './index.css'


createRoot(document.getElementById('root')!).render(
  <StrictMode>
     <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/" element={<TelaInicial />} />
        <Route path="/home" element={<TelaInicial />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
