# 6. Testes de Software
 
## 6.1 Estratégia de Testes
 
Para a validação do sistema usaremos testes unitários, que serão utilizados para verificação imediata de cada parte nova do código. Testes de integração, que determinam se as funcionalidades estão sendo executadas de forma integrada. Além de serem feitos testes de sistema, que vão garantir que as funcionalidades seguem os requisitos. Além da utilização de testes funcionais para termos feedbacks do funcionamento das funcionalidades que terão no sistema e não funcionais que serão aplicados principalmente em requisitos de desempenho, segurança e usabilidade do sistema.
 
Todo o desenvolvimento ocorrerá de forma integrada ao Github, onde cada requisito do projeto, como cadastro, consulta de espaço, reserva, cancelamento, etc, terá sua própria branch para testes locais antes da junção com a branch main, além de termos branches para a documentação e para informação do projeto, que facilitará a visualização da evolução do sistema e organização. Além disso, em primeiro momento para os testes automatizados será utilizado o Github Actions.
 
Para a análise dos testes, será feita a validação no sistema por meio da comparação entre o que se espera e o resultado, e qualquer divergência será registrada como um defeito, corrigida e testada novamente, até que obtenhamos uma versão estável.

Figura 6 - Diagrama do Processo de Desenvolvimento

![Diagrama do processo de desenvolvimento](static/figura6-white.png)

Fonte: Elaborado pelo grupo

 
## 6.2 Roteiro de Teste
 
O roteiro de testes consiste no planejamento dos casos de testes que serão executados ao longo do desenvolvimento do sistema Seu Espaço UnB, com o objetivo de validar as funcionalidades definidas no backlog do produto e garantir a qualidade da aplicação.
 
O roteiro deve conter:
- Código de identificação do teste
- Nome do teste
- Objetivo do teste
- Nível do teste (unitário, integrado, sistema)
- Tipo de teste (funcional, não funcional — se não funcional, especificar tipo: usabilidade, portabilidade, etc.)
- Precondições para o teste ser realizado
- Definição de Aceito/Rejeitado dos testes propostos (resultados esperados para o teste ser aceito como OK)
- Espaço para registro dos resultados do teste (com evidências objetivas)
- Reparos executados
- Quantidade de ciclos de testes executados para cada caso de teste proposto
Quadro x - Roteiro de Testes do Sistema Seu Espaço UnB
 
| ID | Nome do Teste | Objetivo do Teste | Nível | Tipo | Pré-condições | Critério de Aceite | Resultado | Evidências | Reparos | Ciclos |
|---|---|---|---|---|---|---|---|---|---|---|
| CT-01 | Cadastro de usuário | Verificar cadastro | Sistema | Funcional | Usuário não cadastrado | Cadastro realizado | Aprovado | ![teste-ct01](../static/ct01.png)  | Nenhum | 1 |
| CT-02 | Login válido | Validar login correto | Sistema | Funcional | Usuário cadastrado | Acesso permitido | Aprovado | ![teste-ct02](../static/ct02.png) | Nenhum | 1 |
| CT-03 | Login inválido | Validar erro no login | Sistema | Funcional | Usuário cadastrado | Mensagem de erro | Aprovado | ![teste-ct03](../static/ct03.png) | Nenhum | 1 |
| CT-04 | Validação de login | Validar função de autenticação | Unitário | Funcional | Dados válidos | Retorna sucesso | Aprovado | ![teste-ct04](../static/cts040610.png) | Nenhum | 1 |
| CT-05 | Consulta de espaços | Verificar listagem | Integração | Funcional | Usuário logado | Lista exibida | Aprovado | ![teste-ct05](../static/ct05.png) | Nenhum | 1 |
| CT-06 | Verificar disponibilidade | Validar horários disponíveis | Unitário | Funcional | Horários definidos | Retorna disponibilidade correta | Aprovado | ![teste-ct06](../static/cts040610.png) | Nenhum | 1 |
| CT-07 | Filtro de espaços | Validar filtro | Integração | Funcional | Lista disponível | Filtro correto | Aprovado | ![teste-ct07](../static/ct07.png) | Nenhum | 1 |
| CT-08 | Detalhes da sala | Verificar detalhes | Sistema | Funcional | Sala selecionada | Detalhes exibidos | Aprovado | ![teste-ct08](../static/ct08.png) | Nenhum | 1 |
| CT-09 | Solicitação de reserva | Criar reserva | Sistema | Funcional | Usuário logado | Reserva criada | Aprovado | ![teste-ct09](../static/ct09.png) | Nenhum | 1 |
| CT-10 | Criação de reserva | Validar função de criação | Unitário | Funcional | Dados válidos | Reserva criada corretamente | Aprovado | ![teste-ct10](../static/cts040610.png) | Nenhum | 1 |
| CT-11 | Cancelar reserva | Cancelar reserva | Sistema | Funcional | Reserva existente | Reserva cancelada | Aprovado | ![teste-ct11](../static/ct11.png) | Nenhum | 1 |
| CT-12 | Aprovar reserva | Aprovar reserva | Sistema | Funcional | Reserva pendente | Reserva aprovada | Aprovado | ![teste-ct12](../static/ct12.png) | Nenhum | 1 |
| CT-13 | Rejeitar reserva | Rejeitar reserva | Sistema | Funcional | Reserva pendente | Reserva rejeitada | Aprovado | ![teste-ct13](../static/ct13.png) | Nenhum | 1 |
| CT-14 | Desempenho de resposta | Tempo de resposta | Sistema | Não Funcional | Sistema ativo | < 2 segundos | Aprovado | ![teste-ct14](../static/ct14.png) | Nenhum | 1 |
| CT-15 | Usabilidade | Facilidade de uso | Sistema | Não Funcional | Interface ativa | Uso intuitivo | Pendente | - | - | - |
