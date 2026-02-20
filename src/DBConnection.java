import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7817309";
    private static final String USER = "sql7817309";
    private static final String PASSWORD = "z1kDSsF4iL";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}