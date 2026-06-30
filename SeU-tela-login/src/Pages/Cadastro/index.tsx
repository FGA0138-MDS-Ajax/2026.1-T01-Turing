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


function Cadastro() {
  
      const navigate = useNavigate()

      const [name, setName] = useState('')
      const [cpf, setCpf] = useState('')
      const [email, setEmail] = useState('')
      const [password, setPassword] = useState('')
      const [erro, setErro] = useState('')

      async function handleCadastro() {
        setErro('')
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
            navigate('/')
          } else {
            setErro('Erro ao cadastrar usuário. Tente novamente.')
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

        <img src={logo} alt="Logo" className='logo' />

        <button className='btn-login' onClick={() => navigate('/')}>Entrar</button>
          <form>
            <h1>Cadastro</h1> 
            <div className='inputs'>
                <label>Usuário:</label>
                <input name='user' type='text' value={name} onChange={e => setName(e.target.value)} placeholder='Digite seu nome...'/>
            </div>
          <div className='inputs'>  
            <label>CPF:</label>
            <input name='cpf' type='text' value={cpf} onChange={e => setCpf(e.target.value)} placeholder='Digite seu CPF...'/>
          </div>
          <div className='inputs'>
            <label>Email:</label>
            <input name='email' type='email' value={email} onChange={e => setEmail(e.target.value)} placeholder='Digite seu email...'/>
          </div>
          <div className='inputs'>  
            <label>Senha:</label>
            <input name='password' type='password' value={password} onChange={e => setPassword(e.target.value)} placeholder='Digite sua senha...'/>
          </div>
          {erro && <p style={{ color: 'red' }}>{erro}</p>}
            <button className="btn-confirmar" type='button' onClick={handleCadastro}>Confirmar Cadastro</button>
  
          </form>
        </div>
    
  )
}

export default Cadastro
