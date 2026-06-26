# Ata de Reunião

**Data:** 15/06/2026
**Local:** Google Meet


## Pauta e Resumo
Reunião focada na resolução do problema de integração entre as branchs do backend, definição das soluções para apresentação de sábado.

## Divisão de Tarefas (Sprint Atual)

### 1. Finalização da Parte de Reserva e Histórico
**Responsáveis:** Marcely, Pedro Galdino, Samuel Carvalho, Kauan, Rafaela, Thiago
* **Tema:** Terminar Parte de Reserva -> Cancelar solicitação, aprovar/aceitar solicitação (admin), e Histórico de reservas do usuário.
* **Escopo:**
  * O solicitante preenche as informações de espaço, data, horário, finalidade e quantidade de participantes.
  * O solicitante pode cancelar uma solicitação pendente/concluída com antecedência mínima de 1 dia.
  * O administrador acessa as solicitações pendentes e pode aprovar ou rejeitar (contendo justificativas).
  * Testes efetuados e rodando na branch de solicitação, depois enviado para a branch developer backend e main.
  * O usuário tem acesso à lista de suas reservas passadas e futuras com um status (Concluída, Pendente, Prevista).

### 2. Integração Front e Back-end (Vercel e Telas)
**Responsáveis:** Gabriel Vieira, Arthur Brayan, Italo
* **Tema:** Terminar integração definitiva do front e back, corrigir o Vercel para apresentação e terminar tela inicial e a tela de ocupação das salas.
* **Escopo:** 
  * Desenvolver a tela de salas do SeuEspacoUnB.
  * Fazer o deploy e teste do deploy do Vercel antes da apresentação.

### 3. Implementação de Calendário de Ocupação (Administrador)
**Responsáveis:** Evelyn, Alan
* **Tema:** Implementação de Calendário de ocupação (Administrador).
* **Escopo:**
  * Como Administrador, quero visualizar toda a ocupação de espaços dentro da FCTE.
  * Painel com a visão da ocupação das salas em um mesmo calendário.