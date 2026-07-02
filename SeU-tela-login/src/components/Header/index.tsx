import { motion } from 'framer-motion';
import { UserCircle, LogIn, LogOut } from 'lucide-react';
import { Link } from 'react-router-dom'; 
import logo from '/src/assets/assinatura_versao_preferencial_horizontal.svg';
import './style.css';

interface HeaderProps {
  isLogged?: boolean;
  onToggleLogin?: () => void;
}

export const Header = ({ isLogged = false, onToggleLogin }: HeaderProps) => {
  return (
    <motion.header 
      className="navbar"
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="header-logo">
        <img src={logo} alt="Logo Seu Espaço UnB" className="header-logo-img" />
      </div>

      <nav className="nav-links">
        <Link to="/">Tela Inicial</Link>
        <Link to="/agendar">Agendar</Link>
        <Link to="/agendamentos">Meus Agendamentos</Link>
      </nav>

      <div className="user-profile">
        {isLogged ? (
          <>
            <Link to="/perfil" className="login-btn">
               <UserCircle size={24} />
               <span>Olá, Estudante</span>
            </Link>
            <button onClick={onToggleLogin} className="login-btn">
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