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
            System.out.println("=== Connection Info ===");
            System.out.printf("AutoCommit: %s%n", connection.getAutoCommit());
            System.out.printf("Catalog: %s%n", connection.getCatalog());
            System.out.printf("Schema: %s%n", connection.getSchema());
            System.out.printf("Transaction Isolation: %d%n", connection.getTransactionIsolation());
            System.out.printf("Read Only: %s%n", connection.isReadOnly());
            System.out.printf("Closed: %s%n", connection.isClosed());
            System.out.printf("Type Map: %s%n", connection.getTypeMap());
            System.out.printf("Warnings: %s%n", connection.getWarnings());
            System.out.printf("Holdability: %d%n", connection.getHoldability());
            System.out.printf("Client Info: %s%n", connection.getClientInfo());
            System.out.printf("Network Timeout: %d%n", connection.getNetworkTimeout());
            System.out.printf("Current User: %s%n", metaData.getUserName());
            System.out.printf("Database Product: %s %s%n", metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion());
            System.out.printf("Driver: %s %s%n", metaData.getDriverName(), metaData.getDriverVersion());
            System.out.printf("URL: %s%n", metaData.getURL());
            System.out.printf("Max Connections: %d%n", metaData.getMaxConnections());
            System.out.printf("SQL Keywords: %s%n", metaData.getSQLKeywords());
            System.out.printf("Supports Transactions: %s%n", metaData.supportsTransactions());
            System.out.printf("Supports Batch Updates: %s%n", metaData.supportsBatchUpdates());
            System.out.printf("Supports Savepoints: %s%n", metaData.supportsSavepoints());
            System.out.printf("Default Transaction Isolation: %d%n", metaData.getDefaultTransactionIsolation());
            Thread.sleep(15000);
        } catch (SQLException e) {
            // Optionally log SQLState and ErrorCode for debugging
            System.err.printf("SQLState: %s, ErrorCode: %d%n", e.getSQLState(), e.getErrorCode());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
