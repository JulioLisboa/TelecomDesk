-- =====================================================================
--  TelecomDesk - Script de criação do banco de dados
--  Disciplina: Programação Orientada a Objetos
--  SGBD: MySQL 8.0.16 ou superior (necessário para as restrições CHECK)
--
--  Conteúdo:
--    1. Criação do banco
--    2. Tabelas auxiliares (Setor, Marca, Tipo_Aparelho, Operadora)
--    3. Tabelas de cadastro (Modelo, Usuario, Aparelho, Chip)
--    4. Tabela da rotina principal (Emprestimo)
--    5. Dados de demonstração
--    6. View com JOIN (empréstimo detalhado)
--    7. Consultas do relatório e de apoio às regras de negócio
-- =====================================================================


-- =====================================================================
-- 1. CRIAÇÃO DO BANCO
-- ATENÇÃO: o DROP apaga o banco inteiro. Use apenas em ambiente de testes.
-- =====================================================================
DROP DATABASE IF EXISTS telecomdesk;

CREATE DATABASE telecomdesk
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE telecomdesk;


-- =====================================================================
-- 2. TABELAS AUXILIARES (sem chave estrangeira)
-- deletado_em: exclusão lógica. NULL = registro ativo;
-- preenchido = data/hora em que o registro foi excluído.
-- =====================================================================

CREATE TABLE Setor (
    idSet        VARCHAR(10)  NOT NULL,
    nomeSet      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_setor PRIMARY KEY (idSet)
) ENGINE = InnoDB;

CREATE TABLE Marca (
    idMar        VARCHAR(10)  NOT NULL,
    nomeMar      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_marca PRIMARY KEY (idMar)
) ENGINE = InnoDB;

CREATE TABLE Tipo_Aparelho (
    idTip        VARCHAR(10)  NOT NULL,
    nomeTip      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_tipo_aparelho PRIMARY KEY (idTip)
) ENGINE = InnoDB;

CREATE TABLE Operadora (
    idOpe        VARCHAR(10)  NOT NULL,
    nomeOpe      VARCHAR(60)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_operadora PRIMARY KEY (idOpe)
) ENGINE = InnoDB;


-- =====================================================================
-- 3. TABELAS DE CADASTRO (com chave estrangeira)
-- Regra adotada em todas as FKs:
--   ON DELETE RESTRICT -> o banco impede apagar um registro que está
--                         sendo referenciado (ex.: setor com usuários).
--   ON UPDATE CASCADE  -> se a PK mudar, as FKs acompanham.
-- =====================================================================

