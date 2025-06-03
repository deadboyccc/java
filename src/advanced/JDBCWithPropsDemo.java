package advanced;

// import java.beans.Statement; // Removed incorrect import
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.ResultSet;

public class JDBCWithPropsDemo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Path.of("music.properties"),
                    StandardOpenOption.READ));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String albumName = "Tapestry";
        String query = "SELECT * FROM music.albumview WHERE album_name='%s'"
                .formatted(albumName);

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("ServerName"));
        dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
        dataSource.setDatabaseName(properties.getProperty("DatabaseName"));

        try (var connection = dataSource.getConnection(properties.getProperty("user"),
                properties.getProperty("password"));
                Statement statement = connection.createStatement();) {
            System.out.println("Success!");
            ResultSet resultSet = statement.executeQuery(query);

            var metaData = resultSet.getMetaData();
            System.out.println("===".repeat(8));
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.printf("%d %s %s %n",
                        i, metaData.getColumnName(i),
                        metaData.getColumnTypeName(i));

            }System.out.println("====================");

for (int i = 1; i <= metaData.getColumnCount(); i++) {
    System.out.printf("%-15s", metaData.getColumnName(i).toUpperCase());
}
System.out.println();

while (resultSet.next()) {
    for (int i = 1; i <= metaData.getColumnCount(); i++) {
        System.out.printf("%-15s", resultSet.getString(i));
    }
    System.out.println();
}
            // System.out.println("===".repeat(8));
            // while (resultSet.next()) {
            //     System.out.printf("%-5d %-12s %-12s%n",
            //             resultSet.getInt("track_number"),
            //             resultSet.getString("artist_name"), resultSet.getString("song_title"));

            // }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
