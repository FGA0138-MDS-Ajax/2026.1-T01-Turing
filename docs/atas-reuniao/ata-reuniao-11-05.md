# Ata de Reunião

**Data:** 11/05/2026
**Local:** Google Meet


## Pauta
* Início da codificação do projeto - inicialização do repositório.
* Divisão de responsabilidades técnicas entre os membros.
* Definição da arquitetura back-end (Spring Boot) e front-end (React).
* Estratégia de integração entre front-end e back-end via API REST.
* Fluxo de branches e pull requests no GitHub.
* Discussão sobre tecnologias auxiliares: JWT, Spring Security, JPA, Flyway, Axios.

## Discussões e Decisões

### Inicialização do Repositório
* Kauan ficou responsável por inicializar o repositório ainda no dia da reunião ou no dia seguinte, utilizando o Spring Initializr. 
* As dependências acordadas para a inicialização são: Spring Web, Spring Data JPA, Spring Security, Driver do MySQL e JWT (para geração e validação de tokens).

### Fluxo de Branches e Pull Requests
* Cada funcionalidade é desenvolvida em uma branch `feature/<nome-da-feature>`.
* Ao concluir, abre-se um Pull Request para a branch de integração correspondente (ex.: `develop/back-end` ou `develop/front-end`).
* Ao final de cada Sprint, essas branches de integração são mescladas na branch `main` via Pull Request, com revisão da equipe.
* O uso do GitHub Desktop foi recomendado por Pedro para facilitar o fluxo de commits e PRs.

##  Divisão de Tarefas (Sprint Atual)

Para esta Sprint, cada grupo deverá estudar e implementar o módulo correspondente. 

### 1. Spring Security + JWT
**Responsáveis:** Pedro Galdino, Samuel Carvalho
* **Escopo:** Autenticação, geração de token, controle de perfis (roles).

### 2. JPA - Entidade, DTO e Repositório
**Responsáveis:** Thiago, Rafaela, Evelyn
* **Escopo:** Mapeamento de entidades, ORM, DDL automático via Spring Data JPA.

### 3. Tratamento de Exceções
**Responsável:** Kauan T.
* **Escopo:** Global Exception Handler centralizado para erros da API.

### 4. Service + Controller de Usuário
**Responsáveis:** Júlia, Alan, Marcely
* **Escopo:** Lógica de negócio e rotas REST do usuário (cliente/ADM via roles).

### 5. Integração Spring + React (API REST)
**Responsáveis:** Gabriel Vieira, Arthur Brayan, Italo
* **Escopo:** Consumo de endpoints, separação front/back, deploy.

## Próximos Passos e Reuniões
* **Quarta-feira:** Pincelada nos pontos de cada área; cada membro apresenta o que estudou até o momento.
* **Segunda-feira seguinte:** Revisão do que foi implementado e planejamento da próxima etapa.