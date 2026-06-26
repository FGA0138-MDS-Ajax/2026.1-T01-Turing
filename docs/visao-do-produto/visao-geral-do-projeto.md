# 2. Visão Geral do Projeto
 
## 2.1 Ciclo de vida do projeto de desenvolvimento de software
 
O ciclo de vida adotado neste projeto é classificado como ágil, pois combina características iterativas e incrementais. O ciclo de vida do software compreende as fases pelas quais o software passa, desde a definição de requisitos, codificação e manutenção, até sua descontinuidade. Além disso, existem modelos que representam esse ciclo em forma de processo.
 
Nesse sentido, o projeto Seu Espaço UnB (SeU) adota uma abordagem ágil, na qual requisitos são tratados como variáveis, o desenvolvimento ocorre por incrementos e há feedback frequente dos stakeholders. Essa escolha está alinhada ao contexto do projeto, pois o sistema envolve necessidades da comunidade acadêmica da FCTE que podem ser refinadas ao longo do desenvolvimento.
 
A abordagem ágil foi escolhida considerando:
 
- A necessidade de validação constante com o cliente e com os usuários envolvidos;
- A possibilidade de mudanças frequentes nos requisitos, comuns em sistemas acadêmicos;
- O uso de tecnologias modernas, como React, Spring Boot e MySQL, que favorecem o desenvolvimento incremental;
- O curto prazo acadêmico, exigindo entregas parciais e evolutivas;
- A natureza de documento vivo do projeto, permitindo revisão e atualização do escopo ao longo das sprints.

**Instanciação do Ciclo de Vida no Projeto**
 
O projeto adota uma abordagem ágil e um ciclo de vida ágil, pois utiliza características iterativas e incrementais para refinar os requisitos e entregar incrementos funcionais ao longo do desenvolvimento. Esse ciclo de vida será operacionalizado por meio do Scrum, utilizado como framework de organização do trabalho, e por práticas do XP, utilizadas para apoiar a qualidade técnica do produto. Dessa forma, Scrum e XP não são tratados como o ciclo de vida em si, mas como processos e práticas que implementam a abordagem ágil escolhida.
 
No projeto Seu Espaço UnB (SeU), o ciclo será executado em sprints semanais. Em cada sprint, a equipe selecionará itens do Product Backlog, realizará o planejamento, desenvolverá as funcionalidades priorizadas, executará testes, revisará o incremento produzido e coletará feedback do cliente ou Product Owner. A cada iteração, o sistema poderá receber ajustes nos requisitos, melhorias técnicas e novas funcionalidades, mantendo coerência com a natureza de um documento vivo e com a necessidade de adaptação durante o andamento do projeto.
 
**Justificativa da Escolha do Modelo**
 
A escolha por um ciclo de vida ágil se justifica pelos seguintes fatores:
 
- **Flexibilidade:** Permite adaptação rápida a mudanças de requisitos;
- **Entrega contínua:** O sistema evolui gradualmente, reduzindo riscos;
- **Feedback constante:** O cliente participa ativamente do desenvolvimento;
- **Qualidade:** A integração com práticas de XP (testes e revisão de código) garante maior confiabilidade.

**Relação com o Processo de Desenvolvimento**
 
O ciclo de vida definido está diretamente alinhado com o processo descrito na Seção 3, onde:
 
- O Scrum define a organização das iterações;
- O XP garante qualidade técnica através de testes, revisão de código e integração contínua;
- O fluxo de trabalho (diagrama apresentado) representa a execução prática deste ciclo.
O fluxo detalhado de execução deste ciclo pode ser visualizado no diagrama de fluxo de trabalho apresentado na Seção 3.
 
## 2.2 Organização do Projeto
 
A organização do projeto foi estruturada com base nos papéis definidos pelas metodologias ágeis adotadas (Scrum), garantindo uma divisão clara de responsabilidades entre os membros da equipe.
 
Todos os integrantes possuem igual importância no desenvolvimento do projeto, sendo responsáveis de forma colaborativa pelo sucesso das entregas ao longo das sprints.
 
Quadro 2 - Organização do projeto
 
| Papel | Atribuições | Responsável | Participantes |
|---|---|---|---|
| Scrum master | Garante a aplicação da metodologia, facilita as sprints e remove impedimentos que bloqueiam a equipe | Kauan Tarchetti | Kauan Tarchetti |
| Product Owner | Define funcionalidades do produto (backlog), prioriza as histórias de usuário e valida as entregas junto ao cliente | Alan Farias Braga | Alan Farias Braga, Pedro Galdino |
| Desenvolvedor Frontend | Responsáveis pela interface do usuário (UI), experiência do usuário (UX) e integração com a API utilizando React. | — | Arthur Brayan, Rafaela |
| Desenvolvedor Backend | Responsáveis pela lógica de negócio, segurança e criação das APIs REST utilizando Java, junto com o framework Spring Boot. | Pedro Galdino | Pedro Galdino, Marcely, Kauan Tarchetti, Júlia Pêgo |
| Desenvolvedor Banco de Dados | Responsáveis pela modelagem do esquema relacional, integridade dos dados e performance das consultas no MySQL | Evellyn Rocha | Pedro Galdino, Marcely, Kauan Tarchetti, Rafaela, Evellyn Rocha |
| Cliente | Fornecer visão do negócio, validar requisitos, participar da homologação de sprints e dar feedbacks sobre os protótipos. | — | — |
 
