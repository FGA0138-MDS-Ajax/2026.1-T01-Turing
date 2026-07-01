# Ata de Reunião

**Data:** 23/06/2026
**Local:** Google Meet

## Pauta e Resumo
* **Overview:** Discussão técnica sobre integração do front-end com o back-end, correções de lógica de negócio (especialmente no fluxo de solicitações de reservas) e organização das tarefas finais para a apresentação.
* **Decisão Principal:** Foco principal em corrigir a lógica de negócio do sistema de solicitações de salas e finalizar a integração front → back para a apresentação final.
* **Acordo Geral:** Há consenso de que o projeto está perto do estado apresentável no front. A principal pendência é estabilizar o back (regras de solicitação, tratamento de erros e permissões) e a integração com Google Agenda. Após isso, a equipe objetiva ter tudo pronto para testes e apresentação.

## Divisão de Tarefas (Sprint Atual)

###  Desenvolvimento e Integração
* **Arthur:** Revisar o código do Gabriel, adaptar as telas do código e concluir a tela inicial até aproximadamente quinta-feira. Terminar a integração back e front (atualmente a branch main tem apenas Login e Cadastro). **Prioridade máxima:** puxar `developer-frontend / Calendario Ocupacao` e integrar com backend.
* **Pedro (Galdino):** Refatorar a lógica de validação de solicitações (permitir múltiplas solicitações pendentes para o mesmo horário e aceitá-las por critério). Ajuste de bugs, adição de funcionalidades para o front e criação/alteração de rotas e services.
* **Gabriel:** Colaborar com Arthur para reaproveitar componentes do frontend (especialmente a tela de Calendário). Fornecer apoio técnico para integração back e front e comunicar ao Galdino quais filtros e inputs o front precisa.
* **Ítalo:** Trabalhar em conjunto com Arthur e Gabriel, visando a entrega do frontend integrado ao backend com todas as telas. Identificar e informar os endpoints faltantes ao Galdino/Alan.
* **Marcely:** Realizar a Integração do backend com a API do Google Agenda e criar as rotas para comunicação direta com o front (Crítico: `CalendarService`, novas rotas e comunicação com front ainda ausentes).
* **Alan Farias:** Como Product Owner, visualizar se o escopo entregue atende ao projeto (salas, horários, solicitações, usuários, auth, Google Agenda e dashboards). Ajudar o front com a integração pelo lado do back-end e realizar implementação de dashboards ausentes.
* **Kauan:** Consolidar tarefas, puxar as branches mais atualizadas (ex: `developer-frontend`, `Calendario Ocupacao`, `refatorando-horarioSala_solicitacao`) para testar integração, organizar testes finais e atualizar Docs/Mkdocs/Github Pages.

###  Documentação, Testes e Métricas
* **Samuel:** Revisar a documentação de Arquitetura, Visão do produto e Sprints. Ficar responsável por checar a aderência entre o documento e o projeto (adequando um ao outro) e atualizar gráficos/tabelas após a conclusão do ciclo de desenvolvimento.
* **Evellyn:** Averiguar métricas do projeto (engajamento, capacidade da sala, ocupação real). Gerar tabelas e gráficos para revisão (exige o sistema rodando com dados reais).
* **Rafaela:** Realizar Testes Funcionais, de Integração e Unitários (backend possui estrutura testável). Documentar todos os testes no documento de visão e atualizar o roteiro de testes (depende da estabilização do front).
* **Júlia:** Atualizar o documento de visão/arquitetura e Github Pages. Estudar o código para adicionar mais detalhes, atualizar tabelas/gráficos e documentar o código em parte separada no MkDocs.
* **Tiago:** Visualizar a lógica de negócio do banco de dados e atualizar o ERD, gráficos e tabelas no documento de arquitetura e GitHub Pages. Verificar se as entidades (Sala, HorarioSala, Solicitacao, Usuario) refletem o documento.

## Problemas Técnicos e Riscos Identificados

**1. Lógica de Solicitação de Sala:**
* O back impede criar nova solicitação quando já existe uma pendente/aprovada no mesmo horário. A lógica deve permitir múltiplas e deixar o ADM aprovar individualmente, rejeitando as demais.
* Necessidade de validação para cancelamentos (só proprietário ou ADM podem cancelar).
* Sugestão de desempate: FIFO (ordenar por data de criação).

**2. Erros HTTP, Exceções e Riscos:**
* Erros 405 (método não permitido) e 403 ainda não totalmente resolvidos, gerando risco de impedir testes no ambiente de hospedagem.
* Tratamento de erro parcial; é preciso garantir que todos os endpoints chamem o handler apropriado.

**3. Integração e Hospedagem:**
* O front em produção/hospedagem está com versão antiga, o que causa inconsistência. Precisa ser atualizado para integrar corretamente.

**4. Internacionalização / Data:**
* Problema de tradução/locale para dias da semana. Proposta de usar biblioteca para mapeamento no frontend em tempo de renderização em vez de refatoração extensa.