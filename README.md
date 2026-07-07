<div align="center">
  <img src="docs/static/logomarca-horizontal-produto.png" alt="Seu Espaço UnB" width="360"/>
</div>

<h3 align="center">Seu Espaço UnB (SeU)</h3>
<p align="center">Sistema de reserva de espaços acadêmicos da Universidade de Brasília, desenvolvido para a disciplina de Métodos de Desenvolvimento de Software (MDS) — FGA/UnB.</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen" alt="status"/>
  <img src="https://img.shields.io/badge/backend-Java%20%7C%20Spring%20Boot-blue" alt="backend"/>
  <img src="https://img.shields.io/badge/frontend-React%20%7C%20TypeScript-blue" alt="frontend"/>
  <img src="https://img.shields.io/badge/banco-MySQL-orange" alt="banco"/>
</p>

---
## 📖 Sobre o projeto

Estudantes e professores da UnB frequentemente precisam reservar salas e espaços compartilhados para reuniões, monitorias e eventos acadêmicos — hoje um processo manual e descentralizado. O **SeU** centraliza esse fluxo em uma aplicação web: o usuário consulta a disponibilidade de uma sala em um calendário, envia uma solicitação de reserva e um administrador aprova ou rejeita o pedido, com controle de capacidade e conflitos de horário.

O projeto foi construído em 8 sprints por uma equipe de 12 pessoas, cobrindo desde a modelagem UML e o documento de arquitetura até o deploy em produção.

## ✨ Funcionalidades

- Cadastro e login de usuários com autenticação JWT e papéis distintos (aluno/administrador)
- Consulta de disponibilidade de salas por data e horário
- Solicitação de reserva com validação de capacidade (`quantidadeParticipantes`) e detecção de conflitos
- Fluxo de aprovação administrativa (`PENDENTE` → `APROVADA` / `REJEITADA` / `CANCELADA`)
- Painel administrativo com dashboard de indicadores de uso das salas
- Notificação por e-mail das atualizações de status da solicitação

## 🏗️ Arquitetura

O documento completo de arquitetura está em [`docs/MDS Documento de Arquitetura Turing.pdf`](docs/MDS%20Documento%20de%20Arquitetura%20Turing.pdf). Principais diagramas:

| Diagrama | Arquivo |
|---|---|
| Casos de uso | [`docs/static/diagrama-casos-uso.png`](docs/static/diagrama-casos-uso.png) |
| Classes | [`docs/static/diagrama-classes.png`](docs/static/diagrama-classes.png) |
| Componentes | [`docs/static/diagrama-componentes.png`](docs/static/diagrama-componentes.png) |
| Implantação (deploy) | [`docs/static/diagrama-implantacao.png`](docs/static/diagrama-implantacao.png) |

## 🚀 Como rodar localmente

### Backend
```bash
cd turing
cp .env.example .env        # preencha as variáveis (DB, JWT_SECRET, e-mail)
docker-compose up -d        # sobe o banco MySQL local
./mvnw spring-boot:run
```
A API sobe por padrão em `http://localhost:8080`.

### Frontend
```bash
cd SeU-tela-login
npm install
npm run dev
```
A aplicação sobe por padrão em `http://localhost:5173`.

## 📁 Estrutura do repositório

```
├── turing/            # Backend (Spring Boot)
├── SeU-tela-login/    # Frontend (React + TypeScript)
├── docs/              # Documentação MDS (visão de produto, arquitetura, atas)
└── mkdocs.yml         # Configuração da documentação navegável
```

A documentação navegável completa (MkDocs) pode ser consultada a partir de [`docs/index.md`](docs/index.md).

---

**Stack de deploy:** backend no Render, frontend no Netlify, banco MySQL no Railway.
 
## 👥 Equipe
 
