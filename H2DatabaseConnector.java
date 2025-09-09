import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseConnector implements DatabaseInterface {
    private static final String DB_URL = "jdbc:h2:./chatbots.db";
    private Connection connection;

    @Override
    public String connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTableIfNotExists(connection);
            DatabaseMetaData meta = connection.getMetaData();
            return("The driver name is " + meta.getDriverName());
        } catch (SQLException e) {
            return(e.getMessage());
        }
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS chat ("
                + " id INTEGER AUTO_INCREMENT PRIMARY KEY,"
                + " \"user\" VARCHAR(255) NOT NULL,"
                + " prompt VARCHAR(max),"
                + " chat VARCHAR(max) NOT NULL"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public String disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                return("Connection to database closed.");
            }
        } catch (SQLException e) {
            return(e.getMessage());
        }
        return ("Something went wrong while disconnecting.");
    }

    @Override
    public String persistChat(String user, String prompt, String chat) {
        String sql = "INSERT INTO chat(\"user\", prompt, chat) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createTableIfNotExists(conn);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user);
                pstmt.setString(2, prompt);
                pstmt.setString(3, chat);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            return(e.getMessage());
        }
        return ("\n");
    }

    @Override
    public String[][] loadChatData(String user) {
        String sql = "SELECT \"user\", prompt, chat FROM chat WHERE \"user\" = ? ORDER BY id DESC LIMIT 100";
        List<String[]> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createTableIfNotExists(conn);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String[] botData = new String[3];
                    botData[0] = rs.getString("user");
                    botData[1] = rs.getString("prompt");
                    botData[2] = rs.getString("chat");
                    results.add(botData);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results.toArray(new String[0][0]);
    }
}

