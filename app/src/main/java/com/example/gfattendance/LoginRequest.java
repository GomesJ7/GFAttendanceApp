package com.example.gfattendance;

// Classe pour la requête de connexion
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String email, String password) {
        this.username = email;
        this.password = password;
    }

    // Getters et setters si nécessaire
}