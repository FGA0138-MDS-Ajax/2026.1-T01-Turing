import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { motion } from "framer-motion";
import DatePicker, { registerLocale } from "react-datepicker";
import { ptBR } from "date-fns/locale/pt-BR";
import './style.css';
import "react-datepicker/dist/react-datepicker.css";

import {
    ArrowLeft,
    Users,
    MapPin,
    Calendar,
} from "lucide-react";

import { Header } from "../../components/Header";

import "./style.css";

registerLocale("pt-BR", ptBR);

export default function AgendarSala() {

    const { id } = useParams();

    const navigate = useNavigate();

    <Header isLogged={!!localStorage.getItem('token')} />

    const [sala, setSala] = useState<any>(null);
    const [quantidadeParticipantes, setQuantidadeParticipantes] = useState(1);
    const [horarios, setHorarios] = useState<any[]>([]);
    const [horarioSelecionado, setHorarioSelecionado] = useState<any | null>(null);
    const [motivo, setMotivo] = useState("");
    const [enviando, setEnviando] = useState(false);
    const [solicitacoes, setSolicitacoes] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    const [dataSelecionada, setDataSelecionada] =
        useState<Date>(new Date());

    useEffect(() => {

        buscarSala();
        buscarHorarios();
        buscarSolicitacoes();
        
    }, []);

    async function buscarSala() {
        try {

            const resposta = await fetch(
                `https://two026-turing.onrender.com/turing/salas/${id}`
            );

            const dados = await resposta.json();

            setSala(dados);

        } catch (erro) {

            console.error(erro);

        } finally {

            setLoading(false);

        }

    }
async function buscarHorarios() {

    try {

        const resposta = await fetch(
            `https://two026-turing.onrender.com/turing/horarios/sala/${id}`
        );

        if (!resposta.ok) {

            throw new Error();

        }

        const dados = await resposta.json();

        setHorarios(dados);

    } catch (erro) {

        console.error(erro);

    }

}
async function buscarSolicitacoes() {

    try {

        const resposta = await fetch(
            `https://two026-turing.onrender.com/turing/solicitacoes/sala/${id}`
        );

        if (!resposta.ok) {
            throw new Error();
        }

        const dados = await resposta.json();

        setSolicitacoes(dados);

    } catch (erro) {

        console.error("Erro ao buscar solicitações:", erro);

    }

}
async function reservarSala() {

            if (!horarioSelecionado) {
            alert("Selecione um horário.");
            return;
        }

        if (!motivo.trim()) {
            alert("Informe o motivo.");
            return;
        }

        if (quantidadeParticipantes > sala.capacidade) {
            alert(`Essa sala suporta apenas ${sala.capacidade} participantes.`);
            return;
        }

        const token = localStorage.getItem("token");

        if (!token) {
            alert("Faça login novamente.");
            return;
        }

        setEnviando(true);


    if (quantidadeParticipantes > sala.capacidade) {

    alert("Essa sala suporta apenas " + sala.capacidade + " pessoas.");

    return;

}

    try {

        const resposta = await fetch(
            "https://two026-turing.onrender.com/turing/solicitacoes",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },

                body: JSON.stringify({

                horarioSalaId: horarioSelecionado.id,

                dataUso: dataSelecionada
                    .toISOString()
                    .split("T")[0],

                motivo: motivo.trim(),

                quantidadeParticipantes

            })

            }
        );

        if (!resposta.ok) {

            const erro = await resposta.text();

            alert(erro);

            return;

        }

        alert("Reserva realizada com sucesso!");

        navigate("/agendamentos");

    } catch (erro) {

        console.error(erro);

        alert("Erro de conexão.");

    } finally {

        setEnviando(false);

    }

}
const dias = [
    "SUNDAY",
    "MONDAY",
    "TUESDAY",
    "WEDNESDAY",
    "THURSDAY",
    "FRIDAY",
    "SATURDAY"
];

const diaSelecionado =
    dias[dataSelecionada.getDay()];

const horariosDoDia = horarios.filter(
    h => h.diaSemana === diaSelecionado
);

function horarioEstaOcupado(horario: any) {

    return solicitacoes.some((solicitacao) =>

        solicitacao.horarioSalaId === horario.id &&

        solicitacao.dataUso ===
        dataSelecionada.toISOString().split("T")[0] &&

        solicitacao.status !== "CANCELADA"

    );

}

    return (

        <>

            <Header isLogged={!!localStorage.getItem('token')} />

            <div className="agendar-sala-container">

                <button
                    className="btn-voltar"
                    onClick={() => navigate("/agendar")}
                >
                    <ArrowLeft size={18} />
                    Voltar
                </button>

                {loading ? (

                    <h2>Carregando sala...</h2>

                ) : (

                    <>
                        <div className="topo-sala">

                            <div>

                                <h1>{sala.nome}</h1>

                                <div className="informacoes">

                                    <span>

                                        <Users size={16} />

                                        {sala.capacidade} lugares

                                    </span>

                                    <span>

                                        <MapPin size={16} />

                                        {sala.localizacao}

                                    </span>

                                </div>

                            </div>

                        </div>

                        <div className="conteudo">

                            <div className="painel-esquerdo">

                                <h3>

                                    <Calendar size={18} />

                                    Escolha uma data

                                </h3>

                                <DatePicker

                                    inline

                                    selected={dataSelecionada}

                                    locale="pt-BR"

                                    minDate={new Date()}

                                    onChange={(date: any) => {

                                        if (date) {

                                            setDataSelecionada(date);
                                            
                                            setHorarioSelecionado(null)
                                        }

                                    }}

                                />

                            </div>

                            <div className="painel-direito">

                                <h3>

                                    Horários disponíveis

                                </h3>

                                <div className="grade-horarios">

    {horariosDoDia.length === 0 ? (

        <p>Nenhum horário disponível nesta data.</p>

    ) : (

     horariosDoDia.map((horario) => {

    const ocupado = horarioEstaOcupado(horario);

    return (

        <motion.div

            key={horario.id}

            whileHover={{ scale: 1.02 }}

            whileTap={{ scale: 0.98 }}

            onClick={() => {

                if (!ocupado) {

                    setHorarioSelecionado(horario);

                }

            }}

            className={
                ocupado
                    ? "card-horario ocupado"
                    : horarioSelecionado?.id === horario.id
                        ? "card-horario selecionado"
                        : "card-horario"
            }

        >

            <div className="hora">

                {horario.inicioHora} - {horario.fimHora}

            </div>

            <div className="status">

                {ocupado ? "Ocupado" : "Disponível"}

            </div>

        </motion.div>

    );

})
        
    )}

</div>
{horarioSelecionado && (

    <motion.div

        className="painel-reserva"

        initial={{ opacity: 0, y: 15 }}

        animate={{ opacity: 1, y: 0 }}

    >

        <h3>Motivo da reserva</h3>

        <textarea

            value={motivo}

            onChange={(e) => setMotivo(e.target.value)}

            placeholder="Ex.: Reunião de grupo, monitoria, projeto..."

        />

        <label>Quantidade de participantes</label>

        <input
            className="input-participantes"
            type="number"
            min={1}
            max={sala.capacidade}
            value={quantidadeParticipantes}
            onChange={(e) =>
                setQuantidadeParticipantes(Number(e.target.value))
            }
        />
        <button

            className="btn-reservar"

            disabled={enviando}

            onClick={reservarSala}

        >

            {enviando  ? "Reservando..." : "Reservar sala"}

        </button>

    </motion.div>

)}

                            </div>

                        </div>

                    </>

                )}

            </div>

        </>

    );

}