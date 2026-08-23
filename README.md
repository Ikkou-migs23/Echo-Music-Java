<div align="center">

# 🎵 Echo Music

### Sua música. Sua playlist. Seu jeito.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-Application-6C63FF?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)
![Projeto Acadêmico](https://img.shields.io/badge/Projeto-Acadêmico-blue?style=for-the-badge)

<br>

🎶 **Selecione.**  
🎧 **Organize.**  
✨ **Reproduza.**

</div>

---

## 🎵 Sobre o Echo Music

O **Echo Music (EHM)** é uma aplicação desktop desenvolvida em **Java**, criada com o objetivo de oferecer uma experiência prática, intuitiva e organizada para **criação, gerenciamento e reprodução de músicas e playlists**.

O projeto adapta conceitos de uma aplicação web previamente estruturada para o ambiente desktop, buscando manter uma navegação simples, uma interface agradável e uma experiência fluida para o usuário.

A aplicação permite que o usuário organize suas músicas favoritas, crie playlists personalizadas, controle a reprodução e mantenha seus dados armazenados localmente.

---

## 📌 Informações do Projeto

<div align="center">

| 🎓 Projeto | 💻 Tecnologia | 🏫 Instituição | 🚧 Status |
|:---:|:---:|:---:|:---:|
| Echo Music | Java | IF Goiano | Em desenvolvimento |

</div>

**Curso:** Técnico em Informática Integrado ao Ensino Médio  
**Disciplina:** Desenvolvimento para Desktop  
**Campus:** Cristalina  
**Instituição:** Instituto Federal Goiano — IF Goiano

---

## 🎯 Objetivo

O principal objetivo do **Echo Music** é desenvolver uma plataforma de música para desktop que permita ao usuário **centralizar e organizar sua experiência musical** em um único ambiente.

A aplicação busca realizar tarefas como:

1. 👤 Cadastrar-se no sistema;
2. 🔐 Fazer login;
3. 🏠 Acessar a página inicial;
4. 📂 Visualizar playlists;
5. ➕ Criar novas playlists;
6. ✏️ Personalizar playlists;
7. 🎵 Adicionar e organizar músicas;
8. ▶️ Reproduzir faixas;
9. 📜 Consultar o histórico de reprodução;
10. ⚙️ Gerenciar sua conta.

---

# ⚙️ Funcionalidades

<table>
<tr>
<td>👤 <strong>Usuários</strong></td>
<td>Cadastro, login e gerenciamento da conta.</td>
</tr>

<tr>
<td>🔐 <strong>Autenticação</strong></td>
<td>Validação das credenciais e controle de acesso.</td>
</tr>

<tr>
<td>🎵 <strong>Player</strong></td>
<td>Reprodução, pausa, próxima e música anterior.</td>
</tr>

<tr>
<td>📂 <strong>Playlists</strong></td>
<td>Criação, edição e organização de playlists.</td>
</tr>

<tr>
<td>📝 <strong>Descrição</strong></td>
<td>Possibilidade de adicionar descrições às playlists.</td>
</tr>

<tr>
<td>🏷️ <strong>Categorias</strong></td>
<td>Organização das playlists por categorias.</td>
</tr>

<tr>
<td>🖼️ <strong>Capas</strong></td>
<td>Personalização das playlists com imagens.</td>
</tr>

<tr>
<td>📥 <strong>Importação</strong></td>
<td>Possibilidade de adicionar músicas à aplicação.</td>
</tr>

<tr>
<td>📜 <strong>Histórico</strong></td>
<td>Registro das músicas e playlists reproduzidas.</td>
</tr>

<tr>
<td>💾 <strong>Persistência</strong></td>
<td>Armazenamento local das informações.</td>
</tr>

<tr>
<td>🎚️ <strong>Equalizador</strong></td>
<td>Possibilidade de personalização dos controles de áudio.</td>
</tr>

<tr>
<td>🌙 <strong>Personalização</strong></td>
<td>Possibilidade de personalização da interface e modo escuro.</td>
</tr>

</table>

---

## 👤 Gerenciamento de Usuário

O Echo Music possui funcionalidades relacionadas ao gerenciamento da conta do usuário.

Entre elas estão:

- 📝 Cadastro;
- 🔐 Login;
- ✅ Validação das credenciais;
- 🔑 Recuperação de senha;
- ❓ Pergunta de segurança;
- 🚪 Encerramento de sessão;
- 👤 Gerenciamento das informações da conta.

O sistema deve impedir o acesso às funcionalidades internas enquanto o login não for validado corretamente.

---

# 🎧 Player de Música

O player é responsável pelo controle da reprodução das músicas.

### Controles previstos:

| Controle | Função |
|:---:|---|
| ▶️ | Reproduzir |
| ⏸️ | Pausar |
| ⏭️ | Próxima música |
| ⏮️ | Música anterior |
| 🎚️ | Controle de áudio |
| ⌨️ | Atalhos do teclado |

O sistema também prevê suporte às teclas de mídia do teclado e integração com o painel de controle de mídia do Windows.

---

# 📂 Playlists

O Echo Music permite que o usuário organize suas músicas por meio de **playlists personalizadas**.

Cada playlist pode possuir:

- 📝 Nome;
- 📄 Descrição;
- 🏷️ Categoria;
- 🖼️ Imagem de capa;
- 🎵 Músicas associadas.

### Exemplo de organização:

```text
📂 Minhas Playlists
│
├── 🎵 Estudos
│   ├── Música 01
│   ├── Música 02
│   └── Música 03
│
├── 🎵 Treino
│   ├── Música 04
│   ├── Música 05
│   └── Música 06
│
└── 🎵 Favoritas
    ├── Música 07
    ├── Música 08
    └── Música 09
