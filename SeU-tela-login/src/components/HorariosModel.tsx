import { useState, useMemo, useEffect } from "react";
import { motion } from 'framer-motion';
import DatePicker, { registerLocale } from 'react-datepicker';
import { ptBR } from 'date-fns/locale/pt-BR';
import "react-datepicker/dist/react-datepicker.css";
import { X } from 'lucide-react';

registerLocale('pt-BR', ptBR);
 
export const HorariosModal = ({ sala, onClose }: { sala: any, onClose: () => void }) => {
  const [data, setData] = useState<Date | null>(new Date());
  const [horarios, setHorarios] = useState<any[]>([]);
  const [horarioSelecionado, setHorarioSelecionado] = useState<any | null>(null);
  const [etapa, setEtapa] = useState(1); // Controla o passo 1 e 2
  const [motivo, setMotivo] = useState('');


  useEffect(() => {
  const buscarHorarios = async () => {
    try {
      const resposta = await fetch(
        `https://two026-turing.onrender.com/turing/horarios/sala/${sala.id}`
      );

      if (!resposta.ok) {
        throw new Error();
      }

      const dados = await resposta.json();

      setHorarios(dados);

    } catch (erro) {
      console.error("Erro ao buscar horários", erro);
    }
  };

  buscarHorarios();
}, [sala.id]);

  const handleConfirmar = async () => {
    if (!data || horarioSelecionado === null ||  !motivo.trim()
) {
      alert("Por favor, preencha todos os campos.");
      return;
    }

    const token = localStorage.getItem('token');
    const dataFormatada = data.toISOString().split('T')[0];

    try {
      const resposta = await fetch('https://two026-turing.onrender.com/turing/solicitacoes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
        horarioSalaId: horarioSelecionado.id,
        dataUso: dataFormatada,
        motivo: motivo.trim()
    })
      });

      if (resposta.ok) {
        alert('Reserva confirmada com sucesso!');
        onClose();
      } else {
        const erro = await resposta.json();
        alert('Erro ao reservar: ' + (erro.message || 'Verifique sua conexão'));
      }
    } catch (e) {
      alert('Erro de conexão com o servidor.');
    }
  };

  return (
    <motion.div className="modal-overlay" onClick={onClose}>
      <motion.div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}><X size={20}/></button>
        <h2>{sala.nome}</h2>

        {etapa === 1 ? (
          <>
            <p>Selecione dia e horário:</p>
            <div className="calendar-wrapper">
              <DatePicker 
                inline 
                selected={data} 
                onChange={(date: Date | null) => { setData(date); setHorarioSelecionado(null); }} 
                locale="pt-BR" 
                minDate={new Date()} 
              />
            </div>
            <div className="horarios-grid">
              {horarios.length > 0 ? (
                horarios.map((horario) => (
                  <button
                    key={horario.id}
                    className={
                        horarioSelecionado?.id === horario.id
                            ? "active"
                            : ""
                    }
                    onClick={() => setHorarioSelecionado(horario)}
                >
                    {horario.inicioHora} - 
                {horario.fimHora}
                </button>
                ))
              ) : <p>Sem horários disponíveis hoje.</p>}
            </div>
            <button 
              className="btn-confirmar" 
              disabled={!horarioSelecionado} 
              onClick={() => setEtapa(2)}
            >
              Continuar
            </button>
          </>
        ) : (
          <>
            <p>Qual o motivo da reserva?</p>
            <textarea 
              value={motivo} 
              onChange={(e) => setMotivo(e.target.value)}
              placeholder="Ex: Reunião do grupo de estudos..."
              style={{ width: '100%', height: '100px', margin: '20px 0', borderRadius: '10px', padding: '10px', border: '1px solid #ccc' }}
            />
            <button className="btn-confirmar" onClick={handleConfirmar}>Finalizar Reserva</button>
            <button className="close-btn" style={{ position: 'relative', marginTop: '10px', background: 'transparent', width: '100%' }} onClick={() => setEtapa(1)}>
              Voltar
            </button>
          </>
        )}
      </motion.div>
    </motion.div>
  );
};