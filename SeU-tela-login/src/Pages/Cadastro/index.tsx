import './style.css'
import logo from '/src/assets/Assinatura Versão Preferencial Horizontal Reduzida.png'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'


function Cadastro() {
  
      const navigate = useNavigate()

      const [usuario, setUsuario] = useState('')
      const [email, setEmail] = useState('')
      const [cpf, setCpf] = useState('')
      const [senha, setSenha] = useState('')
      const [erro, setErro] = useState('')

      async function handleCadastro() {
        setErro('')
        try {
          const resposta = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name: usuario, email, cpf , password: senha , tipoUsuario: 'CLIENTE'})
          })

          if (resposta.ok) {
            const dados = await resposta.json()
            console.log(dados)
            alert('Cadastro feito com sucesso!')
            navigate('/login')
          } else {
            setErro('Erro ao cadastrar usuário. Tente novamente.')
          }
        } catch (e) {
          setErro('Não foi possível conectar ao servidor!')
        }
      }

  return (
    
        <div className='container'>
        
        <img src={logo} alt="Logo" className='logo' />

        <button className='btn-login' onClick={() => navigate('/')}>Entrar</button>
          <form>
            <h1>Cadastro</h1> 
            <div className='inputs'>
                <label>Usuário:</label>
                <input name='user' type='text' value={usuario} onChange={e => setUsuario(e.target.value)} placeholder='Digite seu nome...'/>
            </div>
          <div className='inputs'>  
            <label>Email:</label>
            <input name='email' type='email' value={email} onChange={e => setEmail(e.target.value)} placeholder='Digite seu email...'/>
          </div>
          <div className='inputs'>
            <label>CPF:</label>
            <input name='cpf' type='text' value={cpf} onChange={e => setCpf(e.target.value)} placeholder='Digite seu Cpf...'/>
          </div>
          <div className='inputs'>  
            <label>Senha:</label>
            <input name='senha' type='password' value={senha} onChange={e => setSenha(e.target.value)} placeholder='Digite sua senha...'/>
          </div>
          {erro && <p style={{ color: 'red' }}>{erro}</p>}
            <button className="btn-confirmar" type='button' onClick={handleCadastro}>Confirmar Cadastro</button>
  
          </form>
        </div>
    
  )
}

export default Cadastro