Fonte: Elaborado pelos autores, 2026.
 
## 2.3 Planejamento das Fases e/ou Iterações do Projeto
 
O planejamento do projeto foi organizado em sprints semanais, permitindo a entrega incremental de funcionalidades e a validação contínua com o cliente.
 
Cada sprint possui objetivos específicos e entregáveis definidos, sendo revisados ao final de cada ciclo, conforme a abordagem ágil adotada.
 
Quadro 3 - Planejamento das fases e/ou iterações do projeto
 
| Sprint | Produto (Entrega) | Data Início | Data Fim | Entregável(eis) | Responsáveis | % conclusão |
|---|---|---|---|---|---|---|
| Sprint 1 | Definição produto e visão do projeto | 25/04/2026 | 02/05/2026 | Documento de visão do produto e projeto | Todos | — |
| Sprint 2 | Protótipo e Arquitetura | 02/05/2026 | 09/05/2026 | Documento de Arquitetura e Protótipo Figma | Todos | — |
| Sprint 3 | Discussão do produto de software | 09/05/2026 | 16/05/2026 | Atualização do Documento de visão e Arquitetura | Todos | — |
| Sprint 4 | Cadastro/Login de Usuários (Autenticação) | 16/05/2026 | 23/05/2026 | Tela e Lógica de negócio do Cadastro/Login de usuários | Todos | — |
| Sprint 5 | — | — | — | — | — | — |
| Sprint 6 | — | — | — | — | — | — |
| Sprint 7 | — | — | — | — | — | — |
| Sprint 8 | — | — | — | — | — | — |
 
Fonte: Elaborado pelos autores, 2026.
 
## 2.4 Matriz de Comunicação
 
A comunicação entre os membros da equipe e demais stakeholders é fundamental para o sucesso do projeto, especialmente em um ambiente ágil.
 
Dessa forma, foi definida uma matriz de comunicação que estabelece a periodicidade das interações, os envolvidos e os artefatos gerados em cada tipo de reunião.
 
Quadro 4 - Matriz de comunicação
 
| Área / Envolvidos | Descrição | Periodicidade | Produtos Gerados |
|---|---|---|---|
| Todos | Reunião de planejamento e revisão da Sprint (Sprint Planning e Sprint Review) | Semanal | Ata de reunião, backlog atualizado e Kanban atualizado |
| Todos + Cliente | Reunião com cliente | Semanal | Ata de reunião, ajuste de prioridade e backlog atualizado |
| Todos | Daily assíncrona | Diária | Status do andamento do projeto de forma individualizada |
| Todos + Professor/Monitor | Comunicação sobre o andamento do projeto | Semanal | Relatório de feedbacks e Ata de reunião |
 
Fonte: Elaborado pelos autores, 2026.
 
## 2.5 Gerenciamento de Riscos
 
O gerenciamento de riscos se refere à identificação e monitoramento de eventos incertos, porém possíveis, de ocorrerem e que podem impactar o projeto, sendo fundamental para aumentar as chances de sucesso do projeto que está sendo realizado.
 
Esse gerenciamento tem como ponto de partida a determinação dos riscos em cada fase, e com isso a elaboração de ações para minimizar as chances de ocorrerem ou seu impacto caso venham a ocorrer. Paralelamente, os riscos devem ser registrados ao longo do desenvolvimento, para que possam ser acompanhados.
 
Por fim, de acordo com Rabechini Junior e Carvalho (2013), a adoção de práticas de gerenciamento de riscos traz benefícios significativos na realização e no desempenho dos projetos. A identificação e acompanhamento contínuo dos riscos permitem maior previsibilidade e controle sobre o andamento do projeto.
 
Quadro 5 - Gerenciamento de riscos
 
| Riscos | Grau de Exposição | Mitigação | Plano de contingência |
|---|---|---|---|
| Atraso no desenvolvimento | Alto | Divisão e acompanhamento das tarefas | Reorganização dos prazos |
| Falhas no sistema | Médio | Testes contínuos durante o desenvolvimento | Correção e ajustes no código |
| Indisponibilidade de membro da equipe | Médio | Monitoramento da participação | Redistribuição das tarefas |
| Problemas nos programas utilizados | Médio | Validação e testes no sistema | Recuperação e ajuste dos dados |
 
Fonte: Elaborado pelos autores, 2026.
 
## 2.6 Critérios de Replanejamento
 
Com base na natureza iterativa do projeto e nos riscos identificados, foram definidos critérios objetivos para o replanejamento, garantindo a adaptação do projeto a possíveis desvios de escopo, prazo ou recursos.
 
O projeto será sujeito a um replanejamento ao atingir algum dos critérios abaixo, implicando em uma reunião de replanejamento, alteração do backlog e o versionamento do documento:
 
- Taxa de conclusão da sprint abaixo de 70% por duas sprints consecutivas (indicando um escopo desalinhado com a capacidade da equipe);
- Indisponibilidade de algum membro por 2 sprints ou mais, sendo necessário a redistribuição de papéis e revisão do backlog;
- Mudança significativa de requisitos por parte do cliente que impactam funcionalidades já existentes ou em andamento;
- Atraso de 2 sprints ou mais em relação ao cronograma, exigindo alteração no escopo juntamente ao cliente para garantir uma entrega razoável.