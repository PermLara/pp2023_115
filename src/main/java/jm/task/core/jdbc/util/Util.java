package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    final static String hostName = "localhost";
    final static String dbName = "pp2023_114";
    final static String userName = "root";
    final static String password = "1234";
    final static String url = "jdbc:mysql://" + hostName + ":3306/" + dbName;

    public static Connection getMySQLConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
