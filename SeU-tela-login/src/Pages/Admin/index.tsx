import { useState, useMemo, useEffect } from "react";
import { Ban, Edit, MapPin, Plus, ShieldCheck, Trash2 } from "lucide-react";
import { motion } from "framer-motion"; 
import { Header } from "../../components/Header"; 
import './style.css';


type Profile = { id: string | number; nome: string; email: string; cpf: string | null; papel: string };
type Sala = { id: string | number; nome: string; capacidade: number; localizacao: string };
type Block = { user_id: string | number; motivo: string };

function formatCpf(cpf: string | null | undefined) {
  const digits = (cpf ?? "").replace(/\D/g, "");
  if (digits.length !== 11) return cpf || "—";
  return `${digits.slice(0, 3)}.${digits.slice(3, 6)}.${digits.slice(6, 9)}-${digits.slice(9)}`;
}

export default function GestaoInfraestrutura() {
  const [abaAtiva, setAbaAtiva] = useState<'salas' | 'alunos'>('salas');
  const [profiles, setProfiles] = useState<Profile[]>([]);
  const [salas, setSalas] = useState<Sala[]>([]);
  const [blocks, setBlocks] = useState<Block[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [erroNaTela, setErroNaTela] = useState("");

  const [buscaAluno, setBuscaAluno] = useState("");
  const [buscaSala, setBuscaSala] = useState("");
  
  const [modalBloqueioAluno, setModalBloqueioAluno] = useState<Profile | null>(null);
  const [modalSala, setModalSala] = useState<Partial<Sala> | null>(null);
  const [modalAluno, setModalAluno] = useState<Profile | null>(null);

  useEffect(() => {
    carregarDados();
  }, []);

  async function carregarDados() {
    setErroNaTela("");
    setIsLoading(true);
    try {
      const token = localStorage.getItem("token"); 
      const headersSeguranca = {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      };

      const [resUsuarios, resSalas] = await Promise.all([
  fetch("https://two026-turing.onrender.com/usuarios", { headers: headersSeguranca }),
  fetch("https://two026-turing.onrender.com/salas", { headers: headersSeguranca })
]);

      if (!resUsuarios.ok || !resSalas.ok) {
        throw new Error("Sua sessão expirou ou você não tem permissão de Admin. Faça login novamente.");
      }

      const dadosUsuarios = await resUsuarios.json();
      const dadosSalas = await resSalas.json();

      setProfiles(dadosUsuarios.content ? dadosUsuarios.content : dadosUsuarios);
      setSalas(dadosSalas.content ? dadosSalas.content : dadosSalas);

    } catch (error) {
      if (error instanceof Error) setErroNaTela(error.message);
    } finally {
      setIsLoading(false);
    }
  }

  async function handleSalvarSala(e: React.FormEvent) {
  e.preventDefault();
  if (!modalSala) return;

  const isEdit = !!modalSala.id;
  const url = isEdit 
    ? `https://two026-turing.onrender.com/salas/${modalSala.id}` 
    : `https://two026-turing.onrender.com/salas`;
  
  try {
    const token = localStorage.getItem("token");
    const response = await fetch(url, {
      method: isEdit ? "PUT" : "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({
        nome: modalSala.nome,
        capacidade: modalSala.capacidade,
        localizacao: modalSala.localizacao
      })
    });

    if (!response.ok) throw new Error("Erro na requisição. Verifique o Swagger.");
    
    alert(`Sala ${isEdit ? 'atualizada' : 'criada'} com sucesso!`);
    setModalSala(null);
    carregarDados();
  } catch (error) {
    alert(error instanceof Error ? error.message : "Erro ao salvar.");
  }
}

  async function handleExcluirSala(id: string | number) {
  if (!window.confirm("Confirma a exclusão desta sala?")) return;
  try {
    const token = localStorage.getItem("token");
    const response = await fetch(`https://two026-turing.onrender.com/salas/${id}`, {
      method: "DELETE",
      headers: { "Authorization": `Bearer ${token}` }
    });
    
    if (!response.ok) throw new Error("Erro ao excluir. A sala pode estar em uso.");
    
    setSalas(prev => prev.filter(sala => sala.id !== id));
    alert("Sala removida!");
  } catch (error) {
    alert("Erro ao remover sala.");
  }
}

  async function handleSalvarAluno(e: React.FormEvent) {
    e.preventDefault();
    if (!modalAluno) return;

    try {
      const token = localStorage.getItem("token");
      const response = await fetch(`https://two026-turing.onrender.com/usuarios/${modalAluno.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(modalAluno)
      });

      if (!response.ok) throw new Error("Erro ao atualizar o aluno.");
      
      alert("Dados do aluno atualizados com sucesso!");
      setModalAluno(null);
      carregarDados();

    } catch (error) {
      alert(error instanceof Error ? error.message : "Erro desconhecido.");
    }
  }

  const handleToggleBlock = (studentId: string | number, block: boolean) => {
    if (block) {
      setBlocks(prev => [...prev, { user_id: studentId, motivo: "" }]);
      alert("Aluno bloqueado!");
      setModalBloqueioAluno(null);
    } else {
      setBlocks(prev => prev.filter(b => b.user_id !== studentId));
      alert("Aluno desbloqueado!");
    }
  };

  const blockedSet = useMemo(() => new Set(blocks.map((b) => b.user_id)), [blocks]);
  
  const alunosFiltrados = useMemo(() => {
    const term = buscaAluno.trim().toLowerCase();
    if (!term) return profiles;
    return profiles.filter((p) => p.nome?.toLowerCase().includes(term) || (p.cpf && p.cpf.includes(term)));
  }, [profiles, buscaAluno]);
  
  const salasFiltradas = useMemo(() => {
    const term = buscaSala.trim().toLowerCase();
    if (!term) return salas;
    return salas.filter((s) => s.nome?.toLowerCase().includes(term) || s.localizacao?.toLowerCase().includes(term));
  }, [salas, buscaSala]);

  return (
    <>
      <Header isLogged={true} />

      <motion.main 
        className="admin-container"
        initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.4 }}
      >
        <div className="topo-admin">
          <div className="topo-header">
            <div>
              <h1>Infraestrutura e Comunidade</h1>
              <p>Gerencie os espaços da faculdade e as permissões de acesso dos alunos.</p>
            </div>
          </div>
          <div className="tabs-container">
            <button className={`tab-button ${abaAtiva === 'salas' ? 'active' : ''}`} onClick={() => setAbaAtiva('salas')}>
              Salas e Horários
            </button>
            <button className={`tab-button ${abaAtiva === 'alunos' ? 'active' : ''}`} onClick={() => setAbaAtiva('alunos')}>
              Alunos (Comunidade)
            </button>
          </div>
        </div>

        {erroNaTela && (
          <div style={{ background: '#fee2e2', color: '#b91c1c', padding: '15px', borderRadius: '10px', marginBottom: '20px', textAlign: 'center' }}>
            <strong>Atenção:</strong> {erroNaTela}
          </div>
        )}

        {isLoading ? (
          <div style={{ textAlign: 'center', marginTop: '50px', color: '#6b7280' }}>Carregando dados do servidor...</div>
        ) : (
          <>
            {abaAtiva === 'salas' && (
              <motion.div className="aba-conteudo" initial={{ opacity: 0, x: -10 }} animate={{ opacity: 1, x: 0 }}>
                <div className="controles-secao">
                  <input type="text" className="pesquisa-input" placeholder="Buscar sala ou localização..." value={buscaSala} onChange={(e) => setBuscaSala(e.target.value)} />
                  <button className="btn-primario" onClick={() => setModalSala({ nome: "", capacidade: 0, localizacao: "" })}>
                    <Plus size={20} /> Nova Sala
                  </button>
                </div>

                <div className="lista-grid">
                  {salasFiltradas.length === 0 ? <p style={{ textAlign: 'center', color: '#6b7280' }}>Nenhuma sala cadastrada no banco.</p> : (
                    salasFiltradas.map((sala) => (
                      <div key={sala.id} className="card-item">
                        <div className="item-info">
                          <div className="item-icone" style={{ background: '#ecfdf5', color: '#059669' }}><MapPin size={24} /></div>
                          <div className="item-dados">
                            <h3>{sala.nome || "Sala sem nome"} <span className="badge-status ativo">Ativa</span></h3>
                            <p>{sala.localizacao || "Localização não informada"}</p>
                            <p>Capacidade: <span className="destaque">{sala.capacidade || 0} pessoas</span></p>
                          </div>
                        </div>
                        <div className="item-acoes">
                          <button className="btn-acao" onClick={() => setModalSala(sala)}>
                            <Edit size={16} /> Editar
                          </button>
                          <button className="btn-acao btn-perigo" onClick={() => handleExcluirSala(sala.id)}>
                            <Trash2 size={16} /> Excluir
                          </button>
                        </div>
                      </div>
                    ))
                  )}
                </div>
              </motion.div>
            )}

            {abaAtiva === 'alunos' && (
              <motion.div className="aba-conteudo" initial={{ opacity: 0, x: 10 }} animate={{ opacity: 1, x: 0 }}>
                <div className="controles-secao">
                  <input type="text" className="pesquisa-input" placeholder="Buscar por nome ou CPF..." value={buscaAluno} onChange={(e) => setBuscaAluno(e.target.value)} />
                </div>

                <div className="lista-grid">
                  {alunosFiltrados.length === 0 ? <p style={{ textAlign: 'center', color: '#6b7280' }}>Nenhum aluno cadastrado no banco.</p> : (
                    alunosFiltrados.map((p) => {
                      const isBlocked = blockedSet.has(p.id);
                      return (
                        <div key={p.id} className="card-item">
                          <div className="item-info">
                            <div className="item-icone">{p.nome ? p.nome.charAt(0).toUpperCase() : "U"}</div>
                            <div className="item-dados">
                              <h3>{p.nome || "Usuário sem nome"} {isBlocked && <span className="badge-status">Bloqueado</span>}</h3>
                              <p>{p.email}</p>
                              <p>CPF: <span className="destaque">{formatCpf(p.cpf)}</span></p>
                            </div>
                          </div>
                          <div className="item-acoes">
                            <button className="btn-acao" onClick={() => setModalAluno(p)}>
                              <Edit size={16} /> Editar
                            </button>
                            
                            {isBlocked ? (
                              <button className="btn-acao" onClick={() => handleToggleBlock(p.id, false)}><ShieldCheck size={16} /> Desbloquear</button>
                            ) : (
                              <button className="btn-acao btn-bloquear" onClick={() => setModalBloqueioAluno(p)}><Ban size={16} /> Bloquear</button>
                            )}
                          </div>
                        </div>
                      );
                    })
                  )}
                </div>
              </motion.div>
            )}
          </>
        )}

        {modalSala && (
          <div className="modal-overlay">
            <div className="modal-content" style={{ maxWidth: '400px' }}>
              <h2>{modalSala.id ? 'Editar Sala' : 'Nova Sala'}</h2>
              <form onSubmit={handleSalvarSala} style={{ display: 'flex', flexDirection: 'column', gap: '15px', marginTop: '15px' }}>
                <input required type="text" placeholder="Nome da Sala" className="pesquisa-input"
                  value={modalSala.nome || ''} onChange={(e) => setModalSala({...modalSala, nome: e.target.value})} />
                
                <input required type="number" placeholder="Capacidade" className="pesquisa-input"
                  value={modalSala.capacidade || ''} onChange={(e) => setModalSala({...modalSala, capacidade: Number(e.target.value)})} />
                
                <input required type="text" placeholder="Localização (Ex: Bloco A)" className="pesquisa-input"
                  value={modalSala.localizacao || ''} onChange={(e) => setModalSala({...modalSala, localizacao: e.target.value})} />

                <div className="modal-acoes">
                  <button type="button" className="btn-cancelar" onClick={() => setModalSala(null)}>Cancelar</button>
                  <button type="submit" className="btn-confirmar">Salvar</button>
                </div>
              </form>
            </div>
          </div>
        )}

        {modalAluno && (
          <div className="modal-overlay">
            <div className="modal-content" style={{ maxWidth: '400px' }}>
              <h2>Editar Aluno</h2>
              <form onSubmit={handleSalvarAluno} style={{ display: 'flex', flexDirection: 'column', gap: '15px', marginTop: '15px' }}>
                <input required type="text" placeholder="Nome" className="pesquisa-input"
                  value={modalAluno.nome || ''} onChange={(e) => setModalAluno({...modalAluno, nome: e.target.value})} />
                
                <input required type="email" placeholder="E-mail" className="pesquisa-input"
                  value={modalAluno.email || ''} onChange={(e) => setModalAluno({...modalAluno, email: e.target.value})} />
                
                <div className="modal-acoes">
                  <button type="button" className="btn-cancelar" onClick={() => setModalAluno(null)}>Cancelar</button>
                  <button type="submit" className="btn-confirmar">Atualizar</button>
                </div>
              </form>
            </div>
          </div>
        )}

        {modalBloqueioAluno && (
          <div className="modal-overlay">
            <div className="modal-content">
              <h2>Bloquear {modalBloqueioAluno.nome}?</h2>
              <p>O aluno continuará podendo entrar no sistema, mas não conseguirá criar novas reservas.</p>
              <div className="modal-acoes">
                <button className="btn-cancelar" onClick={() => setModalBloqueioAluno(null)}>Cancelar</button>
                <button className="btn-confirmar" onClick={() => handleToggleBlock(modalBloqueioAluno.id, true)}>Confirmar Bloqueio</button>
              </div>
            </div>
          </div>
        )}

      </motion.main>
    </>
  );
}
