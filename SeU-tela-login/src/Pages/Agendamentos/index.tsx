import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Header } from '../../components/Header';
import { Calendar, Clock, MapPin, Trash2 } from 'lucide-react';
import './style.css'; 

export const Agendamentos = () => {
  const [minhasReservas, setMinhasReservas] = useState<any[]>([]);

  const carregarReservas = async () => {
    const token = localStorage.getItem('token');
    
    if (!token) {
      console.warn("Usuário não está logado ou token ausente.");
      return;
    }

    try {
      const resposta = await fetch('https://two026-turing.onrender.com/turing/solicitacoes/minhas', {
        method: 'GET',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json' 
        }
      });
      
      if (resposta.ok) {
        const dados = await resposta.json();
        console.log(dados);

        setMinhasReservas(dados);
      } else if (resposta.status === 401) {
        console.error("Token inválido ou expirado. Faça login novamente.");
      } else {
        console.error("Erro ao buscar reservas:", resposta.status);
      }
    } catch (e) {
      console.error("Falha na conexão com o servidor", e);
    }
  };

  useEffect(() => {
    carregarReservas();
  }, []);


  
  const cancelarReserva = async (id: number) => {
    const token = localStorage.getItem('token');
    if (!token) return;

    try {
      const resposta = await fetch(`https://two026-turing.onrender.com/turing/solicitacoes/${id}/cancelar`, {
        method: 'PATCH',
        headers: { 
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
      
      if (resposta.ok) {
        setMinhasReservas(minhasReservas.filter((reserva: any) => reserva.id !== id));
        alert("Reserva cancelada com sucesso!");
      } else {
        alert("Não foi possível cancelar a reserva.");
      }
    } catch (e) {
      alert("Erro de conexão ao cancelar.");
    }
  };

  const statusTraduzido = {
    PENDENTE: "🟡 Pendente",
    APROVADA: "🟢 Aprovada",
    REJEITADA: "🔴 Rejeitada",
    CANCELADA: "⚫ Cancelada"
};



  return (
    <>
      <Header isLogged={true} />
      
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
                {minhasReservas.map((reserva: any) => (
                  <motion.div 
                    key={reserva.id} 
                    className="reserva-card" 
                    initial={{ opacity: 0, y: 20 }} 
                    animate={{ opacity: 1, y: 0 }}
                    exit={{ opacity: 0, scale: 0.95 }}
                    whileHover={{ y: -5 }}
                  >
                    <div className="card-header">
                      <h3>{reserva.horarioSala.sala.nome} • Reserva #{reserva.id}</h3>
                      <button className="delete-btn" onClick={() => cancelarReserva(reserva.id)}>
                        <Trash2 size={18} />
                      </button>
                    </div>
                    <div className="card-details">
                      <span>
                    <Calendar size={14}/>{reserva.dataUso}
                  </span>

                  <span>
                    <Clock size={14}/>
                    {reserva.horarioSala.inicioHora.slice(0,5)} - {reserva.horarioSala.fimHora.slice(0,5)}
                  </span>
                  <span>
                    <MapPin size={14}/>
                   {reserva.horarioSala.sala.localizacao}
                  </span>
                      <span> <Clock size={14} />  Status: {statusTraduzido[reserva.status] ?? reserva.status}</span>
                      <span><MapPin size={14} /> Motivo: {reserva.motivo}</span>
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