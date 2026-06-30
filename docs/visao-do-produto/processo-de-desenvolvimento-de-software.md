# 3. Processo de Desenvolvimento de Software

- A Equipe adota a combinação de Scrum para base do gerenciamento, papéis e cadência de eventos, enquanto o XP dita as regras práticas técnicas do Processo de Construção da Engenharia de Software do produto garantindo alta qualidade.Como metodologias ágeis de desenvolvimento demandam respostas contínuas e mudanças constantes, o processo é estruturado da seguinte forma:

**_Práticas Scrum Aderidas e/ou adaptadas para o projeto:_**

**Reuniões semanais**

**Reunião com o cliente**

Realização semanal com objetivo de:
-  Revisar prioridades do product backlog;
- Alinhar expectativas;
- Apresentar o incremento de software da última sprint;
- Discutir Alterações necessárias;

**Reunião com as equipe**

Realização semanal com objetivo de:
- Planejamento da sprint e revisão da última sprint;
- Organização dos passos que serão tomados;
- Planejamento técnico da Sprint atual;

**Daily Assíncrona:** Adaptação da Daily Scrum. Consiste em um reporte diário via WhatsApp feito pelos desenvolvedores, com o andamento das tarefas em direção à meta da sprint, impedimentos técnicos encontrados e o plano de ação para o próximo dia.

**_Práticas XP Aderidas e/ou adaptadas para o projeto:_**

**Controle de versão:** Desenvolvimento feito usando branches isoladas para cada funcionalidade

**Revisão em Pares:** A aprovação de Pull Requests (PRs) é obrigatória antes de qualquer mesclagem (merge) com a branch principal (main). Isso emula os benefícios de revisão da programação em pares do XP. 

**Integração e Testes:** O código só pode ser integrado à base principal se passar pela revisão e se todos os testes forem executados com sucesso. 

**Definition of Done (DoD):** A Definição de Pronto (Definition of Done) cria transparência e garante que todos entendam quais medidas de qualidade foram aplicadas ao incremento. Uma funcionalidade só é considerada pronta quando: 
O código foi totalmente implementado e revisado via Pull Request aprovado.
Os testes foram realizados e aprovados (100% de sucesso).
Não há defeitos ou falhas críticas em aberto.
A funcionalidade foi validada e aprovada pelo Product Owner (P.O.) / Cliente.
A documentação pertinente foi devidamente atualizada.

**Diagrama de Fluxo de trabalho**
O Diagrama abaixo ilustra as atividades encadeadas do nosso processo adotado, desde a priorização até a entrega do incremento validado.

![Diagrama de Fluxo de Trabalho](<../static/Diagrama-processo-software.jpeg>)

Fonte: Elaborado pelos autores,2026.