-- Modelo: cada modelo pertence a uma marca (Marca 1 --- N Modelo)
CREATE TABLE Modelo (
    idMod        VARCHAR(10)  NOT NULL,
    nomeMod      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idMar        VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_modelo PRIMARY KEY (idMod),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (idMar)
        REFERENCES Marca (idMar)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Usuario: técnicos e colaboradores ficam na mesma tabela,
-- diferenciados pelo campo perfilUsu (Setor 1 --- N Usuario)
CREATE TABLE Usuario (
    idUsu        VARCHAR(10)  NOT NULL,
    nomeUsu      VARCHAR(60)  NOT NULL,
    emailUsu     VARCHAR(60)  NOT NULL,
    telefoneUsu  VARCHAR(15)  NULL,
    tokenUsu     VARCHAR(20)  NULL,
    senhaUsu     VARCHAR(20)  NOT NULL,
    statusUsu    VARCHAR(7)   NOT NULL DEFAULT 'ATIVO',
    perfilUsu    VARCHAR(11)  NOT NULL DEFAULT 'COLABORADOR',
    idSet        VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (idUsu),
    CONSTRAINT uq_usuario_email UNIQUE (emailUsu),
    CONSTRAINT ck_usuario_status CHECK (statusUsu IN ('ATIVO', 'INATIVO')),
    CONSTRAINT ck_usuario_perfil CHECK (perfilUsu IN ('TECNICO', 'COLABORADOR')),
    CONSTRAINT fk_usuario_setor FOREIGN KEY (idSet)
        REFERENCES Setor (idSet)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Aparelho: possui um modelo e um tipo
-- (Modelo 1 --- N Aparelho, Tipo_Aparelho 1 --- N Aparelho)
-- OBS: no modelo lógico statusApa está como VARCHAR(10), mas o valor
-- 'INDISPONIVEL' tem 12 caracteres. Foi ajustado para VARCHAR(12).
CREATE TABLE Aparelho (
    idApa        VARCHAR(10)  NOT NULL,
    imeiApa      VARCHAR(20)  NOT NULL,
    statusApa    VARCHAR(12)  NOT NULL DEFAULT 'DISPONIVEL',
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idTip        VARCHAR(10)  NOT NULL,
    idMod        VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_aparelho PRIMARY KEY (idApa),
    CONSTRAINT uq_aparelho_imei UNIQUE (imeiApa),
    CONSTRAINT ck_aparelho_status CHECK (statusApa IN ('DISPONIVEL', 'EMPRESTADO', 'INDISPONIVEL')),
    CONSTRAINT fk_aparelho_tipo FOREIGN KEY (idTip)
        REFERENCES Tipo_Aparelho (idTip)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_aparelho_modelo FOREIGN KEY (idMod)
        REFERENCES Modelo (idMod)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Chip: fornecido por uma operadora (Operadora 1 --- N Chip)
CREATE TABLE Chip (
    idChip       VARCHAR(10)  NOT NULL,
    iccidChip    VARCHAR(30)  NOT NULL,
    numeroChip   VARCHAR(15)  NOT NULL,
    statusChip   VARCHAR(12)  NOT NULL DEFAULT 'DISPONIVEL',
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idOpe        VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_chip PRIMARY KEY (idChip),
    CONSTRAINT uq_chip_iccid UNIQUE (iccidChip),
    CONSTRAINT uq_chip_numero UNIQUE (numeroChip),
    CONSTRAINT ck_chip_status CHECK (statusChip IN ('DISPONIVEL', 'EMPRESTADO', 'INDISPONIVEL')),
    CONSTRAINT fk_chip_operadora FOREIGN KEY (idOpe)
        REFERENCES Operadora (idOpe)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;


-- =====================================================================
-- 4. ROTINA PRINCIPAL: EMPRÉSTIMO
-- RN-01: todo empréstimo tem obrigatoriamente colaborador, aparelho e
--        chip -> as três FKs são NOT NULL.
-- Fluxo do statusEmp:
--   AGUARDANDO ENTREGA --(aceite)--> EMPRESTADO
--   EMPRESTADO --(colaborador solicita)--> AGUARDANDO DEVOLUCAO
--   AGUARDANDO DEVOLUCAO --(técnico recebe)--> DEVOLVIDO
--   AGUARDANDO ENTREGA --(recusa ou cancelamento)--> CANCELADO
-- =====================================================================
CREATE TABLE Emprestimo (
    idEmp             VARCHAR(10)  NOT NULL,
    dataEmp           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dataDevolucaoEmp  DATETIME     NULL DEFAULT NULL,
    statusEmp         VARCHAR(20)  NOT NULL DEFAULT 'AGUARDANDO ENTREGA',
    idApa             VARCHAR(10)  NOT NULL,
    idUsu             VARCHAR(10)  NOT NULL,
    idChip            VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_emprestimo PRIMARY KEY (idEmp),
    CONSTRAINT ck_emprestimo_status CHECK (statusEmp IN (
        'AGUARDANDO ENTREGA', 'EMPRESTADO', 'AGUARDANDO DEVOLUCAO',
        'DEVOLVIDO', 'CANCELADO')),
    -- a devolução nunca pode ser anterior ao empréstimo
    CONSTRAINT ck_emprestimo_datas CHECK (
        dataDevolucaoEmp IS NULL OR dataDevolucaoEmp >= dataEmp),
    CONSTRAINT fk_emprestimo_aparelho FOREIGN KEY (idApa)
        REFERENCES Aparelho (idApa)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_emprestimo_usuario FOREIGN KEY (idUsu)
        REFERENCES Usuario (idUsu)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_emprestimo_chip FOREIGN KEY (idChip)
        REFERENCES Chip (idChip)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Índices para acelerar os filtros do relatório (período e situação)
CREATE INDEX idx_emprestimo_data   ON Emprestimo (dataEmp);
CREATE INDEX idx_emprestimo_status ON Emprestimo (statusEmp);


-- =====================================================================
-- 5. DADOS DE DEMONSTRAÇÃO
-- Convenção adotada para o status dos ativos:
--   - empréstimo EMPRESTADO ou AGUARDANDO DEVOLUCAO -> ativo EMPRESTADO
--   - empréstimo AGUARDANDO ENTREGA -> ativo continua DISPONIVEL; o
--     Controller deve bloquear nova solicitação (ver consulta da RN-02)
--   - empréstimo DEVOLVIDO ou CANCELADO -> ativo volta a DISPONIVEL
-- =====================================================================

INSERT INTO Setor (idSet, nomeSet) VALUES
    ('SET001', 'Tecnologia da Informação'),
    ('SET002', 'Recursos Humanos'),
    ('SET003', 'Financeiro'),
    ('SET004', 'Comercial'),
    ('SET005', 'Logística');

INSERT INTO Marca (idMar, nomeMar, deletado_em) VALUES
    ('MAR001', 'Samsung',  NULL),
    ('MAR002', 'Apple',    NULL),
    ('MAR003', 'Motorola', NULL),
    ('MAR004', 'Xiaomi',   NULL),
    ('MAR005', 'Nokia',    '2026-08-01 10:00:00');  -- exemplo de exclusão lógica

INSERT INTO Tipo_Aparelho (idTip, nomeTip) VALUES
    ('TIP001', 'Smartphone'),
    ('TIP002', 'Tablet'),
    ('TIP003', 'Roteador 4G');

INSERT INTO Operadora (idOpe, nomeOpe) VALUES
    ('OPE001', 'Vivo'),
    ('OPE002', 'Claro'),
    ('OPE003', 'TIM');

INSERT INTO Modelo (idMod, nomeMod, idMar) VALUES
    ('MOD001', 'Galaxy A55',     'MAR001'),
    ('MOD002', 'Galaxy S24',     'MAR001'),
    ('MOD003', 'Galaxy Tab A9',  'MAR001'),
    ('MOD004', 'iPhone 15',      'MAR002'),
    ('MOD005', 'iPhone 13',      'MAR002'),
    ('MOD006', 'Moto G84',       'MAR003'),
    ('MOD007', 'Edge 50',        'MAR003'),
    ('MOD008', 'Redmi Note 13',  'MAR004');

-- Senha padrão (RN-04): 6 primeiros dígitos do CPF do colaborador.
-- Os valores abaixo são fictícios.
INSERT INTO Usuario (idUsu, nomeUsu, emailUsu, telefoneUsu, tokenUsu, senhaUsu, statusUsu, perfilUsu, idSet) VALUES
    ('USU001', 'Administrador',  'admin@gmail.com',    '(61) 99101-0001', NULL, '123456', 'ATIVO',   'TECNICO',     'SET001'),
    ('USU002', 'Bruno Carvalho',     'bruno.carvalho@empresa.com.br', '(61) 99101-0002', NULL, '234567', 'ATIVO',   'TECNICO',     'SET001'),
    ('USU003', 'Carla Mendes',       'carla.mendes@empresa.com.br',   '(61) 99101-0003', NULL, '345678', 'ATIVO',   'COLABORADOR', 'SET002'),
    ('USU004', 'Diego Fernandes',    'diego.fernandes@empresa.com.br','(61) 99101-0004', NULL, '456789', 'ATIVO',   'COLABORADOR', 'SET003'),
    ('USU005', 'Eduarda Lima',       'eduarda.lima@empresa.com.br',   '(61) 99101-0005', NULL, '567890', 'ATIVO',   'COLABORADOR', 'SET004'),
    ('USU006', 'Felipe Rocha',       'felipe.rocha@empresa.com.br',   '(61) 99101-0006', NULL, '678901', 'ATIVO',   'COLABORADOR', 'SET004'),
    ('USU007', 'Gabriela Souza',     'gabriela.souza@empresa.com.br', '(61) 99101-0007', NULL, '789012', 'ATIVO',   'COLABORADOR', 'SET005'),
    ('USU008', 'Henrique Alves',     'henrique.alves@empresa.com.br', '(61) 99101-0008', NULL, '890123', 'ATIVO',   'COLABORADOR', 'SET003'),
    ('USU009', 'Isabela Martins',    'isabela.martins@empresa.com.br','(61) 99101-0009', NULL, '901234', 'ATIVO',   'COLABORADOR', 'SET002'),
    ('USU010', 'João Pedro Costa',   'joao.costa@empresa.com.br',     '(61) 99101-0010', NULL, '112233', 'INATIVO', 'COLABORADOR', 'SET005');

INSERT INTO Aparelho (idApa, imeiApa, statusApa, idTip, idMod) VALUES
    ('APA001', '356938035643801', 'EMPRESTADO',   'TIP001', 'MOD001'),
    ('APA002', '356938035643802', 'EMPRESTADO',   'TIP001', 'MOD001'),
    ('APA003', '356938035643803', 'DISPONIVEL',   'TIP001', 'MOD004'),
    ('APA004', '356938035643804', 'EMPRESTADO',   'TIP001', 'MOD006'),
    ('APA005', '356938035643805', 'DISPONIVEL',   'TIP001', 'MOD008'),
    ('APA006', '356938035643806', 'EMPRESTADO',   'TIP001', 'MOD002'),
    ('APA007', '356938035643807', 'EMPRESTADO',   'TIP001', 'MOD005'),
    ('APA008', '356938035643808', 'DISPONIVEL',   'TIP001', 'MOD007'),
    ('APA009', '356938035643809', 'DISPONIVEL',   'TIP001', 'MOD006'),
    ('APA010', '356938035643810', 'DISPONIVEL',   'TIP001', 'MOD004'),
    ('APA011', '356938035643811', 'INDISPONIVEL', 'TIP001', 'MOD008'),  -- em manutenção
    ('APA012', '356938035643812', 'DISPONIVEL',   'TIP002', 'MOD003');

INSERT INTO Chip (idChip, iccidChip, numeroChip, statusChip, idOpe) VALUES
    ('CHP001', '89551012345678900011', '(61) 98200-0001', 'EMPRESTADO',   'OPE001'),
    ('CHP002', '89551012345678900012', '(61) 98200-0002', 'EMPRESTADO',   'OPE001'),
    ('CHP003', '89551012345678900013', '(61) 98200-0003', 'DISPONIVEL',   'OPE001'),
    ('CHP004', '89550512345678900014', '(61) 98200-0004', 'EMPRESTADO',   'OPE002'),
    ('CHP005', '89550512345678900015', '(61) 98200-0005', 'EMPRESTADO',   'OPE002'),
    ('CHP006', '89550512345678900016', '(61) 98200-0006', 'DISPONIVEL',   'OPE002'),
    ('CHP007', '89550212345678900017', '(61) 98200-0007', 'EMPRESTADO',   'OPE003'),
    ('CHP008', '89550212345678900018', '(61) 98200-0008', 'DISPONIVEL',   'OPE003'),
    ('CHP009', '89550212345678900019', '(61) 98200-0009', 'DISPONIVEL',   'OPE003'),
    ('CHP010', '89551012345678900020', '(61) 98200-0010', 'DISPONIVEL',   'OPE001'),
    ('CHP011', '89550512345678900021', '(61) 98200-0011', 'DISPONIVEL',   'OPE002'),
    ('CHP012', '89550212345678900022', '(61) 98200-0012', 'INDISPONIVEL', 'OPE003');  -- linha bloqueada

INSERT INTO Emprestimo (idEmp, dataEmp, dataDevolucaoEmp, statusEmp, idApa, idUsu, idChip) VALUES
    ('EMP001', '2026-02-10 09:00:00', '2026-05-15 17:00:00', 'DEVOLVIDO',            'APA001', 'USU010', 'CHP001'),
    ('EMP002', '2026-03-05 10:30:00', '2026-06-20 16:30:00', 'DEVOLVIDO',            'APA002', 'USU003', 'CHP002'),
    ('EMP003', '2026-04-12 14:00:00', NULL,                  'CANCELADO',            'APA003', 'USU004', 'CHP003'),
    ('EMP004', '2026-05-20 08:45:00', NULL,                  'EMPRESTADO',           'APA001', 'USU005', 'CHP004'),
    ('EMP005', '2026-06-25 11:15:00', NULL,                  'EMPRESTADO',           'APA002', 'USU009', 'CHP001'),
    ('EMP006', '2026-07-08 13:20:00', NULL,                  'AGUARDANDO DEVOLUCAO', 'APA004', 'USU006', 'CHP005'),
    ('EMP007', '2026-08-14 09:40:00', '2026-09-30 15:10:00', 'DEVOLVIDO',            'APA005', 'USU007', 'CHP006'),
    ('EMP008', '2026-09-02 15:00:00', NULL,                  'EMPRESTADO',           'APA006', 'USU008', 'CHP007'),
    ('EMP009', '2026-09-18 10:00:00', NULL,                  'CANCELADO',            'APA007', 'USU003', 'CHP008'),
    ('EMP010', '2026-10-05 16:30:00', NULL,                  'EMPRESTADO',           'APA007', 'USU004', 'CHP002'),
    ('EMP011', '2026-10-20 09:10:00', NULL,                  'AGUARDANDO ENTREGA',   'APA008', 'USU007', 'CHP009'),
    ('EMP012', '2026-10-23 14:45:00', NULL,                  'AGUARDANDO ENTREGA',   'APA009', 'USU003', 'CHP010');


-- =====================================================================
-- 6. VIEW COM JOIN: EMPRÉSTIMO DETALHADO
-- Junta as 8 tabelas e mostra nomes no lugar dos códigos de FK.
-- No Java basta: SELECT * FROM vw_emprestimo_detalhado
-- =====================================================================
CREATE VIEW vw_emprestimo_detalhado AS
SELECT
    e.idEmp,
    e.dataEmp,
    e.dataDevolucaoEmp,
    e.statusEmp,
    u.idUsu,
    u.nomeUsu      AS colaborador,
    u.emailUsu     AS emailColaborador,
    s.nomeSet      AS setor,
    a.idApa,
    a.imeiApa      AS imei,
    t.nomeTip      AS tipoAparelho,
    ma.nomeMar     AS marca,
    mo.nomeMod     AS modelo,
    c.idChip,
    c.numeroChip   AS numeroLinha,
    c.iccidChip    AS iccid,
    o.nomeOpe      AS operadora
FROM Emprestimo e
    INNER JOIN Usuario       u  ON u.idUsu  = e.idUsu
    INNER JOIN Setor         s  ON s.idSet  = u.idSet
    INNER JOIN Aparelho      a  ON a.idApa  = e.idApa
    INNER JOIN Tipo_Aparelho t  ON t.idTip  = a.idTip
    INNER JOIN Modelo        mo ON mo.idMod = a.idMod
    INNER JOIN Marca         ma ON ma.idMar = mo.idMar
    INNER JOIN Chip          c  ON c.idChip = e.idChip
    INNER JOIN Operadora     o  ON o.idOpe  = c.idOpe;


-- =====================================================================
-- 7. CONSULTAS DE APOIO
-- Nas consultas com parâmetros, o "?" é o marcador do PreparedStatement
-- do JDBC. Os exemplos comentados usam valores fixos para teste no
-- MySQL Workbench.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 7.1 RELATÓRIO PRINCIPAL: empréstimos por período
-- Java: ps.setString(1, "2026-07-01"); ps.setString(2, "2026-10-31");
-- ---------------------------------------------------------------------
-- SELECT idEmp,
--        DATE_FORMAT(dataEmp, '%d/%m/%Y %H:%i')          AS dataEmprestimo,
--        DATE_FORMAT(dataDevolucaoEmp, '%d/%m/%Y %H:%i') AS dataDevolucao,
--        colaborador, setor, marca, modelo, numeroLinha, operadora, statusEmp
--   FROM vw_emprestimo_detalhado
--  WHERE DATE(dataEmp) BETWEEN ? AND ?
--  ORDER BY dataEmp;

SELECT idEmp,
       DATE_FORMAT(dataEmp, '%d/%m/%Y %H:%i')          AS dataEmprestimo,
       DATE_FORMAT(dataDevolucaoEmp, '%d/%m/%Y %H:%i') AS dataDevolucao,
       colaborador, setor, marca, modelo, numeroLinha, operadora, statusEmp
  FROM vw_emprestimo_detalhado
 WHERE DATE(dataEmp) BETWEEN '2026-07-01' AND '2026-10-31'
 ORDER BY dataEmp;

-- ---------------------------------------------------------------------
-- 7.2 RELATÓRIO: quantidade de empréstimos por situação
-- ---------------------------------------------------------------------
SELECT statusEmp, COUNT(*) AS quantidade
  FROM Emprestimo
 GROUP BY statusEmp
 ORDER BY quantidade DESC;

-- ---------------------------------------------------------------------
-- 7.3 RELATÓRIO: empréstimos por setor (total e em andamento)
-- ---------------------------------------------------------------------
SELECT s.nomeSet AS setor,
       COUNT(e.idEmp) AS totalEmprestimos,
       SUM(CASE WHEN e.statusEmp IN ('EMPRESTADO', 'AGUARDANDO DEVOLUCAO')
                THEN 1 ELSE 0 END) AS emAndamento
  FROM Setor s
  LEFT JOIN Usuario    u ON u.idSet = s.idSet
  LEFT JOIN Emprestimo e ON e.idUsu = u.idUsu
 WHERE s.deletado_em IS NULL
 GROUP BY s.idSet, s.nomeSet
 ORDER BY totalEmprestimos DESC;

-- ---------------------------------------------------------------------
-- 7.4 RELATÓRIO: ativos atualmente com colaboradores
-- ---------------------------------------------------------------------
SELECT colaborador, setor, marca, modelo, imei, numeroLinha, operadora,
       DATE_FORMAT(dataEmp, '%d/%m/%Y') AS desde, statusEmp
  FROM vw_emprestimo_detalhado
 WHERE statusEmp IN ('EMPRESTADO', 'AGUARDANDO DEVOLUCAO')
 ORDER BY dataEmp;

-- ---------------------------------------------------------------------
-- 7.5 RF-03: histórico de empréstimos de um colaborador
-- Java: ps.setString(1, usuarioLogado.getIdUsu());
-- ---------------------------------------------------------------------
-- SELECT * FROM vw_emprestimo_detalhado WHERE idUsu = ? ORDER BY dataEmp DESC;
SELECT * FROM vw_emprestimo_detalhado WHERE idUsu = 'USU003' ORDER BY dataEmp DESC;

-- ---------------------------------------------------------------------
-- 7.6 RN-02: verificar se aparelho ou chip já está em empréstimo aberto
-- Se o resultado for maior que 0, o Controller bloqueia a solicitação.
-- Java: ps.setString(1, idApa); ps.setString(2, idChip);
-- ---------------------------------------------------------------------
-- SELECT COUNT(*) AS emAberto
--   FROM Emprestimo
--  WHERE (idApa = ? OR idChip = ?)
--    AND statusEmp IN ('AGUARDANDO ENTREGA', 'EMPRESTADO', 'AGUARDANDO DEVOLUCAO');

-- ---------------------------------------------------------------------
-- 7.7 RN-03: listar apenas colaboradores ativos (para o combo da tela
-- de empréstimo)
-- ---------------------------------------------------------------------
SELECT idUsu, nomeUsu
  FROM Usuario
 WHERE perfilUsu = 'COLABORADOR' AND statusUsu = 'ATIVO'
 ORDER BY nomeUsu;

-- ---------------------------------------------------------------------
-- 7.8 Ativos disponíveis para empréstimo (combos da tela)
-- ---------------------------------------------------------------------
SELECT a.idApa, CONCAT(ma.nomeMar, ' ', mo.nomeMod, ' - IMEI ', a.imeiApa) AS descricao
  FROM Aparelho a
  INNER JOIN Modelo mo ON mo.idMod = a.idMod
  INNER JOIN Marca  ma ON ma.idMar = mo.idMar
 WHERE a.statusApa = 'DISPONIVEL' AND a.deletado_em IS NULL
 ORDER BY descricao;

SELECT c.idChip, CONCAT(c.numeroChip, ' - ', o.nomeOpe) AS descricao
  FROM Chip c
  INNER JOIN Operadora o ON o.idOpe = c.idOpe
 WHERE c.statusChip = 'DISPONIVEL' AND c.deletado_em IS NULL
 ORDER BY descricao;

-- ---------------------------------------------------------------------
-- 7.9 Login (apenas usuários ativos)
-- Java: ps.setString(1, email); ps.setString(2, senha);
-- ---------------------------------------------------------------------
-- SELECT idUsu, nomeUsu, perfilUsu
--   FROM Usuario
--  WHERE emailUsu = ? AND senhaUsu = ? AND statusUsu = 'ATIVO';

-- ---------------------------------------------------------------------
-- 7.10 Exclusão lógica (o "D" dos CRUDs com deletado_em)
-- Exemplo para Setor; o mesmo padrão vale para Marca, Modelo,
-- Tipo_Aparelho, Operadora, Aparelho e Chip.
-- ---------------------------------------------------------------------
-- UPDATE Setor SET deletado_em = NOW() WHERE idSet = ?;
-- Listagem do CRUD (somente registros não excluídos):
-- SELECT * FROM Setor WHERE deletado_em IS NULL ORDER BY nomeSet;
