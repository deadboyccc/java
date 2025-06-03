package advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import com.mysql.cj.jdbc.MysqlDataSource;

public class JDBCWithPropsDemo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (var in = Files.newInputStream(Path.of("music.properties"))) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Failed to load properties file.");
            e.printStackTrace();
            return;
        }

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("ServerName"));
        dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
        dataSource.setDatabaseName(properties.getProperty("DatabaseName"));

        String albumName;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Album name: ");
            albumName = scanner.nextLine();
        }

        String query = "SELECT * FROM music.albumview WHERE album_name = ?";

        try (
            Connection connection = dataSource.getConnection(
                properties.getProperty("user"),
                properties.getProperty("password"));
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, albumName);
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                System.out.println("=".repeat(24));
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%d %s %s%n",
                        i, metaData.getColumnName(i), metaData.getColumnTypeName(i));
                }
                System.out.println("=".repeat(24));

                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-15s", metaData.getColumnName(i).toUpperCase());
                }
                System.out.println();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-15s", resultSet.getString(i));
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        }
    }
}
