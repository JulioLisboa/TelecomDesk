# 3. Requisitos e Regras do Sistema
Decreve as funcionalidades que o sistema deve oferecer e suas regras.
## Requisitos Funcionais

- **RF-01: Gerenciar Ativos.**\
**Decrição:** O sistema deve permitir que o técnico cadastre, consulte, edite e exclua os registros de chips e aparelhos.\
**Ator:** Técnico.

- **RF-02: Registrar empréstimo.**\
**Decrição:** O sistema deve permitir que o técnico registre um empréstimo, associando um colaborador, um chip e um aparelho.\
**Ator:** Técnico.

- **RF-03: Consultar histórico de empréstimos.**\
**Decrição:** O sistema deve permitir que o colaborador consulte seu histórico de empréstimos, incluindo os chips e aparelhos associados a cada registro.\
**Ator:** Colaborador.

- **RF-04: Consultar empréstimos.**\
**Decrição:** O sistema deve permitir que o técnico consulte os emprestimos registrados.\
**Ator:** Técnico.

- **RF-05: Aceitar emprestimo.**\
**Decrição:** O sistema deve permitir que o colaborador aceite a solicitação de emprestimo enviada a ele.\
**Ator:** Colaborador.

- **RF-06: Recusar emprestimo.**\
**Decrição:** O sistema deve permitir que o colaborador recuse a solicitação de emprestimo enviada a ele.\
**Ator:** Colaborador.

- **RF-07: Solicitar devolução.**\
**Decrição:** O sistema deve permitir que o colaborador solicite uma devolução de emprestimo.\
**Ator:** Colaborador.

- **RF-08: Gerenciar Colaboradores.**\
**Decrição:** O sistema deve permitir que o técnico Cadastre, consulte e edite um colaborador.\
**Ator:** Técnico.

## Regras de Negócio
- **RN-01: Composição do Emprestimo.**\
**Decrição:** Todo empréstimo deve estar associado obrigatoriamente a um colaborador, um chip e um aparelho.\
**Requisitos relacionados:** RF-02.

- **RN-02: Disponibilidade de ativos.**\
**Decrição:** Um ativo que já esteja emprestado não pode ser disponibilizado para um novo emprestimo simultâneo.\
**Requisitos relacionados:** RF-02.

- **RN-03: Elegibilidade do colaborador.**\
**Decrição:** Somente colaboradores cadastrados e ativos podem receber empréstimos.\
**Requisitos relacionados:** RF-02.

- **RN-04: Geração de senha de um colaborador.**\
**Decrição:** Quando um novo colaborador for cadastrado a senha padrão deve ser os 6 primeiros digitos do CPF.\
**Requisitos relacionados:** RF-08.

- **RN-05: Permissao para gerenciamento de ativos.**\
**Decrição:** Somente usuarios com perfil de "tecnico" (Técnico) pode realizar operações de cadastro, edição e exclusão de ativos.\
**Requisitos relacionados:** RF-01.

- **RN-06: Realização de um empréstimo.**\
**Decrição:** o emprétimo só é realizado se o colaborador associado ao emprestimo aceitar a solicitação.\
**Requisitos relacionados:** RF-05, RF-02.