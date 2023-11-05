package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import Util.ConexaoBancoDeDados;

public class Emprestimo {
    private Livros livro;
    private Usuario usuario;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private double valorMulta;

    //Constructor
    public Emprestimo(Livros livro, Usuario usuario) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = Date.valueOf(LocalDate.now());
        this.dataDevolucao = calcularDataDevolucao();
    }

    //Getters
    public Usuario getUsuario() {
        return this.usuario;
    }

    public Livros getLivro() {
        return this.livro;
    }

    public Date getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return this.dataDevolucao;
    }

    public double getValorMulta() {
        return this.valorMulta;
    }

    //Setters
    public void setLivro(Livros livro) {
        this.livro = livro;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    private Date calcularDataDevolucao() {
        LocalDate dataDevolucaoLocalDate = LocalDate.now().plusDays(7); // Adiciona 7 dias à data atual
        return Date.valueOf(dataDevolucaoLocalDate);
    }

    public double calcularMulta() {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataDevolucaoLocalDate =  dataDevolucao.toLocalDate();

        if (dataAtual.isAfter(dataDevolucaoLocalDate)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoLocalDate, dataAtual);

            if (diasAtraso <=5 ) {
                return diasAtraso * 0.50; // Multa de 50 centavos por dias
            } else if (diasAtraso <= 10) {
                return diasAtraso * 1; // Multa de 1 real por dia
            } else {
                return diasAtraso * 5; // Multa de 5 reais por dia
            }
        } else {
            return 0; // Sem multa se o livro for devolvido no prazo
        }
    }

    public void salvarEmprestimo() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            connection.setAutoCommit(false);

            String inserirEmprestimoSQL = "INSERT INTO emprestimo_livro (cod_livro, numero_cartao, data_emprestimo) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(inserirEmprestimoSQL);
            stmt.setInt(1, livro.getCodigoLivro());
            stmt.setInt(2, usuario.getNumeroCartao());
            stmt.setDate(3, new java.sql.Date(dataEmprestimo.getTime()));
            stmt.executeUpdate();

            // Efetiva a persistencia, confirmando todas as operações
            connection.commit();

            System.out.println("Empréstimo cadastrado com sucesso no banco de dados.");
        } catch (SQLException e) {
            // Em caso de erro, faz rolback da transação
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.err.println("Erro ao cadastrar o emprestimo no banco de dados.");
            e.printStackTrace();
        } finally {
            // Restaura o autocomit padrão e fecha as conexôes
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    public void finalizarEmprestimo() {
        Connection  connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            connection.setAutoCommit(false);

            String finalizarEmprestimoSQL = "UPDATE emprestimo_livro SET data_devolucao = ?, valor_multa = ? WHERE cod_livro = ? AND numero_cartao= ?";
            stmt = connection.prepareStatement(finalizarEmprestimoSQL);
            stmt.setDate(1, new java.sql.Date(dataDevolucao.getTime()));
            stmt.setDouble(2, calcularMulta());
            stmt.setInt(3, livro.getCodigoLivro());
            stmt.setInt(4, usuario.getNumeroCartao());
            stmt.executeUpdate();

            connection.commit();

            System.out.println("Devolução cadastrado com sucesso no banco de dados.");
        } catch (SQLException e) {
            // Em caso de erro, faz rolback da transação
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.err.println("Erro ao cadastrar a devolução no banco de dados.");
            e.printStackTrace();
        } finally {
            // Restaura o autocomit padrão e fecha as conexôes
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }

    }

    public static void carregarEmprestimosBanco(List<Emprestimo> emprestimoList) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String careegarEmprestimosSQL = "SELECT cod_livro, numero_cartao, data_emprestimo, data_devolucao, valor_multa FROM emprestimo_livro";
            stmt = connection.prepareStatement(careegarEmprestimosSQL);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int codigoLivro = resultSet.getInt("cod_livro");
                int numeroCartao = resultSet.getInt("numero_cartao");

                Livros livro = Livros.buscarLivro(codigoLivro);
                Usuario usuario = Usuario.buscarUsuario(numeroCartao);

                Emprestimo emprestimo = new Emprestimo(livro, usuario);

                emprestimo.setDataEmprestimo(resultSet.getDate("data_emprestimo"));
                emprestimo.setDataDevolucao(resultSet.getDate("data_devolucao"));
                emprestimo.setValorMulta(resultSet.getDouble("valor_multa"));
                emprestimoList.add(emprestimo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao careegar os dados de empréstimos so banco de dados");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    public static List<Emprestimo> obterEmprestimosUsuarios(Usuario usuario, List<Emprestimo> emprestimoList) {
        List<Emprestimo> emprestimosUsuario = new ArrayList<>();
        for (Emprestimo e : emprestimoList) {
            if (e.getUsuario().equals(usuario)) {
                emprestimosUsuario.add(e);
            }
        }
        return emprestimosUsuario;
    }
}