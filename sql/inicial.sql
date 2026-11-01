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
    idSet        VARCHAR(36)  NOT NULL,
    nomeSet      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_setor PRIMARY KEY (idSet)
) ENGINE = InnoDB;

CREATE TABLE Marca (
    idMar        VARCHAR(36)  NOT NULL,
    nomeMar      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_marca PRIMARY KEY (idMar)
) ENGINE = InnoDB;

CREATE TABLE Tipo_Aparelho (
    idTip        VARCHAR(36)  NOT NULL,
    nomeTip      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    CONSTRAINT pk_tipo_aparelho PRIMARY KEY (idTip)
) ENGINE = InnoDB;

CREATE TABLE Operadora (
    idOpe        VARCHAR(36)  NOT NULL,
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
    idMod        VARCHAR(36)  NOT NULL,
    nomeMod      VARCHAR(50)  NOT NULL,
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idMar        VARCHAR(36)  NOT NULL,
    CONSTRAINT pk_modelo PRIMARY KEY (idMod),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (idMar)
        REFERENCES Marca (idMar)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- Usuario: técnicos e colaboradores ficam na mesma tabela,
-- diferenciados pelo campo perfilUsu (Setor 1 --- N Usuario)
CREATE TABLE Usuario (
    idUsu        VARCHAR(36)  NOT NULL,
    nomeUsu      VARCHAR(60)  NOT NULL,
    emailUsu     VARCHAR(60)  NOT NULL,
    telefoneUsu  VARCHAR(15)  NULL,
    tokenUsu     VARCHAR(20)  NULL,
    senhaUsu     VARCHAR(32)  NOT NULL,
    statusUsu    VARCHAR(7)   NOT NULL DEFAULT 'ATIVO',
    perfilUsu    VARCHAR(11)  NOT NULL DEFAULT 'COLABORADOR',
    idSet        VARCHAR(36)  NOT NULL,
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
    idApa        VARCHAR(36)  NOT NULL,
    imeiApa      VARCHAR(20)  NOT NULL,
    statusApa    VARCHAR(12)  NOT NULL DEFAULT 'DISPONIVEL',
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idTip        VARCHAR(36)  NOT NULL,
    idMod        VARCHAR(36)  NOT NULL,
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
    idChip       VARCHAR(36)  NOT NULL,
    iccidChip    VARCHAR(30)  NOT NULL,
    numeroChip   VARCHAR(15)  NOT NULL,
    statusChip   VARCHAR(12)  NOT NULL DEFAULT 'DISPONIVEL',
    deletado_em  DATETIME     NULL DEFAULT NULL,
    idOpe        VARCHAR(36)  NOT NULL,
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
    idEmp             VARCHAR(36)  NOT NULL,
    dataEmp           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dataDevolucaoEmp  DATETIME     NULL DEFAULT NULL,
    statusEmp         VARCHAR(20)  NOT NULL DEFAULT 'AGUARDANDO ENTREGA',
    idApa             VARCHAR(36)  NOT NULL,
    idUsu             VARCHAR(36)  NOT NULL,
    idChip            VARCHAR(36)  NOT NULL,
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



INSERT INTO Setor (idSet, nomeSet) VALUES
    ('SET001', 'Tecnologia da Informação'),
    ('SET002', 'Recursos Humanos'),
    ('SET003', 'Financeiro'),
    ('SET004', 'Comercial'),
    ('SET005', 'Logística');

// administrador: senha = 123456 (MD5 = e10adc3949ba59abbe56e057f20f883e)
INSERT INTO Usuario (
    idUsu,
    nomeUsu,
    emailUsu,
    telefoneUsu,
    tokenUsu,
    senhaUsu,
    statusUsu,
    perfilUsu,
    idSet
) VALUES (
    '1d28da6a-8ce5-49f0-8bce-1c22615c3c58',
    'Administrador',
    'admin@gmail.com',
    '61999999999',
    NULL,
    'e10adc3949ba59abbe56e057f20f883e',
    'ATIVO',
    'TECNICO',
    'SET001'
);

INSERT INTO Operadora (idOpe, nomeOpe) VALUES
    ('OPE001', 'Vivo'),
    ('OPE002', 'Claro'),
    ('OPE003', 'TIM');

INSERT INTO Marca (idMar, nomeMar, deletado_em) VALUES
    ('MAR001', 'Samsung',  NULL),
    ('MAR002', 'Apple',    NULL),
    ('MAR003', 'Motorola', NULL),
    ('MAR004', 'Xiaomi',   NULL),
    ('MAR005', 'Nokia',    '2026-08-01 10:00:00');

INSERT INTO Modelo (idMod, nomeMod, idMar) VALUES
    ('MOD001', 'Galaxy A55',     'MAR001'),
    ('MOD002', 'Galaxy S24',     'MAR001'),
    ('MOD003', 'Galaxy Tab A9',  'MAR001'),
    ('MOD004', 'iPhone 15',      'MAR002'),
    ('MOD005', 'iPhone 13',      'MAR002'),
    ('MOD006', 'Moto G84',       'MAR003'),
    ('MOD007', 'Edge 50',        'MAR003'),
    ('MOD008', 'Redmi Note 13',  'MAR004');

INSERT INTO Tipo_Aparelho (idTip, nomeTip) VALUES
    ('TIP001', 'Smartphone'),
    ('TIP002', 'Tablet'),
    ('TIP003', 'Roteador 4G');