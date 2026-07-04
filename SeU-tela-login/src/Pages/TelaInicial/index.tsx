import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { CalendarCheck2, Clock, ShieldCheck, MousePointerClick, BellRing,
CalendarPlus, MapPin, Building2, FlaskConical, Presentation } from "lucide-react";
import './style.css';
import assinatura_versao_preferencial_horizontal from '/src/assets/assinatura_versao_preferencial_horizontal.svg';
import FCTE_imagem from '/src/assets/FCTE_imagem.jpeg';

const features = [
  { icon: CalendarCheck2, title: "Calendário de horários", desc: "Visualize a grade do espaço e escolha um horário livre em segundos." },
  { icon: Clock, title: "Confirmação imediata", desc: "Se o horário estiver disponível, sua reserva é confirmada na hora." },
  { icon: ShieldCheck, title: "Sem conflitos", desc: "O sistema impede automaticamente sobreposição de reservas." },
];

const reasons = [
  { icon: MousePointerClick, title: "Fim dos processos ultrapassados", desc: "Na FCTE a verificação de salas ainda é feita de forma informal e presencial. Aqui tudo é digital, consultável e antecipado." },
  { icon: ShieldCheck, title: "Menos conflitos de agenda", desc: "Sem responsável definido, surgem reservas duplicadas e desistências. O sistema garante que cada horário pertença a uma única reserva." },
  { icon: BellRing, title: "Mais produtividade acadêmica", desc: "Estudantes e professores planejam ensino, pesquisa e extensão remotamente, economizando tempo e organizando o espaço acadêmico." },
];

const spaces = [
  { icon: Building2, title: "Salas de aula", desc: "Para reuniões, monitorias e grupos de estudo." },
  { icon: FlaskConical, title: "Laboratórios", desc: "Para práticas, experimentos e projetos." },
  { icon: Presentation, title: "Auditórios", desc: "Para apresentações, defesas e eventos." },
];

const steps = [
  { n: "1", title: "Entre com sua conta", desc: "Acesse com seu e-mail acadêmico da UnB." },
  { n: "2", title: "Escolha o espaço e a data", desc: "Veja o calendário e a disponibilidade em tempo real." },
  { n: "3", title: "Confirme e adicione à agenda", desc: "Registre o motivo e leve a reserva para o Google Agenda." },
];

const fadeUp = {
  initial: { opacity: 0, y: 24 },
  whileInView: { opacity: 1, y: 0 },
  viewport: { once: true, amount: 0.3 },
  transition: { duration: 0.5, ease: "easeOut" },
} as const;

export default function Landing() {
  const navigate = useNavigate();

  return (
    <div className="landing-container">
      <header className="landing-header">
        <img src={assinatura_versao_preferencial_horizontal} alt="Logo Seu Espaço UnB" className="landing-logo-img" style={{ height: '50px', width: 'auto' }}/>
        <button 
          onClick={() => navigate('/login')} 
          className="btn-header">
          Entrar
        </button>
      </header>

      <section className="hero-section">
        {/* Coluna da Esquerda (Textos e Botões) */}
        <motion.div className="hero-text" initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.6 }}>
          <h2 className="hero-title">
            Seu espaço na UnB,<br />reservado sem complicação.
          </h2>
          <p className="hero-subtitle">
            Consulte o calendário de horários disponíveis, escolha salas, laboratórios ou auditórios e registre o motivo da reserva. Tudo em um só lugar.
          </p>
          
          <div className="hero-button">
            <button onClick={() => navigate('/Cadastro')} className="btn-hero">
              Começar agora
            </button>
          </div>
        </motion.div>

        {/* Coluna da Direita (Imagem) */}
        <motion.div className="hero-image" initial={{ opacity: 0, x: 20 }} animate={{ opacity: 1, x: 0 }} transition={{ duration: 0.6, delay: 0.2 }}>
          {/* Pode manter esse link provisório, depois você troca pela sua imagem real! */}
          <img src={FCTE_imagem} alt="Vista aérea da FCTE UnB" />
        </motion.div>
      </section>

      {/* Seção 1: Features*/}
      <section className="features-section">
        <div className="grid-container">
          {features.map((f, i) => (
            <motion.div key={i} className="card-vertical" {...fadeUp} transition={{ ...fadeUp.transition, delay: i * 0.08 }}>
              <div className="icon-box primary"><f.icon className="icon" /></div>
              <h3>{f.title}</h3>
              <p>{f.desc}</p>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Seção 2: Por que existe */}
      <section className="reasons-section">
        <motion.div className="section-header" {...fadeUp}>
          <h2>Por que esta plataforma existe</h2>
          <p>
            Reservar um espaço na FCTE costumava depender de processos manuais e informais,
            gerando conflitos e perda de tempo. O <strong>Seu espaço UnB</strong> nasceu para
            tornar esse processo simples, digital e transparente.
          </p>
        </motion.div>
        <div className="grid-container">
          {reasons.map((r, i) => (
            <motion.div key={i} className="card-vertical" {...fadeUp} transition={{ ...fadeUp.transition, delay: i * 0.08 }}>
              <div className="icon-box success"><r.icon className="icon" /></div>
              <h3>{r.title}</h3>
              <p>{r.desc}</p>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Seção 3: Espaços */}
      <section className="spaces-section">
        <motion.div className="section-header" {...fadeUp}>
          <h2>Reserve qualquer espaço acadêmico</h2>
        </motion.div>
        <div className="grid-container">
          {spaces.map((s, i) => (
            <motion.div key={i} className="card-horizontal" {...fadeUp} transition={{ ...fadeUp.transition, delay: i * 0.08 }}>
              <div className="icon-box primary"><s.icon className="icon" /></div>
              <div>
                <h3>{s.title}</h3>
                <p>{s.desc}</p>
              </div>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Seção 4: Como funciona */}
      <section className="steps-section">
        <motion.div className="section-header" {...fadeUp}>
          <h2>Como funciona</h2>
        </motion.div>
        <div className="grid-container steps-grid">
          {steps.map((s, i) => (
            <motion.div key={i} className="step-item" {...fadeUp} transition={{ ...fadeUp.transition, delay: i * 0.1 }}>
              <span className="step-number">{s.n}</span>
              <h3>{s.title}</h3>
              <p>{s.desc}</p>
            </motion.div>
          ))}
        </div>
        <motion.div className="google-banner" {...fadeUp}>
          <div className="icon-box success"><CalendarPlus className="icon" /></div>
          <div className="google-banner-text">
            <h3>Integração com o Google Agenda</h3>
            <p>Ao confirmar uma reserva, adicione o horário diretamente ao seu Google Agenda com um clique.</p>
          </div>
          <button onClick={() => navigate('/Cadastro')} className="btn-hero">
            Reservar agora
          </button>
        </motion.div>
      </section>

      {/* Seção 5: CTA Final */}
      <section className="cta-section">
        <motion.div {...fadeUp}>
          <MapPin className="icon-large success-text" />
          <h2>Pronto para reservar seu espaço?</h2>
          <p>Crie sua conta com o e-mail acadêmico e organize seus horários na FCTE.</p>
          <button onClick={() => navigate('/Cadastro')} className="btn-hero" style={{ marginTop: '20px' }}>
            Começar agora
          </button>
        </motion.div>
      </section>

      <footer className="landing-footer">
        <p>Desenvolvido pelo Grupo Turing · © 2026</p>
      </footer>
    </div>
  );
}