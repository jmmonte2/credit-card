

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    public static Connection conn;
    private static final String CONNECTION_URL = "jdbc:sqlite:card.s3db";



    public static void connect() {
        try {
            conn = DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn == null) {
                System.out.println("Not connected to a database");
            }
        }

    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS creditCard (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " number VARCHAR(16),\n"
                + " pin VARCHAR(4),\n"
                + " balance INTEGER DEFAULT O\n"
                + ");";

        try (Statement stmt = Database.conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(long number, int pin) {
        String sql = "INSERT INTO creditCard(number,pin) VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, number);
            stmt.setInt(2, pin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
