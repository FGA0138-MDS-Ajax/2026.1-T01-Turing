import { useState, useMemo } from 'react';
import { motion } from 'framer-motion';
import DatePicker, { registerLocale } from 'react-datepicker';
import { ptBR } from 'date-fns/locale/pt-BR';
import "react-datepicker/dist/react-datepicker.css";
import { X } from 'lucide-react';

registerLocale('pt-BR', ptBR);
 
export const HorariosModal = ({ sala, onClose }: { sala: any, onClose: () => void }) => {
  const [data, setData] = useState<Date | null>(new Date());
  const [horarioSelecionado, setHorarioSelecionado] = useState<string | null>(null);

  const horariosDisponiveis = useMemo(() => {
    const todos = ["08:00", "10:00", "14:00", "16:00"];
    const agora = new Date();

    if (data && data.toDateString() === agora.toDateString()) {
      const horaAtual = agora.getHours();
      return todos.filter((h) => parseInt(h.split(':')[0]) > horaAtual);
    }
    
    return todos;
  }, [data]);

  const handleConfirmar = () => {
    alert(`Reserva confirmada para a ${sala.nome} no dia ${data?.toLocaleDateString('pt-BR')} às ${horarioSelecionado}!`);
    onClose();
  };

  return (
    <motion.div className="modal-overlay" onClick={onClose}>
      <motion.div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}><X size={20}/></button>
        <h2>{sala.nome}</h2>
        <p>Selecione o dia e horário para reserva:</p>

        <div className="calendar-wrapper">
          <DatePicker 
            selected={data} 
            onChange={(date: Date | null) => {
              setData(date);
              setHorarioSelecionado(null); 
            }} 
            inline 
            locale="pt-BR"
            minDate={new Date()}
          />
        </div>

        <div className="horarios-grid">
          {horariosDisponiveis.length > 0 ? (
            horariosDisponiveis.map((h) => (
              <button 
                key={h} 
                className={horarioSelecionado === h ? 'active' : ''} 
                onClick={() => setHorarioSelecionado(h)}
              >
                {h}
              </button>
            ))
          ) : (
            <p style={{ gridColumn: 'span 2', textAlign: 'center', color: '#666' }}>
              Não há horários disponíveis para hoje.
            </p>
          )}
        </div>

        <button 
          className="btn-confirmar" 
          disabled={!horarioSelecionado}
          onClick={handleConfirmar}
        >
          Confirmar Reserva
        </button>
      </motion.div>
    </motion.div>
  );
};