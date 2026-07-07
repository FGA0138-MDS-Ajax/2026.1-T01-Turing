# Ata de Reunião

**Data:** 29/06
**Local:** Google Meet

## Pauta e Resumo
* **Overview:** Definição das tarefas e responsabilidades da equipe para finalizar a integração, testes e documentação visando a apresentação do projeto em forma de site.
* **Decisão Principal:** Focar no nivelamento do sistema, garantindo integração entre frontend, backend, banco de dados e a API do Google Agenda, além da consolidação dos testes end-to-end e métricas.

## Divisão de Tarefas (Sprint Atual)

### Desenvolvimento e Integração
* **Arthur:** Desenvolver o Painel ADM e o Painel "Minhas reservas" com ícone e acesso às rotas do botão do Google Agenda. Checar a consistência das rotas do backend com a integração do frontend (solicitando a criação de novas rotas, se necessário) e garantir testes end-to-end da integração sem bugs na branch main. Alterar a estrutura das pastas do frontend para maior fluidez, documentar o código, realizar os ajustes pós-testes e entregar a integração completa até a apresentação.
* **Pedro (Galdino):** Realizar o merge das branches na main e corrigir os bugs do deploy do backend no Render. Executar testes end-to-end, incluindo a criação de usuários, salas e solicitações. Verificar e criar uma padronização das rotas do projeto, comunicar as alterações ao frontend e alterar a estrutura das pastas do backend no GitHub.
* **Gabriel:** Prestar apoio nas telas de "Minhas reservas" e Painel ADM, auxiliando Arthur e Gabriel nas telas faltantes utilizando o contexto da branch *CalendarioOcupacao*. Focar na clareza visual das telas de solicitação e painel ADM, entregando a integração com o backend e banco de dados para a apresentação. Auxiliar na alteração das estruturas de pastas do frontend, documentar o código, realizar testes de frontend e documentar os devidos ajustes.
* **Ítalo:** Integrar a tela de solicitação de reserva com as rotas do backend (rota nova: `/reservar`) e realizar testes end-to-end do fluxo contendo sala, horário, data, motivo e confirmação. Auxiliar nas outras telas disponíveis e na integração geral entre elas. Alterar a estrutura das pastas do frontend, documentar o código estrutural e realizar testes e ajustes documentados.
* **Marcely:** Finalizar os testes end-to-end do Google Agenda e integrar a aplicação na *developer-backend*. Expor a rota (ex: `POST/turing/agenda/evento`) para o frontend utilizar após uma solicitação aprovada e comunicar o contrato da rota (payload e resposta) para Arthur e Ítalo. Ajudar a corrigir erros no backend e produzir os slides/documentação do funcionamento do Google Agenda no projeto.
* **Alan Farias:** Realizar os testes end-to-end da parte do frontend. Executar a correção dos bugs estruturais do frontend com base nestes testes. Visualizar e monitorar o andamento geral da integração com o backend, banco de dados e navegação do site.
* **Kauan:** Consolidar as tarefas do time e auxiliar o backend no tratamento de erros e integração de branches. Realizar reuniões visando o fechamento das etapas, auxiliar na criação dos slides e na produção da documentação.

### Documentação, Testes e Métricas
* **Samuel:** Revisar a documentação de Arquitetura, Visão do produto/projeto, os escopos e as sprints, além de preparar os testes gerais. Checar o que falta para atender ao escopo final, garantindo a aderência mútua entre o documento e o projeto (e vice-versa), e atualizar gráficos/tabelas pós-conclusão do ciclo.
* **Evellyn:** Finalizar as métricas do projeto e exportá-las para a apresentação. Auxiliar na documentação do GitHub, englobando código, fluxo de trabalho, métricas e testes. Dar suporte na entrega do documento final e na estruturação dos slides, focando principalmente nas métricas.
* **Rafaela:** Executar testes após o merge do Pedro (Galdino), criar e documentar novos casos de teste no documento de visão do produto. Auxiliar na correção dos bugs descobertos e documentar fluxos essenciais (usuário, administrador e Google Agenda) para a apresentação de sábado, produzindo também um slide específico sobre o tema.
* **Júlia:** Atualizar o documento de visão, documento de arquitetura e o GitHub Pages. Documentar os códigos existentes, incluindo o processo de inicialização do projeto e as lógicas de negócio voltadas para a apresentação.
* **Tiago:** Realizar os testes de integração internos do banco de dados. Documentar as entidades, seus relacionamentos e criar os slides de apresentação desta parte da infraestrutura.