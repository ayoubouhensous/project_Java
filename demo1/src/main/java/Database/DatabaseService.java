package Database;


import model.AccessLog;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String DB_URL = "jdbc:sqlite:facial_recognition.db";
    public DatabaseService() {
        initializeDatabase();
    }



    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            createUsersTable(conn);
            createAccessLogsTable(conn);
            createLoginTable(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUsersTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                firstname VARCHAR NOT NULL,
                Lastname VARCHAR NOT NULL,
                embedding BLOB NOT NULL
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    private void createAccessLogsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS access_logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_name TEXT NOT NULL,
                timestamp TIMESTAMP NOT NULL,
                status TEXT NOT NULL
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    private void createLoginTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS login (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                user_id INTEGER NOT NULL
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (firstname,lastname, embedding) VALUES ( ? , ? , ? )";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getLastname());
            pstmt.setBytes(3, user.getEmbedding());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, embedding = ?, authorized = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getLastname());
            pstmt.setBytes(3, user.getEmbedding());
            pstmt.setLong(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(Long userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBytes("embedding")

                );
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countStatusLogs(String status) {
        String sql = "SELECT COUNT(*) FROM access_logs WHERE status = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




    public int countAttemps() {
        String sql = "SELECT COUNT(*) FROM access_logs";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




    public  boolean isValidCredentials(String username, String password) {
        // Remplacez cette logique par l'interrogation de la base de données
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Remplir les paramètres de la requête
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Si un résultat est trouvé, cela signifie que les identifiants sont valides
                return resultSet.next();  // Si une ligne existe, les identifiants sont corrects
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // En cas d'erreur de connexion ou autre, retourner false
        }
    }






}
