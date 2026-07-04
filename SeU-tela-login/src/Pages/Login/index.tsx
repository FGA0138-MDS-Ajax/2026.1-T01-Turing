import '../auth.css'
import { useNavigate } from 'react-router-dom'
import { useState, type CSSProperties } from 'react'
import BrandLogo from '../../components/BrandLogo'
import ThemeToggle from '../../components/ThemeToggle'
import heroSalas from '../../assets/hero-salas.jpg'

function Login() {
  const navigate = useNavigate()
  const [usuario, setUsuario] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')
  const [carregando, setCarregando] = useState(false)

  async function handleLogin() {
    setErro('')
    setCarregando(true)
    try {
      const resposta = await fetch('https://two026-turing.onrender.com/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: usuario, password: senha })
      })
      if (resposta.ok) {
        const dados = await resposta.json()
        localStorage.setItem('token', dados.token)
        alert('Login feito com sucesso!')
        navigate('/agendamentos')
      } else {
        setErro('Usuário ou senha inválidos.')
      }
    } catch {
      setErro('Não foi possível conectar ao servidor!')
    } finally {
      setCarregando(false)
    }
  }

  return (
    <div className="auth-shell" style={{ '--auth-hero-image': `url(${heroSalas})` } as CSSProperties}>
      <aside className="auth-hero" aria-label="Apresentação do Seu espaço UnB">
        <div className="auth-hero__content">
          <div className="auth-hero__top">
            <BrandLogo light />
            <button type="button" className="auth-back-button" aria-label="Voltar ao início">
              ←
            </button>
          </div>

          <div className="auth-hero__headline">
            <span className="auth-eyebrow">FCTE · Universidade de Brasília</span>
            <h1>Reserve seu espaço na UnB em minutos.</h1>
            <p>
              Veja a disponibilidade no calendário, acompanhe ocupações acadêmicas e acesse sua conta
              com uma experiência mais simples, moderna e responsiva.
            </p>
          </div>

          <span className="auth-hero__footer">Seu espaço UnB · Salas, laboratórios e auditórios</span>
        </div>
      </aside>

      <main className="auth-main">
        <div className="auth-panel">
          <div className="auth-mobile-header">
            <BrandLogo />
            <ThemeToggle />
          </div>

          <section className="auth-card" aria-labelledby="login-title">
            <div className="auth-card__header">
              <h1 id="login-title">Entrar</h1>
              <p className="auth-card__description">
                Acesse sua conta para consultar reservas e visualizar a ocupação dos espaços da FCTE.
              </p>
            </div>

            <form
              className="auth-form"
              onSubmit={(event) => {
                event.preventDefault()
                handleLogin()
              }}
            >
              <div className="auth-field">
                <label htmlFor="login-email">E-mail</label>
                <input
                  id="login-email"
                  name="user"
                  type="email"
                  placeholder="voce@aluno.unb.br"
                  value={usuario}
                  onChange={(e) => setUsuario(e.target.value)}
                  autoComplete="email"
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="login-password">Senha</label>
                <input
                  id="login-password"
                  name="senha"
                  type="password"
                  placeholder="Digite sua senha"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                  autoComplete="current-password"
                  required
                />
              </div>

              {erro && <p className="auth-alert">{erro}</p>}

              <div className="auth-actions">
                <button className="auth-button auth-button--primary" type="submit" disabled={carregando}>
                  {carregando ? 'Entrando...' : 'Entrar'}
                </button>
                <button className="auth-button auth-button--secondary" type="button">
                  Recuperar senha
                </button>
              </div>
            </form>

            <p className="auth-switch">
              Ainda não tem conta? <button type="button" onClick={() => navigate('/cadastro')}>Cadastre-se</button>
            </p>
          </section>
        </div>
      </main>
    </div>
  )
}

export default Login
