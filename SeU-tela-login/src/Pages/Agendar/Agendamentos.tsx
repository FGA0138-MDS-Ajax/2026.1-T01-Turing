import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Header } from '../../components/Header';
import { Calendar, Clock, MapPin, Trash2 } from 'lucide-react';
import './style-agendamentos.css'; 

export const Agendamentos = () => {
  const [isLogged, setIsLogged] = useState(true);
  const [minhasReservas, setMinhasReservas] = useState([
    { id: 1, sala: 'Sala S10', data: '28/06/2026', horario: '14:00 - 16:00', campus: 'FCTE' },
  ]);

  const cancelarReserva = (id: number) => {
    setMinhasReservas(minhasReservas.filter((reserva) => reserva.id !== id)); 
  };

  return (
    <>
      <Header isLogged={isLogged} onToggleLogin={() => setIsLogged(!isLogged)} />
      
      <div className="agendar-container">
        <main className="content">
          <header className="hero">
            <h1>Minhas reservas</h1>
            <p>Acompanhe e cancele suas reservas de salas.</p>
          </header>

          {minhasReservas.length === 0 ? (
            <motion.div className="empty-state" initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
              <p>Você ainda não tem reservas.</p>
              <a href="/agendar">Vá em "Agendar" para reservar uma sala.</a>
            </motion.div>
          ) : (
            <div className="reservas-grid">
              <AnimatePresence>
                {minhasReservas.map((reserva) => (
                  <motion.div 
                    key={reserva.id} 
                    className="reserva-card" 
                    initial={{ opacity: 0, y: 20 }} 
                    animate={{ opacity: 1, y: 0 }}
                    exit={{ opacity: 0, scale: 0.95 }}
                    whileHover={{ y: -5 }}
                  >
                    <div className="card-header">
                      <h3>{reserva.sala}</h3>
                      <button className="delete-btn" onClick={() => cancelarReserva(reserva.id)}>
                        <Trash2 size={18} />
                      </button>
                    </div>
                    <div className="card-details">
                      <span><Calendar size={14} /> {reserva.data}</span>
                      <span><Clock size={14} /> {reserva.horario}</span>
                      <span><MapPin size={14} /> {reserva.campus}</span>
                    </div>
                  </motion.div>
                ))}
              </AnimatePresence>
            </div>
          )}
        </main>
      </div>
    </>
  );
};

export default Agendamentos;