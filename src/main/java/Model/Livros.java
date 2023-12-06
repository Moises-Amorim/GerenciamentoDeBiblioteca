package Model;


import Util.ConexaoBancoDeDados;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Livros {
    //Atributos
    private int codigoLivro;
    private String titulo;
    private String autor;
    private String genero;
    private String editora;
    private int quantidadeCopias;

    //Getters
    public int getCodigoLivro() {
        return codigoLivro;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public String getGenero(){return genero;}
    public String getEditora() {
        return editora;
    }
    public int getQuantidadeCopias() {
        return quantidadeCopias;
    }

    //Setters
    public void setCodigoLivro(int codigoLivro){this.codigoLivro = codigoLivro;}
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setGenero(String genero){this.genero = genero;}
    public void setEditora(String editora) {
        this.editora = editora;
    }
    public void setQuantidadeCopias(int quantidadeCopias) {
        this.quantidadeCopias = quantidadeCopias;
    }

    public void salvarlivro() {
        // Define a consulta SQL para inserir um livro na tabela
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            connection.setAutoCommit(false);

            // Insere os dados específicos do livro na tabela livro
            String inserirLivroSQL = "INSERT INTO livro (cod_livro, titulo, nome_autor, genero, nome_editora, qtde_copia) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(inserirLivroSQL);
            stmt.setInt(1,getCodigoLivro());
            stmt.setString(2, getTitulo());
            stmt.setString(3, getAutor());
            stmt.setString(4, getGenero());
            stmt.setString(5, getEditora());
            stmt.setInt(6, getQuantidadeCopias());
            stmt.executeUpdate();

            // Efetiva a transação, confirmando todas as operações
            connection.commit();

            System.out.println("Livro cadastrado com sucesso no banco de dados.");
        } catch (SQLException e) {
            // Em caso de erro, faz rolback da transação
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.err.println("Erro ao cadastrar o livro no banco de dados.");
            e.printStackTrace();
        } finally {
            // Restaura o autocommit padrão e fecha as conexões
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

    public static void carregarLivrosBanco(List<Livros> booksList) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String carregarLivroSQL = "SELECT cod_livro, titulo, nome_autor, genero, nome_editora, qtde_copia FROM livro";
            stmt = connection.prepareStatement(carregarLivroSQL);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Livros livros = new Livros();
                livros.setCodigoLivro(resultSet.getInt("cod_livro"));
                livros.setTitulo(resultSet.getString("titulo"));
                livros.setAutor(resultSet.getString("nome_autor"));
                livros.setGenero(resultSet.getString("genero"));
                livros.setEditora(resultSet.getString("nome_editora"));
                livros.setQuantidadeCopias(resultSet.getInt("qtde_copia"));
                booksList.add(livros);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar os livros do banco de dados;");
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

    public static List<Livros> obterTodosOsLivros() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet;
        List<Livros> livros = new ArrayList<>();

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            // Define a consulta para selecionar todos os livros
            String sql = "SELECT * FROM livro";
            stmt = connection.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            // Itera sobre os resultados e cria objetos livros
            while (resultSet.next()) {
                Livros livro = new Livros();
                livro.setCodigoLivro(resultSet.getInt("cod_livro"));
                livro.setTitulo(resultSet.getString("titulo"));
                livro.setAutor(resultSet.getString("nome_autor"));
                livro.setGenero(resultSet.getString("genero"));
                livro.setEditora(resultSet.getString("nome_editora"));
                livro.setQuantidadeCopias(resultSet.getInt("qtde_copia"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter os livros do banco de dados.");
            e.printStackTrace();
        } finally {
            // Restaura o autocommit padrão e fecha as conexões
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return livros;
    }

    public static Livros buscarLivro(int codigoLivro) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet;
        List<Livros> livros = new ArrayList<>();

        Livros livro = null;
        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String buscarLivroSQL = "SELECT * FROM livro WHERE cod_livro = ?";
            stmt = connection.prepareStatement(buscarLivroSQL);

            stmt.setInt(1, codigoLivro);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                livro = new Livros();
                livro.setCodigoLivro(resultSet.getInt("cod_livro"));
                livro.setTitulo(resultSet.getString("titulo"));
                livro.setAutor(resultSet.getString("nome_autor"));
                livro.setGenero(resultSet.getString("genero"));
                livro.setEditora(resultSet.getString("nome_editora"));
                livro.setQuantidadeCopias(resultSet.getInt("qtde_copia"));
                livros.add(livro);
            }

            /*if (livroEncontrado > 0) {

            } else {
                System.out.println("Nenhum livro encontrado com o código informado");
            }*/
        } catch (SQLException e) {
            System.err.println("Erro ao apagar o livro no banco de dados.");
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
        return livro;
    }

    public void atualizarLivro() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);


            String atualizarLivroSQL = "UPDATE livro SET titulo = ?, nome_autor = ?, genero = ?, nome_editora = ?, qtde_copia = ? WHERE cod_livro = ? ";
            stmt = connection.prepareStatement(atualizarLivroSQL);

            stmt.setString(1, getTitulo());
            stmt.setString(2, getAutor());
            stmt.setString(3, getGenero());
            stmt.setString(4, getEditora());
            stmt.setInt(5, getQuantidadeCopias());
            stmt.setInt(6, getCodigoLivro());

            stmt.executeUpdate();

            System.out.println("Livro atualizado com sucesso no banco de dados.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o livro no banco de dados.");
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    public void apagarLivro(int codigoLivro) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String apagarLivroSQL = "DELETE FROM livro WHERE cod_livro = ?";
            stmt = connection.prepareStatement(apagarLivroSQL);

            stmt.setInt(1, codigoLivro);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro apagado com sucesso");
            } else {
                System.out.println("Nenhum livro encontrado com o código informado");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar o livro no banco de dados.");
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    public void decrementarQuantidadeCopias() {
        setQuantidadeCopias(getQuantidadeCopias() - 1);
        atualizarLivro();
    }

    public void icrementarQuantidadeCopias() {
        setQuantidadeCopias(getQuantidadeCopias() + 1);
        atualizarLivro();
    }

}
