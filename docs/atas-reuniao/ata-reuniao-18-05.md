# Ata de Reunião

**Data:** 18/05/2026
**Local:** Google Meet


## Pauta e Resumo
* Ficou definido que o foco desta sprint será usuário + sala + horário + autenticação + Spring Security/JWT.
* A equipe decidiu manter um único modelo de usuário (sem admin e cliente separados por herança), usando role/perfil para diferenciar permissões.
* Para a próxima etapa, o grupo tende a seguir com sala e horário segundo DOC de arquitetura e DOC de visão; a solicitação fica para depois, porque depende dessas entidades.
* No front, Arthur já começou os protótipos no Sigma/Figma: login, cadastro e tela inicial.
* Também foi discutida a integração React + Spring via JSON/API e a necessidade de revisar testes com Postman.

## Divisão de Tarefas (Sprint Atual)

Para esta Sprint, cada grupo deverá estudar e implementar o módulo correspondente. Na reunião de quarta-feira, cada pessoa apresentará o que aprendeu; na reunião de segunda-feira seguinte, será avaliado o que foi implementado.

### 1. Spring Security + JWT + Terminar Entidade de Usuário
**Responsáveis:** Pedro Galdino, Samuel Carvalho, Kauan
* **Escopo:** Autenticação, geração de token, controle de perfis (roles), Cadastro/Autenticação/Alteração de usuário segundo DOC de Visão.
* **Observação:** Inclui Services, Controllers e Repositório.

### 2. Entidades, Services, Controllers, Repositório e DTO (JPA) de Sala e Horário
**Responsáveis:** Júlia, Alan, Marcely, Thiago, Rafaela, Evelyn
* **Escopo:** 
  * Toda a Lógica de negócio, rotas e mapeamento de entidades de Sala e Horário segundo o DOC de arquitetura.
  * Listagem de Espaços, Filtro de espaços e Detalhes de espaços segundo DOC de visão.

### 3. Telas (Login, Cadastro, etc)
**Responsáveis:** Gabriel Vieira, Arthur Brayan, Italo
* **Escopo:** Construção das telas tanto no FIGMA quanto implementado em Código propriamente dito.