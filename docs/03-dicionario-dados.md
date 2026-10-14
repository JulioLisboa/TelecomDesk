# 3. Dicionario de dados
Descreve os campos relevantes do sistema, seus significados, os valores permitidos e os valores padrão definidos.


| Tabela | Campo | Descrição | Valores permitidos | Default |
|---|---|---|---|---|
| Usuario | `statusUsu` | Define a situação cadastral do usuário. | `ATIVO`, `INATIVO`| `ATIVO` |
| Usuario | `perfilUsu` | Define o perfil de acesso do usuário. | `TECNICO`, `COLABORADOR` | `COLABORADOR` |
| Emprestimo | `statusEmp` | Define a situação atual do Emprestimo. | `AGUARDANDO DEVOLUCAO`,`AGUARDANDO ENTREGA`, `CANCELADO`, `EMPRESTADO`, `DEVOLVIDO`| `AGUARDANDO ENTREGA` |
| Chip | `statusChip` | Define a situação de disponibilidade do CHIP. | `DISPONIVEL`, `EMPRESTADO`, `INDISPONIVEL` | `DISPONIVEL` |
| Aparelho | `statusApa` | Define a situação de disponibilidade do Aparelho. | `DISPONIVEL`, `EMPRESTADO`, `INDISPONIVEL` | `DISPONIVEL` |