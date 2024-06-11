package com.example.gfattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Cette classe représente l'activité principale de l'application
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des variables
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonForgotPassword;
    private LoginManager loginManager;

    // Expression régulière pour valider l'email
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // Méthode appelée lors de la création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialisation des variables
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);

        loginManager = new LoginManager(this);

        // Définition des listeners pour les boutons
        buttonLogin.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
    }

    // Méthode appelée lorsqu'un bouton est cliqué
    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            // Récupération de l'email et du mot de passe
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Vérification que l'email et le mot de passe ne sont pas vides
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Vérification que l'email est valide
                if (email.matches(emailPattern)) {
                    // Si l'email est valide, on valide les informations de connexion
                    loginManager.validateLogin(email, password, new LoginManager.LoginCallback() {
                        @Override
                        public void onSuccess(String token) {
                            // Si la connexion est réussie, on affiche un message et on ouvre l'activité HomeActivity
                            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("TOKEN", token);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            // Si la connexion échoue, on affiche un message d'erreur
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Si l'email n'est pas valide, on affiche un message d'erreur
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v == buttonForgotPassword) {
            // Si le bouton "Mot de passe oublié" est cliqué, on ouvre l'activité PasswordRecoveryManager
            Intent intent = new Intent(this, PasswordRecoveryManager.class);
            startActivity(intent);
        }
    }
}