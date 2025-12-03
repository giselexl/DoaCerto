-- 1. Cria o banco de dados com o nome do seu sistema
CREATE DATABASE SistemaDoacoes;

-- 2. Seleciona o banco para que os próximos comandos rodem dentro dele
USE SistemaDoacoes;

-- Tabela para a classe Doador
CREATE TABLE Doador (
    idDoador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    endereco TEXT,
    dataCadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    pontuacaoAvaliacao DECIMAL(3, 2) -- Ex: 4.50
);

-- Tabela para a classe Beneficiario
CREATE TABLE Beneficiario (
    idBeneficiario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    endereco TEXT,
    dataCadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    pontuacaoAvaliacao DECIMAL(3, 2)
);

-- Tabela para a classe Anuncio
CREATE TABLE Anuncio (
    idAnuncio INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    dataCriacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    statusAnuncio VARCHAR(50), -- Ex: 'Ativo', 'Pausado', 'Finalizado'
    localizacao VARCHAR(255),
    opcoesFrete VARCHAR(255)
);

-- Tabela unificada para a classe Doação e suas subclasses (Em Análise, Aprovada, Rejeitada)
CREATE TABLE Doacao (
    idDoacao INT PRIMARY KEY AUTO_INCREMENT,
    idBeneficiario INT NOT NULL, -- FK para Beneficiário
    dataSolicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    dataAtualizacaoStatus DATETIME,
    avaliacao TEXT,
    
    -- Coluna discriminadora para saber o estado atual (Baseada na herança)
    statusDoacao VARCHAR(50) NOT NULL, -- Valores: 'EmAnalise', 'Aprovada', 'Rejeitada'

    -- Campos específicos de 'Em Análise'
    documentacaoVerificada BOOLEAN,
    parecerInicial TEXT,

    -- Campos específicos de 'Aprovada'
    dataAprovacao DATETIME,
    comprovanteEntrega VARCHAR(255), -- Caminho do arquivo ou URL
    dataEntregaPrevista DATETIME,
    dataEntregaReal DATETIME,

    -- Campos específicos de 'Rejeitada'
    dataRejeicao DATETIME,
    motivoRejeicao TEXT,

    FOREIGN KEY (idBeneficiario) REFERENCES Beneficiario(idBeneficiario)
);

-- Tabela para a classe Itens
-- O Item é o ponto central que conecta Doador, Anúncio e Doação
CREATE TABLE Item (
    idItem INT PRIMARY KEY AUTO_INCREMENT,
    idDoador INT NOT NULL,       -- O item sempre pertence a um doador
    idAnuncio INT,               -- O item pode estar em um anúncio (Pode ser NULL inicialmente)
    idDoacao INT,                -- O item pode estar vinculado a uma doação (NULL até ser doado)
    
    nomeItem VARCHAR(150) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(50),
    estadoConservacao VARCHAR(50),
    dataCadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    anexosItem TEXT,             -- Pode ser JSON ou URL de imagens

    FOREIGN KEY (idDoador) REFERENCES Doador(idDoador),
    FOREIGN KEY (idAnuncio) REFERENCES Anuncio(idAnuncio),
    FOREIGN KEY (idDoacao) REFERENCES Doacao(idDoacao)
);

SELECT * FROM Doador;

SELECT * FROM Item;

SELECT * FROM beneficiario;




SELECT 
    d.idDoacao,
    d.dataSolicitacao,
    b.nome AS Nome_Beneficiario,
    i.nomeItem AS Item_Doado,
    doa.nome AS Nome_Doador  -- <--- O Doador aparece aqui!
FROM Doacao d
INNER JOIN Beneficiario b ON d.idBeneficiario = b.idBeneficiario
INNER JOIN Item i ON i.idDoacao = d.idDoacao
INNER JOIN Doador doa ON i.idDoador = doa.idDoador;



ALTER TABLE Doacao ADD COLUMN nota INT DEFAULT 0;

ALTER TABLE Doacao ADD COLUMN notaBeneficiario INT DEFAULT 0;
ALTER TABLE Doacao ADD COLUMN avaliacaoBeneficiario TEXT;