<div align="center">
<table>
<tr>
<td align="center" width="150px">
<a href="https://github.com/AlanFBraga">
<img src="https://avatars.githubusercontent.com/u/154356279?v=4" width="90px;" style="border-radius:50%" alt="Alan"/>
<br><br>
<b>Alan Farias</b>
</a>
<br>
<sub>P.O · Backend · Frontend</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/kauantarchetti">
<img src="https://avatars.githubusercontent.com/u/212615435?v=4" width="90px;" style="border-radius:50%" alt="Kauan"/>
<br><br>
<b>Kauan Tarchetti</b>
</a>
<br>
<sub>Scrum Master · Backend · BD</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/juliamenes">
<img src="https://avatars.githubusercontent.com/u/130939657?v=4" width="90px;" style="border-radius:50%" alt="Júlia"/>
<br><br>
<b>Júlia Pêgo</b>
</a>
<br>
<sub>Backend</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/NSMarcely">
<img src="https://avatars.githubusercontent.com/u/238081103?v=4" width="90px;" style="border-radius:50%" alt="Marcely"/>
<br><br>
<b>Marcely do Nascimento</b>
</a>
<br>
<sub>Backend · Banco de Dados</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/selksi">
<img src="https://avatars.githubusercontent.com/u/210460243?v=4" width="90px;" style="border-radius:50%" alt="Samuel"/>
<br><br>
<b>Samuel Carvalho</b>
</a>
<br>
<sub>Backend</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/PedroGaldino2007">
<img src="https://avatars.githubusercontent.com/u/169562476?v=4" width="90px;" style="border-radius:50%" alt="Pedro"/>
<br><br>
<b>Pedro Galdino</b>
</a>
<br>
<sub>Backend · Banco de Dados</sub>
</td>
</tr>
<tr>
<td align="center" width="150px">
<a href="https://github.com/arthurbra2806">
<img src="https://avatars.githubusercontent.com/u/95577748?v=4" width="90px;" style="border-radius:50%" alt="Arthur"/>
<br><br>
<b>Arthur Brayan</b>
</a>
<br>
<sub>Frontend</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/italocarlosss">
<img src="https://avatars.githubusercontent.com/u/227226203?v=4" width="90px;" style="border-radius:50%" alt="Ítalo"/>
<br><br>
<b>Ítalo Carlos</b>
</a>
<br>
<sub>Frontend</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/aleafaruos">
<img src="https://avatars.githubusercontent.com/u/133793265?v=4" width="90px;" style="border-radius:50%" alt="Rafaela"/>
<br><br>
<b>Rafaela Santos</b>
</a>
<br>
<sub>Frontend · Banco de Dados</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/ellevyn7">
<img src="https://avatars.githubusercontent.com/u/208838360?v=4" width="90px;" style="border-radius:50%" alt="Evelyn"/>
<br><br>
<b>Evelyn de Sousa</b>
</a>
<br>
<sub>Banco de Dados</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/TiagoCTnepo">
<img src="https://avatars.githubusercontent.com/u/73538646?v=4" width="90px;" style="border-radius:50%" alt="Tiago"/>
<br><br>
<b>Tiago Sousa</b>
</a>
<br>
<sub>Banco de Dados</sub>
</td>
<td align="center" width="150px">
<a href="https://github.com/gsVieiraaa">
<img src="https://avatars.githubusercontent.com/u/101344435?v=4" width="90px"; style="border-radius:50%" alt="Gabriel""
<br><br>
<b>Gabriel Vieira</b>
</a>
<br>
<sub>Frontend</sub>
</td>
</tr>
</table>
</div>
---

## 🛠️ Tecnologias
 
### Frontend
- React · TypeScript · Vite
### Backend
- Java · Spring Boot · Spring Security · JWT
### Banco de Dados
- MySQL
### Ferramentas
- Git · GitHub · Docker · Render · Railway · MkDocs · Figma
---


# Política de Branches
| Branch | Descrição |
|--------|-----------|
| `main` | Versão estável e pronta para produção. Commits diretos proibidos. |
| `developer` | Integração das features; testes de integração são executados aqui. |
| `feature<nome>` | Desenvolvimento de funcionalidade específica.|

---

# Política de commit
| Prefixo | Uso |
|---------|-----|
| `add/ feat:` | Nova funcionalidade |
| `fix:` | Correção de bug |
| `refactor:` | Alteração de regra de negócio |
| `docs:` | Mudanças em documentação |
| `style:` | Formatação sem impacto na lógica |
| `test:` | Adição ou correção de testes |
| `chore:` | Tarefas de build, CI/CD, dependências |
 
> Exemplo: `git commit -m "feat: adiciona endpoint de reserva de sala"`
 

