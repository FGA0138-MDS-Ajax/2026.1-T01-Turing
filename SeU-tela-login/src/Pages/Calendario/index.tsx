import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Calendar, dateFnsLocalizer, Views, type View } from 'react-big-calendar'
import { format, parse, startOfWeek, getDay } from 'date-fns'
import { ptBR } from 'date-fns/locale/pt-BR'
import axios from 'axios'
import BrandLogo from '../../components/BrandLogo'
import ThemeToggle from '../../components/ThemeToggle'
import 'react-big-calendar/lib/css/react-big-calendar.css'
import './style.css'

const locales = {
  'pt-BR': ptBR,
}

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales,
})

type EventoCalendario = {
  title: string
  start: Date
  end: Date
}

type HorarioOcupacao = {
  inicioPeriodo: string
  inicioHora: string
  fimHora: string
  descricaoOcupacao: string
  status: string
}

export default function CalendarioAdmin() {
  const navigate = useNavigate()
  const [eventos, setEventos] = useState<EventoCalendario[]>([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')
  const [view, setView] = useState<View>(Views.MONTH)
  const [dataAtual, setDataAtual] = useState(new Date())

  useEffect(() => {
    const buscarOcupacoes = async () => {
      setCarregando(true)
      setErro('')
      try {
        const token = localStorage.getItem('token')

        const resposta = await axios.get<HorarioOcupacao[]>('http://localhost:8080/horarios/todos', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })

        const eventosFormatados = resposta.data.map((horario) => {
          const dataInicio = new Date(`${horario.inicioPeriodo}T${horario.inicioHora}`)
          const dataFim = new Date(`${horario.inicioPeriodo}T${horario.fimHora}`)

          return {
            title: `${horario.descricaoOcupacao} (${horario.status})`,
            start: dataInicio,
            end: dataFim,
          }
        })

        setEventos(eventosFormatados)
      } catch (erro) {
        console.error('Erro ao buscar as ocupações:', erro)
        setErro('Não foi possível carregar as ocupações do calendário.')
      } finally {
        setCarregando(false)
      }
    }

    buscarOcupacoes()
  }, [])

  function handleSair() {
    localStorage.removeItem('token')
    navigate('/')
  }

  return (
    <div className="calendar-page">
      <header className="app-header">
        <div className="app-header__inner">
          <BrandLogo />

          <nav className="app-nav" aria-label="Navegação principal">
            <span className="app-nav__item app-nav__item--active">▦ Calendário</span>
            <span className="app-nav__item">☑ Reservas</span>
            <span className="app-nav__item">⚙ Administração</span>
          </nav>

          <div className="app-header__actions">
            <span className="app-admin-badge">◆ Admin</span>
            <ThemeToggle />
            <button type="button" className="app-logout" onClick={handleSair}>
              Sair
            </button>
          </div>
        </div>
      </header>

      <main className="calendar-main">
        <section className="calendar-hero" aria-labelledby="calendar-title">
          <div className="calendar-hero__copy">
            <span className="calendar-eyebrow">Visão administrativa · FCTE</span>
            <h1 id="calendar-title">Ocupação dos espaços acadêmicos</h1>
            <p>
              Acompanhe a disponibilidade e os horários reservados em uma visualização mais limpa,
              responsiva e alinhada à identidade do Seu espaço UnB.
            </p>
          </div>

          <div className="calendar-stats" aria-label="Resumo do calendário">
            <div className="calendar-stat">
              <span>Ocupações</span>
              <strong>{eventos.length}</strong>
            </div>
            <div className="calendar-stat">
              <span>Visualização</span>
              <strong>{view === Views.MONTH ? 'Mês' : view === Views.WEEK ? 'Semana' : view === Views.DAY ? 'Dia' : 'Agenda'}</strong>
            </div>
          </div>
        </section>

        <section className="calendar-shell" aria-label="Calendário de ocupações">
          <div className="calendar-shell__header">
            <div>
              <h2>Calendário de reservas</h2>
              <p>Dados carregados pela integração existente com o backend.</p>
            </div>
            <span className={`calendar-status ${erro ? 'calendar-status--danger' : ''}`}>
              <span className="calendar-status__dot" />
              {erro ? 'Falha ao carregar' : carregando ? 'Carregando' : 'Sincronizado'}
            </span>
          </div>

          <div className="calendar-surface">
            {carregando && <div className="calendar-loading">Carregando ocupações...</div>}
            {erro && <div className="calendar-error">{erro}</div>}

            <Calendar
              localizer={localizer}
              events={eventos}
              view={view}
              onView={(novaView) => setView(novaView)}
              date={dataAtual}
              onNavigate={(novaData) => setDataAtual(novaData)}
              startAccessor="start"
              endAccessor="end"
              style={{ height: 650 }}
              culture="pt-BR"
              messages={{
                next: 'Próximo',
                previous: 'Anterior',
                today: 'Hoje',
                month: 'Mês',
                week: 'Semana',
                day: 'Dia',
                agenda: 'Agenda',
                noEventsInRange: 'Nenhuma ocupação neste período.'
              }}
            />
          </div>
        </section>
      </main>
    </div>
  )
}
