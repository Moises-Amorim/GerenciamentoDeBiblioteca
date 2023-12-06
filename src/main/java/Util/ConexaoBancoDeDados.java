package Util;

import java.sql.*;

public class ConexaoBancoDeDados {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/gerenciamentodebiblioteca";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro na conexão com o banco de dados.");
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar o banco de dados.");
        }
    }

    public static void testarConexao() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoBancoDeDados.getConnection();
            stmt = connection.prepareStatement("SELECT 1");
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Conexão bem sucedida");
            } else {
                System.out.println("Conexão falhou");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar conectar ao banco de dados.");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
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
}
