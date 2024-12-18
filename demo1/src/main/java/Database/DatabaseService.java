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
                embedding BLOB NOT NULL,
                status String NOT NULL
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
                user_id INTEGER NOT NULL,
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (firstname,lastname, embedding, authorized) VALUES ( ? , ? , ? , ? )";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getFirstname());
            pstmt.setBytes(3, user.getEmbedding());
            pstmt.setString(4, user.getStatus());
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
            pstmt.setString(4, user.getStatus());
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
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBytes("embedding"),
                        rs.getString("status")

                );
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



    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Renvoie true si l'utilisateur est trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void addAccessLog(AccessLog Log) {
        String sql = "INSERT INTO access_logs  (id,username,timestamp,status) VALUES ( ? , ? , ? , ? )";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, Log.getId());
            pstmt.setString(2, Log.getUserName());
            pstmt.setTimestamp(3, Timestamp.valueOf(Log.getTimestamp()));
            pstmt.setString(4, Log.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
