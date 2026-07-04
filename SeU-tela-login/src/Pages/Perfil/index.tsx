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

 async function carregarUsuario() {
    setLoading(true);

    await new Promise(resolve => setTimeout(resolve, 500));

    const mock = {
        name: "Usuário Teste",
        email: "usuario@aluno.unb.br",
        cpf: "123.456.789-00",
        tipoUsuario: "CLIENTE"
    };

    setUsuario(mock);
    setOriginal(mock);

    setLoading(false);
}

    function cancelar() {
    setUsuario(original);
    setSenha("");
    setConfirmarSenha("");
}

  async function salvar() {

    if (senha !== confirmarSenha) {
        alert("As senhas não coincidem.");
        return;
    }

    setSaving(true);

    await new Promise(resolve => setTimeout(resolve, 1000));

    setOriginal(usuario);

    setSenha("");
    setConfirmarSenha("");

    setSaving(false);

    alert("Dados atualizados com sucesso! (Mock)");
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

                      {usuario.name.charAt(0).toUpperCase()}

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