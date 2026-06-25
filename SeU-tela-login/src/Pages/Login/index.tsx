import './style.css'
import logo from '/src/assets/Assinatura Versão Preferencial Horizontal Reduzida.png'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import triangulo_azul from '/src/assets/triangulo_azul.png'
import triangulo_verde from '/src/assets/triangulo_verde.png'
import meia_lua_azul from '/src/assets/meia_lua_azul.png'
import meia_lua_verde from '/src/assets/meia_lua_verde.png'
import meio_arco_azul from '/src/assets/meio_arco_azul.png'
import meio_arco_verde from '/src/assets/meio_arco_verde.png'
import arco_verde from '/src/assets/arco_verde.png'


function Login() {
  const navigate = useNavigate()
  const [usuario, setUsuario] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')

  async function handleLogin() {
    setErro('')
    try {
      const resposta = await fetch('https://two026-turing.onrender.com/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: usuario, password: senha })
      })
      if (resposta.ok) {
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
      <img src={arco_verde} style={{position:'absolute', left:0, bottom:'10%', width:'100px', transform:'rotate(180deg)', zIndex: -1}} />
      <img src={meia_lua_azul} style={{position:'absolute', left:'50%', top:'75px', width:'100px', transform:'rotate(45deg)', zIndex: -1}} />
      <img src={triangulo_azul} style={{position:'absolute', left:'25%', bottom:'25%', width:'200px', transform:'rotate(-45deg)', zIndex: -1}} />
      <img src={meio_arco_verde} style={{position:'absolute', top:'100px', left:'0', width:'150px', transform:'rotate(180deg)', zIndex: -1}} />
      <img src={meia_lua_verde} style={{position:'absolute', right:0, top:'225px', width:'75px', zIndex: -1}} />
      <img src={meio_arco_azul} style={{position:'absolute', right:0, top:'150px', width:'150px', zIndex: -1}} />
      <img src={triangulo_verde} style={{position:'absolute', right:'0', bottom:'10%', width:'200px', transform:'rotate(-90deg)', zIndex: -1}} />
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