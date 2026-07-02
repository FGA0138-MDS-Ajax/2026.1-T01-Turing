# 5. Métrica e medições
- Este parágrafo  apresenta uma proposta inicial de definição de métricas para o projeto Seu Espaço UnB (SeU), utilizando a abordagem GQM (Goal–Question–Metric).
Considerando que o projeto encontra-se em fase inicial, as métricas aqui definidas serão utilizadas futuramente para coleta e análise de dados ao longo das sprints, permitindo o acompanhamento da produtividade da equipe, do cumprimento de prazos e da qualidade do produto.
O projeto SeU (Seu Espaço UnB) está organizado em sprints semanais, nas quais funcionalidades específicas são desenvolvidas de forma incremental. A tabela referente as sprints encontra se no item 4.3 deste documento

# 5.1 GQM de Medições
-                   Quadro 9 - GQM de medições


| Elemento  | Descrição  |
|---|---|
|Analisar (Objeto)   | Processo de desenvolvimento do sistema SeU (Seu espaço unb)  |
| Propósito  | Avaliar  |
| Foco de qualidade  | Cumprimento de prazos, produtividade da equipe e qualidade do produto  |
| Ponto de vista  | Equipe de desenvolvimento  |
| Contexto  | Utilizando metodologia Scrum com Sprints semanais Projeto acadêmico da UnB  |

-   Fonte: Elaborado pelos autores, 2026.

# Questões
- As seguintes questões foram definidas com base no objetivo de medição:
Q1: O projeto está sendo entregue dentro dos prazos definidos nas sprints?
Q2: Qual o nível de produtividade da equipe ao longo das sprints?
Q3: O sistema apresenta muitos defeitos durante o desenvolvimento?
Q4: O débito técnico está sendo acumulado ao longo do projeto?
Q5: Os cenários funcionais estão sendo entregues conforme planejado?

# Métricas
**Métrica 1** - Taxa de cumprimento do prazo

**Ligação com GQM: Goal → Q1 → Métrica 1**

**Definição:**
Mede o percentual de tarefas concluídas dentro do prazo planejado.

**5W1H:**
|Item  |Descrição   |
|---|---|
|What (O que)   |Percentual de tarefas no prazo   |
|Why (Porque)   |Avaliar atrasos  |
|Who (Quem)   |Equipe de desenvolvimento   |
|Where (Onde)   |Github   |
|When (Quando)   |Ao final de cada sprint   |
|How (Como)   |Comparação entre tarefas planejadas e concluídas   |

**Fórmula:**
$\text{Taxa} = \frac{\text{Tarefas concluídas no prazo}}{\text{Total de tarefas}} \times 100$

|Unidade:   | Porcentagem   |
|---|---|
|Escala   |0% a 100%   |
|Valor Esperado   | >= 80%   |
|Analise   |Valores baixos indicam problemas no planejamento e execução das tarefas  |

**Métrica 2 Produtividade da Equipe**

**Ligação com GQM: Goal → Q2 → Métrica 2**

**Definição:**
Quantidade de tarefas concluídas por sprint.

|Item   |Descrição   |
|---|---|
|What   |Número de tarefas Concluídas   |
|Why   | Avaliar produtividade  |
|Who   | Equipe  |
|Where   | Github  |
|When   | Por Sprint  |
|How    | Contagem de tarefas finalizadas
|         |   |

**Fórmula:**

*Produtividade* = (Número de tarefass concluídas) / (Número de sprints)

|   |   |
|---|---|
|Unidade   | Tarefas / Sprints  |
|Escala   | Número inteiro >= 0  |
|Valor esperado   | Estável ou crescente  |
| Análise          | Queda na produtividade podem indicar problemas no processo ou sobrecargas da equipe

**Métrica 3 - Densidade de Defeitos**

Ligação com GQM: Goal -> Q3 -> Métrica 3

**Definição:**
Quantidadde de defeitos identificados por cenário funcional desenvolvido

**5W1H:**

|Item   | Descrição  |
|---|---|
|What   |Número de defeitos por cenário   |
|Why   |Avaliar qualidade do sistema   |
|Who   | Equipe   |
|Where     |GitHub (Issues)
|When      |Ao final de cada sprint
|How      |Contagem de bugs registrados

**Fórmula:**
Densidade = Número de defeitos / Número de cenários desenvolvidos

|   |   |
|---|---|
|Unidades   |Defeitos por cenário   |
|Escala   |>= 0   |
|Valor esperado   | < 0.5 |
|Análise   |Valores elevados indicam falhas na implementação e testes insuficientes   |


**Métrica 4 - Débito Técnico**

**Ligação com GQM: Goal → Q4 → Métrica 4**
 
**Definição:**
Quantidade de pendências técnicas acumuladas no projeto.
 
**5W1H:**
 
| Item | Descrição |
|---|---|
| What | Pendências técnicas |
| Why | Avaliar qualidade do código |
| Who | Equipe |
| Where | GitHub |
| When | Por sprint |
| How | Contagem de tarefas técnicas pendentes |
 
**Fórmula:**
Débito técnico = Número de pendências técnicas
 
| | |
|---|---|
| Unidade | Quantidade |
| Escala | >= 0 |
| Valor esperado | Redução ao longo do tempo |
| Análise | Aumento contínuo indica risco para manutenção futura do sistema |
 
**Métrica 5 – Taxa de Conclusão de Cenários Funcionais**
 
**Ligação com GQM: Goal → Q5 → Métrica 5**
 
**Definição:**
Percentual de cenários funcionais concluídos em relação ao planejado para cada sprint.
 
**5W1H:**
 
| Item | Descrição |
|---|---|
| What | Cenários funcionais concluídos |
| Why | Avaliar progresso |
| Who | Equipe |
| Where | Planejamento de sprints / GitHub |
| When | Por sprint |
| How | Comparação entre planejado e entregue |
 
**Fórmula:**
 
$\text{Taxa} = \frac{\text{Cenários concluídos}}{\text{Cenários planejados}} \times 100$
 
| | |
|---|---|
| Unidade | Porcentagem |
| Escala | 0% a 100% |
| Valor esperado | >= 90% |
| Análise | Valores abaixo indicam atrasos ou falhas no planejamento |
 
As métricas serão analisadas ao final de cada sprint semanal, por meio da comparação com os valores esperados e da observação de tendências ao longo do tempo. A análise poderá utilizar gráficos para identificar evolução ou degradação do processo. Desvios significativos indicarão a necessidade de ações corretivas, discutidas durante as reuniões de retrospectiva da equipe.
 