package com.example.gfattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// MainActivity implémente l'interface OnClickListener pour gérer les clics sur les boutons
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des éléments de l'interface utilisateur et des gestionnaires
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonForgotPassword;
    private LoginManager loginManager;
    private PasswordRecoveryManager passwordRecoveryManager;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialisation des éléments de l'interface utilisateur
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);

        // Initialisation des gestionnaires de connexion et de récupération de mot de passe
        loginManager = new LoginManager(this);
        passwordRecoveryManager = new PasswordRecoveryManager(this);

        // Ajout des écouteurs d'événements aux boutons
        buttonLogin.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
    }

    // Méthode appelée lorsque l'un des boutons est cliqué
    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            // Récupération des valeurs entrées par l'utilisateur
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Vérification si les champs email et mot de passe sont vides
            if (email.isEmpty() || password.isEmpty()) {
                // Affichage d'un message d'erreur si l'un des champs est vide
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                if (email.matches(emailPattern))
                {
                    // Validation des informations de connexion
                    boolean loginSuccessful = loginManager.validateLogin(email, password);
                    if (loginSuccessful) {
                        // Affichage d'un message de succès si la connexion est réussie
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, HomeManager.class);
                        startActivity(intent);
                    } else {
                        // Affichage d'un message d'erreur si la connexion échoue
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v == buttonForgotPassword) {
            // Récupération de l'email entré par l'utilisateur
            String email = editTextEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                // Appel de la méthode de récupération de mot de passe si l'email n'est pas vide
                passwordRecoveryManager.recoverPassword(email);
            } else {
                // Affichage d'un message d'erreur si l'utilisateur n'a pas saisi d'email
                Toast.makeText(this, "Please enter your email to recover password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
