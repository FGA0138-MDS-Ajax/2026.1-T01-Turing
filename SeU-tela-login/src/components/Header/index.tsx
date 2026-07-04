import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { UserCircle, LogIn, LogOut, Shield, ArrowLeft } from 'lucide-react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import logo from '/src/assets/assinatura_versao_preferencial_horizontal.svg';
import './style.css';

interface HeaderProps {
  isLogged?: boolean;
}

export const Header = ({ isLogged = false }: HeaderProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isAdmin, setIsAdmin] = useState(false);

  const isAdminRoute = location.pathname.includes('/admin');

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setIsAdmin(false);
      return;
    }

    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const payload = JSON.parse(window.atob(base64));
      const adminsAutorizados = ["adm@gmail.com", "seu-email@unb.br"];
      const ehAdmin = adminsAutorizados.includes(payload.sub);
      
      console.log("É ADMIN?", ehAdmin);
      setIsAdmin(ehAdmin);
    } catch (e) {
      setIsAdmin(false);
    }
  }, [isLogged]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/", { replace:true });
  };

  const handleVoltarClick = () => {
    navigate('/agendamentos');
  };

  return (
    <motion.header className="navbar" initial={{ y: -100 }} animate={{ y: 0 }} transition={{ duration: 0.5 }}>
      <div className="header-logo">
        <img src={logo} alt="Logo Seu Espaço UnB" className="header-logo-img" />
      </div>

      {!isAdminRoute && (
          <nav className="nav-links">
            <Link to="/">Tela Inicial</Link>
            <Link to="/agendar">Agendar</Link>
            <Link to="/agendamentos">Meus Agendamentos</Link>
          </nav>
        )}

      <div className="user-profile">
        {isLogged ? (
          <>
            {isAdmin && (
              isAdminRoute ? (
                <button onClick={handleVoltarClick} className="login-btn" style={{ background: 'transparent', border: 'none', cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '5px' }}>
                  <ArrowLeft size={20} />
                  <span>Voltar</span>
                </button>
              ) : (
                <button onClick={() => navigate('/admin')} className="login-btn" style={{ background: 'transparent', border: 'none', cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '5px' }}>
                  <Shield size={20} />
                  <span>Admin</span>
                </button>
              )
            )}

            <Link to="/perfil" className="login-btn" style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
              <UserCircle size={24} />
              <span>Olá, Estudante</span>
            </Link>
            
            <button onClick={handleLogout} className="login-btn" title="Sair">
              <LogOut size={18} />
            </button>
          </>
        ) : (
          <Link to="/login" className="login-btn">
            <LogIn size={18} /> Entrar
          </Link>
        )}
      </div>
    </motion.header>
  );
};