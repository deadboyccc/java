package advanced;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SimpleIntroJDBCDemo {
    // Connection string for DriverManager (legacy approach)
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {
        // Prompt for DB username
        String username = JOptionPane.showInputDialog(null, "Enter DB Username:");

        // Prompt for DB password securely
        JPasswordField pf = new JPasswordField();
        pf.requestFocusInWindow(); // Request focus on the password field
        int okCxl = JOptionPane.showConfirmDialog(
            null, pf, "Enter DB Password:",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        final char[] password = (okCxl == JOptionPane.OK_OPTION) ? pf.getPassword() : null;

        // Modern approach: Using DataSource
        MysqlDataSource dataSource = new MysqlDataSource();
        // dataSource.setURL(CONN_STRING); // Alternative: use full URL
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("sys");

        // Legacy approach: DriverManager (commented for reference)
        // try (Connection connection = DriverManager.getConnection(CONN_STRING,
        // username, String.valueOf(password))) {

        try (Connection connection = dataSource.getConnection(username, String.valueOf(password))) {
            System.out.println("Success! Connected to the DB");
            Arrays.fill(password, ' '); // Clear password from memory
            var metaData = connection.getMetaData();
            System.out.println(metaData.getDatabaseProductName());
        } catch (SQLException e) {
            // Optionally log SQLState and ErrorCode for debugging
            System.err.printf("SQLState: %s, ErrorCode: %d%n", e.getSQLState(), e.getErrorCode());
            throw new RuntimeException(e);
        }
    }
}
