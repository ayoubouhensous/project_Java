package model;

import java.sql.Blob;
import java.time.LocalDateTime;

public class User {

    private Long id;
    private String firstname;
    private String lastname;

    private byte[] embedding; // Encodage facial sous forme de texte ou base64


    public User() {}

    public User( String firstname, String lastname, byte[] embedding) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.embedding = embedding;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public byte[] getEmbedding() {
        return embedding;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmbedding(byte[] embedding) {
        this.embedding = embedding;
    }




}

