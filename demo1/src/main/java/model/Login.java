package model;

public class Login {

    private Long id;
    private String username;
    private String passwordHash; // Stockage hashé du mot de passe
    private Long userId;


    public Login() {}

    public Login(Long id, String username, String passwordHash, Long userId) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
