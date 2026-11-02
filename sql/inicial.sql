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

-- senha = 123456 (MD5 = e10adc3949ba59abbe56e057f20f883e)
INSERT INTO Usuario (idUsu, nomeUsu, emailUsu, telefoneUsu, tokenUsu, senhaUsu, statusUsu, perfilUsu, idSet) VALUES
('2a3b4c5d-6e7f-4890-a123-b45678901234', 'Administrador', 'admin@gmail.com', '61988888881', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'ATIVO', 'TECNICO', 'SET002'),
('3b4c5d6e-7f80-4901-b234-c56789012345', 'Mariana Souza', 'mariana.souza@gmail.com', '61988888882', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'ATIVO', 'COLABORADOR', 'SET001'),
('4c5d6e7f-8091-4012-c345-d67890123456', 'Pedro Santos', 'pedro.santos@gmail.com', '61988888883', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'ATIVO', 'COLABORADOR', 'SET001'),
('5d6e7f80-9123-4123-d456-e78901234567', 'Ana Oliveira', 'ana.oliveira@gmail.com', '61988888884', NULL, 'e10adc3949ba59abbe56e057f20f883e', 'ATIVO', 'COLABORADOR', 'SET001');
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

INSERT INTO Aparelho (idApa, imeiApa, statusApa, idTip, idMod) VALUES
    ('APA001', '356938035643801', 'DISPONIVEL',   'TIP001', 'MOD001'),
    ('APA002', '356938035643802', 'DISPONIVEL',   'TIP001', 'MOD001'),
    ('APA003', '356938035643803', 'DISPONIVEL',   'TIP001', 'MOD004'),
    ('APA004', '356938035643804', 'EMPRESTADO',   'TIP001', 'MOD006'),
    ('APA005', '356938035643805', 'DISPONIVEL',   'TIP001', 'MOD008'),
    ('APA006', '356938035643806', 'EMPRESTADO',   'TIP001', 'MOD002'),
    ('APA007', '356938035643807', 'EMPRESTADO',   'TIP001', 'MOD005'),
    ('APA008', '356938035643808', 'DISPONIVEL',   'TIP001', 'MOD007'),
    ('APA009', '356938035643809', 'DISPONIVEL',   'TIP001', 'MOD006'),
    ('APA010', '356938035643810', 'DISPONIVEL',   'TIP001', 'MOD004'),
    ('APA011', '356938035643811', 'INDISPONIVEL', 'TIP001', 'MOD008'),
    ('APA012', '356938035643812', 'DISPONIVEL',   'TIP002', 'MOD003');

INSERT INTO Chip (idChip, iccidChip, numeroChip, statusChip, idOpe) VALUES
    ('CHP001', '89551012345678900011', '(61) 98200-0001', 'DISPONIVEL',   'OPE001'),
    ('CHP002', '89551012345678900012', '(61) 98200-0002', 'DISPONIVEL',   'OPE001'),
    ('CHP003', '89551012345678900013', '(61) 98200-0003', 'DISPONIVEL',   'OPE001'),
    ('CHP004', '89550512345678900014', '(61) 98200-0004', 'EMPRESTADO',   'OPE002'),
    ('CHP005', '89550512345678900015', '(61) 98200-0005', 'EMPRESTADO',   'OPE002'),
    ('CHP006', '89550512345678900016', '(61) 98200-0006', 'DISPONIVEL',   'OPE002'),
    ('CHP007', '89550212345678900017', '(61) 98200-0007', 'EMPRESTADO',   'OPE003'),
    ('CHP008', '89550212345678900018', '(61) 98200-0008', 'DISPONIVEL',   'OPE003'),
    ('CHP009', '89550212345678900019', '(61) 98200-0009', 'DISPONIVEL',   'OPE003'),
    ('CHP010', '89551012345678900020', '(61) 98200-0010', 'DISPONIVEL',   'OPE001'),
    ('CHP011', '89550512345678900021', '(61) 98200-0011', 'DISPONIVEL',   'OPE002'),
    ('CHP012', '89550212345678900022', '(61) 98200-0012', 'INDISPONIVEL', 'OPE003');


INSERT INTO Emprestimo (idEmp, statusEmp, idApa, idUsu, idChip) VALUES
    ('EMP001', 'AGUARDANDO ENTREGA', 'APA004', '3b4c5d6e-7f80-4901-b234-c56789012345', 'CHP004'),
    ('EMP002', 'AGUARDANDO ENTREGA', 'APA006', '3b4c5d6e-7f80-4901-b234-c56789012345', 'CHP005'),
    ('EMP003', 'AGUARDANDO ENTREGA', 'APA007', '3b4c5d6e-7f80-4901-b234-c56789012345', 'CHP007');
