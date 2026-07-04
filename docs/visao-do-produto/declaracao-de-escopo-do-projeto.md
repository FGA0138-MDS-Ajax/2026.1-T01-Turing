# 4. Declaração de Escopo do Projeto

# 4.1 Backlog do produto
- Os nossos requisitos foram elicitados por meio de um brainstorm com a equipe e entrevistas estruturadas com membros da comunidade acadêmica, representando os perfis de usuário que queremos atender com nosso projeto.

# 4.2 Perfis
- O Sistema possui dois perfis de acesso com permissões diferentes. A elicitação de requisitos foi realizada

-                               Quadro 6 -  Perfis de acesso

|#  |Nome do perfil   |Características do perfil   |Permissões de acesso  |
|---|---|---|---|
| 1  |Usuário   | Capacidade de fazer requisição de sala para uso e consultar horários pré-definidos. Realiza login por meio de cadastro com e-mail, senha e CPF.  | Usuário tem acesso às funcionalidades mais básicas, Escolher horário, duração e motivo de agendamento na lista de atividades a serem realizadas  |
| 2 | Administrador  | Responsável por autorizar as requisições das reservas de sala, informar horários de aula e possíveis eventualidades como limpeza ou manutenção e manter a sala com calendário atualizado.  | Administrador tem acesso às funcionalidades de Aceitar solicitação do uso da sala, checar perfil do usuário solicitante, visualizar a motivação dos usuários em agendar a sala  |

-   Fonte: Elaborado pelos autores, 2026.

# 4.3 Cenários
-                           Quadro 7 - Cenários funcionais
Sistema: Seu Espaço UnB - Cenários funcionais

|Numeração do cenário  | Nome do cenário   | Sprints  |
|---|---|---|
| 01  |Cadastro/Login d Usuários (Autenticação)  |Sprint 4   |
| 02  |Consulta de espaços com filtros e detalhes   |Sprint 5   |
| 03  |Visualização da disponibilidade (calendário)   |Sprint 5   |
| 04  |Solicitação da reserva pelo Usuário  |Sprint 6   |
| 05  |Aprovação/Rejeição pelo Administrador   |Sprint 6   |
| 06  | Painel Usuário/ Painel Administrador  |Sprint 7   |
| 07  |Usabilidade do site e integração com plataformas   |Sprint 8   |
 
-   Fonte: Elaborado pelos autores, 2026

# 4.4 Tabela de Backlog do Produto
Sistema: Seu Espaço UnB - Backlog do Produto

|Numeração (Cenário / requisito)  | Sprint  | Nome do requisito  | Tipo de requisito (Funcional/ não funcional)  | Priorização do requisito (Must, Should, Could)  | Descrição sucinta do requisito  | User histories (U.S) associadas |
|---|---|---|---|---|---|---|
| 01  | 04  | Cadastro de Usuário/Administrador   | Funcional   | Must   | O usuário pode criar uma conta com e-mail, senha e CPF.  | Como usuário eu quero me cadastrar para poder acessar o sistema.  |
| 02  | 04  | Autenticação de Usuário/Administrador | Funcional  | Must   | Autenticação via e-mail e senha.  | Como usuário eu quero poder realizar login e acessar as funcionalidades do sistema.  |
| 03  | 04  | Alterar Usuário/Administrador (Editar o perfil)  | Funcional   | Should   | O usuário altera o nome, e-mail e senha.  | Como usuário, quero conseguir alterar as informações do meu perfil.  |
| 04  | 05  | Listagem de Espaços   | Funcional  | Must  | Exibição dos espaços da FCTE, contendo nome da sala, capacidade.  | Como usuário quero visualizar todos os espaços disponíveis  |
| 05  | 05  | Filtro dos espaços  | Funcional  | Must  | Filtrar espaços por nome da sala e data/horário (disponíveis/indisponíveis)   | Como usuário quero filtrar os espaços por características  |
| 06  | 05  | Detalhes do espaços   | Funcional  | Should  | Descrição completa da sala, localização da sala e recursos disponíveis  | Como usuário quero visualizar informações detalhadas de cada espaço  |
| 07  | 06  | Solicitação de reserva  | Funcional  | Must   | O solicitante preenche as informações de espaço, data, horário, finalidade e quantidade de participantes.  | Como usuário quero reservar uma sala de acordo com uma finalidade.  |
| 08  | 06  | Cancelar solicitação de reserva   | Funcional  | Must  | O solicitante pode cancelar uma solicitação pendente/concluída com antecedência mínima de 1 dia.  | Como usuário quero cancelar a solicitação de uma reserva que não é mais necessária.  |
| 09  | 06  | Aprovação/ Rejeição da reserva pelo administrador  | Funcional  | Must  | O administrador acessa as solicitações pendentes e pode aprovar ou rejeitar (Contendo justificativas).  | Como administrador eu quero aprovar ou rejeitar as solicitações pendentes de reserva de uma sala.  |
| 10  | 07  | Histórico de reservas do usuário  | Funcional  | Should  | O usuário tem acesso a lista de suas reservas passadas e futuras com um status (Concluída, Pendente, Prevista).  | Como usuário quero ver o histórico das minhas reservas.  |
| 11  | 07  | CRUD dos horários  (Administrador)  | Funcional  | Could  | Administrador pode Cadastrar, Editar e remover os horários dos espaços.   | Como Administrador quero gerenciar cadastro de espaços dentro da FCTE.  |
| 12  | 07  | Calendário de ocupação (Administrador)   | Funcional  | Should  | Painel com a visão da ocupação das salas em um mesmo calendário.   | Como Administrador quero visualizar toda a ocupação de espaços dentro da FCTE.  |
| 13  | 08  | Melhora da usabilidade  | Não Funcional  | Should  | Interface mais intuitiva e concisa.  | Como usuário, quero navegar pelo site sem problemas de não entendimento das funcionalidades.   |
| 14  | 08  | Integração com o Google Agenda  | Funcional   | Could  | Ao reservar um espaço e essa solicitação ser aprovada o evento é cadastrado automaticamente ao google agenda do solicitante  | Como usuário quero que a reserva apareça no meu google agenda.   |

-    Fonte: Elaborado pelos autores, 2026.





