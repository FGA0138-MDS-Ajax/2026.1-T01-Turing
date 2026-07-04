import '../auth.css'
import { useNavigate } from 'react-router-dom'
import { useState, type CSSProperties } from 'react'
import BrandLogo from '../../components/BrandLogo'
import ThemeToggle from '../../components/ThemeToggle'
import heroSalas from '../../assets/hero-salas.jpg'

function Cadastro() {
  const navigate = useNavigate()

  const [name, setName] = useState('')
  const [cpf, setCpf] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [erro, setErro] = useState('')
  const [carregando, setCarregando] = useState(false)

  async function handleCadastro() {
    setErro('')
    setCarregando(true)
    try {
      const resposta = await fetch('https://two026-turing.onrender.com/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email, cpf, password, tipousuario: 'CLIENTE' })
      })

      if (resposta.ok) {
        const dados = await resposta.json()
        console.log(dados)
        alert('Cadastro feito com sucesso!')
        navigate('/login')
      } else {
        const erroDoServidor = await resposta.text()
        console.error("Motivo da recusa pelo backend:", erroDoServidor)
        setErro('Erro ao cadastrar. Verifique se o CPF é válido ou se o email já existe.')
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
            <button type="button" className="auth-ghost-link" onClick={() => navigate('/')}>
              Entrar
            </button>
          </div>

          <div className="auth-hero__headline">
            <span className="auth-eyebrow">Cadastro acadêmico</span>
            <h1>Crie sua conta para reservar espaços.</h1>
            <p>
              Cadastre seus dados para acompanhar horários disponíveis e participar da gestão digital
              de salas, laboratórios e auditórios.
            </p>
          </div>

          <span className="auth-hero__footer">FCTE · Universidade de Brasília</span>
        </div>
      </aside>

      <main className="auth-main">
        <div className="auth-panel">
          <div className="auth-mobile-header">
            <BrandLogo />
            <ThemeToggle />
          </div>

          <section className="auth-card" aria-labelledby="signup-title">
            <div className="auth-card__header">
              <h1 id="signup-title">Cadastro</h1>
              <p className="auth-card__description">
                Informe seus dados para criar uma conta no Seu espaço UnB.
              </p>
            </div>

            <form
              className="auth-form"
              onSubmit={(event) => {
                event.preventDefault()
                handleCadastro()
              }}
            >
              <div className="auth-field">
                <label htmlFor="signup-name">Nome completo</label>
                <input
                  id="signup-name"
                  name="user"
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Seu nome"
                  autoComplete="name"
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="signup-cpf">CPF</label>
                <input
                  id="signup-cpf"
                  name="cpf"
                  type="text"
                  value={cpf}
                  onChange={(e) => setCpf(e.target.value)}
                  placeholder="000.000.000-00"
                  inputMode="numeric"
                  autoComplete="off"
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="signup-email">E-mail</label>
                <input
                  id="signup-email"
                  name="email"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="voce@aluno.unb.br"
                  autoComplete="email"
                  required
                />
              </div>

              <div className="auth-field">
                <label htmlFor="signup-password">Senha</label>
                <input
                  id="signup-password"
                  name="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Crie uma senha"
                  autoComplete="new-password"
                  required
                />
              </div>

              {erro && <p className="auth-alert">{erro}</p>}

              <div className="auth-actions">
                <button className="auth-button auth-button--primary" type="submit" disabled={carregando}>
                  {carregando ? 'Criando conta...' : 'Confirmar cadastro'}
                </button>
              </div>
            </form>

            <p className="auth-switch">
              Já tem conta? <button type="button" onClick={() => navigate('/')}>Entrar</button>
            </p>
          </section>
        </div>
      </main>
    </div>
  )
}

export default Cadastro
