package Model;

import Util.ConexaoBancoDeDados;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int numeroCartao;
    private String nomeUsuario, email, telefoneUsuario;

    //Getters
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public String getEmail(){return email;}
    public int getNumeroCartao() {
        return numeroCartao;
    }
    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    //Setters
    public void setNomeUsuario(String nomeUsuario){this.nomeUsuario = nomeUsuario;}
    public void setEmail(String email){this.email = email;}
    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public void salvarUsuario() {
        // Define a consulta SQL para inserir um usuario na tabela
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();

            connection.setAutoCommit(false);

            // Insere os dados específicos do usuario na tabela de usuarios
            String inserirUsuarioSQL = "INSERT INTO usuario (numero_cartao, nome_usuario, telefone, email) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(inserirUsuarioSQL);
            stmt.setInt(1, getNumeroCartao());
            stmt.setString(2, getNomeUsuario());
            stmt.setString(3, getTelefoneUsuario());
            stmt.setString(4, getEmail());
            stmt.executeUpdate();

            // Efetiva a transação, confirmando todas as operações
            connection.commit();

            System.out.println("Usuario cadastrado com sucesso no banco de dados.");
        } catch (SQLException e) {
            // Em caso de erro, faz rolback da transação
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.err.println("Erro ao cadastrar o usuario no banco de dados.");
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

    public static void carregarUsuariosBanco(List<Usuario> usersList) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String carregarUsuarioSQL = "SELECT numero_cartao, nome_usuario, telefone, email FROM usuario";
            stmt = connection.prepareStatement(carregarUsuarioSQL);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Usuario usuarios = new Usuario();
                usuarios.setNumeroCartao(resultSet.getInt("numero_cartao"));
                usuarios.setNomeUsuario(resultSet.getString("nome_usuario"));
                usuarios.setTelefoneUsuario(resultSet.getString("telefone"));
                usuarios.setEmail(resultSet.getString("email"));
                usersList.add(usuarios);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar os usuarios do banco de dados;");
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

    public static List<Usuario> obterTodosOsUsuarios() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet;
        List<Usuario> usuarios = new ArrayList<>();


        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            // Define a consulta para selecionar todos os Usuarios
            String sql = "SELECT * FROM usuario";
            stmt = connection.prepareStatement(sql);
            resultSet = stmt.executeQuery();

            // Itera sobre os resultados
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setNumeroCartao(resultSet.getInt("numero_cartao"));
                usuario.setNomeUsuario(resultSet.getString("nome_usuario"));
                usuario.setTelefoneUsuario(resultSet.getString("telefone"));
                usuario.setEmail(resultSet.getString("email"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter os usuarios do banco de dados.");
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
        return usuarios;
    }

    public static Usuario buscarUsuario(int numeroCartao) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet;
        List<Usuario> usuarios = new ArrayList<>();


        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String buscarUsuarioSQL = "SELECT * FROM usuario WHERE numero_cartao = ?";
            stmt = connection.prepareStatement(buscarUsuarioSQL);

            stmt.setInt(1, numeroCartao);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setNumeroCartao(resultSet.getInt("numero_cartao"));
                usuario.setNomeUsuario(resultSet.getString("nome_usuario"));
                usuario.setTelefoneUsuario(resultSet.getString("telefone"));
                usuario.setTelefoneUsuario(resultSet.getString("email"));
                usuarios.add(usuario);
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
        return null;
    }

    public void atualizarUsuario() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);


            String atualizarUsuarioSQL = "UPDATE usuario SET nome_usuario = ?, telefone = ?, email = ? WHERE numero_cartao = ?";
            stmt = connection.prepareStatement(atualizarUsuarioSQL);

            stmt = connection.prepareStatement(atualizarUsuarioSQL);
            stmt.setString(1, getNomeUsuario());
            stmt.setString(2, getTelefoneUsuario());
            stmt.setString(3, getEmail());
            stmt.setInt(4, getNumeroCartao());
            stmt.executeUpdate();

            System.out.println("Usuario atualizado com sucesso no banco de dados.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o usuario no banco de dados.");
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

    public void apagarUsuario(int numeroCartao) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            connection.setAutoCommit(false);

            String apagarUsuarioSQL = "DELETE FROM usuario WHERE numero_cartao = ?";
            stmt = connection.prepareStatement(apagarUsuarioSQL);

            stmt.setInt(1, numeroCartao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuario apagado com sucesso");
            } else {
                System.out.println("Nenhum usuario encontrado com o nome numero de cartão informado");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar o usuario no banco de dados.");
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

}

