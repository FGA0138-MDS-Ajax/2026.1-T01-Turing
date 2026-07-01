# Ata de Reunião

**Data:** 25/06/2026
**Local:** Google Meet

## Pauta e Resumo
* **Resumo:** A reunião consolidou o status do desenvolvimento, alinhou critérios de conclusão e ajustou o cronograma da sprint.
* **Status técnico e documentação:** A implementação do backend avançou com a definição de regras para usuários administradores. O sucesso no merge para a branch principal foi estabelecido como critério oficial para conclusão de tarefas
* **Integração e ajustes da interface:** O progresso na interface adaptada enfrentou desafios de deploy automático, exigindo correções no backend.
* **Gestão de sprint e riscos:** Foi criada uma nona sprint para acomodar entregas finais e documentar melhor a aderência do projeto. O grupo discutiu estratégias para mitigar riscos de baixa adesão de usuários com confirmações de presença.

## Proximas Etapas:
* Atualizar documentacao: Atualizar a tabela de criterios de cenarios e adocumentacao no GitHub Pages.
* Revisar arquitetura: Revisar o documento de arquitetura e verificar os padroes implementados.
* Adaptar frontend: Adaptar JavaScript e CSS na pagina integrada.
* Ajustar rotas: Arrumar a rota de redirecionamento do botao de entrar.
* Implementar Google Agenda: Finalizar a implementacao do Google Agenda e sua integracao com o frontend.
* Compartilhar credenciais: Enviar o e-mail e a senha criados no console do
* Cloud para os outros membros da equipe.
* Refazer fluxo de trabalho: Refazer o fluxo de trabalho e enviar no grupo.
* Atualizar documentação: Adicionar instruções de uso ao README e à página
* inicial da documentação no GitHub Pages.
* Documentar testes: Verificar no grupo se os testes foram documentados e,
se possível, incluí-los até sábado.
* Corrigir documentos: Corrigir os pontos destacados pelo professor na correção dos documentos de visão e arquitetura.
* Analisar Spring Security: Analisar a implementação de segurança com Spring Security e verificar a viabilidade de ajustar as configurações de senha.
* Compartilhar bibliotecas: Compartilhar as bibliotecas utilizadas no código no grupo para possível uso em outras partes do projeto.
* Revisar documento de visão: Revisar o documento de visão enviado pelo professor para entender os questionamentos sobre métricas e riscos de negócio.

## Detalhes

* **Estado das funcionalidades do backend:** Pedro Galdino relatou a
conclusão da funcionalidade de criação de usuários como clientes,
mencionando que ainda é necessário implementar a lógica para definir o
primeiro registro como administrador, caso o banco de dados esteja vazio.
Kauan Tarchetti e Pedro Galdino discutiram a necessidade de testar a
integração entre front-end e back-end, com Kauan Tarchetti reforçando a
estratégia de testar no servidor de nuvem antes de mesclar na branch main
para evitar erros.
* **Planejamento de reuniões e Google Agenda:** Kauan Tarchetti, Pedro
Galdino e Gabriel Vieira alinharam uma reunião para o dia seguinte, com o
objetivo de avançar na integração das funcionalidades do sistema.
* **Gestão das sprints e documentação de aderência:** Samuel Carvalho
propôs a criação de uma nona sprint para acomodar as entregas finais, dado
que o cronograma original contava apenas com oito. Samuel Carvalho
também introduziu uma nova coluna de status (entregue, parcial, não
entregue) na tabela de backlog para documentar melhor a aderência do
projeto e o progresso real das tarefas.
* **Critérios de conclusão e atualização de documentos:** Kauan Tarchetti e
Samuel Carvalho estabeleceram que, para fins de documentação, o sucesso
do merge na branch main será o critério para considerar uma tarefa como
100% concluída. O grupo concordou em atualizar o documento de
arquitetura e o de visão no GitHub, corrigindo gráficos e tabelas conforme o
feedback recebido.
* **Cronograma e inspeção de repositórios:** A equipe confirmou que a
inspeção dos repositórios GitHub será realizada no próximo domingo. Kauan
Tarchetti orientou que a documentação, incluindo a do GitHub Pages, deve
ser atualizada para garantir que o projeto esteja em conformidade para essa
inspeção.
* **Integração de front-end e back-end:** Arthur Brayan apresentou o
progresso na adaptação da interface (HTML/JavaScript/CSS) e relatou falhas
no deploy automático no Netlify, enquanto Kauan Tarchetti se comprometeu
a corrigir erros de importação no back-end para facilitar a integração entre
as partes.
* **Desenvolvimento de UI/UX e prototipagem**: Gabriel Vieira sugeriu a
implementação de um efeito de translucidez ("glassmorphism") e maior
fluidez nas telas para modernizar a interface, enquanto Arthur Brayan
demonstrou a estrutura das páginas e a integração das bibliotecas de
animação.
* **Integração com Google Agenda:** Marceli Silva explicou que está
pesquisando a API do Google e encontrando desafios relacionados à
autenticação e ao uso do framework, especificamente com dependências
do Maven . A equipe decidiu priorizar as funcionalidades básicas, avaliando
que integrações complexas de notificação poderão ser feitas apenas se
houver tempo disponível).
* **Segurança e validação:** Gabriel Vieira e Marceli Silva discutiram a
implementação de validação de e-mail e segurança no sistema, com Gabriel
Vieira mencionando que já havia trabalhado com bibliotecas de verificação
de senha). O grupo também abordou a necessidade de limitar o número de
solicitações.
* **Refinamento de diagramas e feedback do professor:** O grupo debateu a
necessidade de corrigir diagramas UML e de atividades para que
correspondam ao código real, respondendo a críticas específicas feitas pelo
professor sobre a clareza dos gráficos e a validade dos diferenciais do
projeto.
* **Gestão de riscos e engajamento:** Kauan Tarchetti e Marceli Silva discutiram
como abordar riscos de negócio, como a baixa adesão de usuários. O grupo
considerou a possibilidade de implementar funcionalidades de confirmação
de presença ("check-in") para garantir que as reservas de salas sejam de fato
utilizadas.

