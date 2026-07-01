import { useState, useEffect } from 'react';
import { Calendar, dateFnsLocalizer, Views } from 'react-big-calendar';
import { format, parse, startOfWeek, getDay } from 'date-fns';
import { ptBR } from 'date-fns/locale/pt-BR';
import axios from 'axios';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const locales = {
  'pt-BR': ptBR,
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales,
});

export default function CalendarioAdmin() {
  const [eventos, setEventos] = useState([]);
  
  const [view, setView] = useState<any>(Views.MONTH);
  const [dataAtual, setDataAtual] = useState(new Date());

  useEffect(() => {
    const buscarOcupacoes = async () => {
      try {
        const token = localStorage.getItem('token'); 

        const resposta = await axios.get('http://localhost:8080/horarios/todos', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        const eventosFormatados = resposta.data.map((horario: any) => {
          const dataInicio = new Date(`${horario.inicioPeriodo}T${horario.inicioHora}`);
          const dataFim = new Date(`${horario.inicioPeriodo}T${horario.fimHora}`);

          return {
            title: `${horario.descricaoOcupacao} (${horario.status})`,
            start: dataInicio,
            end: dataFim,
          };
        });

        setEventos(eventosFormatados);
      } catch (erro) {
        console.error("Erro ao buscar as ocupações:", erro);
      }
    };

    buscarOcupacoes();
  }, []);

  return (
    <div style={{ padding: '20px', backgroundColor: '#f4f4f4', height: '100vh', width: '100vw' }}>
      <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', boxShadow: '0 4px 6px rgba(0,0,0,0.1)' }}>
        <h2 style={{ marginBottom: '20px', color: '#333' }}>Visão Geral de Ocupação - FCTE</h2>
        
        <Calendar
          localizer={localizer}
          events={eventos}
          
          view={view}
          onView={(novaView) => setView(novaView)}
          date={dataAtual}
          onNavigate={(novaData) => setDataAtual(novaData)}

          startAccessor="start"
          endAccessor="end"
          style={{ height: 600 }}
          culture="pt-BR"
          messages={{
            next: "Próximo",
            previous: "Anterior",
            today: "Hoje",
            month: "Mês",
            week: "Semana",
            day: "Dia",
            agenda: "Agenda",
            noEventsInRange: "Nenhuma ocupação neste período."
          }}
        />
      </div>
    </div>
  );
}