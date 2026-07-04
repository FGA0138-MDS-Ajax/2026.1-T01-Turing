import { useState, useMemo, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Search, MapPin, Building2, Users, CalendarPlus, ChevronLeft, ChevronRight } from 'lucide-react';
import { Header } from '../../components/Header';
import './style.css';
 
export const Agendar = () => {
  const navigate = useNavigate();
  const [isLogged, setIsLogged] = useState(!!localStorage.getItem('token'));
  const [busca, setBusca] = useState('');
  const [campus, setCampus] = useState('Todos');
  const [predio, setPredio] = useState('Todos');
  const [capacidade, setCapacidade] = useState(0);
  const [salas, setSalas] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Estados da Paginação
  const [paginaAtual, setPaginaAtual] = useState(1);
  const itensPorPagina = 6; 

  useEffect(() => {
    setLoading(true);
    fetch('https://two026-turing.onrender.com/turing/salas')
      .then((res) => res.json())
      .then((data) => {
        setSalas(data || []);
      })
      .catch((err) => {
        console.error("Erro ao buscar salas:", err);
      })
      .finally(() => setLoading(false));
  }, []);

  const salasFiltradas = useMemo(() => {
    return salas.filter((s) => {
      const matchNome = s.nome?.toLowerCase().includes(busca.toLowerCase());
      const matchCap = s.capacidade >= capacidade;
      const matchCampus = campus === 'Todos' || (s.localizacao && s.localizacao.includes(campus));
      const matchPredio = predio === 'Todos' || (s.localizacao && s.localizacao.includes(predio));
      return matchNome && matchCap && matchCampus && matchPredio;
    });
  }, [busca, capacidade, campus, predio, salas]);

  const salasPaginadas = useMemo(() => {
    const inicio = (paginaAtual - 1) * itensPorPagina;
    return salasFiltradas.slice(inicio, inicio + itensPorPagina);
  }, [salasFiltradas, paginaAtual]);

  const totalPaginas = Math.ceil(salasFiltradas.length / itensPorPagina) || 1;

  const handleAbrirSala = (sala: any) => {
    if (!localStorage.getItem('token')) {
      alert("Você precisa estar logado para agendar!");
      navigate('/login');
      return;
    }
    navigate(`/agendar/sala/${sala.id}`);
  };

  return (
    <>
      <Header isLogged={isLogged} />

      <div className="agendar-container">
        <header className="hero">
          <h1>Agendar uma sala</h1>
          <p>Use os filtros abaixo para localizar a sala ideal para o seu grupo.</p>
          
          <div className="search-wrapper">
            <Search className="search-icon" size={20} />
            <input placeholder="Buscar por nome da sala..." value={busca} onChange={(e) => setBusca(e.target.value)} />
          </div>
          
          <div className="filters-row">
            <div className="filter-group">
              <MapPin size={18} />
              <select onChange={(e) => { setCampus(e.target.value); setPaginaAtual(1); }}>
                <option value="Todos">Todos os campus</option>
                <option value="FCTE">FCTE</option>
              </select>
            </div>
            <div className="filter-group">
              <Building2 size={18} />
              <select onChange={(e) => { setPredio(e.target.value); setPaginaAtual(1); }}>
                <option value="Todos">Todos os prédios</option>
                <option value="UED">UED</option>
                <option value="UAC">UAC</option>
                <option value="ULEG">ULEG</option>
              </select>
            </div>
            <div className="filter-group">
              <Users size={18} />
              <input type="number" min={0} placeholder="Min. pessoas" value={capacidade}  onChange={(e) => { const valor = Math.max(0, Number(e.target.value));
        setCapacidade(valor);
    }}
/>
            </div>
          </div>
        </header>

        <div className="grid-salas">
          {loading ? (
            <p style={{ textAlign: 'center', marginTop: '20px' }}>Carregando salas...</p>
          ) : salasPaginadas.length > 0 ? (
            salasPaginadas.map((sala) => (
              <motion.div key={sala.id} className="sala-card" whileHover={{ y: -5 }}>
                <h3>{sala.nome}</h3>
                <div className="card-info">
                  <span><MapPin size={14}/> {sala.localizacao}</span>
                  <span><Users size={14}/> {sala.capacidade} lugares</span>
                </div>
                <button className="btn-ver-horarios" onClick={() => handleAbrirSala(sala)}>
                  <CalendarPlus size={16}/> Ver horários
                </button>
              </motion.div>
            ))
          ) : (
            <p style={{ textAlign: 'center', marginTop: '20px' }}>Nenhuma sala encontrada.</p>
          )}
        </div>

        {totalPaginas > 1 && (
          <div className="paginacao" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '20px', marginTop: '30px' }}>
            <button onClick={() => setPaginaAtual(p => Math.max(1, p - 1))} disabled={paginaAtual === 1}><ChevronLeft /></button>
            <span>Página {paginaAtual} de {totalPaginas}</span>
            <button onClick={() => setPaginaAtual(p => Math.min(totalPaginas, p + 1))} disabled={paginaAtual === totalPaginas}><ChevronRight /></button>
          </div>
        )}
      </div>
    </>
  );
};

export default Agendar;