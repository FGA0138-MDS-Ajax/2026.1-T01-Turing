import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { Header } from "../../components/Header";

import {
  User,
  Mail,
  CreditCard,
  Lock,
  Save,
  X
} from "lucide-react";

import "./style.css";

interface Usuario {

  name: string;
  email: string;
  cpf: string;
  tipoUsuario: string;

}

export default function Perfil() {

  const [isLogged,setIsLogged] = useState(true);

  const [loading,setLoading] = useState(true);

  const [saving,setSaving] = useState(false);

  const [usuario,setUsuario] = useState<Usuario>({
      name:"",
      email:"",
      cpf:"",
      tipoUsuario:"CLIENTE"
  });

  const [senha,setSenha] = useState("");

  const [confirmarSenha,setConfirmarSenha] = useState("");

  const [original,setOriginal] = useState<Usuario>({
      name:"",
      email:"",
      cpf:"",
      tipoUsuario:"CLIENTE"
  });

  useEffect(()=>{

      carregarUsuario();

  },[]);

  async function carregarUsuario(){

      const token = localStorage.getItem("token");

      if(!token){

          alert("Faça login.");
          setLoading(false);
          return;

      }

      try{

          /*
          Quando o backend criar:

          GET /turing/usuarios/me

          basta remover o mock abaixo.
          */

          const resposta = await fetch(
              "https://two026-turing.onrender.com/turing/usuarios/me",
              {
                  headers:{
                      Authorization:`Bearer ${token}`
                  }
              }
          );

          if(resposta.ok){

              const dados = await resposta.json();

              setUsuario(dados);

              setOriginal(dados);

          }

          else{

              /*
              MOCK TEMPORÁRIO

              Quando existir o endpoint
              este trecho nunca será executado.
              */

              const mock = {

                  name:"Usuário",

                  email:"usuario@aluno.unb.br",

                  cpf:"00000000000",

                  tipoUsuario:"CLIENTE"

              };

              setUsuario(mock);

              setOriginal(mock);

          }

      }

      catch{

          const mock = {

              name:"Usuário",

              email:"usuario@aluno.unb.br",

              cpf:"00000000000",

              tipoUsuario:"CLIENTE"

          };

          setUsuario(mock);

          setOriginal(mock);

      }

      finally{

          setLoading(false);

      }

  }

  function cancelar(){

      setUsuario(original);

      setSenha("");

      setConfirmarSenha("");

  }

  async function salvar(){

      if(senha!==confirmarSenha){

          alert("As senhas não coincidem.");

          return;

      }

      setSaving(true);

      const token = localStorage.getItem("token");

      try{

          const resposta = await fetch(

              "https://two026-turing.onrender.com/turing/usuarios",

              {

                  method:"PUT",

                  headers:{

                      "Content-Type":"application/json",

                      Authorization:`Bearer ${token}`

                  },

                  body:JSON.stringify({

                      name:usuario.name,

                      email:usuario.email,

                      cpf:usuario.cpf,

                      senha:senha,

                      tipoUsuario:usuario.tipoUsuario

                  })

              }

          );

          if(resposta.ok){

              const dados = await resposta.json();

              if(dados.usuario){

                  setUsuario(dados.usuario);

                  setOriginal(dados.usuario);

              }

              if(dados.token){

                  localStorage.setItem("token",dados.token);

              }

              setSenha("");

              setConfirmarSenha("");

              alert("Dados atualizados com sucesso!");

          }

          else{

              alert("Não foi possível atualizar.");

          }

      }

      catch{

          alert("Erro ao conectar ao servidor.");

      }

      finally{

          setSaving(false);

      }

  }

  if(loading){

      return(

          <>
              <Header
                  isLogged={isLogged}
                  onToggleLogin={()=>setIsLogged(!isLogged)}
              />

              <div className="perfil-loading">

                  Carregando...

              </div>

          </>

      );

  }

  return(

      <>

      <Header
          isLogged={isLogged}
          onToggleLogin={()=>setIsLogged(!isLogged)}
      />

      <div className="perfil-container">

          <motion.div

              className="perfil-card"

              initial={{opacity:0,y:25}}

              animate={{opacity:1,y:0}}

          >

              <div className="perfil-topo">

                  <div className="perfil-avatar">

                      {usuario?.name ? usuario.name.charAt(0).toUpperCase() : "U"}

                  </div>

                  <h1>{usuario.name}</h1>

                  <span className="perfil-badge">

                      {usuario.tipoUsuario}

                  </span>

              </div>

              <div className="perfil-form">

                  <label>

                      <User size={18}/>

                      Nome

                  </label>

                  <input

                      value={usuario.name}

                      onChange={(e)=>

                          setUsuario({

                              ...usuario,

                              name:e.target.value

                          })

                      }

                  />

                  <label>

                      <Mail size={18}/>

                      Email

                  </label>

                  <input

                      value={usuario.email}

                      onChange={(e)=>

                          setUsuario({

                              ...usuario,

                              email:e.target.value

                          })

                      }

                  />

                  <label>

                      <CreditCard size={18}/>

                      CPF

                  </label>

                  <input

                      value={usuario.cpf}

                      onChange={(e)=>

                          setUsuario({

                              ...usuario,

                              cpf:e.target.value

                          })

                      }

                  />

                  <label>

                      <Lock size={18}/>

                      Nova senha

                  </label>

                  <input

                      type="password"

                      value={senha}

                      onChange={(e)=>setSenha(e.target.value)}

                  />

                  <label>

                      <Lock size={18}/>

                      Confirmar senha

                  </label>

                  <input

                      type="password"

                      value={confirmarSenha}

                      onChange={(e)=>setConfirmarSenha(e.target.value)}

                  />

              </div>

              <div className="perfil-buttons">

                  <button

                      className="cancelar"

                      onClick={cancelar}

                  >

                      <X size={18}/>

                      Cancelar

                  </button>

                  <button

                      className="salvar"

                      disabled={saving}

                      onClick={salvar}

                  >

                      <Save size={18}/>

                      {

                          saving

                          ?

                          "Salvando..."

                          :

                          "Salvar alterações"

                      }

                  </button>

              </div>

          </motion.div>

      </div>

      </>

  );

}