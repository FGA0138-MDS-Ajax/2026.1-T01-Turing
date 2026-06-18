import './style.css'
import logo from '/src/assets/Assinatura Versão Preferencial Horizontal Reduzida.png'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'

function Login() {
  const navigate = useNavigate()
  const [usuario, setUsuario] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')

  async function handleLogin() {
    setErro('')
    try {
      const resposta = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: usuario, password: senha })
      })
      if (resposta.ok) {
        const dados = await resposta.json()
        localStorage.setItem('token', dados.token)
        alert('Login feito com sucesso!')
      } else {
        setErro('Usuário ou senha inválidos.')
      }
    } catch (e) {
      setErro('Não foi possível conectar ao servidor!')
    }
  }

  return (
    <div className='container'>
      <img src={logo} alt="Logo" className='logo' />
      <button className='btn-cadastro' onClick={() => navigate('/cadastro')}>Cadastre-se</button>
      <form>
        <h1>Login</h1>
        <div className='inputs'>
          <label>Usuário:</label>
          <input
            name='user'
            type='text'
            placeholder='Digite seu email...'
            value={usuario}
            onChange={(e) => setUsuario(e.target.value)}
          />
        </div>
        <div className='inputs'>
          <label>Senha:</label>
          <input
            name='senha'
            type='password'
            placeholder='Digite sua senha...'
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
        </div>
        {erro && <p style={{ color: 'red' }}>{erro}</p>}
        <button className="btn-recuperar" type='button'>Recuperar Senha</button>
        <button className="btn-Entrar" type='button' onClick={handleLogin}>Entrar</button>
      </form>
    </div>
  )
}

export default Login