-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 06-Dez-2023 às 23:22
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `gerenciamentodebiblioteca`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `emprestimo_livro`
--

CREATE TABLE `emprestimo_livro` (
  `cod_livro` int(11) NOT NULL,
  `numero_cartao` int(11) NOT NULL,
  `data_emprestimo` char(10) NOT NULL,
  `data_devolucao` char(10) DEFAULT NULL,
  `valor_multa` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `emprestimo_livro`
--

INSERT INTO `emprestimo_livro` (`cod_livro`, `numero_cartao`, `data_emprestimo`, `data_devolucao`, `valor_multa`) VALUES
(10, 202301, '2023-12-06', '2023-01-10', 0.00),
(10, 202302, '2023-12-06', '2023-12-14', 0.00),
(20, 202302, '2023-12-06', '2024-01-07', 90.00),
(30, 202301, '2023-12-06', '2023-12-27', 7.00),
(40, 202301, '2023-12-06', '2023-12-29', 9.00);

-- --------------------------------------------------------

--
-- Estrutura da tabela `livro`
--

CREATE TABLE `livro` (
  `cod_livro` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `nome_autor` varchar(500) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `nome_editora` varchar(50) NOT NULL,
  `qtde_copia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `livro`
--

INSERT INTO `livro` (`cod_livro`, `titulo`, `nome_autor`, `genero`, `nome_editora`, `qtde_copia`) VALUES
(10, 'HARRY POTTER E A PEDRA FILOSOFAL', 'J K ROWLING', 'FANTASIA/LITERATURA JUVENIL', 'BLOMSBURRY ', 89),
(20, 'HARRY POTTER E A CAMARA SECRETA', 'J K ROWLING', 'FANTASIA/LITERATURA JUVENIL', 'BLOMSBURRY', 86),
(30, 'HARRY POTTER E O PRISIONEIRO DE AZKABAN', 'JK ROWLING', 'FANTASIA/LITERATURA JUVENIL', 'BLOMSBURRY', 58),
(40, 'HARRY POTTER E O CALICE DE FOGO', 'J K ROWLING', 'FANTASIA', 'BLOMSBURRY', 40);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `numero_cartao` int(11) NOT NULL,
  `nome_usuario` varchar(100) NOT NULL,
  `telefone` char(11) NOT NULL,
  `email` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`numero_cartao`, `nome_usuario`, `telefone`, `email`) VALUES
(202301, 'MOISES AMORIM', '21999999999', 'moises@email.com'),
(202302, 'LARISSI', '21777777777', 'larissi@email.com');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `emprestimo_livro`
--
ALTER TABLE `emprestimo_livro`
  ADD PRIMARY KEY (`cod_livro`,`numero_cartao`),
  ADD KEY `numero_cartao` (`numero_cartao`);

--
-- Índices para tabela `livro`
--
ALTER TABLE `livro`
  ADD PRIMARY KEY (`cod_livro`);

--
-- Índices para tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`numero_cartao`);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `emprestimo_livro`
--
ALTER TABLE `emprestimo_livro`
  ADD CONSTRAINT `emprestimo_livro_ibfk_1` FOREIGN KEY (`cod_livro`) REFERENCES `livro` (`cod_livro`),
  ADD CONSTRAINT `emprestimo_livro_ibfk_2` FOREIGN KEY (`numero_cartao`) REFERENCES `usuario` (`numero_cartao`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
