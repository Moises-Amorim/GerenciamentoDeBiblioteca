package Util;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
