import { useState, useMemo } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Search, MapPin, Building2, Users, CalendarPlus } from 'lucide-react';
import { Header } from '../../components/Header';
import { HorariosModal } from '../../components/HorariosModel';
import './style.css';
 
export const Agendar = () => {
  const [isLogged, setIsLogged] = useState(true);
  const [busca, setBusca] = useState('');
  const [campus, setCampus] = useState('Todos');
  const [predio, setPredio] = useState('Todos');
  const [capacidade, setCapacidade] = useState(0);
  const [selectedRoom, setSelectedRoom] = useState<any | null>(null);

  const salas = [
    { id: 1, nome: 'Sala S10', campus: 'FCTE', predio: 'UAC', capacidade: 50 },
    { id: 2, nome: 'Sala I7', campus: 'FCTE', predio: 'UED', capacidade: 20 },
    { id: 3, nome: 'Sala ?', campus: 'FCTE', predio: 'UED', capacidade: 150 },
    { id: 4, nome: 'Sala ?', campus: 'FCTE', predio: 'UED', capacidade: 60 },
];

  const salasFiltradas = useMemo(() => {
    return salas.filter((s) => {
      const matchNome = s.nome.toLowerCase().includes(busca.toLowerCase());
      const matchCampus = campus === 'Todos' || s.campus === campus;
      const matchPredio = predio === 'Todos' || s.predio === predio;
      const matchCap = s.capacidade >= capacidade;
      return matchNome && matchCampus && matchPredio && matchCap;
    });
  }, [busca, campus, predio, capacidade]);

  return (
    <>
      <Header isLogged={isLogged} onToggleLogin={() => setIsLogged(!isLogged)} />

      <div className="agendar-container">
        <header className="hero">
          <h1>Agendar uma sala</h1>
          <p>Use os filtros abaixo para localizar a sala ideal para o seu grupo.</p>
          
          <div className="search-wrapper">
            <Search className="search-icon" size={20} />
            <input 
              placeholder="Buscar por nome da sala (ex.: A1-01)" 
              value={busca}
              onChange={(e) => setBusca(e.target.value)}
            />
          </div>
          
          <div className="filters-row">
            <div className="filter-group">
              <MapPin size={18} />
              <select onChange={(e) => setCampus(e.target.value)}>
                <option value="Todos">Todos os campus</option>
                <option value="FCTE">FCTE</option>
              </select>
            </div>

            <div className="filter-group">
              <Building2 size={18} />
              <select onChange={(e) => setPredio(e.target.value)}>
                <option value="Todos">Todos os prédios</option>
                <option value="UED">UED</option>
                <option value="UAC">UAC</option>
                <option value="ULEG">ULEG</option>
              </select>
            </div>

            <div className="filter-group">
              <Users size={18} />
              <input 
                type="number" 
                placeholder="Min. pessoas" 
                min="0" 
                onChange={(e) => {
                  const val = parseInt(e.target.value);
                  setCapacidade(val >= 0 ? val : 0); 
                }} 
              />
            </div>
          </div>
        </header>

        <div className="grid-salas">
          {salasFiltradas.length > 0 ? (
            salasFiltradas.map((sala) => (
              <motion.div 
                key={sala.id} 
                className="sala-card" 
                whileHover={{ y: -5 }} 
                onClick={() => setSelectedRoom(sala)}
              >
                <h3>{sala.nome}</h3>
                <div className="card-info">
                  <span><MapPin size={14}/> {sala.campus}</span>
                  <span><Building2 size={14}/> {sala.predio}</span>
                  <span><Users size={14}/> {sala.capacidade} lugares</span>
                </div>
                <button className="btn-ver-horarios"><CalendarPlus size={16}/> Ver horários</button>
              </motion.div>
            ))
          ) : (
            <p style={{ textAlign: 'center', marginTop: '20px' }}>Nenhuma sala encontrada com esses filtros.</p>
          )}
        </div>

                  <AnimatePresence>
            {selectedRoom && (
              <HorariosModal 
                sala={selectedRoom} 
                onClose={() => setSelectedRoom(null)} 
              />
            )}
          </AnimatePresence>
      </div>
    </>
  );
};

export default Agendar;