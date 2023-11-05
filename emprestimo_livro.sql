-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 05-Nov-2023 às 01:32
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 7.3.27

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
  `valor_multa` decimal(10,2) DEFAULT NULL,
  `calculo_multa` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `emprestimo_livro`
--

INSERT INTO `emprestimo_livro` (`cod_livro`, `numero_cartao`, `data_emprestimo`, `data_devolucao`, `valor_multa`, `calculo_multa`) VALUES
(10, 202301, '2023-11-04', '2023-11-11', '0.00', NULL),
(20, 202301, '2023-11-04', '2023-11-11', '0.00', NULL),
(20, 202303, '2023-11-04', NULL, NULL, NULL),
(30, 202303, '2023-11-04', NULL, NULL, NULL);

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
