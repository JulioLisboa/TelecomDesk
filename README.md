# TelecomDesk

Sistema corporativo para controle de inventário e alocação de dispositivos móveis (smartphones/aparelhos) e linhas telefônicas (chips SIM).

---
## Objetivo do Sistema
O sistema tem como finalidade centralizar, padronizar e automatizar a gestão do ciclo de vida dos ativos de aparelhos e cartões SIM. A plataforma permite:
- Manter o inventário de linhas e aparelhos telefonicos.
- Controlar o fluxo completo de concessão de ativos (solicitação, aceitação e devolução).
- Garantir a responsabilização individual através do consentimento formal do colaborador antes da efetivação da entrega.
- Proporcionar transparência no histórico de uso de cada equipamento e linha.

## Perfis de Usuário
O sistema opera com controle de acesso baseado em perfis, distinguindo dois níveis principais de utilização:

| Perfil | Descrição e Responsabilidades |
|---|---|
| **Técnico** | Usuário administrativo responsável pela gestão operacional. Tem permissão para cadastrar e gerenciar colaboradores, aparelhos e chips, além de iniciar solicitações de empréstimo e acompanhar o status dos ativos. |
| **Colaborador** | Usuário final vinculado a um setor. Pode consultar o seu próprio histórico de empréstimos, aceitar ou recusar solicitações de alocação de dispositivos e solicitar a devolução de ativos sob sua guarda. |
## Documentação do Projeto

Navegue pelos documentos detalhados de especificação e modelagem do sistema:


* [**01. Glossário**](docs/01-glossario.md) — Definição dos termos técnicos e papéis utilizados.
* [**02. Requisitos e Regras do Sistema**](/docs/02-requisitos-e-regras-do-sistema.md) — Requisitos Funcionais (RF) e Regras de Negócio (RN).
* [**03. Dicionário de Dados**](docs/03-dicionario-dados.md) — Descrição dos campos, restrições e valores padrão.
* **Modelagem de Dados**:
  * [Modelo Conceitual](docs/ModeloConceitual.jpg)
  * [Modelo Lógico](docs/ModeloLogico.jpg)
